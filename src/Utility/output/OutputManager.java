package Utility.output;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.TreeMap;

import Utility.ComparatorResult;
import Utility.Metric;
import Utility.Utils;
import Utility.output.gnuplot.GNU3DGraph;
import Utility.output.gnuplot.GNUBarGraph;
import Utility.output.gnuplot.GNUGraph;
import Utility.output.gnuplot.GNULineGraph;

/**
 * @author Martin
 */
public class OutputManager {
	
	/**
	 * 
	 */
	private static final boolean OUTPUT_ENABLED = false;
	
	/**
	 * Multiple hiders per file, and multiple files.
	 */
	private ArrayList<ArrayList<HiderRecord>> fileHiderRecords;
	
	/**
	 * @return
	 */
	public ArrayList<ArrayList<HiderRecord>> getFileHiderRecords() {
		
		return fileHiderRecords;
		
	}
	
	/**
	 * 
	 */
	public OutputManager() {
		
		fileHiderRecords = new ArrayList<ArrayList<HiderRecord>>();
		
	}
	
	/**
	 * @return
	 */
	public ArrayList<Path> availableFiles() {
		
		ArrayList<Path> files = listFilesForFolder(new File(FILEPREFIX + "data"));
		
		ArrayList<Path> availableFiles = new ArrayList<Path>(files);
		
		if (files.size() == 0) {
			
			return new ArrayList<Path>();
			
		}
		
		// For each file in the directory
		for ( Path path : files ) {
			
			// If it is a .csv file (what we want):
			if (!path.toString().substring(path.toString().length() - 3, path.toString().length()).equals("csv")) {
			
				availableFiles.remove(path);
				
			}
			
		}
		
		return availableFiles;
		
	}
			
	/**
	 * 
	 */
	public void processAllOutput() {
		
		fileHiderRecords.clear();
		
		for ( Path path : availableFiles() ) {
			
			processOutput(path);
			
		}
	
	}
	
	/**
	 * @param path
	 */
	public void processIndividualOutput(Path path) {
		
		fileHiderRecords.clear();
		
		processOutput(path);
		
	}
	
