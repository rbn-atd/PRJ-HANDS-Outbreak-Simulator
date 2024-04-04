package org.kclhi.hands;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.kclhi.hands.utility.Pair;
import org.kclhi.hands.utility.Utils;
import org.kclhi.hands.utility.adaptive.AdaptiveMeasure;
import org.kclhi.hands.utility.adaptive.AdaptiveWeightings;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;
import org.kclhi.hands.hider.AdaptiveHider;
import org.kclhi.hands.hider.AdaptiveHidingAgent;
import org.kclhi.hands.hider.Hider;
import org.kclhi.hands.hider.repeatgame.bias.FixedStartVariableBias;
import org.kclhi.hands.hider.repeatgame.bias.VariableBias;
import org.kclhi.hands.hider.repeatgame.bias.VariableBiasLocations;
import org.kclhi.hands.hider.repeatgame.bias.VariableBiasStaticBetween;
import org.kclhi.hands.hider.repeatgame.deceptive.Deceptive;
import org.kclhi.hands.hider.repeatgame.deceptive.DeceptiveNew;
import org.kclhi.hands.hider.repeatgame.deceptive.EpsilonDeceptive;
import org.kclhi.hands.hider.repeatgame.deceptive.GroupedDeceptive;
import org.kclhi.hands.hider.repeatgame.deceptive.LeastConnectedDeceptive;
import org.kclhi.hands.hider.repeatgame.random.UniqueRandomSet;
import org.kclhi.hands.hider.repeatgame.random.UniqueRandomSetRepeat;
import org.kclhi.hands.hider.repeatgame.random.UniqueRandomSetRepeatRandomNodes;
import org.kclhi.hands.hider.repeatgame.random.adaptable.RandomSetAdaptable;
import org.kclhi.hands.hider.repeatgame.random.adaptable.UniqueRandomSetRepeatAdaptable;
import org.kclhi.hands.hider.repeatgame.random.automatic.AutomaticUniqueRandomSetRepeat;
import org.kclhi.hands.hider.singleshot.cost.FixedStartVariableGreedy;
import org.kclhi.hands.hider.singleshot.cost.VariableGreedy;
import org.kclhi.hands.hider.singleshot.cost.VariableGreedyStaticBetween;
import org.kclhi.hands.hider.singleshot.distance.GreedyRandomFixedDistance;
import org.kclhi.hands.hider.singleshot.distance.GreedyRandomFixedDistanceStaticBetween;
import org.kclhi.hands.hider.singleshot.distance.GreedyVariableFixedDistance;
import org.kclhi.hands.hider.singleshot.distance.RandomFixedDistance;
import org.kclhi.hands.hider.singleshot.distance.RandomFixedDistanceFixedStart;
import org.kclhi.hands.hider.singleshot.distance.RandomFixedDistanceStaticBetween;
import org.kclhi.hands.hider.singleshot.distance.VariableFixedDistance;
import org.kclhi.hands.hider.singleshot.distance.VariableFixedDistanceFixedStart;
import org.kclhi.hands.hider.singleshot.distance.VariableFixedDistanceStaticBetween;
import org.kclhi.hands.hider.singleshot.preference.LeastConnected;
import org.kclhi.hands.hider.singleshot.preference.LeastConnectedLeastConnectedFirst;
import org.kclhi.hands.hider.singleshot.preference.LeastConnectedStaticBetween;
import org.kclhi.hands.hider.singleshot.preference.MaxDistance;
import org.kclhi.hands.hider.singleshot.preference.MaxDistanceStaticBetween;
import org.kclhi.hands.hider.singleshot.preference.NotConnected;
import org.kclhi.hands.hider.singleshot.preference.adaptable.LeastConnectedAdaptable;
import org.kclhi.hands.hider.singleshot.random.GreedyRandomSet;
import org.kclhi.hands.hider.singleshot.random.GreedyRandomSetStaticBetween;
import org.kclhi.hands.hider.singleshot.random.Random;
import org.kclhi.hands.hider.singleshot.random.RandomFixedStart;
import org.kclhi.hands.hider.singleshot.random.RandomSet;
import org.kclhi.hands.hider.singleshot.random.RandomSetStaticBetween;
import org.kclhi.hands.hider.singleshot.random.RandomStaticBetween;
import org.kclhi.hands.hider.singleshot.random.RandomVariableHidePotential;
import org.kclhi.hands.hider.singleshot.staticlocations.StaticLocations;
import org.kclhi.hands.seeker.AdaptiveSeeker;
import org.kclhi.hands.seeker.AdaptiveSeekingAgent;
import org.kclhi.hands.seeker.AdaptiveSeekingAgentInfection;
import org.kclhi.hands.seeker.AdaptiveSeekingAgentHighInfection;
import org.kclhi.hands.seeker.AdaptiveSeekingAgentLowInfection;
import org.kclhi.hands.seeker.AdaptiveSeekingAgentLowerInfection;
import org.kclhi.hands.seeker.AdaptiveSeekingAgentMediumInfection;
import org.kclhi.hands.seeker.AdaptiveSeekingAgentUpperInfection;
import org.kclhi.hands.seeker.Seeker;
import org.kclhi.hands.seeker.repeatgame.preference.ApproximateLeastConnectedNodes;
import org.kclhi.hands.seeker.repeatgame.probability.HighProbability;
import org.kclhi.hands.seeker.repeatgame.probability.HighProbabilityRepetitionCheck;
import org.kclhi.hands.seeker.repeatgame.probability.InverseHighProbability;
import org.kclhi.hands.seeker.repeatgame.probability.VariableHistoryHighProbability;
import org.kclhi.hands.seeker.repeatgame.probability.VariableNodesHighProbability;
import org.kclhi.hands.seeker.repeatgame.probability.adaptable.HighProbabilityAdaptable;
import org.kclhi.hands.seeker.repeatgame.probability.adaptable.InverseHighProbabilityAdaptable;
import org.kclhi.hands.seeker.singleshot.adaptable.MaxDistanceAdaptable;
import org.kclhi.hands.seeker.singleshot.adaptable.MaxDistanceAdaptableInfection;
import org.kclhi.hands.seeker.singleshot.adaptable.MaxDistanceAdaptableHighInfection;
import org.kclhi.hands.seeker.singleshot.adaptable.MaxDistanceAdaptableLowInfection;
import org.kclhi.hands.seeker.singleshot.adaptable.MaxDistanceAdaptableLowerInfection;
import org.kclhi.hands.seeker.singleshot.adaptable.MaxDistanceAdaptableMediumInfection;
import org.kclhi.hands.seeker.singleshot.adaptable.MaxDistanceAdaptableUpperInfection;
import org.kclhi.hands.seeker.singleshot.adaptable.RandomWalkAdaptable;
import org.kclhi.hands.seeker.singleshot.adaptable.RandomWalkAdaptableInfection;
import org.kclhi.hands.seeker.singleshot.adaptable.RandomWalkAdaptableHighInfection;
import org.kclhi.hands.seeker.singleshot.adaptable.RandomWalkAdaptableLowInfection;
import org.kclhi.hands.seeker.singleshot.adaptable.RandomWalkAdaptableLowerInfection;
import org.kclhi.hands.seeker.singleshot.adaptable.RandomWalkAdaptableMediumInfection;
import org.kclhi.hands.seeker.singleshot.adaptable.RandomWalkAdaptableUpperInfection;
import org.kclhi.hands.seeker.singleshot.adaptable.RandomWalkStationaryChanceAdaptableInfection;
import org.kclhi.hands.seeker.singleshot.adaptable.RandomWalkStationaryChanceAdaptableHighInfection;
import org.kclhi.hands.seeker.singleshot.adaptable.RandomWalkStationaryChanceAdaptableLowInfection;
import org.kclhi.hands.seeker.singleshot.adaptable.RandomWalkStationaryChanceAdaptableLowerInfection;
import org.kclhi.hands.seeker.singleshot.adaptable.RandomWalkStationaryChanceAdaptableMediumInfection;
import org.kclhi.hands.seeker.singleshot.adaptable.RandomWalkStationaryChanceAdaptableUpperInfection;
import org.kclhi.hands.seeker.singleshot.cost.Greedy;
import org.kclhi.hands.seeker.singleshot.coverage.BacktrackGreedy;
import org.kclhi.hands.seeker.singleshot.coverage.BacktrackPath;
import org.kclhi.hands.seeker.singleshot.coverage.BreadthFirstSearch;
import org.kclhi.hands.seeker.singleshot.coverage.BreadthFirstSearchGreedy;
import org.kclhi.hands.seeker.singleshot.coverage.DepthFirstSearch;
import org.kclhi.hands.seeker.singleshot.coverage.DepthFirstSearchGreedy;
import org.kclhi.hands.seeker.singleshot.coverage.DepthFirstSearchMechanism;
import org.kclhi.hands.seeker.singleshot.coverage.RandomTarry;
import org.kclhi.hands.seeker.singleshot.coverage.VariableBacktrackPath;
import org.kclhi.hands.seeker.singleshot.preference.LinkedPath;
import org.kclhi.hands.seeker.singleshot.preference.MaxDistanceHighInfection;
import org.kclhi.hands.seeker.singleshot.preference.MaxDistanceLowInfection;
import org.kclhi.hands.seeker.singleshot.preference.MaxDistanceLowerInfection;
import org.kclhi.hands.seeker.singleshot.preference.MaxDistanceMediumInfection;
import org.kclhi.hands.seeker.singleshot.preference.MaxDistanceUpperInfection;
import org.kclhi.hands.seeker.singleshot.preference.MostConnectedFirst;
import org.kclhi.hands.seeker.singleshot.random.FixedStartRandomWalk;
import org.kclhi.hands.seeker.singleshot.random.RandomWalk;
import org.kclhi.hands.seeker.singleshot.random.RandomWalkHighInfection;
import org.kclhi.hands.seeker.singleshot.random.RandomWalkStationaryChanceHighInfection;
import org.kclhi.hands.seeker.singleshot.random.RandomWalkStationaryChanceLowerInfection;
import org.kclhi.hands.seeker.singleshot.random.RandomWalkStationaryChanceMediumInfection;
import org.kclhi.hands.seeker.singleshot.random.RandomWalkStationaryChanceUpperInfection;
import org.kclhi.hands.seeker.singleshot.random.RandomWalkStationaryChanceLowInfection;
import org.kclhi.hands.seeker.singleshot.random.RandomWalkUpperInfection;
import org.kclhi.hands.seeker.singleshot.random.RandomWalkLowerInfection;
import org.kclhi.hands.seeker.singleshot.random.RandomWalkLowInfection;
import org.kclhi.hands.seeker.singleshot.random.RandomWalkMediumInfection;
import org.kclhi.hands.seeker.singleshot.random.RandomWalkStationaryChance;
import org.kclhi.hands.seeker.singleshot.random.SelfAvoidingRandomWalk;
import org.kclhi.hands.seeker.singleshot.random.SelfAvoidingRandomWalkGreedy;

