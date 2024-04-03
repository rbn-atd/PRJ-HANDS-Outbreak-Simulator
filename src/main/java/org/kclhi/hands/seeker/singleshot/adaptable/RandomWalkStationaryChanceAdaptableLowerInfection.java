package org.kclhi.hands.seeker.singleshot.adaptable;

import org.kclhi.hands.Disease.LowerInfectionGraphTraverser;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;

/**
* 
* 
* @author Martin
*
*/
public class RandomWalkStationaryChanceAdaptableLowerInfection extends RandomWalkStationaryChanceAdaptable implements LowerInfectionGraphTraverser {

  public RandomWalkStationaryChanceAdaptableLowerInfection(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController);
  }

  @Override
  public boolean useInfectionBonus() {
    return true;
  }
    
}
