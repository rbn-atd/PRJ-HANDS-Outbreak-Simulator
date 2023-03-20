package org.kclhi.hands.seeker;

import java.util.ArrayList;

import org.kclhi.hands.utility.Pair;
import org.kclhi.hands.AdaptiveGraphTraverser;
import org.kclhi.hands.Gas.MediumGasGraphTraverser;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;

/**
* @author Martin
*/
public class AdaptiveSeekingAgentMediumGas<E extends Seeker & AdaptiveGraphTraverser> extends AdaptiveSeekingAgent<E> implements MediumGasGraphTraverser {
 
  public AdaptiveSeekingAgentMediumGas(GraphController<StringVertex, StringEdge> graphController, String name, ArrayList<Pair<E, Double>> strategyPortfolio, int totalRounds, double cueTriggerThreshold, boolean canReuse) {
    super(graphController, name, strategyPortfolio, totalRounds, cueTriggerThreshold, canReuse);
  }

  protected double confidenceLevel() { 
    return 0; 
  }

  @Override
  public boolean useGas() {
    return true;
  }
    
}
