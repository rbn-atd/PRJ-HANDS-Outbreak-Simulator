package org.kclhi.hands.seeker.singleshot.adaptable;

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
public class MaxDistanceAdaptableLowVaccine extends MaxDistanceAdaptable implements LowVaccineGraphTraverser {

  public MaxDistanceAdaptableLowVaccine(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController, 1.0);
  }

  @Override
  public boolean useVaccine() {
    return true;
  }
    
}
