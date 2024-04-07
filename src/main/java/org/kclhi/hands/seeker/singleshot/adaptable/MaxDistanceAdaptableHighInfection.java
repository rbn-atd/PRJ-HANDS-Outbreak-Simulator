package org.kclhi.hands.seeker.singleshot.adaptable;

import org.json.JSONObject;
import org.kclhi.hands.Disease;
import org.kclhi.hands.Disease.HighInfectionGraphTraverser;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;
import org.kclhi.hands.utility.Utils;

/**
* 
* 
* @author Reuben Atendido
*
*/
public class MaxDistanceAdaptableHighInfection extends MaxDistanceAdaptable implements HighInfectionGraphTraverser {

  public MaxDistanceAdaptableHighInfection(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController, 1.0);
  }

  @Override
  public boolean useInfectionBonus() {
    JSONObject plugin = Utils.getPlugin();
    double usageUpper = plugin!=null ? plugin.getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("properties").getJSONObject("HighInfection").getDouble("infectionBonus") : Disease.DEFAULT_INFECTION_UPPER;
    return Math.random() < usageUpper;
  }
   
}
