package org.kclhi.hands.seeker.singleshot.preference;

import org.kclhi.hands.Vaccine.MediumVaccineGraphTraverser;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;

/**
* 
* 
* @author Martin
*
*/
public class MaxDistanceMediumVaccine extends MaxDistance implements MediumVaccineGraphTraverser {

  public MaxDistanceMediumVaccine(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController, 1.0);
  }

  @Override
  public boolean useVaccine() {
    return true;
  }
    
}
