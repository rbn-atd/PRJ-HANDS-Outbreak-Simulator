package org.kclhi.hands.seeker.singleshot.random;

import org.kclhi.hands.Vaccine.UpperVaccineGraphTraverser;
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
public class RandomWalkUpperVaccineResourceVaccinated extends RandomWalk implements UpperVaccineGraphTraverser, ResourceVaccinatedTraverser {

  public RandomWalkUpperVaccineResourceVaccinated(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController);
  }

  @Override
  public boolean useVaccine() {
    return true;
  }
    
}
