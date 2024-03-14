package org.kclhi.hands;

import org.kclhi.hands.utility.Utils;

import org.json.JSONObject;

public class Age {

  public static final double DEFAULT_AGE_PROPORTION = 1;
  public static final double DEFAULT_USAGE_UPPER = 0.6;

  public static double getVaccineProportion(GraphTraverser traverser) {

    JSONObject properties = Utils.getPlugin().getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("properties");
    if( traverser.getName().contains("Elderly") ) {
      return properties.getJSONObject("Elderly").getDouble("ageProportion");
    } else if( traverser.getName().contains("Adult") ) {
      return properties.getJSONObject("Adult").getDouble("ageProportion");
    } else if( traverser.getName().contains("Child") ) {
      return properties.getJSONObject("Child").getDouble("ageProportion");
    }
    return Age.DEFAULT_AGE_PROPORTION;
  
  }

  public interface AgeGraphTraverser {

    public boolean getOlder();

  }

  public interface ElderlyGraphTraverser extends AgeGraphTraverser {}
  public interface AdultGraphTraverser extends AgeGraphTraverser {}
  public interface ChildGraphTraverser extends AgeGraphTraverser {}


}