/**
* @author Martin
* @author Reuben Atendido
*/
public class Main {
  
  /**
  * 
  */
  private int gameNumber;
  
  /**
  * 
  */
  private int totalGames;
  
  /**
  * 
  */
  private int totalRounds;
  
  /**
  * 
  */
  private String currentSimulationIdentifier = "";
  
  /**
  * Graph 
  */
  private GraphController<StringVertex, StringEdge> graphController;
  
  /**
  * Whether to output a corresponding animation of the search process
  */
  private final boolean OUTPUT_JS = false;
  
  /**
  * 
  */
  private String hiderList;
  
  /**
  * 
  */
  private String seekerList;
  
  /**
  * 
  */
  private int numberOfHideLocations;
  
  /**
  * 
  */
  private boolean mixHiders;
  
  /**
  * 
  */
  private boolean mixSeekers;
  
  /**
  * 
  */
  private boolean generateOutput;
  
  /**
  * @param args
  */
  public Main(String[] args) {
    
    currentSimulationIdentifier = Utils.readFromFile(Utils.FILEPREFIX + "simRecordID.txt").get(0);
    
    System.out.println(currentSimulationIdentifier);
    
    Utils.talk("Main", "Simulation parameters " + Arrays.toString(args));
    
    gameNumber = Integer.parseInt(args[0]);
    
    totalGames = Integer.parseInt(args[1]);
    
    //
    
    String topology = args[4];
    
    int numberOfVertices = Integer.parseInt(args[5]);
    
    numberOfVertices = numberOfVertices == 0 ? 1 : numberOfVertices;
    
    String fixedOrUpperBound = args[9];
    
    double fixedOrUpperValue = Double.parseDouble(args[8]);
    
    int edgeTraversalDecrement = Integer.parseInt(args[10]);
    
    numberOfHideLocations = Integer.parseInt(args[6]);
    
    if ( numberOfVertices < numberOfHideLocations ) {
      
      throw new UnsupportedOperationException("More objects to hide than there are vertices.");
      
    }
    
    double baseVaccineProportion = Double.parseDouble(args[15]);

    initGraph(topology, numberOfVertices, numberOfHideLocations, fixedOrUpperBound, fixedOrUpperValue, edgeTraversalDecrement, baseVaccineProportion);
    
    mixHiders = Boolean.parseBoolean(args[11]);
    
    mixSeekers = Boolean.parseBoolean(args[12]);
    
    boolean resetPerRound = Boolean.parseBoolean(args[13]);

    double additionalResourceVaccinated = Double.parseDouble(args[14]);

    boolean strategyOverRounds = Boolean.parseBoolean(args[16]);
    
    generateOutput = Boolean.parseBoolean(args[17]);
    
    //
    
    String agentList;
    
    int rounds = Integer.parseInt(args[7]);
    
    this.totalRounds = rounds;
    
    hiderList = args[2];
    
    seekerList = args[3];
    
    startRounds(initHiders(hiderList, numberOfHideLocations, mixHiders), initSeekers(seekerList, mixSeekers), rounds, true, resetPerRound, strategyOverRounds);
    
  }
  
  /**
  * @param args
  */
  private void initGraph(String topology, int numberOfVertices, int numberOfHideLocations, String fixedOrUpperBound, double fixedOrUpperValue, int edgeTraversalDecrement, double baseVaccineProportion) {
    
    graphController = new GraphController<StringVertex, StringEdge>(topology, numberOfVertices, numberOfHideLocations, fixedOrUpperBound, fixedOrUpperValue, edgeTraversalDecrement, baseVaccineProportion);
    
  }
  
