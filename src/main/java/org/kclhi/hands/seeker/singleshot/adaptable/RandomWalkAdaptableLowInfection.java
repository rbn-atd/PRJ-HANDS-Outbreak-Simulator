package org.kclhi.hands.seeker.singleshot.adaptable;

import org.kclhi.hands.Disease.LowInfectionGraphTraverser;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;

/**
* 
* 
* @author Martin
*
*/
public class RandomWalkAdaptableLowInfection extends RandomWalkAdaptable implements LowInfectionGraphTraverser {

  public RandomWalkAdaptableLowInfection(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController);
  }

  @Override
  public boolean useInfectionBonus() {
    return true;
  }
    
}
