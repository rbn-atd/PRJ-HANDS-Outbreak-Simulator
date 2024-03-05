package org.kclhi.hands.seeker.singleshot.random;
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
public class RandomWalkVaccinated extends RandomWalk implements VaccinatedGraphTraverser {

  public RandomWalkVaccinated(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController);
  }

  @Override
  public boolean useVaccine() {
    return true;
  }
   
}
