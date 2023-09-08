package barabasiAlbert;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import erdosRenyi.ErdosRenyi;

public class BarabasiAlbert<V, E> {
	
	private UndirectedSparseGraph<V, E> graph;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BarabasiAlbert(int numberOfNodes, int startingNumberOfNodes, double probability, int newConnections,
			Transformer<Integer, V> vertexTransformer, Transformer<String, E> edgeTransformer) {
		
		super();
		//Izuzetak dodat da se osigura da svaki čvor mora imati barem 1 suseda
		if(newConnections <= 0) throw new IllegalArgumentException("New node must add at least 1 new connection");
		
		this.graph = new ErdosRenyi(startingNumberOfNodes, probability, vertexTransformer, edgeTransformer).getGraph();
		
		List<V> verteces = new LinkedList<>();
		List<V> degreeList = new LinkedList<>();
		Iterator<V> vertexIterator = graph.getVertices().iterator();
		while(vertexIterator.hasNext()) {
			V vertex = vertexIterator.next();
			verteces.add(vertex);
			for(int i = 0; i < graph.degree(vertex); i++) {
				degreeList.add(vertex);
			}
		}
		
		Random rnd = new Random();		
		for(int i = startingNumberOfNodes; i < numberOfNodes; i++) {
			V vertex = vertexTransformer.transform(i);
			graph.addVertex(vertex);
			
			for(int j = 0; j < newConnections; j++) {
				int neighbourIndex = rnd.nextInt(verteces.size());
				V neighbour = verteces.get(neighbourIndex);
				E edge = edgeTransformer.transform(vertex +  " " + neighbour);
				graph.addEdge(edge, vertex, neighbour);
				degreeList.add(neighbour);
			}
			
			verteces.add(vertex);
			for(int j = 0; j < graph.degree(vertex); j++) {
				degreeList.add(vertex);
			}
		}
	}

	public BarabasiAlbert(Transformer<Integer, V> vertexTransformer, Transformer<String, E> edgeTransformer) {
		this(2000, 10, 0.01, 7, vertexTransformer, edgeTransformer);
	}
	
	public UndirectedSparseGraph<V, E> getGraph(){
		return graph;
	}
}
