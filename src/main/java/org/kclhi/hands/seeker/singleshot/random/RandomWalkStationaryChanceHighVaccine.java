package org.kclhi.hands.seeker.singleshot.random;

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
public class RandomWalkStationaryChanceHighVaccine extends RandomWalkStationaryChance implements HighVaccineGraphTraverser {

  public RandomWalkStationaryChanceHighVaccine(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController);
  }

  @Override
  public boolean useVaccine() {
    return true;
  }
   
}
