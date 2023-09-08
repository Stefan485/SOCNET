package metrike;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import edu.uci.ics.jung.algorithms.shortestpath.UnweightedShortestPath;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class ClosenessVertexCentrality {

	private UndirectedSparseGraph<Integer, String> graph;
	private double vertexCount;

	private Map<Integer, Double> scores;
	
	public ClosenessVertexCentrality(UndirectedSparseGraph<Integer, String> graph) {
		super();
		this.graph = graph;
		this.vertexCount = graph.getVertexCount();
		this.scores = new HashMap<>();
		calculate();
	}
	
	public Map<Integer, Double> getScores() {
		return scores;
	}

	private void calculate() {
		for(Integer currentVertex : graph.getVertices()) {
			double currentScore = distanceToAll(currentVertex);
			
			currentScore = (vertexCount - 1) / currentScore;
			scores.put(currentVertex, currentScore);
		}
	}
	
	private double distanceToAll(Integer vertex) {
		UnweightedShortestPath<Integer, String> unSP = new UnweightedShortestPath<>(graph);
		Map<Integer, Number> wrSet = unSP.getDistanceMap(vertex);
		double distance = 0;
		
		for(Integer other : wrSet.keySet()) {
			distance += (int) wrSet.get(other);
		}
		
		return distance;
	}
}
