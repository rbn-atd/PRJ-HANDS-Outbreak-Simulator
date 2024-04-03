package org.kclhi.hands.seeker.singleshot.adaptable;

import org.kclhi.hands.Disease.LowerInfectionGraphTraverser;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;

/**
* 
* 
* @author Martin
*
*/
public class MaxDistanceAdaptableLowerInfection extends MaxDistanceAdaptable implements LowerInfectionGraphTraverser {

  public MaxDistanceAdaptableLowerInfection(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController, 1.0);
  }

  @Override
  public boolean useInfectionBonus() {
    return true;
  }
    
}
