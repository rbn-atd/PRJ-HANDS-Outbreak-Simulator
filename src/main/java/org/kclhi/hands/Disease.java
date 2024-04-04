package org.kclhi.hands;

import org.kclhi.hands.utility.Utils;

import org.json.JSONObject;

/**
 * @author Reuben
 */

 /**  this class defines the "infection bonus" property in the outbreak simulator which adapts the gas resource
 * in the winter plugin. This effectively acts as a bonus for edge traversal costs lowering total cost of finding
 * hiding diseases. 
 * the main idea is to provide seekers with higher susceptibility to disease infection with a higher infection bonus
 */
public class Disease {

  public static final double DEFAULT_INFECTION_BONUS = 0.1;
  public static final double DEFAULT_INFECTION_UPPER = 0.75;

  // returns infection bonus for respective graph traverser, otherwise return default value if traverser not found
  public static double getInfectionBonus(GraphTraverser traverser) {

    JSONObject properties = Utils.getPlugin().getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("properties");
    if( traverser.getName().contains("LowInfection") ) {
      return properties.getJSONObject("LowInfection").getDouble("infectionBonus");
    } else if( traverser.getName().contains("MediumInfection") ) {
      return properties.getJSONObject("MediumInfection").getDouble("infectionBonus");
    } else if( traverser.getName().contains("HighInfection") ) {
      return properties.getJSONObject("HighInfection").getDouble("infectionBonus");
    }
  
    return Disease.DEFAULT_INFECTION_BONUS;
  
  }

  // method that allows "usage" of the bonus
  public interface InfectionGraphTraverser {

    public boolean useInfectionBonus();

  }

  // defining interface extensions to infection graph traverser which uses the respective infectionBonus
  public interface HighInfectionGraphTraverser extends InfectionGraphTraverser {}
  public interface MediumInfectionGraphTraverser extends InfectionGraphTraverser {}
  public interface LowInfectionGraphTraverser extends InfectionGraphTraverser {}

  public interface UpperInfectionGraphTraverser extends InfectionGraphTraverser {}
  public interface LowerInfectionGraphTraverser extends InfectionGraphTraverser {}
  
  // public interface VaccinatedGraphTraverser extends InfectionGraphTraverser {}

}