  /**
  * @param agentList
  * @param numberOfHideLocations
  * @return
  */
  private List<Hider> initHiders(String agentList, int numberOfHideLocations, boolean mixHiders) {
    
    /**************************
    * 
    * Set up hiding agents
    * 
    * * * * * * * * * * * * */
    
    List<Hider> allHidingAgents = new ArrayList<Hider>();
    
    for( Pair<String, String> hiderType : Utils.stringToArray(agentList, "(\\[([0-9a-zA-Z]+),([0-9]+)\\])") ) {
      
      // Single-shot:
      
      if (hiderType.getElement0().equals("StaticLocations")) {
        
        allHidingAgents.add(new StaticLocations(graphController, numberOfHideLocations));
        
      } 
      
      if (hiderType.getElement0().equals("Random")) {
        
        allHidingAgents.add(new Random(graphController, numberOfHideLocations));
        
      } 
      
      if (hiderType.getElement0().equals("RandomFixedStart")) {
        
        allHidingAgents.add(new RandomFixedStart(graphController, numberOfHideLocations));
        
      } 
      
      if (hiderType.getElement0().equals("RandomStaticBetween")) {
        
        allHidingAgents.add(new RandomStaticBetween(graphController, numberOfHideLocations));
        
      }
      
      if (hiderType.getElement0().equals("RandomVariableHidePotential")) {
        
        allHidingAgents.add(new RandomVariableHidePotential(graphController, numberOfHideLocations, gameNumber / ((float)totalGames)));
        
      } 
      
      //
      
      if (hiderType.getElement0().equals("FirstK")) {
        
        allHidingAgents.add(new VariableFixedDistance(graphController, "FirstK", numberOfHideLocations, 0));
        
      }
      
      if (hiderType.getElement0().equals("FirstKMinus1")) {
        
        allHidingAgents.add(new VariableFixedDistance(graphController, "FirstKMinus1", numberOfHideLocations, 0) {
          
          public boolean hideHere(StringVertex vertex) {
            
            if ( hideLocations().size() < numberOfHideLocations - 1) {
              
              return super.hideHere(vertex);
              
            } else {
              
              if ( currentNode == vertex ) {
                
                for ( StringVertex hideLocation : hideLocations() ) {
                  
                  for ( StringEdge edge : graphController.edgesOf(responsibleAgent, vertex) ) {
                    
                    if ( edgeToTarget(edge, vertex).equals(hideLocation) ) {
                      
                      return false;
                      
                    }
                    
                  }
                  
                }
                
                Utils.talk(toString(), "Disconnected node: " + vertex +  " " + graphController.edgesOf(responsibleAgent, vertex));
                
                return true;
                
              } else {
                
                return false;
                
              }
              
            }
            
          }
          
        });
        
      }
      
      if (hiderType.getElement0().equals("NotConnected")) {
        
        allHidingAgents.add(new NotConnected(graphController, numberOfHideLocations));
        
        
      }
      if (hiderType.getElement0().equals("FirstKFixedStart")) {
        
        allHidingAgents.add(new VariableFixedDistanceFixedStart(graphController, "FirstKFixedStart", numberOfHideLocations, 0));
        
        
      }
      
      if (hiderType.getElement0().equals("FirstNStaticBetween")) {
        
        allHidingAgents.add(new VariableFixedDistanceStaticBetween(graphController, "FirstNStaticBetween", numberOfHideLocations, 0));
        
      }
      
      //
      
      if (hiderType.getElement0().equals("RandomSet")) {
        
        allHidingAgents.add(new RandomSet(graphController, numberOfHideLocations));
        
      } 
      
      if (hiderType.getElement0().equals("RandomSetStaticBetween")) {
        
        allHidingAgents.add(new RandomSetStaticBetween(graphController, numberOfHideLocations));
        
      } 
      
      if (hiderType.getElement0().equals("GreedyRandomSet")) {
        
        allHidingAgents.add(new GreedyRandomSet(graphController, numberOfHideLocations));
        
      } 
      
      if (hiderType.getElement0().equals("GreedyRandomSetStaticBetween")) {
        
        allHidingAgents.add(new GreedyRandomSetStaticBetween(graphController, numberOfHideLocations));
        
      } 
      
      if (hiderType.getElement0().equals("UniqueRandomSet")) {
        
        allHidingAgents.add(new UniqueRandomSet(graphController, numberOfHideLocations));
        
      }
      
      if (hiderType.getElement0().equals("UniqueRandomSetRepeat")) {
        
        allHidingAgents.add(new UniqueRandomSetRepeat(graphController, numberOfHideLocations));
        
      }
      
      if (hiderType.getElement0().equals("UniqueRandomSetRepeatRandomNodes")) {
        
        allHidingAgents.add(new UniqueRandomSetRepeatRandomNodes(graphController, numberOfHideLocations));
        
      } 
      
      if (hiderType.getElement0().equals("AutomaticUniqueRandomSetRepeat")) {
        
        allHidingAgents.add(new AutomaticUniqueRandomSetRepeat(graphController, numberOfHideLocations, 3));
        
      } 
      
      //
      
      if (hiderType.getElement0().equals("RandomFixedDistance")) {
        
        allHidingAgents.add(new RandomFixedDistance(graphController, numberOfHideLocations));
        
      } 
      
      if (hiderType.getElement0().equals("RandomFixedDistanceFixedStart")) {
        
        allHidingAgents.add(new RandomFixedDistanceFixedStart(graphController, numberOfHideLocations));
        
      } 
      
      if (hiderType.getElement0().equals("RandomFixedDistanceStaticBetween")) {
        
        allHidingAgents.add(new RandomFixedDistanceStaticBetween(graphController, numberOfHideLocations));
        
      } 
      
      if (hiderType.getElement0().equals("GreedyRandomFixedDistance")) {
        
        allHidingAgents.add(new GreedyRandomFixedDistance(graphController, numberOfHideLocations));
        
      }
      
      if (hiderType.getElement0().equals("GreedyRandomFixedDistanceStaticBetween")) {
        
        allHidingAgents.add(new GreedyRandomFixedDistanceStaticBetween(graphController, numberOfHideLocations));
        
      }
      
      
      
      if (hiderType.getElement0().equals("VariableFixedDistance")) {
        
        allHidingAgents.add(new VariableFixedDistance(graphController, numberOfHideLocations, gameNumber));
        
      } 
      
      if (hiderType.getElement0().equals("VariableFixedDistanceFixedStart")) {
        
        allHidingAgents.add(new VariableFixedDistanceFixedStart(graphController, numberOfHideLocations, gameNumber));
        
      } 
      
      if (hiderType.getElement0().equals("VariableFixedDistanceStaticBetween")) {
        
        allHidingAgents.add(new VariableFixedDistanceStaticBetween(graphController, numberOfHideLocations, gameNumber));
        
      } 
      
      if (hiderType.getElement0().equals("GreedyVariableFixedDistance")) {
        
        allHidingAgents.add(new GreedyVariableFixedDistance(graphController, numberOfHideLocations, gameNumber));
        
      } 
      
      //
      
      if (hiderType.getElement0().equals("LeastConnected")) {
        
        allHidingAgents.add(new LeastConnected(graphController, numberOfHideLocations));
        
      } 
      
      if (hiderType.getElement0().equals("VariableGraphKnowledgeLeastConnectedDFS")) {
        
        final class VariableGraphKnowledgeLeastConnectedDFS extends LeastConnected {
          
          public VariableGraphKnowledgeLeastConnectedDFS(GraphController <StringVertex, StringEdge> graphController, int numberOfHideLocations, double graphPortion) {
            
            super(graphController, numberOfHideLocations, graphPortion);
            
          }
          
          public OpenTraverserStrategy getExplorationMechanism(GraphTraverser responsibleAgent) {
            
            return new DepthFirstSearchMechanism(graphController, responsibleAgent);
            
          }
          
        }
        
        allHidingAgents.add(new VariableGraphKnowledgeLeastConnectedDFS(graphController, numberOfHideLocations, gameNumber / (double)totalGames));
        
      } 
      
      if (hiderType.getElement0().equals("LeastConnectedLeastConnectedFirst")) {
        
        allHidingAgents.add(new LeastConnectedLeastConnectedFirst(graphController, numberOfHideLocations));
        
      } 
      
      if (hiderType.getElement0().equals("LeastConnectedStaticBetween")) {
        
        allHidingAgents.add(new LeastConnectedStaticBetween(graphController, numberOfHideLocations));
        
      } 
      
      if (hiderType.getElement0().equals("VariableGraphKnowledgeLeastConnected")) {
        
        allHidingAgents.add(new LeastConnected(graphController, "VariableGraphKnowledgeLeastConnected", numberOfHideLocations, gameNumber / (double)totalGames));
        
      } 
      
      if (hiderType.getElement0().equals("MaxDistance")) {
        
        allHidingAgents.add(new MaxDistance(graphController, numberOfHideLocations));
        
      } 
      
      if (hiderType.getElement0().equals("MaxDistanceStaticBetween")) {
        
        allHidingAgents.add(new MaxDistanceStaticBetween(graphController, numberOfHideLocations));
        
      } 
      
      if (hiderType.getElement0().equals("VariableGraphKnowledgeMaxDistance")) {
        
        allHidingAgents.add(new MaxDistance(graphController, "VariableGraphKnowledgeMaxDistance", numberOfHideLocations, gameNumber / (double)totalGames, -1));
        
      } 
      
      if (hiderType.getElement0().equals("Greedy")) {
        
        allHidingAgents.add(new VariableGreedy(graphController, numberOfHideLocations, 1.0));
        
      } 
      
      if (hiderType.getElement0().equals("GreedyStaticBetween")) {
        
        allHidingAgents.add(new VariableGreedyStaticBetween(graphController, numberOfHideLocations, 1.0));
        
      }
      
      if (hiderType.getElement0().equals("EqualEdgeCost")) {
        
        allHidingAgents.add(new VariableGreedy(graphController, numberOfHideLocations, 0.0));
        
      }
      
      if (hiderType.getElement0().equals("FixedStartEqualEdgeCost")) {
        
        allHidingAgents.add(new FixedStartVariableGreedy(graphController, numberOfHideLocations, 0.0));
        
      } 
      
      if (hiderType.getElement0().equals("VariableGreedy")) {
        
        allHidingAgents.add(new VariableGreedy(graphController, numberOfHideLocations, gameNumber / (float)totalGames));
        
      } 
      
      if (hiderType.getElement0().equals("FixedStartVariableGreedy")) {
        
        allHidingAgents.add(new FixedStartVariableGreedy(graphController, numberOfHideLocations, gameNumber / (float)totalGames));
        
      } 
      
      // Repeat-game:
      
      if (hiderType.getElement0().equals("FullyBias")) {
        
        allHidingAgents.add(new VariableBias(graphController, "FullyBias", numberOfHideLocations, 1.0));
        
      }
      
      if (hiderType.getElement0().equals("FullyBiasStaticBetween")) {
        
        allHidingAgents.add(new VariableBiasStaticBetween(graphController, "FullyBiasStaticBetween", numberOfHideLocations, 1.0));
        
      }
      
      if (hiderType.getElement0().equals("FullyExplorative")) {
        
        allHidingAgents.add(new VariableBias(graphController, "FullyExplorative", numberOfHideLocations, 0.0));
        
      }
      
      if (hiderType.getElement0().equals("LooselyBias")) {
        
        allHidingAgents.add(new VariableBias(graphController, "LooselyBias", numberOfHideLocations, 0.5));
        
      } 
      
      if (hiderType.getElement0().equals("VariableBias")) {
        
        allHidingAgents.add(new VariableBias(graphController, numberOfHideLocations, gameNumber / (float)totalGames));
        
      } 
      
      if (hiderType.getElement0().equals("FixedStartVariableBias")) {
        
        allHidingAgents.add(new FixedStartVariableBias(graphController, numberOfHideLocations, gameNumber / (float)totalGames));
        
      } 
      
      if (hiderType.getElement0().equals("FixedStartFullyBias")) {
        
        allHidingAgents.add(new FixedStartVariableBias(graphController, "FixedStartFullyBias", numberOfHideLocations, 1.0));
        
      } 
      
      if (hiderType.getElement0().equals("FixedStartFullyExplorative")) {
        
        allHidingAgents.add(new FixedStartVariableBias(graphController, "FixedStartFullyExplorative", numberOfHideLocations, 0.0));
        
      } 
      
      // Discovered through experimentation (~MDC 24/2/15 ?)
      if (hiderType.getElement0().equals("OptimalBias")) {
        
        allHidingAgents.add(new VariableBias(graphController, numberOfHideLocations, 0.6));
        
      }
      
      //
      
      if (hiderType.getElement0().equals("VariableBiasLocations")) {
        
        allHidingAgents.add(new VariableBiasLocations(graphController, numberOfHideLocations, gameNumber));
        
      }
      
      ////
      
      if (hiderType.getElement0().equals("DeceptiveNew")) {
        
        allHidingAgents.add(new DeceptiveNew(graphController, "Deceptive", numberOfHideLocations, (int)(totalRounds / 2)));
        
      }
      
      if (hiderType.getElement0().equals("DeceptiveTemp")) {
        
        allHidingAgents.add(new DeceptiveNew(graphController, "Deceptive", numberOfHideLocations, totalRounds - ( totalRounds / 10 )));
        
      }
      
      if (hiderType.getElement0().equals("VariableDeceptiveNew")) {
        
        allHidingAgents.add(new DeceptiveNew(graphController, "Deceptive", numberOfHideLocations, gameNumber) {
          
          public boolean strategyOverRounds() {
            
            return true;
            
          }
          
        });
        
      }
      
      if (hiderType.getElement0().equals("SetDeceptiveNodes")) {
        
        allHidingAgents.add(new Deceptive(graphController, "SetDeceptiveNodes", numberOfHideLocations, numberOfHideLocations, (int)(totalRounds / 2)));
        
      }
      
      if (hiderType.getElement0().equals("VariableDeceptiveNodes")) {
        
        allHidingAgents.add(new Deceptive(graphController, "VariableDeceptiveNodes", numberOfHideLocations, gameNumber, (int)(Math.random() * totalGames)));
        
      }
      
      //
      
      if (hiderType.getElement0().equals("SetDeceptionDuration")) {
        
        allHidingAgents.add(new Deceptive(graphController, "SetDeceptionDuration", numberOfHideLocations, numberOfHideLocations, (int)(totalRounds / 2)));
        
      }
      
      if (hiderType.getElement0().equals("VariableDeceptionDuration")) {
        
        allHidingAgents.add(new Deceptive(graphController, "VariableDeceptionDuration", numberOfHideLocations, numberOfHideLocations, gameNumber));
        
      }
      
      if (hiderType.getElement0().equals("SetDeceptionDurationVariableDeceptiveNodes")) {
        
        allHidingAgents.add(new Deceptive(graphController, "SetDeceptionDurationVariableDeceptiveNodes", numberOfHideLocations, gameNumber, (int)(totalRounds / 2)));
        
      }
      
      if (hiderType.getElement0().equals("VariableDeceptionDurationVariableDeceptiveNodes")) {
        
        for ( int a = 1; a <= numberOfHideLocations; a++) {
          
          allHidingAgents.add(new Deceptive(graphController, "VariableDeceptionDurationVariableDeceptiveNodes-" + a, numberOfHideLocations, a, gameNumber));
          
        }
        
      }
      
      //
      
      boolean refreshDeceptiveNodes = false;
      
      if (hiderType.getElement0().contains("RefreshDeceptiveNodes")) { 
        
        refreshDeceptiveNodes = true;
        
        hiderType = Pair.createPair(hiderType.getElement0().substring(0, hiderType.getElement0().indexOf("RefreshDeceptiveNodes")), hiderType.getElement1());
        
      }
      
      // ~MDC Values (deception duration and repeat interval) considered to be 'optimal' via experimentation
      if (hiderType.getElement0().equals("SetDeceptionDurationSetDeceptionIntervalSetRepeatDuration")) {
        
        allHidingAgents.add(new Deceptive(graphController, "SetDeceptionDurationSetDeceptionIntervalSetRepeatDuration", numberOfHideLocations, numberOfHideLocations, 1, 0, (int)(totalRounds / 2), refreshDeceptiveNodes));
        
      }
      
      if (hiderType.getElement0().equals("SetDeceptionDurationVariableDeceptionIntervalSetRepeatDuration")) {
        
        allHidingAgents.add(new Deceptive(graphController, "SetDeceptionDurationVariableDeceptionIntervalSetRepeatDuration", numberOfHideLocations, numberOfHideLocations, 1, gameNumber, totalRounds, refreshDeceptiveNodes));
        
      }
      
      if (hiderType.getElement0().equals("VariableDeceptionDurationSetDeceptionIntervalSetRepeatDuration")) {
        
        allHidingAgents.add(new Deceptive(graphController, "VariableDeceptionDurationSetDeceptionIntervalSetRepeatDuration", numberOfHideLocations, numberOfHideLocations, gameNumber, 0, totalRounds, refreshDeceptiveNodes));
        
      }
      
      if (hiderType.getElement0().equals("VariableDeceptionDurationVariableDeceptionIntervalSetRepeatDuration")) {
        
        final int MAXINTERVAL = totalGames;
        
        for ( int interval = 0; interval < MAXINTERVAL; interval++) {
          
          allHidingAgents.add(new Deceptive(graphController, "VariableDeceptionDurationVariableDeceptionInterval-" + interval, numberOfHideLocations, numberOfHideLocations, gameNumber, MAXINTERVAL, totalRounds, refreshDeceptiveNodes));
          
        }
        
      }
      
      // ~MDC Expanded as necessary by the results above
      
      if (hiderType.getElement0().equals("SetDeceptionDurationSetDeceptionIntervalVariableRepeatDuration")) {
        
        allHidingAgents.add(new Deceptive(graphController, "SetDeceptionDurationSetDeceptionIntervalVariableRepeatDuration", numberOfHideLocations, numberOfHideLocations, 1, 0, gameNumber, refreshDeceptiveNodes));
        
      }
      
      if (hiderType.getElement0().equals("SetDeceptionDurationSetDeceptionIntervalVariableRepeatDuration-NonUniqueRandom")) {
        
        allHidingAgents.add(new Deceptive(graphController, "SetDeceptionDurationSetDeceptionIntervalVariableRepeatDuration", numberOfHideLocations, numberOfHideLocations, 1, 0, gameNumber, refreshDeceptiveNodes, false));
        
      }
      
      if (hiderType.getElement0().equals("VariableDeceptiveNodesSetDeceptionDurationSetDeceptionIntervalSetRepeatDuration")) {
        
        allHidingAgents.add(new Deceptive(graphController, "SetDeceptionDurationSetDeceptionIntervalVariableDeceptiveNodes", numberOfHideLocations, gameNumber, 1, 0, totalRounds, refreshDeceptiveNodes));
        
      }
      
      // ~Misc.
      
      if (hiderType.getElement0().equals("EpsilonDeceptive")) {
        
        allHidingAgents.add(new EpsilonDeceptive(graphController, numberOfHideLocations, numberOfHideLocations, 0, (double)(gameNumber / 100.0)));
        
      }
      
      if (hiderType.getElement0().equals("LeastConnectedDeceptive")) {
        
        allHidingAgents.add(new LeastConnectedDeceptive(graphController, numberOfHideLocations, numberOfHideLocations, 1, 0, totalRounds, refreshDeceptiveNodes));
        
      }
      
      if (hiderType.getElement0().equals("GroupedDeceptiveSetDuration")) {
        
        allHidingAgents.add(new GroupedDeceptive(graphController, "GroupedDeceptiveSetDuration", numberOfHideLocations, numberOfHideLocations,  (int)(totalRounds / 2)));
        
      }
      
      if (hiderType.getElement0().equals("GroupedDeceptive")) {
        
        allHidingAgents.add(new GroupedDeceptive(graphController, numberOfHideLocations, numberOfHideLocations, 1, 0, totalRounds, refreshDeceptiveNodes));
        
      }
      
      if (hiderType.getElement0().equals("GroupedDeceptiveVariableDeceptionDuration")) {
        
        allHidingAgents.add(new GroupedDeceptive(graphController, "GroupedDeceptiveVariableDeceptionDuration", numberOfHideLocations, numberOfHideLocations, 11));
        
      }
      
      if (hiderType.getElement0().equals("VariableDeceptiveSets")) {
        
        // ~MDC Choice of repeat duration here is arbitrary
        allHidingAgents.add(new Deceptive(graphController, "VariableDeceptiveSets", numberOfHideLocations, numberOfHideLocations, (int)(totalRounds / 2), 0, (int)(totalRounds / 2), 10));
        
      }
      
      if (hiderType.getElement0().equals("VariableGroupedDeceptiveSets")) {
        
        allHidingAgents.add(new GroupedDeceptive(graphController, "VariableDeceptiveSets", numberOfHideLocations, numberOfHideLocations, (int)(totalRounds / 2), 0, (int)(totalRounds / 2), 10));
        
      }
      
      
      
      // Unknown:
      if (hiderType.getElement0().equals("UnknownRandom")) {
        
        ArrayList<Pair<AdaptiveHider, Double>> strategyPortfolio = new ArrayList<Pair<AdaptiveHider, Double>>();
        
        abstract class RandomSetAnonymous extends RandomSet implements AdaptiveHider {
          public RandomSetAnonymous(GraphController<StringVertex, StringEdge> graphController, String name, int numberOfHideLocations) {
            super(graphController, name, numberOfHideLocations);
          } 
          
        }
        
        strategyPortfolio.add(new Pair<AdaptiveHider, Double>(new RandomSetAnonymous(graphController, "RandomSet", numberOfHideLocations) {
          public AdaptiveMeasure environmentalMeasure() { return new AdaptiveMeasure(0.0); }
          public AdaptiveMeasure socialMeasure() { return new AdaptiveMeasure(0.0); }
          public AdaptiveMeasure internalMeasure(ArrayList<Double> strategyPerformance) { return new AdaptiveMeasure(0.0); }
          public AdaptiveWeightings getAdaptiveWeightings() { return new AdaptiveWeightings(0.33, 0.33, 0.33); }
          public void stopStrategy() {}
          
        }, 0.0));
        
        abstract class UniqueRandomSetRepeatAnonymous extends UniqueRandomSetRepeat implements AdaptiveHider {
          public UniqueRandomSetRepeatAnonymous(GraphController<StringVertex, StringEdge> graphController, String name, int numberOfHideLocations, int goodPerformanceRounds) {
            super(graphController, name, numberOfHideLocations);
          }
        }
        
        strategyPortfolio.add(new Pair<AdaptiveHider, Double>(new UniqueRandomSetRepeatAnonymous(graphController, "UniqueRandomSetRepeat", numberOfHideLocations, 3) {
          public AdaptiveMeasure environmentalMeasure() { return new AdaptiveMeasure(0.0); }
          public AdaptiveMeasure socialMeasure() { return new AdaptiveMeasure(0.0); }
          public AdaptiveMeasure internalMeasure(ArrayList<Double> strategyPerformance) { return new AdaptiveMeasure(0.0); }
          public AdaptiveWeightings getAdaptiveWeightings() { return new AdaptiveWeightings(0.33, 0.33, 0.33); }
          public void stopStrategy() {}
        }, 0.0));
        
        /*abstract class AutomaticUniqueRandomSetRepeatAnonymous extends AutomaticUniqueRandomSetRepeat implements AdaptiveHider {
          public AutomaticUniqueRandomSetRepeatAnonymous(GraphController<StringVertex, StringEdge> graphController, String name, int numberOfHideLocations, int goodPerformanceRounds) {
            super(graphController, name, numberOfHideLocations, goodPerformanceRounds);
          }
        }
        
        strategyPortfolio.add(new AutomaticUniqueRandomSetRepeatAnonymous(graphController, "AutomaticUniqueRandomSetRepeat", numberOfHideLocations, 3) {
          public double environmentalMeasure() { return 0; }
          public double socialMeasure() { return 0; }
          public double internalMeasure(ArrayList<Double> strategyPerformance) { return 0; }
          public void stopStrategy() {}
        });*/
        
        allHidingAgents.add(new AdaptiveHidingAgent<AdaptiveHider>(graphController, "UnknownRandom", strategyPortfolio, totalRounds));
        
      }
      
      if (hiderType.getElement0().contains("MetaRandom")) {
        
        ArrayList<Pair<AdaptiveHider, Double>> strategyPortfolio = new ArrayList<Pair<AdaptiveHider, Double>>();
        
        strategyPortfolio.add(new Pair<AdaptiveHider, Double>(new RandomSetAdaptable(graphController, numberOfHideLocations), 0.84));
        
        strategyPortfolio.add(new Pair<AdaptiveHider, Double>(new UniqueRandomSetRepeatAdaptable(graphController, numberOfHideLocations), 0.16));
        
        allHidingAgents.add(new AdaptiveHidingAgent<AdaptiveHider>(graphController, "MetaRandom", strategyPortfolio, totalRounds));
        
      }
      
      if (hiderType.getElement0().contains("MetaConnected")) {
        
        ArrayList<Pair<AdaptiveHider, Double>> strategyPortfolio = new ArrayList<Pair<AdaptiveHider, Double>>();
        
        abstract class RandomSetAnonymous extends RandomSet implements AdaptiveHider {
          public RandomSetAnonymous(GraphController<StringVertex, StringEdge> graphController, String name, int numberOfHideLocations) {
            super(graphController, name, numberOfHideLocations);
          } 
          
        }
        
        strategyPortfolio.add(new Pair<AdaptiveHider, Double>(new RandomSetAnonymous(graphController, "RandomSet", numberOfHideLocations) {
          public AdaptiveMeasure environmentalMeasure() { return new AdaptiveMeasure(0.0); }
          public AdaptiveMeasure socialMeasure() { return new AdaptiveMeasure(0.0); }
          public AdaptiveMeasure internalMeasure(ArrayList<Double> strategyPerformance) { return new AdaptiveMeasure(0.0); }
          public AdaptiveWeightings getAdaptiveWeightings() { return new AdaptiveWeightings(0.33, 0.33, 0.33); }
          public void stopStrategy() {}
          
        }, 0.0));
        
        strategyPortfolio.add(new Pair<AdaptiveHider, Double>(new LeastConnectedAdaptable(graphController, numberOfHideLocations), 0.0));
        
        allHidingAgents.add(new AdaptiveHidingAgent<AdaptiveHider>(graphController, "MetaConnected", strategyPortfolio, totalRounds, "LeastConnectedAdaptable"));
        
      }
      
    }
    
    if (mixHiders) {
      
      Collections.shuffle(allHidingAgents);
      
      allHidingAgents = allHidingAgents.subList(0, 1);
      
    }
    
    return allHidingAgents;
    
  }
  
