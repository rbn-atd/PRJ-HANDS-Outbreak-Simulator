package org.kclhi.hands.seeker.singleshot.adaptable;

import org.kclhi.hands.Disease.UpperInfectionGraphTraverser;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;

/**
* 
* 
* @author Martin
*
*/
public class RandomWalkStationaryChanceAdaptableUpperInfection extends RandomWalkStationaryChanceAdaptable implements UpperInfectionGraphTraverser {

  public RandomWalkStationaryChanceAdaptableUpperInfection(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController);
  }

  @Override
  public boolean useInfectionBonus() {
    return true;
  }
   
}
