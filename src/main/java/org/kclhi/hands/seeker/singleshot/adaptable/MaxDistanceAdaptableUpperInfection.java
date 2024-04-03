package org.kclhi.hands.seeker.singleshot.adaptable;

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
public class MaxDistanceAdaptableUpperInfection extends MaxDistanceAdaptable implements UpperInfectionGraphTraverser {

  public MaxDistanceAdaptableUpperInfection(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController, 1.0);
  }

  @Override
  public boolean useInfectionBonus() {
    return true;
  }
   
}
