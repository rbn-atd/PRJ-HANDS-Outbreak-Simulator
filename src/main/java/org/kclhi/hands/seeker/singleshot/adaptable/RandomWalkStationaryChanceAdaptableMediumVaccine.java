package org.kclhi.hands.seeker.singleshot.adaptable;

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
public class RandomWalkStationaryChanceAdaptableMediumVaccine extends RandomWalkStationaryChanceAdaptable implements MediumVaccineGraphTraverser {

  public RandomWalkStationaryChanceAdaptableMediumVaccine(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController);
  }

  @Override
  public boolean useVaccine() {
    return true;
  }
    
}
