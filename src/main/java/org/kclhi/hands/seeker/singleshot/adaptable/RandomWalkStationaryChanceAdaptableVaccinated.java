package org.kclhi.hands.seeker.singleshot.adaptable;

import org.kclhi.hands.Vaccine.VaccinatedGraphTraverser;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;

/**
* 
* 
* @author Martin
*
*/
public class RandomWalkStationaryChanceAdaptableVaccinated extends RandomWalkStationaryChanceAdaptable implements VaccinatedGraphTraverser {

  public RandomWalkStationaryChanceAdaptableVaccinated(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController);
  }

  @Override
  public boolean useVaccine() {
    return true;
  }
   
}