	/**
	 * 
	 */
	public void processOutput(Path path) {
		
		ArrayList<HiderRecord> hiderRecords = new ArrayList<HiderRecord>();
		
		ArrayList<String> lines = Utils.readFromFile(path.toString());
		
		String parameters = lines.remove(0);
		
		String topology = "";
		
		for ( String parameter : parameters.split(" ") ) {
			
			String[] keyAndValue = parameter.split(",");
			
			if ( keyAndValue[0].replace("{", "").equals("Topology") ) {
				
				topology = keyAndValue[1].replace("}", "");
				
			}
			
		}
		
		for ( String line : lines ) {
		
			if ( Utils.DEBUG ) Utils.printSystemStats();
			
			String lastHider = "";
			
			String lastSeeker = "";
			
			String lastTraverser = "";
			
			String lastAttribute = "";
			
			String gameOrRound = "";
			
			for ( String word : line.split(",")) {
				
				word = word.trim();
				
				try {
					
					Double value = Double.parseDouble(word);
					
					if ( lastTraverser.equals("hider") ) {
					
						hiderRecords.get(hiderRecords.indexOf(new HiderRecord(lastHider))).attribute(gameOrRound, lastAttribute, value);
						
					} else if ( lastTraverser.equals("seeker") ) {
					
						hiderRecords.get(hiderRecords.indexOf(new HiderRecord(lastHider))).getSeeker(lastSeeker).attribute(gameOrRound, lastAttribute, value);
						
					}

				} catch (NumberFormatException e) { 
					
					// If we come across an entry for a Hider
					if ( word.charAt(0) == 'h') {
					
						// Mixing, and first time round
						if ( parameters.contains("{MixHiders,true}") ) {
							
							// If mixing, only ever one hide record, for all hiders
							if ( (hiderRecords.size() == 0) ) {
							
								hiderRecords.add(new HiderRecord(path, "MixedHiderStrats"));
								
								hiderRecords.get(hiderRecords.size() - 1 ).setTopology(topology);
								
								hiderRecords.get(hiderRecords.size() - 1 ).setParameters(parameters);
								
							
							}
							
							lastHider = hiderRecords.get(0).getTraverser();
							
						} else {
							
							// If we do not yet have a record for this hider
							if (!hiderRecords.contains(new TraverserRecord(word))) {
								
								// Create it
								hiderRecords.add(new HiderRecord(path, word));
								
								hiderRecords.get(hiderRecords.size() - 1 ).setTopology(topology);
								
								hiderRecords.get(hiderRecords.size() - 1 ).setParameters(parameters);
								
							}
							
							lastHider = word;
							
						}
						
						lastTraverser = "hider";
						
					// If we come across an entry for a Seeker
					} else if ( word.charAt(0) == 's' ) {
						
						if ( parameters.contains("{MixSeekers,true}") ) { 
							
							if ( hiderRecords.get(hiderRecords.indexOf(new HiderRecord(lastHider))).getSeekersAndAttributes().size() == 0 ) {
								
								hiderRecords.get(hiderRecords.indexOf(new HiderRecord(lastHider))).addSeeker(new TraverserRecord("MixedSeekerStrats"));
								
								hiderRecords.get(hiderRecords.indexOf(new HiderRecord(lastHider))).getSeeker("MixedSeekerStrats").setTopology(topology);
							
							}
							
							lastSeeker = hiderRecords.get(hiderRecords.indexOf(new HiderRecord(lastHider))).getSeeker("MixedSeekerStrats").getTraverser();
							
						} else {
							
							// If the last hider doesn't have a record of this seeker, add it
							if (!hiderRecords.get(hiderRecords.indexOf(new HiderRecord(lastHider))).containsSeeker(new TraverserRecord(word))) {
									
								hiderRecords.get(hiderRecords.indexOf(new HiderRecord(lastHider))).addSeeker(new TraverserRecord(word));
								
								hiderRecords.get(hiderRecords.indexOf(new HiderRecord(lastHider))).getSeeker(word).setTopology(topology);
							
							}
							
							lastSeeker = word;
							
						}
						
						lastTraverser = "seeker";
					
					} else if ( word.equals("G") ) {
					
						gameOrRound = "Game";
						
					} else if ( word.equals("R") ) {
						
						gameOrRound = "Round";
						
					// If we come across an attribute entry
					} else {
					
						lastAttribute = word;
						
					}
				
				}
				
			}
			
		}
		
		fileHiderRecords.add(hiderRecords);
	
	}
	
	/**
	 * @param traversers
	 * @param title
	 * @param attribute
	 */
	public void showLineGraphForAttribute(ArrayList<TraverserRecord> traversers, String gameOrRound, String title, String attribute) {
		
		if ( gameOrRound.equals("Game") ) {
			
			showGraphForAttribute(traversers, gameOrRound, title, "Line", "Game Number", attribute, "");
			
		} else if ( gameOrRound.equals("Round") ) {
			
			showGraphForAttribute(traversers, gameOrRound, title, "Line", "Round Number", attribute, "");
			
		}
		
	}

	/**
	 * @param traversers
	 * @param title
	 * @param attribute
	 */
	public void showBarGraphForAttribute(ArrayList<TraverserRecord> traversers, String gameOrRound, String title, String attribute, String category) {
		
		showGraphForAttribute(traversers, gameOrRound, title, "Bar", "Game Number", attribute, category);
		
	}
	
	/**
	 * @param traversers
	 * @param gameOrRound
	 * @param title
	 * @param attribute
	 * @param category
	 */
	public void show3DGraphForAttribute(ArrayList<TraverserRecord> traversers, String gameOrRound, String title, String attribute, String category) {
		
		showGraphForAttribute(traversers, gameOrRound, title, "3D", "Game Number", attribute, category);
		
	}
	
	/**
	 * @param traverserRecords
	 * @return
	 */
	private ArrayList<TraverserRecord> expandTraverserRecords( ArrayList<TraverserRecord> traverserRecords ) {
		
		ArrayList<TraverserRecord> localTraverserRecords = new ArrayList<TraverserRecord>();
		
		for ( TraverserRecord traverserRecord : traverserRecords ) {
			
			localTraverserRecords.add(traverserRecord);
			
			if ( traverserRecord instanceof HiderRecord ) {
				
				localTraverserRecords.addAll(((HiderRecord)traverserRecord).getSeekersAndAttributes());
				
			}
			
		}
	
		return localTraverserRecords;
			
	}
	
