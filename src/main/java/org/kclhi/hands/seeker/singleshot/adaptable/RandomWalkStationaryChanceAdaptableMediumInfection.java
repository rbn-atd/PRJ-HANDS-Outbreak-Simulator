package org.kclhi.hands.seeker.singleshot.adaptable;

import org.kclhi.hands.Disease.MediumInfectionGraphTraverser;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;

/**
* 
* 
* @author Martin
*
*/
public class RandomWalkStationaryChanceAdaptableMediumInfection extends RandomWalkStationaryChanceAdaptable implements MediumInfectionGraphTraverser {

  public RandomWalkStationaryChanceAdaptableMediumInfection(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController);
  }

  @Override
  public boolean useInfectionBonus() {
    return true;
  }
    
}
