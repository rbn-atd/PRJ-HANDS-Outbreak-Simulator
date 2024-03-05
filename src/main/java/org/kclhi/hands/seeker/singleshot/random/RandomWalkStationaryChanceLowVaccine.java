package org.kclhi.hands.seeker.singleshot.random;

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
public class RandomWalkStationaryChanceLowVaccine extends RandomWalkStationaryChance implements LowVaccineGraphTraverser {

  public RandomWalkStationaryChanceLowVaccine(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController);
  }

  @Override
  public boolean useVaccine() {
    return true;
  }
    
}
