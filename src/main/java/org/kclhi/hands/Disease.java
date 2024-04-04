package org.kclhi.hands;

import org.kclhi.hands.utility.Utils;

import org.json.JSONObject;

/**
 * @author Reuben
 */

public class Disease {

  public static final double DEFAULT_INFECTION_BONUS = 0.1;
  public static final double DEFAULT_INFECTION_UPPER = 0.75;

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
  public interface InfectionGraphTraverser {

    public boolean useInfectionBonus();

  }

  public interface HighInfectionGraphTraverser extends InfectionGraphTraverser {}
  public interface MediumInfectionGraphTraverser extends InfectionGraphTraverser {}
  public interface LowInfectionGraphTraverser extends InfectionGraphTraverser {}

  public interface UpperInfectionGraphTraverser extends InfectionGraphTraverser {}
  public interface LowerInfectionGraphTraverser extends InfectionGraphTraverser {}
  
  // public interface VaccinatedGraphTraverser extends InfectionGraphTraverser {}

}