  /**
  * @param agentList
  * @return
  */
  private List<Seeker> initSeekers(String agentList, boolean mixSeekers) {
    
    /**************************
    * 
    * Set up seeking agents
    * 
    * * * * * * * * * * * * */
    
    List<Seeker> allSeekingAgents = new ArrayList<Seeker>();
    
    for( Pair<String, String> seekerType : Utils.stringToArray(agentList, "(\\[([0-9a-zA-Z]+),([0-9]+)\\])") ) {
      
      for( int seekerCount = 0; seekerCount < Integer.parseInt(seekerType.getElement1()); seekerCount++ ) {

        String seekerName = seekerType.getElement0(); 
        int currentNumberOfSeekingAgents = allSeekingAgents.size();

        // Single-shot:
        
        if (seekerName.equals("SelfAvoidingRandomWalk")) {
          
          allSeekingAgents.add(new SelfAvoidingRandomWalk(graphController));
          
        }
        
        if (seekerName.equals("SelfAvoidingRandomWalkGreedy")) {
          
          allSeekingAgents.add(new SelfAvoidingRandomWalkGreedy(graphController));
          
        }
        
        if (seekerName.equals("FixedStartRandomWalk")) {
          
          allSeekingAgents.add(new FixedStartRandomWalk(graphController));
          
        }

        if (seekerName.equals("RandomWalkStationaryChance")) {
          
          allSeekingAgents.add(new RandomWalkStationaryChance(graphController));
          
        }
        
        if (seekerName.equals("Greedy")) {
          
          allSeekingAgents.add(new Greedy(graphController));
          
        }
        
        if (seekerName.equals("RepeatGreedy")) {
          
          allSeekingAgents.add(new Greedy(graphController) {
            
            public StringVertex nextNode(StringVertex currentNode) {
              
              uniquelyVisitNodes = false;
              
              return super.nextNode(currentNode);
              
            }
            
          });
          
        }
        
        if (seekerName.equals("DepthFirstSearch")) {
          
          allSeekingAgents.add(new DepthFirstSearch(graphController));
          
        }
        
        if (seekerName.equals("DepthFirstSearchGreedy")) {
          
          allSeekingAgents.add(new DepthFirstSearchGreedy(graphController));
          
        }
        
        if (seekerName.equals("BreadthFirstSearch")) {
          
          allSeekingAgents.add(new BreadthFirstSearch(graphController));
          
        }
        
        if (seekerName.equals("BreadthFirstSearchGreedy")) {
          
          allSeekingAgents.add(new BreadthFirstSearchGreedy(graphController));
          
        }
        
        /*if (seekerName.equals("LeastConnectedFirst")) {
          
          allSeekingAgents.add(new LeastConnectedFirst(graphController));
          
        }*/
        // Instead:
        if (seekerName.equals("LeastConnectedFirst")) {
          
          allSeekingAgents.add(new org.kclhi.hands.seeker.singleshot.preference.LeastConnected(graphController, "LeastConnectedFirst", 1.0));
          
        }
        
        if (seekerName.equals("VariableLeastConnectedFirst")) {
          
          allSeekingAgents.add(new org.kclhi.hands.seeker.singleshot.preference.LeastConnected(graphController, "VariableLeastConnectedFirst", gameNumber / (float)totalGames));
          
        }
        
        if (seekerName.equals("MostConnectedFirst")) {
          
          allSeekingAgents.add(new MostConnectedFirst(graphController));
          
        }
        
        if (seekerName.equals("ApproximateLeastConnectedNodes")) {
          
          allSeekingAgents.add(new ApproximateLeastConnectedNodes(graphController));
          
        }
        
        if (seekerName.equals("MaxDistanceFirst")) {
          
          allSeekingAgents.add(new org.kclhi.hands.seeker.singleshot.preference.MaxDistance(graphController, "MaxDistanceFirst", 1.0));
          
        }
        
        if (seekerName.equals("LinkedPath")) {
          
          allSeekingAgents.add(new LinkedPath(graphController, 0));
          
        }
        
        if (seekerName.equals("BacktrackPath")) {
          
          allSeekingAgents.add(new BacktrackPath(graphController));
          
        }
        
        if (seekerName.equals("VariableBacktrackPath")) {
          
          allSeekingAgents.add(new VariableBacktrackPath(graphController, gameNumber));
          
        }
        
        // Optimal backtrack path -- found by experimentation
        if (seekerName.equals("OptimalBacktrackPath")) {
          
          allSeekingAgents.add(new VariableBacktrackPath(graphController, "OptimalBacktrackPath", 1));
          
        }
        
        if (seekerName.equals("BacktrackGreedy")) {
          
          allSeekingAgents.add(new BacktrackGreedy(graphController));
          
        }
        
        if (seekerName.equals("NearestNeighbour")) {
          
          final class BacktrackGreedyWithoutBacktracking extends BacktrackGreedy {
            public BacktrackGreedyWithoutBacktracking(GraphController<StringVertex, StringEdge> graphController) { super(graphController); }
            public boolean backtracks() { return false; }
          }
          
          allSeekingAgents.add(new BacktrackGreedyWithoutBacktracking(graphController));
          
        }
        
        if (seekerName.equals("RandomTarry")) {
          
          allSeekingAgents.add(new RandomTarry(graphController));
          
        }
        
        // Repeat-game: 
        
        if (seekerName.equals("HighProbability")) {
          
          allSeekingAgents.add(new HighProbability(graphController));
          
        }
        
        if (seekerName.equals("HighProbabilityK")) {
          
          allSeekingAgents.add(new VariableNodesHighProbability(graphController, "HighProbabilityK", numberOfHideLocations, false));
          
        }
        
        if (seekerName.equals("VariableNodesHighProbability")) {
          
          allSeekingAgents.add(new VariableNodesHighProbability(graphController, gameNumber, true));
          
        }
        
        if (seekerName.equals("VariableHistoryHighProbability")) {
          
          allSeekingAgents.add(new VariableHistoryHighProbability(graphController, gameNumber));
          
        }
        
        if (seekerName.equals("HighProbabilityRepetitionCheck")) {
          
          allSeekingAgents.add(new HighProbabilityRepetitionCheck(graphController, 2, numberOfHideLocations));
          
        }
        
        if (seekerName.equals("InverseHighProbability")) {
          
          allSeekingAgents.add(new InverseHighProbability(graphController));
          
        }
        
        // Adaptive:
        
        ArrayList<Pair<AdaptiveSeeker, Double>> strategyPortfolio = new ArrayList<Pair<AdaptiveSeeker, Double>>();
        
        if (seekerName.contains("MetaProbability")) {
          
          strategyPortfolio.clear();
          
          strategyPortfolio.add(new Pair<AdaptiveSeeker, Double>(new InverseHighProbabilityAdaptable(graphController, Integer.MAX_VALUE), 0.24));
          
          strategyPortfolio.add(new Pair<AdaptiveSeeker, Double>(new HighProbabilityAdaptable(graphController), 0.76));
          
          allSeekingAgents.add(new AdaptiveSeekingAgent<AdaptiveSeeker>(graphController, "MetaProbability", strategyPortfolio, totalRounds, 0.5, false) {
            
            /* ~MDC Should be moved into the actual strategy
            * (non-Javadoc)
            * @see HideAndSeek.AdaptiveGraphTraversingAgent#confidenceLevel()
            */
            protected double confidenceLevel() {
              
              return uniqueHideLocations().size() / (double)graphController.vertexSet().size();
              
            }
            
          });
          
        }

        double leverageMetaRandomProbability = Utils.getPlugin().getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("behaviour").getJSONObject("MetaRandom").getDouble("leverageProbability");
        double leverageElderlyProbability = Utils.getPlugin().getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("behaviour").getJSONObject("Elderly").getDouble("leverageProbability");
        double leverageAdultProbability = Utils.getPlugin().getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("behaviour").getJSONObject("Adult").getDouble("leverageProbability");
        double leverageChildProbability = Utils.getPlugin().getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("behaviour").getJSONObject("Child").getDouble("leverageProbability");
        double leverageVaccinatedProbability = Utils.getPlugin().getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("behaviour").getJSONObject("Vaccinated").getDouble("leverageProbability");
        double leverageImmunocompromisedProbability = Utils.getPlugin().getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("behaviour").getJSONObject("Immunocompromised").getDouble("leverageProbability");
        double leverageActiveProbability = Utils.getPlugin().getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("behaviour").getJSONObject("Active").getDouble("leverageProbability");
        double leverageObeseProbability = Utils.getPlugin().getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("behaviour").getJSONObject("Obese").getDouble("leverageProbability");
        double leverageSmokerProbability = Utils.getPlugin().getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("behaviour").getJSONObject("Smoker").getDouble("leverageProbability");
        double leverageAsthmaticProbability = Utils.getPlugin().getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("behaviour").getJSONObject("Asthmatic").getDouble("leverageProbability");

        // Random selection:
        if (seekerName.contains("MetaRandomStationaryChance")) {
          
          ArrayList<Pair<AdaptiveSeeker, Double>> strategyPortfolioRandomSelection = new ArrayList<Pair<AdaptiveSeeker, Double>>();

          strategyPortfolioRandomSelection.add(new Pair<AdaptiveSeeker, Double>(
            // seekerName.contains("MetaRandomStationaryChanceHighVaccine") ? new MaxDistanceAdaptableHighVaccine(graphController) :
            // seekerName.contains("MetaRandomStationaryChanceMediumVaccine") ? new MaxDistanceAdaptableMediumVaccine(graphController) :
            // seekerName.contains("MetaRandomStationaryChanceLowVaccine") ? new MaxDistanceAdaptableLowVaccine(graphController) :
            // seekerName.contains("MetaRandomStationaryChanceLowerVaccine") ? new MaxDistanceAdaptableLowerVaccine(graphController) :
            // seekerName.contains("MetaRandomStationaryChanceUpperVaccine") ? new MaxDistanceAdaptableUpperVaccine(graphController) :
            seekerName.contains("MetaRandomStationaryChanceVaccinated") ? new MaxDistanceAdaptableInfection(graphController) : 
            seekerName.contains("MetaRandomStationaryChanceElderly") ? new MaxDistanceAdaptableMediumInfection(graphController) :
            // add maxdistanceadaptable for immunocompromised
            // add maxdistanceadaptable for asthma
            // add maxdistanceadaptable for medicine intake
            new MaxDistanceAdaptable(graphController, 1.0),
            seekerName.contains("MetaRandomStationaryChanceVaccinated") ? leverageVaccinatedProbability: 
            seekerName.contains("MetaRandomStationaryChanceElderly") ? leverageElderlyProbability: 
          leverageMetaRandomProbability));

          strategyPortfolioRandomSelection.add(new Pair<AdaptiveSeeker, Double>(
            // seekerName.contains("MetaRandomStationaryChanceHighVaccine") ? new RandomWalkStationaryChanceAdaptableHighVaccine(graphController) :
            // seekerName.contains("MetaRandomStationaryChanceMediumVaccine") ? new RandomWalkStationaryChanceAdaptableMediumVaccine(graphController) :
            // seekerName.contains("MetaRandomStationaryChanceLowVaccine") ? new RandomWalkStationaryChanceAdaptableLowVaccine(graphController) :
            // seekerName.contains("MetaRandomStationaryChanceLowerVaccine") ? new RandomWalkStationaryChanceAdaptableLowerVaccine(graphController) :
            // seekerName.contains("MetaRandomStationaryChanceUpperVaccine") ? new RandomWalkStationaryChanceAdaptableUpperVaccine(graphController) :
            seekerName.contains("MetaRandomStationaryChanceVaccinated") ? new RandomWalkStationaryChanceAdaptableInfection(graphController) :
            seekerName.contains("MetaRandomStationaryChanceElderly") ? new RandomWalkStationaryChanceAdaptableMediumInfection(graphController) :
            // add randomwalkadaptable for immunocompromised
            // add randomwalkadaptable for medicine intake
            // add randomwalkadaptable for asthma
            new RandomWalkAdaptable(graphController), 
            seekerName.contains("MetaRandomStationaryChanceVaccinated") ? 1-leverageVaccinatedProbability: 
            seekerName.contains("MetaRandomStationaryChanceElderly") ? 1-leverageElderlyProbability: 
          1 - leverageMetaRandomProbability));
          
          allSeekingAgents.add(
            // seekerName.contains("Lower") ? new AdaptiveSeekingAgentLowerVaccine<AdaptiveSeeker>(graphController, seekerName, strategyPortfolioRandomSelection, totalRounds, 1, false) :
            // seekerName.contains("Upper") ? new AdaptiveSeekingAgentUpperVaccine<AdaptiveSeeker>(graphController, seekerName, strategyPortfolioRandomSelection, totalRounds, 1, false) :
            // seekerName.contains("Low") ? new AdaptiveSeekingAgentLowVaccine<AdaptiveSeeker>(graphController, seekerName, strategyPortfolioRandomSelection, totalRounds, 1, false) :
            // seekerName.contains("Medium") ? new AdaptiveSeekingAgentMediumVaccine<AdaptiveSeeker>(graphController, seekerName, strategyPortfolioRandomSelection, totalRounds, 1, false) :
            // seekerName.contains("High") ? new AdaptiveSeekingAgentHighVaccine<AdaptiveSeeker>(graphController, seekerName, strategyPortfolioRandomSelection, totalRounds, 1, false) :
            seekerName.contains("Vaccinated") ? new AdaptiveSeekingAgentInfection<AdaptiveSeeker>(graphController, seekerName, strategyPortfolioRandomSelection, totalRounds, 1, false) :
            seekerName.contains("Elderly") ? new AdaptiveSeekingAgentMediumInfection<AdaptiveSeeker>(graphController, seekerName, strategyPortfolioRandomSelection, totalRounds, 1, false) :
            // add randomwalkadaptable for immunocompromised
            // add randomwalkadaptable for asthma
            // add randomwalkadaptable for medicine intake
            new AdaptiveSeekingAgent<AdaptiveSeeker>(graphController, seekerName, strategyPortfolioRandomSelection, totalRounds, 1, false) { protected double confidenceLevel() { return 0; }
          });
          
        } else if (seekerName.contains("MetaRandom")) {
          
          ArrayList<Pair<AdaptiveSeeker, Double>> strategyPortfolioRandomSelection = new ArrayList<Pair<AdaptiveSeeker, Double>>();

          strategyPortfolioRandomSelection.add(new Pair<AdaptiveSeeker, Double>(
            seekerName.contains("Vaccinated") ? new MaxDistanceAdaptableLowerInfection(graphController) :
            seekerName.contains("Immunocompromised") ? new MaxDistanceAdaptableUpperInfection(graphController) :
            seekerName.contains("Asthmatic") ? new MaxDistanceAdaptableUpperInfection(graphController) :
            seekerName.contains("Smoker") ? new MaxDistanceAdaptableUpperInfection(graphController) :
            seekerName.contains("Obese") ? new MaxDistanceAdaptableMediumInfection(graphController) :
            seekerName.contains("Active") ? new MaxDistanceAdaptableLowInfection(graphController) :
            seekerName.contains("Elderly") ? new MaxDistanceAdaptableMediumInfection(graphController) :
            seekerName.contains("Adult") ? new MaxDistanceAdaptableLowInfection(graphController) :
            seekerName.contains("Child") ? new MaxDistanceAdaptableLowInfection(graphController) :

            new MaxDistanceAdaptable(graphController, 1.0),
            seekerName.contains("MetaRandomVaccinated") ? leverageVaccinatedProbability: 
            seekerName.contains("MetaRandomElderly") ? leverageElderlyProbability:
            seekerName.contains("MetaRandomAdult") ? leverageAdultProbability:
            seekerName.contains("MetaRandomChild") ? leverageChildProbability:
            seekerName.contains("MetaRandomVaccinatedElderly") ? ((leverageElderlyProbability+leverageVaccinatedProbability)/2):
            seekerName.contains("MetaRandomVaccinatedAdult") ? ((leverageAdultProbability+leverageVaccinatedProbability)/2): 
            seekerName.contains("MetaRandomVaccinatedChild") ? ((leverageChildProbability+leverageVaccinatedProbability)/2): 
            seekerName.contains("MetaRandomImmunocompromisedElderly") ? ((leverageElderlyProbability+leverageImmunocompromisedProbability)/2):
            seekerName.contains("MetaRandomImmunocompromisedAdult") ? ((leverageAdultProbability+leverageImmunocompromisedProbability)/2): 
            seekerName.contains("MetaRandomImmunocompromisedChild") ? ((leverageChildProbability+leverageImmunocompromisedProbability)/2):
            seekerName.contains("MetaRandomSmokerElderly") ? ((leverageElderlyProbability+leverageSmokerProbability)/2):
            seekerName.contains("MetaRandomSmokerAdult") ? ((leverageAdultProbability+leverageSmokerProbability)/2): 
            seekerName.contains("MetaRandomSmokerChild") ? ((leverageChildProbability+leverageSmokerProbability)/2): 
            seekerName.contains("MetaRandomActiveElderly") ? ((leverageElderlyProbability+leverageActiveProbability)/2):
            seekerName.contains("MetaRandomActiveAdult") ? ((leverageAdultProbability+leverageActiveProbability)/2): 
            seekerName.contains("MetaRandomActiveChild") ? ((leverageChildProbability+leverageActiveProbability)/2): 
            seekerName.contains("MetaRandomObeseElderly") ? ((leverageElderlyProbability+leverageObeseProbability)/2):
            seekerName.contains("MetaRandomObeseAdult") ? ((leverageAdultProbability+leverageObeseProbability)/2): 
            seekerName.contains("MetaRandomObeseChild") ? ((leverageChildProbability+leverageObeseProbability)/2):
            seekerName.contains("MetaRandomVaccinatedImmunocompromisedElderly") ? ((leverageElderlyProbability+leverageImmunocompromisedProbability+leverageVaccinatedProbability)/3):
            seekerName.contains("MetaRandomVaccinatedImmunocompromisedAdult") ? ((leverageAdultProbability+leverageImmunocompromisedProbability+leverageVaccinatedProbability)/3): 
            seekerName.contains("MetaRandomVaccinatedImmunocompromisedChild") ? ((leverageChildProbability+leverageImmunocompromisedProbability+leverageVaccinatedProbability)/3):
            seekerName.contains("MetaRandomVaccinatedSmokerElderly") ? ((leverageElderlyProbability+leverageSmokerProbability+leverageVaccinatedProbability)/3):
            seekerName.contains("MetaRandomVaccinatedSmokerAdult") ? ((leverageAdultProbability+leverageSmokerProbability+leverageVaccinatedProbability)/3): 
            seekerName.contains("MetaRandomVaccinatedSmokerChild") ? ((leverageChildProbability+leverageSmokerProbability+leverageVaccinatedProbability)/3): 
            seekerName.contains("MetaRandomVaccinatedActiveElderly") ? ((leverageElderlyProbability+leverageActiveProbability+leverageVaccinatedProbability)/3):
            seekerName.contains("MetaRandomVaccinatedActiveAdult") ? ((leverageAdultProbability+leverageActiveProbability+leverageVaccinatedProbability)/3): 
            seekerName.contains("MetaRandomVaccinatedActiveChild") ? ((leverageChildProbability+leverageActiveProbability+leverageVaccinatedProbability)/3): 
            seekerName.contains("MetaRandomVaccinatedObeseElderly") ? ((leverageElderlyProbability+leverageObeseProbability+leverageVaccinatedProbability)/3):
            seekerName.contains("MetaRandomVaccinatedObeseAdult") ? ((leverageAdultProbability+leverageObeseProbability+leverageVaccinatedProbability)/3): 
            seekerName.contains("MetaRandomVaccinatedObeseChild") ? ((leverageChildProbability+leverageObeseProbability+leverageVaccinatedProbability)/3):  

          leverageMetaRandomProbability));

          strategyPortfolioRandomSelection.add(new Pair<AdaptiveSeeker, Double>(
            seekerName.contains("Vaccinated") ? new RandomWalkAdaptableLowerInfection(graphController) :
            seekerName.contains("Immunocompromised") ? new RandomWalkAdaptableUpperInfection(graphController) :
            seekerName.contains("Asthmatic") ? new RandomWalkAdaptableUpperInfection(graphController) :
            seekerName.contains("Smoker") ? new RandomWalkAdaptableUpperInfection(graphController) :
            seekerName.contains("Obese") ? new RandomWalkAdaptableMediumInfection(graphController) :
            seekerName.contains("Active") ? new RandomWalkAdaptableLowInfection(graphController) :
            seekerName.contains("Elderly") ? new RandomWalkAdaptableMediumInfection(graphController) :
            seekerName.contains("Adult") ? new RandomWalkAdaptableLowInfection(graphController) :
            seekerName.contains("Child") ? new RandomWalkAdaptableLowInfection(graphController) :
            
            new RandomWalkAdaptable(graphController), 
            seekerName.contains("MetaRandomVaccinated") ? 1-leverageVaccinatedProbability: 
            seekerName.contains("MetaRandomElderly") ? 1-leverageElderlyProbability:
            seekerName.contains("MetaRandomAdult") ? 1-leverageAdultProbability:
            seekerName.contains("MetaRandomChild") ? 1-leverageChildProbability:
            seekerName.contains("MetaRandomVaccinatedElderly") ? 1-((leverageElderlyProbability+leverageVaccinatedProbability)/2):
            seekerName.contains("MetaRandomVaccinatedAdult") ? 1-((leverageAdultProbability+leverageVaccinatedProbability)/2): 
            seekerName.contains("MetaRandomVaccinatedChild") ? 1-((leverageChildProbability+leverageVaccinatedProbability)/2): 
            seekerName.contains("MetaRandomImmunocompromisedElderly") ? 1-((leverageElderlyProbability+leverageImmunocompromisedProbability)/2):
            seekerName.contains("MetaRandomImmunocompromisedAdult") ? 1-((leverageAdultProbability+leverageImmunocompromisedProbability)/2): 
            seekerName.contains("MetaRandomImmunocompromisedChild") ? 1-((leverageChildProbability+leverageImmunocompromisedProbability)/2):
            seekerName.contains("MetaRandomAsthmaticElderly") ? 1-((leverageElderlyProbability+leverageAsthmaticProbability)/2):
            seekerName.contains("MetaRandomAsthmaticAdult") ? 1-((leverageAdultProbability+leverageAsthmaticProbability)/2): 
            seekerName.contains("MetaRandomAsthmaticChild") ? 1-((leverageChildProbability+leverageAsthmaticProbability)/2):  
            seekerName.contains("MetaRandomSmokerElderly") ? 1-((leverageElderlyProbability+leverageSmokerProbability)/2):
            seekerName.contains("MetaRandomSmokerAdult") ? 1-((leverageAdultProbability+leverageSmokerProbability)/2): 
            seekerName.contains("MetaRandomSmokerChild") ? 1-((leverageChildProbability+leverageSmokerProbability)/2):  
            seekerName.contains("MetaRandomActiveElderly") ? 1-((leverageElderlyProbability+leverageActiveProbability)/2):
            seekerName.contains("MetaRandomActiveAdult") ? 1-((leverageAdultProbability+leverageActiveProbability)/2): 
            seekerName.contains("MetaRandomActiveChild") ? 1-((leverageChildProbability+leverageActiveProbability)/2): 
            seekerName.contains("MetaRandomObeseElderly") ? 1-((leverageElderlyProbability+leverageObeseProbability)/2):
            seekerName.contains("MetaRandomObeseAdult") ? 1-((leverageAdultProbability+leverageObeseProbability)/2): 
            seekerName.contains("MetaRandomObeseChild") ? 1-((leverageChildProbability+leverageObeseProbability)/2):
            seekerName.contains("MetaRandomVaccinatedImmunocompromisedElderly") ? 1-((leverageElderlyProbability+leverageImmunocompromisedProbability+leverageVaccinatedProbability)/3):
            seekerName.contains("MetaRandomVaccinatedImmunocompromisedAdult") ? 1-((leverageAdultProbability+leverageImmunocompromisedProbability+leverageVaccinatedProbability)/3): 
            seekerName.contains("MetaRandomVaccinatedImmunocompromisedChild") ? 1-((leverageChildProbability+leverageImmunocompromisedProbability+leverageVaccinatedProbability)/3):
            seekerName.contains("MetaRandomVaccinatedSmokerElderly") ? 1-((leverageElderlyProbability+leverageSmokerProbability+leverageVaccinatedProbability)/3):
            seekerName.contains("MetaRandomVaccinatedSmokerAdult") ? 1-((leverageAdultProbability+leverageSmokerProbability+leverageVaccinatedProbability)/3): 
            seekerName.contains("MetaRandomVaccinatedSmokerChild") ? 1-((leverageChildProbability+leverageSmokerProbability+leverageVaccinatedProbability)/3): 
            seekerName.contains("MetaRandomVaccinatedActiveElderly") ? 1-((leverageElderlyProbability+leverageActiveProbability+leverageVaccinatedProbability)/3):
            seekerName.contains("MetaRandomVaccinatedActiveAdult") ? 1-((leverageAdultProbability+leverageActiveProbability+leverageVaccinatedProbability)/3): 
            seekerName.contains("MetaRandomVaccinatedActiveChild") ? 1-((leverageChildProbability+leverageActiveProbability+leverageVaccinatedProbability)/3): 
            seekerName.contains("MetaRandomVaccinatedObeseElderly") ? 1-((leverageElderlyProbability+leverageObeseProbability+leverageVaccinatedProbability)/3):
            seekerName.contains("MetaRandomVaccinatedObeseAdult") ? 1-((leverageAdultProbability+leverageObeseProbability+leverageVaccinatedProbability)/3): 
            seekerName.contains("MetaRandomVaccinatedObeseChild") ? 1-((leverageChildProbability+leverageObeseProbability+leverageVaccinatedProbability)/3):  


          1 - leverageMetaRandomProbability));
          
          allSeekingAgents.add(
            seekerName.contains("Vaccinated") ? new AdaptiveSeekingAgentLowerInfection<AdaptiveSeeker>(graphController, seekerName, strategyPortfolioRandomSelection, totalRounds, 1, false) :
            seekerName.contains("Elderly") ? new AdaptiveSeekingAgentHighInfection<AdaptiveSeeker>(graphController, seekerName, strategyPortfolioRandomSelection, totalRounds, 1, false) :
            seekerName.contains("Adult") ? new AdaptiveSeekingAgentMediumInfection<AdaptiveSeeker>(graphController, seekerName, strategyPortfolioRandomSelection, totalRounds, 1, false) :
            seekerName.contains("Child") ? new AdaptiveSeekingAgentLowInfection<AdaptiveSeeker>(graphController, seekerName, strategyPortfolioRandomSelection, totalRounds, 1, false) :
            seekerName.contains("Immunocompromised") ? new AdaptiveSeekingAgentLowInfection<AdaptiveSeeker>(graphController, seekerName, strategyPortfolioRandomSelection, totalRounds, 1, false) :
            seekerName.contains("Asthmatic") ? new AdaptiveSeekingAgentLowInfection<AdaptiveSeeker>(graphController, seekerName, strategyPortfolioRandomSelection, totalRounds, 1, false) :
            seekerName.contains("Smoker") ? new AdaptiveSeekingAgentLowInfection<AdaptiveSeeker>(graphController, seekerName, strategyPortfolioRandomSelection, totalRounds, 1, false) :
            seekerName.contains("Active") ? new AdaptiveSeekingAgentLowInfection<AdaptiveSeeker>(graphController, seekerName, strategyPortfolioRandomSelection, totalRounds, 1, false) :
            seekerName.contains("Obese") ? new AdaptiveSeekingAgentLowInfection<AdaptiveSeeker>(graphController, seekerName, strategyPortfolioRandomSelection, totalRounds, 1, false) :

            new AdaptiveSeekingAgent<AdaptiveSeeker>(graphController, seekerName, strategyPortfolioRandomSelection, totalRounds, 1, false) { protected double confidenceLevel() { return 0; }
          });
          
        }

        if (seekerName.contains("RandomWalk") && !seekerName.contains("StationaryChance")) {
          
          allSeekingAgents.add(
            seekerName.contains("HighInfection") ? new RandomWalkHighInfection(graphController) : 
            seekerName.contains("MediumInfection") ? new RandomWalkMediumInfection(graphController) :
            seekerName.contains("LowInfection") ? new RandomWalkLowInfection(graphController) : 
            seekerName.contains("UpperInfection") ? new RandomWalkUpperInfection(graphController) :
            seekerName.contains("LowerInfection") ? new RandomWalkLowerInfection(graphController) : 
            new RandomWalk(graphController)
          );

        }
        
        if (seekerName.contains("RandomWalk") && seekerName.contains("StationaryChance")) {

          allSeekingAgents.add(
            seekerName.contains("HighInfection") ? new RandomWalkStationaryChanceHighInfection(graphController) : 
            seekerName.contains("MediumInfection") ? new RandomWalkStationaryChanceMediumInfection(graphController) :
            seekerName.contains("LowInfection") ? new RandomWalkStationaryChanceLowInfection(graphController) : 
            seekerName.contains("UpperInfection") ? new RandomWalkStationaryChanceUpperInfection(graphController) :
            seekerName.contains("LowerInfection") ? new RandomWalkStationaryChanceLowerInfection(graphController) : 
            new RandomWalkStationaryChance(graphController)
          );
          
        }

        if (seekerName.contains("MaxDistance")) {

          allSeekingAgents.add(
            seekerName.contains("HighInfection") ? new MaxDistanceHighInfection(graphController) :
            seekerName.contains("MediumInfection") ? new MaxDistanceMediumInfection(graphController) :
            seekerName.contains("LowInfection") ? new MaxDistanceLowInfection(graphController) :
            seekerName.contains("UpperInfection") ? new MaxDistanceUpperInfection(graphController) :
            seekerName.contains("LowerInfection") ? new MaxDistanceLowerInfection(graphController) :
            new org.kclhi.hands.seeker.singleshot.preference.MaxDistance(graphController, "MaxDistanceFirst", 1.0)
          );

        }

        if( currentNumberOfSeekingAgents == allSeekingAgents.size() ) System.out.println("WARN: Seeker " + seekerName + " not found.");

        if (seekerCount > 0) {
          Seeker lastAddedSeeker = allSeekingAgents.get(allSeekingAgents.size()-1);
          lastAddedSeeker.label(seekerCount);
          // ~MDC 31/01 Our duplicate seeker won't have been registered initially as the name will have matched, so re-register
          graphController.registerTraversingAgent(lastAddedSeeker.getResponsibleAgent());
        }

      }
      
    }
    
    if (mixSeekers) {
      
      Collections.shuffle(allSeekingAgents);
      
      allSeekingAgents = allSeekingAgents.subList(0, 1);
      
    }
    
    return allSeekingAgents;
    
  }
  
