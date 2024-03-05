package org.kclhi.hands.seeker.singleshot.random;

import org.kclhi.hands.Vaccine.HighVaccineGraphTraverser;
import org.kclhi.hands.Success.ResourceVaccinatedTraverser;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;

/**
* 
* 
* @author Martin
*
*/
public class RandomWalkHighVaccineResourceVaccinated extends RandomWalk implements HighVaccineGraphTraverser, ResourceVaccinatedTraverser {

  public RandomWalkHighVaccineResourceVaccinated(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController);
  }

  @Override
  public boolean useVaccine() {
    return true;
  }
    
}
