package org.kclhi.hands.seeker.singleshot.adaptable;

import org.kclhi.hands.Vaccine.VaccinatedGraphTraverser;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;

/**
* 
* 
* @author Reuben
*
*/

public class RandomWalkAdaptableVaccinated extends RandomWalkAdaptable implements VaccinatedGraphTraverser{
    public RandomWalkAdaptableVaccinated(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController);
  }

  @Override
  public boolean useVaccine() {
    return true;
  }
}
