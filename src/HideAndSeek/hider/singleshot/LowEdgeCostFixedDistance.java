package HideAndSeek.hider.singleshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import HideAndSeek.graph.GraphController;
import HideAndSeek.graph.StringEdge;
import HideAndSeek.graph.StringVertex;

/**
 * Orders edges according to lowest cost
 * 
 * @author Martin
 *
 */
public class LowEdgeCostFixedDistance extends RandomFixedDistance {

	/**
	 * @param graph
	 */
	public LowEdgeCostFixedDistance(GraphController <StringVertex, StringEdge> graphController, int numberOfHideLocations) {
		
		super(graphController, numberOfHideLocations);
		
	}

	/* (non-Javadoc)
	 * @see HideAndSeek.GraphTraverser#getConnectedEdge(HideAndSeek.graph.StringVertex, java.util.List)
	 */
	@Override
	protected StringEdge getConnectedEdge(StringVertex currentNode, List<StringEdge> connectedEdges) {
		
		for (StringEdge edge : connectedEdges ) {

			if ( uniquelyVisitedNodes().contains(edgeToTarget(edge, currentNode)) ) continue;
			
			return edge;
			
		}
		
		return connectedEdges.get((int)(Math.random() * connectedEdges.size()));
		
	}

	/* (non-Javadoc)
	 * @see HideAndSeek.GraphTraverser#getConnectedEdges(HideAndSeek.graph.StringVertex)
	 */
	protected List<StringEdge> getConnectedEdges(StringVertex currentNode) {
		
		ArrayList<StringEdge> edges = new ArrayList<StringEdge>(graphController.edgesOf(currentNode));
		
		Collections.sort(edges);
		
		return edges;
		
	}

}