  /**
  * Rounds are designed to re-test the same parameter configurations (which may vary between games)
  * multiples times AND to allow for patterns or histories to develop
  * 
  * @param hiders
  * @param seekers
  * @param rounds
  * @param recordPerRound
  */
  private void startRounds(List<Hider> hiders, List<Seeker> seekers, int rounds, boolean recordPerRound, boolean resetPerRound, boolean strategyOverRounds) {
    
    // Pre-round outputting
    
    FileWriter mainOutputWriter = null, outputJavascript = null, outputHTML = null;
    
    try {
      
      if ( generateOutput ) mainOutputWriter = new FileWriter(Utils.FILEPREFIX + "/data/" + currentSimulationIdentifier + ".csv", true);
      
      if (OUTPUT_JS) {
        
        outputJavascript = new FileWriter(Utils.FILEPREFIX + "/data/js/data/" + currentSimulationIdentifier + "-vis.js", true);
        
        outputHTML = new FileWriter(Utils.FILEPREFIX + "/data/" + currentSimulationIdentifier + "-vis.html", true);
        
      }
      
    } catch (IOException e) {
      
      e.printStackTrace();
      
    }
    
    if (OUTPUT_JS) Utils.writeToFile(outputJavascript, "var graphNodes = \"" + graphController.edgeSet(this) + "\"; \n var hidden = new Array(); \n var path = new Array(); \n");
    
    /**************************
    * 
    * Main rounds loop
    * 
    * * * * * * * * * * * * */
    // Default repeat.
    int repeatAllRounds = 1;
    
    int REPEAT_CONSTANT = rounds;
    
    if ( strategyOverRounds ) repeatAllRounds = REPEAT_CONSTANT;
    
    // Pre-checks for presence of strategy which is defined by a sequence of rounds,
    // not individual ones (e.g. deceptive), and thus must be tested multiple times, as sets of rounds, in addition.
    for ( Hider hider : hiders ) {
      
      // ~MDC Dramatically affects the size of the output files
      if (hider.strategyOverRounds()) repeatAllRounds = REPEAT_CONSTANT; 
      
    }
    
    for ( Seeker  seeker : seekers ) {
      
      if (seeker.strategyOverRounds()) repeatAllRounds = REPEAT_CONSTANT;
      
    }

    // Run rounds and record output per hider
    for ( Hider hider : hiders ) {
      
      Utils.talk("Main", hiders.toString());
      
      /* If changes occur over a set of rounds (over a game), by nature of the strategy,
      * this process must repeat (i.e. to check how a strategy evolves over different 
      * rounds under a given parameter).
      * 
      * ~MDC This should occur for all multiple round games that aren't reset per round
      */
      for (int roundRepeat = 0; roundRepeat < repeatAllRounds; roundRepeat++) {
        
        hider.startPlaying();
        
        boolean lastRoundRepeat = false;
        
        if (roundRepeat == (repeatAllRounds - 1)) lastRoundRepeat = true;
        
        for (int i = 0; i < rounds; i++) {
          
          Utils.talk("", "Game " + gameNumber + " Round " + i + ": " + ( ( i / ( ( (float) rounds * hiders.size() ) ) ) * totalGames ) + "%");
          
          /* 
          * Allows rounds to act as individual games. Useful for varying parameters per a set
          * of games, automatically
          */
          if ( resetPerRound ) {
            
            graphController.generateGraph(graphController.getTopologyProperties().getType(), graphController.vertexSet().size());
            
            Utils.talk("Main", "Resetting " + hider);
            
            for ( Hider newHiderObject : initHiders(hiderList, numberOfHideLocations, mixHiders) ) {
              
              if ( newHiderObject.toString().equals(hider.toString()) ) {
                
                hider = newHiderObject;
                
              }
              
            }
            
            hider.startPlaying();
            
          }
          
          hider.run();
          
          ArrayList<Seeker> newSeekers = new ArrayList<Seeker>();
          
          if ( resetPerRound ) { 
            
            for ( Seeker seeker : seekers ) {
              
              Utils.talk("Main", "Resetting " + seeker);
              
              for ( Seeker newSeekerObject : initSeekers(seekerList, mixSeekers) ) {
                
                if ( newSeekerObject.toString().equals(seeker.toString()) ) {
                  
                  seeker = newSeekerObject;
                  
                }
                
              }
              
              newSeekers.add(seeker);
              
            }
            
            seekers = newSeekers;

          }
          
          for ( Seeker seeker : seekers ) {
            
            seeker.run();
            
          }
          
          graphController.clearHideLocations(this);
          
          if (OUTPUT_JS) {
            
            // Visualise first hider and first seeker, for novelty, mainly.
            
            Utils.writeToFile(outputJavascript, "hidden[" + i + "] = \"" + hiders.get(0).requestHideLocations(hiders.get(0)) + "\"; \n");
            
            Utils.writeToFile(outputJavascript, "path[" + i + "] = \"" + graphController.latestRoundPaths(this, seekers.get(0)) + "\"; \n");
            
          }
          
          //
          
          /*if ( recordPerRound) {
            
            Utils.talk("Main", hider.toString() + "," + hider.printRoundStats());
            
            for( Seeker seeker : seekers ) {
              
              Utils.talk("Main", seeker.toString() + "," + seeker.printRoundStats());
              
            }
            
          }*/
          
          if (generateOutput && recordPerRound) {
            
            Utils.writeToFile(mainOutputWriter, "R, " + hider.toString() + "," + hider.getClass().getName() + "," + hider.printRoundStats() + ",");
            
            Utils.talk("Main", hider.toString() + "," + hider.printRoundStats());
            
            for( Seeker seeker : seekers ) {
              
              Utils.writeToFile(mainOutputWriter, seeker.toString() + "," + seeker.getClass().getName() + "," + seeker.printRoundStats() + ",");
              
              Utils.talk("Main", seeker.toString() + "," + seeker.printRoundStats());
              
            }
            
            Utils.writeToFile(mainOutputWriter, "\n");
            
          }
          
          hider.endOfRound();
          
          for (Seeker seeker : seekers) {
            
            seeker.endOfRound();
            
          }
          
          // Must be notified before hider and seeker are if these two agents wish to print latest payoff information
          graphController.notifyEndOfRound(this);
          
        }
        
        //
        
        if (OUTPUT_JS) {
          
          ArrayList<String> javascriptOutputTemplate = Utils.readFromFile(Utils.FILEPREFIX + "data/js/vis-template.js");
          
          for ( String line : javascriptOutputTemplate ) {
            
            Utils.writeToFile(outputJavascript, line + "\n");
            
          }
          
          ArrayList<String> firstHalfHTMLTemplate = Utils.readFromFile(Utils.FILEPREFIX + "data/template/vis-template-1.html");
          
          for (String line : firstHalfHTMLTemplate) {
            
            Utils.writeToFile(outputHTML, line + "\n");
            
          }
          
          Utils.writeToFile(outputHTML, "<script type=\"text/javascript\" src=\"js/data/" + currentSimulationIdentifier + "-vis.js\"></script>");
          
          ArrayList<String> secondHalfHTMLTemplate = Utils.readFromFile(Utils.FILEPREFIX + "data/template/vis-template-2.html");
          
          for (String line : secondHalfHTMLTemplate) {
            
            Utils.writeToFile(outputHTML, line + "\n");
            
          }
          
        }
        
        hider.endOfGame();
        
        // Output hider stats
        
        if (lastRoundRepeat) {
          
          Utils.talk("Main", "End of game \n------------------------------------------");
          
          Utils.talk("Main", hider.toString() + "," + hider.printGameStats());
          
        }
        
        //if ( !recordPerRound ) {
          
          if (lastRoundRepeat) if ( generateOutput ) Utils.writeToFile(mainOutputWriter, "G, " + hider.toString() + "," + hider.getClass().getName() + "," + hider.printGameStats() + ",");
          
          // Output costs for Seekers
          
          for ( Seeker seeker : seekers ) {
            
            seeker.endOfGame();
            
            if (lastRoundRepeat) {
              
              Utils.talk("Main", seeker.toString() + "," + seeker.printGameStats());
              
              // Cost per round
              
              if ( generateOutput ) Utils.writeToFile(mainOutputWriter, seeker.toString() + "," + seeker.getClass().getName() + "," + seeker.printGameStats() + ",");
              
            }
            
          }
          
          if (lastRoundRepeat) if ( generateOutput ) Utils.writeToFile(mainOutputWriter, "\n");
          
          //}
          
          graphController.resetGameEnvironment(this);
          
        } // End of hider loop
        
      } // End of repeat loop
      
      try {
        
        if ( generateOutput ) mainOutputWriter.close();
        
        if (OUTPUT_JS) {
          
          outputJavascript.close();
          
          outputHTML.close();
          
        }
        
      } catch (IOException e) {
        
        e.printStackTrace();
      }
      
    }
    
