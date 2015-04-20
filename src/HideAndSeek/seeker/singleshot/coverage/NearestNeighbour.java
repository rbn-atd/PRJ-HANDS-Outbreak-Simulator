package HideAndSeek.seeker.singleshot.coverage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.jgrapht.alg.DijkstraShortestPath;

import HideAndSeek.graph.GraphController;
import HideAndSeek.graph.StringEdge;
import HideAndSeek.graph.StringVertex;
import HideAndSeek.seeker.SeekerLocalGraph;

/**
 * A 'greedy' strategy, which chooses the locally closest
 * unvisited vertex (from all those that can currently
 * be seen from the current node, and those that were 
 * viewed, but not visited, previously).
 * 
 * @author Martin
 */
public class NearestNeighbour extends SeekerLocalGraph {

	/**
	 * Viewed by unvisited nodes.
	 */
	protected HashSet<StringVertex> unvisitedNodes;
	
	/**
	 * Current lower cost path to a previous node.
	 */
	protected List<StringEdge> currentPath;
	
	/**
	 * @param graphController
	 */
	public NearestNeighbour(GraphController<StringVertex, StringEdge> graphController) {
		
		super(graphController);
		
		unvisitedNodes = new HashSet<StringVertex>();
		
		currentPath = new ArrayList<StringEdge>();

	}

	/* (non-Javadoc)
	 * @see HideAndSeek.GraphTraversingAgent#startNode()
	 */
	@Override
	public StringVertex startNode() {

		return randomNode();
	
	}
	
	/* (non-Javadoc)
	 * @see HideAndSeek.seeker.SeekerLocalGraph#getStatus()
	 */
	public String getStatus() {
		
		return super.getStatus() + "\nQueued Nodes " + unvisitedNodes + " (" + unvisitedNodes.size() + ")" +
								   "\nCurrent Path " + currentPath + " (" + currentPath.size() + ")"; 
		
	}
	
	/* (non-Javadoc)
	 * @see HideAndSeek.GraphTraverser#nextNode(HideAndSeek.graph.StringVertex)
	 */
	public StringVertex nextNode(StringVertex currentNode) {
		
		super.nextNode(currentNode);
		
		if ( unvisitedNodes.contains(currentNode) ) unvisitedNodes.remove(currentNode);
		
		// If not on way back to a closer, and previously unvisited, node:
		if ( currentPath.size() == 0 ) {
		
			for (StringEdge edge : getConnectedEdges(currentNode) ) {
				
				// Do not relist nodes as unvisited if they have already been visited
				if ( !exploredNodes.contains(edgeToTarget(edge, currentNode)) ) unvisitedNodes.add(edgeToTarget(edge, currentNode));
				
			}
			
			if ( unvisitedNodes.size() == 0 ) {
				
				System.out.println(this.getStatus());
				
				return connectedNode(currentNode);
				
			}
			
			StringVertex closestNode = (StringVertex) unvisitedNodes.toArray()[0];
			
			double shortestDistance = Double.MAX_VALUE;
			
			DijkstraShortestPath<StringVertex, StringEdge> dsp;
			
			// Check which node is closest: may be one adjacent, or one observed, but not visited, previously.
			for ( StringVertex unvisitedNode : unvisitedNodes ) {
				
				dsp = new DijkstraShortestPath<StringVertex, StringEdge>(localGraph, currentNode, unvisitedNode);
		    	
				if ( dsp.getPathLength() <= shortestDistance ) { 
					
					closestNode = unvisitedNode;
				
					shortestDistance = dsp.getPathLength();
					
				}
				
			}
			
			dsp = new DijkstraShortestPath<StringVertex, StringEdge>(localGraph, currentNode, closestNode);
			
			// If no path can be found, return adjacent node on lowest cost edge (ordered by methods below).
			if ( dsp.getPathEdgeList().size() == 0 ) return connectedNode(currentNode);
			
			currentPath = dsp.getPathEdgeList();
			
		} 
		
		return edgeToTarget(currentPath.remove(0), currentNode);
		
	}
	
	/* (non-Javadoc)
	 * @see HideAndSeek.seeker.FixedStartDepthFirstSearch#getConnectedEdges(HideAndSeek.graph.StringVertex)
	 */
	protected List<StringEdge> getConnectedEdges(StringVertex currentNode) {
		
		ArrayList<StringEdge> edges = new ArrayList<StringEdge>(super.getConnectedEdges(currentNode));
		
		Collections.sort(edges);
		
		return edges;
		
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
	
}