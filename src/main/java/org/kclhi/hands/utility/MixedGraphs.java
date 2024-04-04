package org.kclhi.hands.utility;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Component;

import java.util.HashMap;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException; 

import java.io.*;
import java.nio.file.*;
import java.util.concurrent.atomic.AtomicReference;
import javax.imageio.ImageIO;

/**
* @author Reuben Atendido
*
*/

/**
* defining a class that can generate graphs that give visual context to the data output after running a simulation.
* also contains some auxiliary methods for finding the complete path of csv file outputs
*/
public class MixedGraphs {

    /** takes the selected file as input and processes it to firstly attain its complete file path
    * directory would be unique to every system so a function was made that is able to determine the
    * correct filepath. The output CSV file is read to extract the following features:
    * infection total for each node, successful rounds, failed rounds.
    */
    public void processData(String selectedFile, String selectedLocation) {
        
        // split input parameter filePath to just the number ID and add csv tag to the end
        // generate the complete filepath of the csv file
        String completeFilePath = getCSVFile(selectedFile);

        // initialise counts hash map to store node visit frequency
        // initialise numberOfNodes to store number of nodes in simulation
        Map<String, Integer> allInfectionFrequencies = new HashMap<>();
        int numberOfNodes = 0;
        int successes = 0;
        int failures = 0;

        try{  
            
            // open up csv file for parsing
            CSVReader reader = new CSVReader(new FileReader(new File(completeFilePath)));
            // System.out.println("File Found.");
            String[] line = reader.readNext();

            /** extract number of nodes in the run from the first line
            * have to iterate through this line as the index of the desired value can
            * vary depending on how many agents are in the config
            */
            for (int i = 0; i < line.length; i++) {
                String entry = line[i];
                if (entry.equals(" {NumberOfNodes")) {
                    String value = line[i+1];
                    if (value.endsWith("}")) {
                        value = value.substring(0, value.length() - 1);
                    }
                    numberOfNodes = Integer.parseInt(value);
                    System.out.println("--------------------------------");
                    System.out.println("Number of nodes: " + numberOfNodes);
                    System.out.println("--------------------------------");
                    break;
                } 
            }

            // iterate through rest of the lines starting with R to extract the paths taken and
            // sum the frequency each node is visited. Count is only added if the success is 1.0
            while ((line = reader.readNext()) != null && line[0].equals("R")) {
                // Assuming that the paths are always in the same columns
                for (int i = 0; i < line.length; i++) { // Adjust the step to match the actual data structure

                    // sum all unsuccessful rounds
                    if (line[i].equals(" 0.0")){
                        failures++;
                    }

                    // sum all successful rounds and sum all successful rounds per terminal node
                    if (line[i].startsWith(" Path")&& line[i+3].equals(" 1.0")) {
                        successes++;
                        String nodesVisited = line[i + 1]; // Assuming the actual path data is in the next column
                        nodesVisited = nodesVisited.substring(1, nodesVisited.length() - 1); // remove the curly braces
                        String[] nodes = nodesVisited.split(" "); // split the line into items
                        String node = nodes[nodes.length - 1];
                        String[] parts = node.split("="); // split the item into variable and frequency
                        String variable = parts[0];
                        allInfectionFrequencies.put(variable, 
                        allInfectionFrequencies.getOrDefault(variable, 0) + 1);
                    }
                }
            }
            
            float infectionPercentage = ((float)successes/(successes+failures))*100;

            // print some contextual info into the terminal
            System.out.println("================================================");

            System.out.println("Selected location: "+ selectedLocation);
            System.out.println("Node infection frequency:" + allInfectionFrequencies);
            System.out.println("Successful infections: "+ successes);
            System.out.println("Failed infections: "+ failures);
            System.out.println("Infection percentage: "+infectionPercentage+"%");
            System.out.println("================================================");

            reader.close();
        } catch (IOException|CsvValidationException u){
            System.out.println("Something went wrong when reading the CSV File.");
            u.printStackTrace();
        }
        
        // pass extracted data into graph generation function
        generateGraphs(selectedFile, numberOfNodes, allInfectionFrequencies, successes, failures);
        
    }

