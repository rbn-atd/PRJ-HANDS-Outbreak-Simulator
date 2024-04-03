package org.kclhi.hands.seeker.singleshot.preference;

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
public class MaxDistanceUpperInfection extends MaxDistance implements UpperInfectionGraphTraverser {

  public MaxDistanceUpperInfection(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController, 1.0);
  }

  @Override
  public boolean useInfectionBonus() {
    return true;
  }
   
}
