package org.kclhi.hands.seeker.singleshot.random;

import org.json.JSONObject;
import org.kclhi.hands.Vaccine;
import org.kclhi.hands.Vaccine.LowerVaccineGraphTraverser;
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
public class RandomWalkLowerVaccineVariableVaccine extends RandomWalk implements LowerVaccineGraphTraverser {

  public RandomWalkLowerVaccineVariableVaccine(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController);
  }

  @Override
  public boolean useVaccine() {
    JSONObject plugin = Utils.getPlugin();
    double usageUpper = plugin!=null ? plugin.getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("behaviour").getJSONObject("Gas").getDouble("leverageProbability") : Vaccine.DEFAULT_USAGE_UPPER;
    return Math.random() < usageUpper;
  }
    
}
