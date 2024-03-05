package org.kclhi.hands.seeker.singleshot.random;

import org.kclhi.hands.Vaccine.MediumVaccineGraphTraverser;
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
public class RandomWalkMediumVaccineResourceVaccinated extends RandomWalk implements MediumVaccineGraphTraverser, ResourceVaccinatedTraverser {

  public RandomWalkMediumVaccineResourceVaccinated(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController);
  }

  @Override
  public boolean useVaccine() {
    return true;
  }
    
}