    /**
    * @param originalHider
    * @return
    */
    private Hider newHiderObject(Hider originalHider) {
      
      String hiderName = originalHider.toString().substring(1);
      
      try {
        
        Constructor<?> ctor = originalHider.getClass().getConstructor(GraphController.class, int.class);
        
        Object object = ctor.newInstance(new Object[] { graphController, originalHider.numberOfHideLocations() });
        
        return (Hider)object;
        
      } catch (InstantiationException e) {
        
        e.printStackTrace();
        
      } catch (IllegalAccessException e) {
        
        e.printStackTrace();
        
      } catch (IllegalArgumentException e) {
        
        e.printStackTrace();
        
      } catch (InvocationTargetException e) {
        
        e.printStackTrace();
        
      } catch (NoSuchMethodException e) {
        
        e.printStackTrace();
        
      } catch (SecurityException e) {
        
        e.printStackTrace();
        
      }
      
      return null;
      
    }
    
    /**
    * @param originalHider
    * @return
    */
    private Seeker newSeekerObject(Seeker originalSeeker) {
      
      String seekerName = originalSeeker.toString().substring(1);
      
      try {
        
        Constructor<?> ctor = originalSeeker.getClass().getConstructor(GraphController.class);
        
        Object object = ctor.newInstance(new Object[] { graphController });
        
        return (Seeker)object;
        
      } catch (InstantiationException e) {
        
        e.printStackTrace();
        
      } catch (IllegalAccessException e) {
        
        e.printStackTrace();
        
      } catch (IllegalArgumentException e) {
        
        e.printStackTrace();
        
      } catch (InvocationTargetException e) {
        
        e.printStackTrace();
        
      } catch (NoSuchMethodException e) {
        
        e.printStackTrace();
        
      } catch (SecurityException e) {
        
        e.printStackTrace();
        
      }
      
      return null;
      
    }
    
