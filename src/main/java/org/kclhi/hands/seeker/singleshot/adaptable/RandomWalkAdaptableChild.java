package org.kclhi.hands.seeker.singleshot.adaptable;

import org.kclhi.hands.Age.ChildGraphTraverser;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;

/**
* 
* 
* @author Reuben
*
*/

public class RandomWalkAdaptableChild extends RandomWalkAdaptable implements ChildGraphTraverser{
    public RandomWalkAdaptableChild(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController);
  }

  @Override
  public boolean getOlder() {
    return true;
  }
}
