package corePeripheryModel;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;

public class CorePeripheryGraph<V, E> {

	private UndirectedSparseGraph<V, E> graph;

	public CorePeripheryGraph(Transformer<Integer, V> vertexTransformer, Transformer<String, E> edgeTransformer) {
		this(vertexTransformer, edgeTransformer, new double[]{0.02, 0.001, 0.005}, 0.9, 2000);
	}

	public CorePeripheryGraph(Transformer<Integer, V> vertexTransformer, Transformer<String, E> edgeTransformer,
			double[] probabilityArray, double corePercentage, int numberOfVertecies) {
		super();
		this.graph = new UndirectedSparseGraph<>();
		int amountOfVerteciesInCore =  (int) Math.floor(corePercentage * numberOfVertecies);
		List<V> coreVertices = new LinkedList<>();
		List<V> peripheryVertecies = new LinkedList<>();
		for(int i = 0; i < amountOfVerteciesInCore; i++) {
			V vertex = vertexTransformer.transform(i);
			coreVertices.add(vertex);
			graph.addVertex(vertex);
		}
	
		for(int i = amountOfVerteciesInCore; i < numberOfVertecies; i++) {
			V vertex = vertexTransformer.transform(i);
			peripheryVertecies.add(vertex);
			graph.addVertex(vertex);
		}

		List<V> allVertices = new LinkedList<>();
		allVertices.addAll(coreVertices);
		allVertices.addAll(peripheryVertecies);

		Random rnd = new Random();
		for(int i = 0 ; i < numberOfVertecies; i++) {
			V currentVertex = allVertices.get(i);
			
			if(graph.degree(currentVertex) == 0) {
				if(coreVertices.contains(currentVertex)) {
					int neighobur = -1;
					V neig = null;
					do {
						neighobur = rnd.nextInt(0, coreVertices.size());
						neig = coreVertices.get(neighobur);
					} while(neig.equals(currentVertex));				
					E edge = edgeTransformer.transform(currentVertex + " " + neig);
					graph.addEdge(edge, currentVertex, neig, EdgeType.UNDIRECTED);	
				} else {
					int neighobur = -1;
					V neig = null;
					do {
						neighobur = rnd.nextInt(0, peripheryVertecies.size());
						neig = peripheryVertecies.get(neighobur);
					} while(neig.equals(currentVertex));
					E edge = edgeTransformer.transform(currentVertex + " " + neig);
					graph.addEdge(edge, currentVertex, neig, EdgeType.UNDIRECTED);
				}
			}
			
			for(int j = i + 1; j < numberOfVertecies; j++) {
				V tempVertex = allVertices.get(j);
				if(coreVertices.contains(currentVertex) && coreVertices.contains(tempVertex)) {
					if(Math.random() <= probabilityArray[0]) {
						E edge2 = edgeTransformer.transform(currentVertex + " " + tempVertex);
						graph.addEdge(edge2, currentVertex, tempVertex, EdgeType.UNDIRECTED);
					}
				} else if(peripheryVertecies.contains(currentVertex) && peripheryVertecies.contains(tempVertex)) {
					if(Math.random() <= probabilityArray[1]) {
						E edge = edgeTransformer.transform(currentVertex + " " + tempVertex);
						graph.addEdge(edge, currentVertex, tempVertex, EdgeType.UNDIRECTED);
					}
				} else {
					if(Math.random() <= probabilityArray[2]) {
						E edge = edgeTransformer.transform(currentVertex + " " + tempVertex);
						graph.addEdge(edge, currentVertex, tempVertex, EdgeType.UNDIRECTED);
					}
				}
			}
		}
	}

	public UndirectedSparseGraph<V, E> getGraph() {
		return graph;
	}	
}
