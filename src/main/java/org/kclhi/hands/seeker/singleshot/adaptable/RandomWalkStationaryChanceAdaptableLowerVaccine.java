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
public class RandomWalkStationaryChanceAdaptableLowerVaccine extends RandomWalkStationaryChanceAdaptable implements LowerVaccineGraphTraverser {

  public RandomWalkStationaryChanceAdaptableLowerVaccine(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController);
  }

  @Override
  public boolean useVaccine() {
    return true;
  }
    
}
