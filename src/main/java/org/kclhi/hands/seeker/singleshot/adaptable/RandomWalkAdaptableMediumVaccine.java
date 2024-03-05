package org.kclhi.hands.seeker.singleshot.adaptable;

import org.kclhi.hands.Vaccine.MediumVaccineGraphTraverser;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;

/**
* 
* 
* @author Martin
*
*/
public class RandomWalkAdaptableMediumVaccine extends RandomWalkAdaptable implements MediumVaccineGraphTraverser {

  public RandomWalkAdaptableMediumVaccine(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController);
  }

  @Override
  public boolean useVaccine() {
    return true;
  }
    
}
