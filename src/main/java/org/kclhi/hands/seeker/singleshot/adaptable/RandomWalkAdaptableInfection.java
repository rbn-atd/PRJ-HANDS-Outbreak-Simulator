package org.kclhi.hands.seeker.singleshot.adaptable;

import org.kclhi.hands.Disease.InfectionGraphTraverser;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;

/**
* 
* 
* @author Reuben
*
*/

public class RandomWalkAdaptableInfection extends RandomWalkAdaptable implements InfectionGraphTraverser{
    public RandomWalkAdaptableInfection(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController);
  }

  @Override
  public boolean useInfectionBonus() {
    return true;
  }
}
