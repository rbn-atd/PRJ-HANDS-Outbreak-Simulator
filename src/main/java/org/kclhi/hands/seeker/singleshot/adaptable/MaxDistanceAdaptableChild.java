package org.kclhi.hands.seeker.singleshot.adaptable;
import org.kclhi.hands.Age.ChildGraphTraverser;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;

/**
* 
* 
* @author Martin
*
*/
public class MaxDistanceAdaptableChild extends MaxDistanceAdaptable implements ChildGraphTraverser {

  public MaxDistanceAdaptableChild(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController, 1.0);
  }

  @Override
  public boolean getOlder() {
    return true;
  }
   
}
