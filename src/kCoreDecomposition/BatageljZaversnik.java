package kCoreDecomposition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class BatageljZaversnik<V, E> {

	private UndirectedSparseGraph<V, E> graph;
	
	public BatageljZaversnik(UndirectedSparseGraph<V, E> graph) {
		super();
		if(graph == null) {
			throw new IllegalArgumentException("Graph is null");
		}
		
		if(graph.getVertexCount() == 0) {
			throw new IllegalArgumentException("Graph doesn't have any nodes");
		}
		
		this.graph = graph;
	}

	public UndirectedSparseGraph<V, E> getGraph() {
		return graph;
	}

	public Map<V, Integer> decomposition() {
		int maxDegre = - 1;
		Map<V, Integer> nodeShell = new HashMap<>();
		for(V node : graph.getVertices()) {
			Integer currentDegree = graph.degree(node);
			nodeShell.put(node, currentDegree);
			if(currentDegree > maxDegre) maxDegre = currentDegree;
		}
		
		ArrayList<List<V>> nodeLists = new ArrayList<>(maxDegre + 1);
		for(int i = 0; i <= maxDegre; i++) {
			nodeLists.add(new LinkedList<>());
		}
				
		for (V node : graph.getVertices()) {
			int degree = graph.degree(node);
			nodeLists.get(degree).add(node);
		}
		
		for(int i = 0; i <= maxDegre; i++) {
			List<V> currentList = nodeLists.get(i);
			while(!currentList.isEmpty()) {
				V node = currentList.remove(0);
				for(V neighbour : graph.getNeighbors(node)) {
					int neighbourShell = nodeShell.get(neighbour);
					if(neighbourShell > i) {
						nodeLists.get(neighbourShell).remove(neighbour);
						nodeLists.get(neighbourShell - 1).add(neighbour);
						nodeShell.replace(neighbour, neighbourShell - 1);
					}
				}
			}
		}
		
		return nodeShell;
	}
	
	
	public UndirectedSparseGraph<V, E> kCore(int k) {
		Map<V, Integer> shellIndexes = decomposition();
		UndirectedSparseGraph<V, E> subGraph = new UndirectedSparseGraph<>();
		
		for(V vertex : graph.getVertices()) {
			subGraph.addVertex(vertex);
		}
		
		for(E edge : graph.getEdges()) {
			subGraph.addEdge(edge, graph.getIncidentVertices(edge));
		}
		
		//To remove
		shellIndexes = shellIndexes.entrySet().stream().filter(x -> x.getValue() < k)
				.collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));
		
		for(V vertex : shellIndexes.keySet()) {
			subGraph.removeVertex(vertex);
		}

		return subGraph;
	}
}
