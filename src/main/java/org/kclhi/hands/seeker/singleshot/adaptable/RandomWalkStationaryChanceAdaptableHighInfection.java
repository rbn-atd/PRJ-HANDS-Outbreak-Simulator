package org.kclhi.hands.seeker.singleshot.adaptable;

import org.kclhi.hands.Disease.HighInfectionGraphTraverser;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;

/**
* 
* 
* @author Martin
*
*/
public class RandomWalkStationaryChanceAdaptableHighInfection extends RandomWalkStationaryChanceAdaptable implements HighInfectionGraphTraverser {

  public RandomWalkStationaryChanceAdaptableHighInfection(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController);
  }

  @Override
  public boolean useInfectionBonus() {
    return true;
  }
   
}
