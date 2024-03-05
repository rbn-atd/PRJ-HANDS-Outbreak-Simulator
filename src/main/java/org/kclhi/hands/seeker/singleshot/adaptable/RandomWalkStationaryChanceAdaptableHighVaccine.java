package org.kclhi.hands.seeker.singleshot.adaptable;

import org.kclhi.hands.Vaccine.HighVaccineGraphTraverser;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;

/**
* 
* 
* @author Martin
*
*/
public class RandomWalkStationaryChanceAdaptableHighVaccine extends RandomWalkStationaryChanceAdaptable implements HighVaccineGraphTraverser {

  public RandomWalkStationaryChanceAdaptableHighVaccine(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController);
  }

  @Override
  public boolean useVaccine() {
    return true;
  }
   
}
