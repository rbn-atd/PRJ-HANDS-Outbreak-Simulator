package org.kclhi.hands.seeker.singleshot.adaptable;

import org.kclhi.hands.Vaccine.LowerVaccineGraphTraverser;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;

/**
* 
* 
* @author Martin
*
*/
public class RandomWalkAdaptableLowerVaccine extends RandomWalkAdaptable implements LowerVaccineGraphTraverser {

  public RandomWalkAdaptableLowerVaccine(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController);
  }

  @Override
  public boolean useVaccine() {
    return true;
  }
    
}
