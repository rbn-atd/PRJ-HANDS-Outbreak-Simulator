package org.kclhi.hands.seeker.singleshot.random;
import org.kclhi.hands.Disease.InfectionGraphTraverser;
import org.kclhi.hands.graph.GraphController;
import org.kclhi.hands.graph.StringEdge;
import org.kclhi.hands.graph.StringVertex;

/**
* 
* 
* @author Reuben
*
*/
public class RandomWalkInfection extends RandomWalk implements InfectionGraphTraverser {

  public RandomWalkInfection(GraphController<StringVertex, StringEdge> graphController) {
    super(graphController);
  }

  @Override
  public boolean useInfectionBonus() {
    return true;
  }
   
}
