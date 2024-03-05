package org.kclhi.hands.seeker.singleshot.preference;

import org.kclhi.hands.Vaccine.LowVaccineGraphTraverser;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;

/**
* 
* 
* @author Martin
*
*/
public class MaxDistanceLowVaccine extends MaxDistance implements LowVaccineGraphTraverser {

  public MaxDistanceLowVaccine(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController, 1.0);
  }

  @Override
  public boolean useVaccine() {
    return true;
  }
    
}