	/**
	 * @param traverser
	 * @param gameOrRound
	 * @return
	 */
	private ArrayList<Entry<AttributeSetIdentifier, Hashtable<String,Double>>> getTraverserSeries(TraverserRecord traverser, String gameOrRound) {
		
		if ( gameOrRound.equals("Game") ) {
			
			return new ArrayList<Entry<AttributeSetIdentifier, Hashtable<String,Double>>>(traverser.getGameSeries().entrySet());
			
		} else if ( gameOrRound.equals("Round") ) {
			
			return new ArrayList<Entry<AttributeSetIdentifier, Hashtable<String,Double>>>(traverser.getRoundSeries().entrySet());
			
		} else {
			
			return new ArrayList<Entry<AttributeSetIdentifier, Hashtable<String,Double>>>();
			
		}
		
	}
	
	/**
	 * @param traverserRecords
	 * @return
	 */
	private Hashtable<String, Double> maxForAttributeInAllSeries( ArrayList<TraverserRecord> traverserRecords, String gameOrRound ) {
		
		Hashtable<String, Double> maxAttributeToValueAllSeries = new Hashtable<String, Double>();
		
		ArrayList<TraverserRecord> localTraverserRecords = expandTraverserRecords(traverserRecords);
		
		for ( TraverserRecord traverserRecord : localTraverserRecords ) {
			
			for ( Entry<AttributeSetIdentifier, Hashtable<String,Double>> seriesEntry : getTraverserSeries(traverserRecord, gameOrRound) ) {
				
				for (Entry<String, Double> attributeToValueEntry : seriesEntry.getValue().entrySet()) {
					
					if ( !maxAttributeToValueAllSeries.containsKey(attributeToValueEntry.getKey()) ) {
						
						maxAttributeToValueAllSeries.put(attributeToValueEntry.getKey(), attributeToValueEntry.getValue());
						
					} else {
						
						if ( attributeToValueEntry.getValue() > maxAttributeToValueAllSeries.get(attributeToValueEntry.getKey())) {
							
							maxAttributeToValueAllSeries.put(attributeToValueEntry.getKey(), attributeToValueEntry.getValue());
							
						}
						
					}
					
				}
				
			}
		
		}
		
		return maxAttributeToValueAllSeries;
		
	}
	
	/**
	 * @param traverserRecords
	 * @return
	 */
	private Hashtable<String, Double> minForAttributeInAllSeries( ArrayList<TraverserRecord> traverserRecords, String gameOrRound ) {
		
		Hashtable<String, Double> minAttributeToValueAllSeries = new Hashtable<String, Double>();
		
		ArrayList<TraverserRecord> localTraverserRecords = expandTraverserRecords(traverserRecords);
		
		for ( TraverserRecord traverserRecord : localTraverserRecords ) {
		
			for ( Entry<AttributeSetIdentifier, Hashtable<String,Double>> seriesEntry : getTraverserSeries(traverserRecord, gameOrRound) ) {
				
				for (Entry<String, Double> attributeToValueEntry : seriesEntry.getValue().entrySet()) {
					
					if (!minAttributeToValueAllSeries.containsKey(attributeToValueEntry.getKey())) {
						
						minAttributeToValueAllSeries.put(attributeToValueEntry.getKey(), attributeToValueEntry.getValue());
						
					} else {
						
						if ( attributeToValueEntry.getValue() < minAttributeToValueAllSeries.get(attributeToValueEntry.getKey())) {
							
							minAttributeToValueAllSeries.put(attributeToValueEntry.getKey(), attributeToValueEntry.getValue());
							
						}
						
					}
					
				}
				
			}
		
		}
		
		return minAttributeToValueAllSeries;
		
	}
	
