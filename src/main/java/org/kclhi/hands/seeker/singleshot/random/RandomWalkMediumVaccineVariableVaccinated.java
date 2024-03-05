package org.kclhi.hands.seeker.singleshot.random;

import org.kclhi.hands.Gas.MediumGasGraphTraverser;
import org.kclhi.hands.Success.VariableVaccinatedTraverser;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;

/**
* 
* 
* @author Martin
*
*/
public class RandomWalkMediumVaccineVariableVaccinated extends RandomWalk implements MediumGasGraphTraverser, VariableVaccinatedTraverser {

  public RandomWalkMediumVaccineVariableVaccinated(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController);
  }

  @Override
  public boolean useGas() {
    return true;
  }
    
}
