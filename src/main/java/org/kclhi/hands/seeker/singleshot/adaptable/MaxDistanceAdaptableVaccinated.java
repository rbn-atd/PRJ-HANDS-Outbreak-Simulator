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
public class MaxDistanceAdaptableVaccinated extends MaxDistanceAdaptable implements VaccinatedGraphTraverser {

  public MaxDistanceAdaptableVaccinated(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController, 1.0);
  }

  @Override
  public boolean useVaccine() {
    return true;
  }
   
}