	/**
	 * @param entries
	 * @param minInSeries
	 * @param maxInSeries
	 * @return
	 */
	private double normaliseEntry(Hashtable<String, Double> entries, Hashtable<String, Double> minInSeries,  Hashtable<String, Double> maxInSeries) {
		
		return ( entries.get(Metric.COST.getText()) - minInSeries.get(Metric.COST.getText()) ) / ( maxInSeries.get(Metric.COST.getText()) - minInSeries.get(Metric.COST.getText()) );
		
	}
	
	/**
	 * @param traverserRecords
	 * @param title
	 * @param graphType
	 * @param attribute
	 */
	public void showGraphForAttribute(ArrayList<TraverserRecord> traverserRecords, String gameOrRound, String title, String graphType, String xLabel, String yLabel, String category) {
		
		//TraverserGraph graph = null;
		GNUGraph graph = null;
		
		if ( title.length() > 200 ) title = title.substring(0, 200);
		
		Hashtable<String, Double> minForAttributeInAllSeries = minForAttributeInAllSeries(traverserRecords, gameOrRound);
		
		Hashtable<String, Double> maxForAttributeInAllSeries = maxForAttributeInAllSeries(traverserRecords, gameOrRound);
		
		if (graphType.equals("Line") || graphType.equals("3D")) {
		
			if (graphType.equals("Line")) {
			
				//graph = new LineGraph(title);
				graph = new GNULineGraph(title);
			
			} else if (graphType.equals("3D")) {
				
				graph = new GNU3DGraph(title);
				
			}
			
			Hashtable<String, ArrayList<ArrayList<Double>>> multipleAttributeToValues = new Hashtable<String, ArrayList<ArrayList<Double>>>();
			
			for ( TraverserRecord traverser : traverserRecords ) {
				
				String traverserName = traverser.toString();
				
				if (traverserName.contains("-")) traverserName = traverserName.substring(0, traverserName.indexOf("-"));
				
				if ( !multipleAttributeToValues.containsKey(traverserName)) multipleAttributeToValues.put(traverserName, new ArrayList<ArrayList<Double>>());
				
				ArrayList<Double> attributeToValues = new ArrayList<Double>();
				
				int seriesNumber = 0;
				
				for ( Entry<AttributeSetIdentifier, Hashtable<String,Double>> seriesEntry : getTraverserSeries(traverser, gameOrRound) ) {
					
					if ( yLabel.contains("Score" ) ) {
						
						if ( traverser instanceof HiderRecord ) {
							
							double cumulativeNormalisedSeekerCosts = 0.0;
							
							for ( TraverserRecord hidersSeeker : ((HiderRecord)traverser).getSeekersAndAttributes()) {
								
								cumulativeNormalisedSeekerCosts += normaliseEntry(getTraverserSeries(hidersSeeker, gameOrRound).get(seriesNumber).getValue(), minForAttributeInAllSeries, maxForAttributeInAllSeries );
								
							}
							
							// Score = (Average) Seeker(s) cost - Hider cost.
							attributeToValues.add( ( cumulativeNormalisedSeekerCosts / (double)((HiderRecord)traverser).getSeekersAndAttributes().size() ) - normaliseEntry(seriesEntry.getValue(), minForAttributeInAllSeries, maxForAttributeInAllSeries) );
							
						} else {
							
							attributeToValues.add( -1 * normaliseEntry(seriesEntry.getValue(), minForAttributeInAllSeries, maxForAttributeInAllSeries) );
						
						}
						
					} else {
					
						attributeToValues.add( seriesEntry.getValue().get(yLabel)  );
					
						
					}
					
					seriesNumber++;
					
				}
				
				multipleAttributeToValues.get(traverserName).add(attributeToValues);
				
				if (graphType.equals("Line")) {
					
					((GNULineGraph) graph).addDataset(traverserName, attributeToValues);
					
				}
				
			}
			
			if (graphType.equals("3D")) {
				
				String traverser = traverserRecords.get(0).getTraverser();
				
				// Extrapolate those values that will not 'fill' the entire 3D area to do so.
				
				int maxRows = Integer.MIN_VALUE;
				ArrayList<ArrayList<ArrayList<Double>>> datasets = new ArrayList<ArrayList<ArrayList<Double>>>();
				
				for ( Entry<String, ArrayList<ArrayList<Double>>> individualAttributeToValues : multipleAttributeToValues.entrySet()) {
					
					if ( individualAttributeToValues.getValue().size() > maxRows ) maxRows = individualAttributeToValues.getValue().size();
					
					datasets.add(individualAttributeToValues.getValue());
					
				}
				
				for ( ArrayList<ArrayList<Double>> dataset : datasets ) {
					
					if (dataset.size() < maxRows) {
						
						for ( int i = 1; i < maxRows; i ++) {
							
							dataset.add(dataset.get(0));
							
						}
						
					}
					
					((GNU3DGraph) graph).addDataset(traverserRecords.get(0).getTraverser(), dataset);
					
				}
				
				((GNU3DGraph) graph).setZAxisLabel(yLabel);
				
				traverser = traverser.substring(traverser.indexOf("Variable"), traverser.length());
				
				String[] labels = traverser.split("Variable");
				
				for ( int i = 0; i < labels.length; i++ ) {
					
					if ( labels[i].contains("-") ) {
						
						labels[i] = labels[i].substring(0, labels[i].indexOf("-"));
						
					}
					
				}
				
				if (labels.length > 2 ) {
					
					yLabel = labels[1]; 
				
					xLabel = labels[2];
				
				}
				
			}
			
		} else if (graphType.equals("Bar")) {
			
			graph = new GNUBarGraph(title);
			
			TreeMap<String, ArrayList<Entry<TraverserRecord, Double>>> categoryToTraverserAndData = new TreeMap<String, ArrayList<Entry<TraverserRecord, Double>>>();

			ArrayList<Entry<TraverserRecord, Double>> traverserAndData = new ArrayList<Entry<TraverserRecord, Double>>();
			
			/* Sort so that check for new category is accurate (i.e. last category is
			 * definitely exhausted).
			 */
			if ( category.equals("Topology") ) {
			
				Collections.sort(traverserRecords, new Comparator<TraverserRecord>() {
	
					@Override
					public int compare(TraverserRecord o1, TraverserRecord o2) {
						
						if ( o1.getTopology().compareTo(o2.getTopology()) == ComparatorResult.AFTER ) {
							
							return ComparatorResult.AFTER;
							
						} else if ( o1.getTopology().compareTo(o2.getTopology()) == ComparatorResult.BEFORE ) {
							
							return ComparatorResult.BEFORE;
							
						} else {
							
							return ComparatorResult.EQUAL;
							
						}
						
					}
					
				});
			
			} else {
				
				Collections.sort(traverserRecords);
				
			}
			
			String localCategory = "";
			
			for ( TraverserRecord traverser : traverserRecords ) {
				
				Utils.talk(this.toString(), "Processing " + traverser);
				
				//if ( traverser instanceof HiderRecord ) ((HiderRecord)traverser).switchShowSeekers();
				
				if ( category.equals("Topology") ) {
				
					if ( !traverser.getTopology().equals(localCategory) ) traverserAndData.clear(); 
					
					localCategory = traverser.getTopology();
					
				} else if ( category.equals("Player") ) {
					
					if ( !traverser.getCategory().equals(localCategory) ) traverserAndData.clear(); 
					
					localCategory = traverser.getCategory();
					
				}
				
				if ( yLabel.contains("Score") ) {
					
					/* 
					 * May need to calculate score for individual games, and then take average of score,
					 * rather than calculating score for all games, through average of cost, in order
					 * to calculate stats. for scores.
					 */
					
					if ( traverser instanceof HiderRecord ) {
						
						double cumulativeNormalisedSeekerCost = 0.0;
						
						for ( TraverserRecord hidersSeeker : ((HiderRecord)traverser).getSeekersAndAttributes()) {
							
							cumulativeNormalisedSeekerCost += hidersSeeker.getAttributeToGameAverage(Metric.COST.getText(), minForAttributeInAllSeries, maxForAttributeInAllSeries );
							
						}
						
						traverserAndData.add(new AbstractMap.SimpleEntry<TraverserRecord, Double>(traverser, ( cumulativeNormalisedSeekerCost / (double)((HiderRecord)traverser).getSeekersAndAttributes().size() ) - traverser.getAttributeToGameAverage(Metric.COST.getText(), minForAttributeInAllSeries, maxForAttributeInAllSeries )));
						
					} else {
						
						traverserAndData.add(new AbstractMap.SimpleEntry<TraverserRecord, Double>(traverser, -1 * traverser.getAttributeToGameAverage(Metric.COST.getText(), minForAttributeInAllSeries, maxForAttributeInAllSeries )));
						
					}
					
				} else {
				
					traverserAndData.add(new AbstractMap.SimpleEntry<TraverserRecord, Double>(traverser, traverser.getAverageGameAttributeValue(yLabel)));	
					
				}

				categoryToTraverserAndData.put(localCategory, new ArrayList<Entry<TraverserRecord, Double>>(traverserAndData));
				
			}
			
			// ~MDC 19/8 Could be neater
			for ( Entry<String, ArrayList<Entry<TraverserRecord, Double>>> storedTraverserAndData : categoryToTraverserAndData.entrySet() ) {
			
				System.out.println("Adding bar: " + storedTraverserAndData.getValue() + " " + storedTraverserAndData.getKey());
				
				Hashtable<TraverserRecord, String> traverserToSignificanceClass = new Hashtable<TraverserRecord, String>();
				
				if ( yLabel.equals(Metric.COST.getText()) || yLabel.equals(Metric.SCORE.getText()) ) {
					
					outer:
					for ( Entry<TraverserRecord, Double> traverserA : storedTraverserAndData.getValue() ) {
				
						double cumulativeP = 0.0;
						
						traverserToSignificanceClass.put(traverserA.getKey(), "");
						
						for ( Entry<TraverserRecord, Double> traverserB : storedTraverserAndData.getValue() ) {
							
							if ( traverserA.getKey() == traverserB.getKey() ) continue;
							
							if ( yLabel.equals(Metric.COST.getText()) ) {
							
								if ( traverserA.getKey().pGroup(traverserB.getKey(), Metric.COST).equals("") ) continue outer;
								
								double pValue = traverserA.getKey().pValue(traverserB.getKey(), Metric.COST);
								
								Utils.talk(this.toString(), traverserA.getKey() + " vs " + traverserB.getKey() + " :" + pValue);
								
								cumulativeP += pValue;
								
							} else if ( yLabel.equals(Metric.SCORE.getText()) ) {
								
								if ( traverserA.getKey().pGroup(traverserB.getKey(), minForAttributeInAllSeries, maxForAttributeInAllSeries ).equals("") ) continue outer;
								
								double pValue = traverserA.getKey().pValue(traverserB.getKey(), minForAttributeInAllSeries, maxForAttributeInAllSeries );
								
								Utils.talk(this.toString(), traverserA.getKey() + " vs " + traverserB.getKey() + " :" + pValue);
								
								cumulativeP += pValue;
								
							}
							
						}
						
						Utils.talk(this.toString(), "Average pValue against other traversers (" + storedTraverserAndData.getValue().size() + "): " + ( cumulativeP / storedTraverserAndData.getValue().size() ));
								
						traverserToSignificanceClass.put(traverserA.getKey(), StatisticalTest.getPGroup(cumulativeP / storedTraverserAndData.getValue().size()));
						
						Utils.talk(this.toString(), "Significance class of this value: " + traverserToSignificanceClass);
						
					}
				
				}
				
				((GNUBarGraph) graph).addBars(storedTraverserAndData.getValue(), storedTraverserAndData.getKey(), traverserToSignificanceClass);
			
			}
			
			xLabel = "Strategy";
			
		}
		
		graph.styleGraph();

		String outputPath = "figure" + new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
		
		graph.createChart("", xLabel, yLabel);
		
		if ( OUTPUT_ENABLED ) {
		
			graph.exportChartAsEPS(Utils.FILEPREFIX + "data/charts/" + outputPath + ".eps");
			
			graph.exportChartAsTikz(Utils.FILEPREFIX + "data/charts/" + outputPath + ".tex");
		
		}
		
		try {
		
			Utils.writeToFile(new FileWriter(Utils.FILEPREFIX + "/data/charts/figures.bib", true), "\n @FIG{" + outputPath + ", main = {}, add = { " + title + " }, file = {/Users/Martin/Dropbox/workspace/SearchGames/output/data/charts/" + outputPath + "}, source = {}}");
		
		} catch (IOException e) {
			
			e.printStackTrace();
		
		}
		
		/*graph.pack();
		
		RefineryUtilities.centerFrameOnScreen(graph);
		 
		graph.setVisible(true);*/
		
	} 
	