    // generates and displays a circular heat map and pie chart from the extracted data
    private void generateGraphs(String selectedFile, int numberOfNodes, 
            Map<String, Integer> allInfectionFrequencies, int successes, int failures){
        // create JFrame to display graphs
        String[] selectedFileSplit = selectedFile.split("/");
        String fileName = selectedFileSplit[1]+".csv";
        JFrame frame = new JFrame("Circular Heatmap for "+fileName);
        JTable table = new JTable(numberOfNodes, numberOfNodes);
        
        // iterates through the node counts and sets the value to the corresponding cell for circular heatmap
        for (int i = 0; i < numberOfNodes; i++) {
            Integer value = allInfectionFrequencies.get("v" + i);
            if (value != null) {
                table.setValueAt(value/25, i, i); //scale down value otherwise every cell would be fully filled
            } else {
                table.setValueAt(0, i, i);
            }
        }

        // barchart generation
        // DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        // //populate dataset with infection frequency hashmap values
        // for (int i = 0; i < numberOfNodes; i++) {
        //     Integer value = allInfectionFrequencies.get("v" + i);
        //     int chartValue = (value != null) ? value : 0;
        //     dataset.addValue(chartValue, "Frequency", "v" + i);
        // }
    
        // creating a chart with the dataset
        // JFreeChart chart = ChartFactory.createBarChart(
        //     "Infection Frequencies", // chart title
        //     "Locations (Nodes)", // domain axis label
        //     "Frequency", // range axis label
        //     dataset, // data
        //     PlotOrientation.VERTICAL, // orientation
        //     true, // include legend
        //     true, 
        //     false 
        // );

        // piechart generation
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Successes", successes);
        dataset.setValue("Failures", failures);

        // Create the chart
        JFreeChart chart = ChartFactory.createPieChart(
            "Infection Success vs Failure", // chart title
            dataset, // dataset
            true, // include legend
            true,
            false
        );
        
        // set pie chart colours
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Successes", Color.BLUE);
        plot.setSectionPaint("Failures", Color.RED);
        
        // create split pane and add heatmap and pie chart to it
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
            new JScrollPane(table), new ChartPanel(chart));
        splitPane.setResizeWeight(0.5);
        splitPane.setOneTouchExpandable(true);
        
        // set heatmap headers to respective node names
        for (int i = 0; i < numberOfNodes; i++) {
            table.getColumnModel().getColumn(i).setHeaderValue("v" + i);
        }

        // Update the table header with the new column names
        table.getTableHeader().repaint();

        // Add the split pane to the frame
        frame.setContentPane(splitPane);

        // set how the table looks
        table.setDefaultRenderer(Object.class, new HeatmapCellRenderer());
        table.setRowHeight(50);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setBackground(Color.LIGHT_GRAY);
        table.setShowGrid(true);
        table.setGridColor(Color.BLACK);
        // pack and show frame
        frame.pack();
        frame.setVisible(true);
    }

    // function that returns the complete filepath for the given CSV file respective
    // to the user's home directory 
    public String getFilePath(String fileName) {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        AtomicReference<String> filePath = new AtomicReference<>("");

        try {
            Files.walk(currentPath)
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    if (path.getFileName().toString().equals(fileName)) {
                        filePath.set(path.toString());
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath.get();
    }

    // function that returns the file name plus CSV tag for the selected file and its complete filepath
    public String getCSVFile(String selectedFile){
        String[] selectedFileSplit = selectedFile.split("/");
        String fileName = selectedFileSplit[1]+".csv";
        String completePath = getFilePath(fileName);

        // System.out.println(completePath);
        return completePath;
    }

    // table renderer
    private static class HeatmapCellRenderer implements TableCellRenderer {

        private final DotRenderer renderer = new DotRenderer();
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                boolean hasFocus, int row, int column) {

            if (value instanceof Integer) {
                this.renderer.setValue((Integer) value);
                return this.renderer;
            }
            return null;
        }
    }

    // dot renderer that sets the colour and oval shape of the heat map cells when given values
    private static class DotRenderer extends JComponent {
        private int value;

        public void setValue(int value) {
            this.value = value;
        }

        @Override
        protected void paintComponent(Graphics g) {

            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(Color.RED);
            int centerX = this.getWidth() / 2;
            int centerY = this.getHeight() / 2;
            g.fillOval(centerX - this.value, centerY - this.value, this.value * 2, this.value * 2);

        }

    }

}
