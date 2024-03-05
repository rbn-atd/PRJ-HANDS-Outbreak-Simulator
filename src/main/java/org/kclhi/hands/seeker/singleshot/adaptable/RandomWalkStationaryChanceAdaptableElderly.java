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
public class RandomWalkStationaryChanceAdaptableElderly extends RandomWalkStationaryChanceAdaptable implements ElderlyGraphTraverser {

  public RandomWalkStationaryChanceAdaptableElderly(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController);
  }

  @Override
  public boolean getOlder() {
    return true;
  }
   
}
