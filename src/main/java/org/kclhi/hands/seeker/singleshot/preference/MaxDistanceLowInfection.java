package org.kclhi.hands.seeker.singleshot.preference;

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
public class MaxDistanceLowInfection extends MaxDistance implements LowInfectionGraphTraverser {

  public MaxDistanceLowInfection(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController, 1.0);
  }

  @Override
  public boolean useInfectionBonus() {
    return true;
  }
    
}
