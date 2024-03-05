package org.kclhi.hands.seeker.singleshot.adaptable;
import org.kclhi.hands.Age.ElderlyGraphTraverser;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;

/**
* 
* 
* @author Martin
*
*/
public class MaxDistanceAdaptableElderly extends MaxDistanceAdaptable implements ElderlyGraphTraverser {

  public MaxDistanceAdaptableElderly(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController, 1.0);
  }

  @Override
  public boolean getOlder() {
    return true;
  }
   
}
