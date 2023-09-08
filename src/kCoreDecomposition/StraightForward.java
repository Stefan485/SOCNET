package kCoreDecomposition;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class StraightForward<V, E> {

	private UndirectedSparseGraph<V, E> graph;
	private UndirectedSparseGraph<V, E> graphForCore;
	
	public StraightForward(UndirectedSparseGraph<V, E> graph) {
		super();
		
		if(graph == null) throw new IllegalArgumentException("Graph is null");
		
		if(graph.getVertexCount() == 0) throw new IllegalArgumentException("Graph doesn't have any nodes");
		this.graph = graph;
		
		this.graph = new UndirectedSparseGraph<>();
		this.graphForCore = new UndirectedSparseGraph<>();
		Iterator<V> vertexIterator = graph.getVertices().iterator();
		while(vertexIterator.hasNext()) {
			V vertex = vertexIterator.next();
			this.graph.addVertex(vertex);
		}
		
		Iterator<E> edgeIterator = graph.getEdges().iterator();
		while(edgeIterator.hasNext()) {
			E edge = edgeIterator.next();
			this.graph.addEdge(edge, graph.getIncidentVertices(edge));
		}
	}

	public Map<V, Integer> decomposition() {		
		int maxDegree = -1;
		Iterator<V> vertices = graph.getVertices().iterator();
		while(vertices.hasNext()) {
			V vertex = vertices.next();
			int currentDegree = graph.degree(vertex);
			if(currentDegree > maxDegree) maxDegree = currentDegree; 
		}

		Map<V, Integer> vertexShell = new HashMap<>();
		
		int i = 0;
		while(graph.getVertexCount() > 0 &&  i <= maxDegree + 1) {
			List<V> removed = findCore(i);
			for(V vertex : removed) {
				vertexShell.put(vertex, i - 1);
			}
			i++;
		}
 		
		return vertexShell;
	}
	
	private void removeAllFromGraph(List<V> verticesToRemove, UndirectedSparseGraph<V, E> removeFrom) {
		for (V v : verticesToRemove) {
			removeFrom.removeVertex(v);
		}
	}
	
	private List<V> findCore(int shellIndex) {
		List<V> core = new LinkedList<>();
		boolean foundAny = false;
		do {
			System.gc();
			foundAny = false;
			for(V vertex : graph.getVertices()) {
				if(graph.degree(vertex) < shellIndex) {
					core.add(vertex);
					foundAny = true;
				}
			}
			
			removeAllFromGraph(core, graph);
		} while(foundAny);

		return core;
	}
	
	public UndirectedSparseGraph<V, E> kCore(int k) {
		boolean removedAny = false;
		do {
			System.gc();
			List<V> toRemove = new LinkedList<>();
			Iterator<V> vertexIterator = graphForCore.getVertices().iterator();
			while(vertexIterator.hasNext()) {
				V vertex = vertexIterator.next();
				if(graphForCore.degree(vertex) < k) {
					toRemove.add(vertex);
					removedAny = true;
				}
			}
			removeAllFromGraph(toRemove, graphForCore);
			
		} while(!removedAny);
		
		return graph;
	}
}
