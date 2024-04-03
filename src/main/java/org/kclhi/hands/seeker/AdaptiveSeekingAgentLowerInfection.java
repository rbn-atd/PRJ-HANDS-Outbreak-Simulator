package org.kclhi.hands.seeker;

import java.util.ArrayList;

import org.kclhi.hands.utility.Pair;
import org.kclhi.hands.AdaptiveGraphTraverser;
import org.kclhi.hands.Disease.LowerInfectionGraphTraverser;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;

/**
* @author Reuben Atendido
*/
public class AdaptiveSeekingAgentLowerInfection<E extends Seeker & AdaptiveGraphTraverser> extends AdaptiveSeekingAgent<E> implements LowerInfectionGraphTraverser {
 
  public AdaptiveSeekingAgentLowerInfection(GraphController<StringVertex, StringEdge> graphController, String name, ArrayList<Pair<E, Double>> strategyPortfolio, int totalRounds, double cueTriggerThreshold, boolean canReuse) {
    super(graphController, name, strategyPortfolio, totalRounds, cueTriggerThreshold, canReuse);
  }

  protected double confidenceLevel() { 
    return 0; 
  }

  @Override
  public boolean useInfectionBonus() {
    return true;
  }
    
}
