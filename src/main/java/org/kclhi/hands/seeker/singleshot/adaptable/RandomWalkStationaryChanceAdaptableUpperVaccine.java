package org.kclhi.hands.seeker.singleshot.adaptable;

import org.kclhi.hands.Vaccine.UpperVaccineGraphTraverser;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;

/**
* 
* 
* @author Martin
*
*/
public class RandomWalkStationaryChanceAdaptableUpperVaccine extends RandomWalkStationaryChanceAdaptable implements UpperVaccineGraphTraverser {

  public RandomWalkStationaryChanceAdaptableUpperVaccine(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController);
  }

  @Override
  public boolean useVaccine() {
    return true;
  }
   
}
