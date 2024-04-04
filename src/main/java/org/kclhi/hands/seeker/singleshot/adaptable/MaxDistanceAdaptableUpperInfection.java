package org.kclhi.hands.seeker.singleshot.adaptable;

import org.json.JSONObject;
import org.kclhi.hands.Disease;
import org.kclhi.hands.Disease.UpperInfectionGraphTraverser;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;
import org.kclhi.hands.utility.Utils;

/**
* 
* 
* @author Martin
*
*/
public class MaxDistanceAdaptableUpperInfection extends MaxDistanceAdaptable implements UpperInfectionGraphTraverser {

  public MaxDistanceAdaptableUpperInfection(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController, 1.0);
  }

  @Override
  public boolean useInfectionBonus() {
    JSONObject plugin = Utils.getPlugin();
    double usageUpper = plugin!=null ? plugin.getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("properties").getJSONObject("UpperInfection").getDouble("infectionBonus") : Disease.DEFAULT_INFECTION_UPPER;
    return Math.random() < usageUpper;
  }
   
}