    /**
    * @param hiders
    * @param seekers
    * @return
    */
    private ArrayList<GraphTraverser> allTraversers(ArrayList<Hider> hiders, ArrayList<Seeker> seekers) {
      
      ArrayList<GraphTraverser> traversers = new ArrayList<GraphTraverser>();
      
      for (Hider hider : hiders) {
        
        traversers.add(hider);
        
      }
      
      for (Seeker seeker : seekers) {
        
        traversers.add(seeker);
        
      }
      
      return traversers;
      
    }
    
    /**
    * @param args
    */
    public static void main(String[] args) {
      
      System.setProperty("jdk.internal.FileHandlerLogging.maxLocks", Integer.MAX_VALUE + "");
      Logger logger = Logger.getLogger(Main.class.toString());  
      FileHandler fh;  
      
      try {  
        
        // This block configure the logger with handler and formatter  
        fh = new FileHandler(Utils.FILEPREFIX + "log/exceptions.log", true);  
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();  
        fh.setFormatter(formatter);  
        
      } catch (SecurityException e) {  
        e.printStackTrace();  
      } catch (IOException e) {  
        e.printStackTrace();  
      }  
      
      Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { 
        public void uncaughtException(Thread t, Throwable e) { 
          StringWriter sw = new StringWriter();
          e.printStackTrace(new PrintWriter(sw));
          String stacktrace = sw.toString();
          logger.info(stacktrace); 
        }
      });  
      
      new Main(args);
      
    }
    
  }
  