	/**
	 * @return
	 */
	public String printAllStats() {
		
		String returner = "";
		
		for ( ArrayList<HiderRecord> hiderRecords : fileHiderRecords ) {
			
			for ( HiderRecord hiderRecord : hiderRecords ) {
				
				returner += "\n" + hiderRecord.getTopology();
				
				returner += "\n" + hiderRecord.printStats();
				
			}
			
		}
		
		return returner;
		
	}
	
	/**
	 * 
	 */
	public void removeAllOutputFiles() {
		
		// Archive instead
		for ( Path path : listFilesForFolder(new File(FILEPREFIX + "/data")) ) { 
			
			File archivedFile = new File(FILEPREFIX + "/dataArchive/" + path.getFileName());
			
			moveFile(path, Paths.get(archivedFile.getAbsolutePath()));
		
			// deleteFile(path);
			
		}
			
		for ( Path path : listFilesForFolder(new File(FILEPREFIX + "/data/js/data")) ) deleteFile(path);
		
	}
	
	/**
	 * Remove relating .js and .html files if .csv have been removed
	 */
	public void removeOrphaned() {
		
		ArrayList<String> CSVIDs = new ArrayList<String>();
		
		for ( Path path : listFilesForFolder(new File(FILEPREFIX + "/data")) ) { 
			
			// If this is a .csv file, track its ID
			if ( path.toString().substring(path.toString().lastIndexOf('.'), path.toString().length()).equals(".csv")) {
				
				CSVIDs.add(path.toString().substring(path.toString().lastIndexOf('/') + 1, path.toString().lastIndexOf('.')));
			
			}
		    
		}
		
		// For all .html files, if no corresponding .csv ID recorded, remove.
		for ( Path path : listFilesForFolder(new File(FILEPREFIX + "/data")) ) { 
			
			if ( path.toString().contains("-") && !CSVIDs.contains( path.toString().substring(path.toString().lastIndexOf('/') + 1, path.toString().lastIndexOf('-')) )) {
				
				deleteFile(path);
				
			}
		
		}
		
		// Similar for .js files
		for ( Path path : listFilesForFolder(new File(FILEPREFIX + "/data/js/data")) ) {
			
			if ( path.toString().contains("-") && !CSVIDs.contains( path.toString().substring(path.toString().lastIndexOf('/') + 1, path.toString().lastIndexOf('-')) )) {
				
				deleteFile(path);
				
			}
			
		}
		
		
	}
	
	/**
	 * 
	 */
	private final static String FILEPREFIX = "Output/";
	
	/**
	 * @param source
	 * @param target
	 */
	public void moveFile(Path source, Path target) {
		
		try {
			
			Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		
		}
		
	}
	
	/**
	 * @param path
	 */
	public void deleteFile(Path path) {
		
		try {
		    
			Files.delete(path);
		    
		} catch (NoSuchFileException x) {
		    
			System.err.format("%s: no such" + " file or directory%n", path);
		
		} catch (DirectoryNotEmptyException x) {
		
			System.err.format("%s not empty%n", path);
		
		} catch (IOException x) {
		
			// File permission problems are caught here.
		    System.err.println(x);
		}
	
	}
	
	/**
	 * @param folder
	 */
	private ArrayList<Path> listFilesForFolder(final File folder) {
		
		ArrayList<Path> files = new ArrayList<Path>();
		
	    for (final File fileEntry : folder.listFiles()) {
	    	
	        if (!fileEntry.isDirectory()) {
	        
	        	files.add(Paths.get(fileEntry.getAbsolutePath()));
	        
	        }
	        
	    }
	    
	    return files;
	    
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		
		return "OutputManager";
		
	}

}