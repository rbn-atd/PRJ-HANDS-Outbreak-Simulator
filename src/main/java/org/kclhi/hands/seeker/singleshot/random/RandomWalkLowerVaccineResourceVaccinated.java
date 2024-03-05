package org.kclhi.hands.seeker.singleshot.random;

import org.kclhi.hands.Vaccine.LowerVaccineGraphTraverser;
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
public class RandomWalkLowerVaccineResourceVaccinated extends RandomWalk implements LowerVaccineGraphTraverser, ResourceVaccinatedTraverser {

  public RandomWalkLowerVaccineResourceVaccinated(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController);
  }

  @Override
  public boolean useVaccine() {
    return true;
  }
    
}
