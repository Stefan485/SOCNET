package erdosRenyi;

import java.util.LinkedList;
import java.util.Random;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;

public class ErdosRenyi<V, E> {

	private UndirectedSparseGraph<V, E> graph;

	public ErdosRenyi(int numberOfNodes, double probability,
			Transformer<Integer, V> vertexTransformer, Transformer<String, E> edgeTransformer) {
		super();
		LinkedList<V> vertexList = new LinkedList<>();
		this.graph = new UndirectedSparseGraph<>();
		for(int i = 0; i < numberOfNodes; i++) {
			V vertex = vertexTransformer.transform(i);
			this.graph.addVertex(vertex);
			vertexList.add(vertex);
		}
		
		for(int i = 0; i < numberOfNodes; i++) {
			 V currentVertex = vertexList.get(i);

			 Random randomGenerator = new Random();
			 if(graph.degree(currentVertex) == 0) {
				 int randomIndex = -1;
				 do {
					 randomIndex = randomGenerator.nextInt(numberOfNodes);
				 } while(randomIndex == i);
				 
				V randomVertex = vertexList.get(randomIndex);
				graph.addEdge(edgeTransformer.transform(currentVertex + " : " + randomVertex),
						currentVertex, randomVertex,  EdgeType.UNDIRECTED); 
			 }
			
			for(int j = i + 1; j < numberOfNodes; j++) {
				if(Math.random() <= probability) {
					V secondVertex = vertexList.get(j);
					graph.addEdge(edgeTransformer.transform(currentVertex + " " + secondVertex ),
							currentVertex, secondVertex,  EdgeType.UNDIRECTED);
				}
			}
		}
	}

	public ErdosRenyi(Transformer<Integer, V> vertexTransformer, Transformer<String, E> edgeTransformer) {
		this(2000, 0.01, vertexTransformer, edgeTransformer);
	}

	public UndirectedSparseGraph<V, E> getGraph() {
		return graph;
	}
}
