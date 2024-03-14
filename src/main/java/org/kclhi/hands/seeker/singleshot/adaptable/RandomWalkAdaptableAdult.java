package org.kclhi.hands.seeker.singleshot.adaptable;

import org.kclhi.hands.Age.AdultGraphTraverser;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;

/**
* 
* 
* @author Reuben
*
*/

public class RandomWalkAdaptableAdult extends RandomWalkAdaptable implements AdultGraphTraverser{
    public RandomWalkAdaptableAdult(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController);
  }

  @Override
  public boolean getOlder() {
    return true;
  }
}
