package org.kclhi.hands.seeker;

import java.util.ArrayList;

import org.kclhi.hands.utility.Pair;
import org.kclhi.hands.utility.Utils;
import org.json.JSONObject;
import org.kclhi.hands.AdaptiveGraphTraverser;
import org.kclhi.hands.Disease;
import org.kclhi.hands.Disease.MediumInfectionGraphTraverser;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;

/**
* @author Reuben Atendido
*
* defines an adaptive seeking agent with a medium infection bonus
*/
public class AdaptiveSeekingAgentMediumInfection<E extends Seeker & AdaptiveGraphTraverser> extends AdaptiveSeekingAgent<E> implements MediumInfectionGraphTraverser {
 
  public AdaptiveSeekingAgentMediumInfection(GraphController<StringVertex, StringEdge> graphController, String name, ArrayList<Pair<E, Double>> strategyPortfolio, int totalRounds, double cueTriggerThreshold, boolean canReuse) {
    super(graphController, name, strategyPortfolio, totalRounds, cueTriggerThreshold, canReuse);
  }

  protected double confidenceLevel() { 
    return 0; 
  }

  // overides infection bonus to be variable. If the randomly generated number is less than usage, do not use the bonus otherwise use the bonus.
  @Override
  public boolean useInfectionBonus() {
    JSONObject plugin = Utils.getPlugin();
    double usageUpper = plugin!=null ? plugin.getJSONObject("seekers").getJSONObject("variablesByType").getJSONObject("properties").getJSONObject("MediumInfection").getDouble("infectionBonus") : Disease.DEFAULT_INFECTION_UPPER;
    return Math.random() < usageUpper;
  }
    
}
