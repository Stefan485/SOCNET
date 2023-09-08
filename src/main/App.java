package main;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;

import barabasiAlbert.BarabasiAlbert;
import corePeripheryModel.CorePeripheryGraph;
import data.NodeInfo;
import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;
import edu.uci.ics.jung.algorithms.metrics.Metrics;
import edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality;
import edu.uci.ics.jung.algorithms.scoring.ClosenessCentrality;
import edu.uci.ics.jung.algorithms.scoring.EigenvectorCentrality;
import edu.uci.ics.jung.algorithms.shortestpath.UnweightedShortestPath;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import erdosRenyi.ErdosRenyi;
import graphIO.GraphIO;
import kCoreDecomposition.BatageljZaversnik;
import kCoreDecomposition.StraightForward;
import metrike.ClosenessVertexCentrality;

import static main.Transformers.*;
import static java.util.stream.Collectors.*;


/**
 * @author Stefan Obradović 287/21
 * Vm parametar: -Xmx32768m
 * Verzija jave: 19
 * Appache commons math: 3.6.1
 * 
 * Sve što se postavlja na null radi se zbog oslobađanja memorije
 */
public class App {
	
	public static void main(String[] args) throws IOException, InterruptedException {			
	
//		Testiranje tačnosti algoritma
//		UndirectedSparseGraph<Integer, String> graph = createSmallGraph1();		
//		GraphIO.saveGraph("smallGraph1.graphml", graph);
//		BatageljZaversnik<Integer, String> bz = new BatageljZaversnik<>(graph);
//		StraightForward<Integer, String> sf = new StraightForward<>(graph);
//		Map<Integer, Integer> bzScores = bz.decomposition();
//		Map<Integer, Integer> sfScores = sf.decomposition();
//		
//		List<NodeInfo> nodeInfos = new LinkedList<>();
//		for(Integer vertex : graph.getVertices()) {
//			NodeInfo ni = new NodeInfo(vertex, 0, 0, 0, 0, bzScores.get(vertex), sfScores.get(vertex));
//			nodeInfos.add(ni);
//		}
//
//		GraphIO.saveSmallGraphShell("smallGraph1.txt", nodeInfos);
//		
//		graph = createSmallGraph2();		
//		GraphIO.saveGraph("smallGraph2.graphml", graph);
//		bz = new BatageljZaversnik<>(graph);
//		sf = new StraightForward<>(graph);
//		bzScores = bz.decomposition();
//		sfScores = sf.decomposition();
//		
//		nodeInfos = new LinkedList<>();
//		for(Integer vertex : graph.getVertices()) {
//			NodeInfo ni = new NodeInfo(vertex, 0, 0, 0, 0, bzScores.get(vertex), sfScores.get(vertex));
//			nodeInfos.add(ni);
//		}
//		
//		GraphIO.saveSmallGraphShell("smallGraph2.txt", nodeInfos); 	
//
//		graph = createSmallGraph3();	
//		GraphIO.saveGraph("smallGraph3.graphml", graph);
//		bz = new BatageljZaversnik<>(graph);
//		sf = new StraightForward<>(graph);
//		bzScores = bz.decomposition();
//		sfScores = sf.decomposition();
//		
//		nodeInfos = new LinkedList<>();
//		for(Integer vertex : graph.getVertices()) {
//			NodeInfo ni = new NodeInfo(vertex, 0, 0, 0, 0, bzScores.get(vertex), sfScores.get(vertex));
//			nodeInfos.add(ni);
//		}
//
//		GraphIO.saveSmallGraphShell("smallGraph3.txt", nodeInfos);

//		Realne mreže
//		LocalDateTime lcd = LocalDateTime.now();
//		System.out.println(lcd);
//		UndirectedSparseGraph<Integer, String> graph = GraphIO.reeadInInfoEdgeList("Email-Enron.txt");
//		testReal("EmailEnron", graph);
//		lcd = LocalDateTime.now();
//		System.out.println(lcd);	
//		
//		lcd = LocalDateTime.now();
//		System.out.println(lcd);
//		graph = GraphIO.readInFromCSV("musae_git_edges.csv");
//		testReal("MusaeGit", graph);
//		lcd = LocalDateTime.now();
//		System.out.println(lcd);	
//		
//		
//		lcd = LocalDateTime.now();
//		System.out.println(lcd);
//		graph = GraphIO.readInCond("cond-mat-2005.gml");
//		testReal("CondMat", graph);
//		lcd = LocalDateTime.now();
//		System.out.println(lcd);	
//		
//		lcd = LocalDateTime.now();
//		System.out.println(lcd);
//		graph = GraphIO.readInAsEdgeList("collaboration.edgelist.txt");
//		testReal("CollaborationNet", graph);
//		lcd = LocalDateTime.now();
//		System.out.println(lcd);	
		
//		Generisanje po modelu
//		LocalDateTime lcd = LocalDateTime.now();
//		System.out.println(lcd);
//		for(int i = 0; i < 50; i++) {
//			testErdos(i);
//			System.gc();
//			testBarabasi(i);
//			System.gc();
//			testCorePeriphery(i);
//			System.gc();
//		}
//		lcd = LocalDateTime.now();
//		System.out.println(lcd);

//		Primer čuvanja podataka u tabelu za k-jezgra
//		Korišteno po potrebi za izabrane mreže
//		GraphIO.saveKcoreData("GraphsGenerated\\BarabasiAlbert\\Graph17\\Cores");
	
	}
	
	@SuppressWarnings("unused")
	private static UndirectedSparseGraph<Integer, String> createSmallGraph1(){
		UndirectedSparseGraph<Integer, String> graph = new UndirectedSparseGraph<>();
		for(int i = 1; i < 20; i++) {
			graph.addVertex(i);
		}
		
		graph.addEdge(1 + " " + 4, 1, 4, EdgeType.UNDIRECTED);
		graph.addEdge(2 + " " + 4, 2, 4, EdgeType.UNDIRECTED);
		graph.addEdge(3 + " " + 4, 3, 4, EdgeType.UNDIRECTED);
		graph.addEdge(5 + " " + 4, 5, 4, EdgeType.UNDIRECTED);
		graph.addEdge(6 + " " + 4, 6, 4, EdgeType.UNDIRECTED);
		graph.addEdge(7 + " " + 4, 7, 4, EdgeType.UNDIRECTED);
		graph.addEdge(10 + " " + 4, 10, 4, EdgeType.UNDIRECTED);
		graph.addEdge(10 + " " + 8, 10, 8, EdgeType.UNDIRECTED);
		graph.addEdge(8 + " " + 9, 8, 9, EdgeType.UNDIRECTED);
		graph.addEdge(10 + " " + 11, 10, 11, EdgeType.UNDIRECTED);
		graph.addEdge(9 + " " + 11, 9, 11, EdgeType.UNDIRECTED);
		graph.addEdge(11 + " " + 13, 11, 13, EdgeType.UNDIRECTED);
		graph.addEdge(12 + " " + 13, 12, 13, EdgeType.UNDIRECTED);
		graph.addEdge(5 + " " + 14, 5, 14, EdgeType.UNDIRECTED);
		graph.addEdge(15 + " " + 14, 15, 14, EdgeType.UNDIRECTED);
		graph.addEdge(15 + " " + 16, 15, 16, EdgeType.UNDIRECTED);
		graph.addEdge(14 + " " + 17, 14, 17, EdgeType.UNDIRECTED);
		graph.addEdge(14 + " " + 18, 14, 18, EdgeType.UNDIRECTED);
		graph.addEdge(18 + " " + 19, 18, 19, EdgeType.UNDIRECTED);

		
		return graph;
	}
	
	@SuppressWarnings("unused")
	private static UndirectedSparseGraph<Integer, String> createSmallGraph2(){
		UndirectedSparseGraph<Integer, String> graph = new UndirectedSparseGraph<>();
		for(int i = 1; i < 16; i++) {
			graph.addVertex(i);
		}
		
		graph.addEdge(1 + " " + 2, 1, 2, EdgeType.UNDIRECTED);
		graph.addEdge(1 + " " + 5, 1, 5, EdgeType.UNDIRECTED);
		graph.addEdge(2 + " " + 3, 2, 3, EdgeType.UNDIRECTED);
		graph.addEdge(3 + " " + 4, 3, 4, EdgeType.UNDIRECTED);
		graph.addEdge(4 + " " + 5, 4, 5, EdgeType.UNDIRECTED);
		graph.addEdge(1 + " " + 10, 1, 10, EdgeType.UNDIRECTED);
		graph.addEdge(1 + " " + 6, 1, 6, EdgeType.UNDIRECTED);
		graph.addEdge(2 + " " + 7, 2, 7, EdgeType.UNDIRECTED);
		graph.addEdge(3 + " " + 8, 3, 8, EdgeType.UNDIRECTED);
		graph.addEdge(4 + " " + 9, 4, 9, EdgeType.UNDIRECTED);
		graph.addEdge(6 + " " + 7, 6, 7, EdgeType.UNDIRECTED);
		graph.addEdge(6 + " " + 16, 6, 16, EdgeType.UNDIRECTED);
		graph.addEdge(6 + " " + 13, 6, 13, EdgeType.UNDIRECTED);
		graph.addEdge(7 + " " + 15, 7, 15, EdgeType.UNDIRECTED);
		graph.addEdge(7 + " " + 8, 7, 8, EdgeType.UNDIRECTED);
		graph.addEdge(15 + " " + 16, 15, 16, EdgeType.UNDIRECTED);
		graph.addEdge(8 + " " + 9, 8, 9, EdgeType.UNDIRECTED);
		graph.addEdge(9 + " " + 10, 9, 10, EdgeType.UNDIRECTED);
		graph.addEdge(10 + " " + 14, 10, 14, EdgeType.UNDIRECTED);
		graph.addEdge(10 + " " + 11, 10, 11, EdgeType.UNDIRECTED);
		graph.addEdge(10 + " " + 12, 10, 12, EdgeType.UNDIRECTED);
		
		return graph;
	}
	
	@SuppressWarnings("unused")
	private static UndirectedSparseGraph<Integer, String> createSmallGraph3(){
		UndirectedSparseGraph<Integer, String> graph = new UndirectedSparseGraph<>();
		for(int i = 1; i <= 17; i++) {
			graph.addVertex(i);
		}
		
		
		graph.addEdge(1 + " " + 2, 1, 2, EdgeType.UNDIRECTED);
		graph.addEdge(1 + " " + 3, 1, 3, EdgeType.UNDIRECTED);
		graph.addEdge(1 + " " + 4, 1, 4, EdgeType.UNDIRECTED);
		graph.addEdge(1 + " " + 5, 1, 5, EdgeType.UNDIRECTED);
		graph.addEdge(2 + " " + 7, 2, 7, EdgeType.UNDIRECTED);
		graph.addEdge(2 + " " + 11, 2, 11, EdgeType.UNDIRECTED);
		graph.addEdge(2 + " " + 6, 2, 6, EdgeType.UNDIRECTED);
		graph.addEdge(3 + " " + 7, 3, 7, EdgeType.UNDIRECTED);
		graph.addEdge(3 + " " + 12, 1, 12, EdgeType.UNDIRECTED);
		graph.addEdge(3 + " " + 8, 3, 8, EdgeType.UNDIRECTED);
		graph.addEdge(4 + " " + 8, 4, 8, EdgeType.UNDIRECTED);
		graph.addEdge(4 + " " + 14, 4, 14, EdgeType.UNDIRECTED);
		graph.addEdge(4 + " " + 9, 4, 9, EdgeType.UNDIRECTED);
		graph.addEdge(5 + " " + 10, 5, 10, EdgeType.UNDIRECTED);
		graph.addEdge(5 + " " + 9, 5, 9, EdgeType.UNDIRECTED);
		graph.addEdge(5 + " " + 6, 5, 6, EdgeType.UNDIRECTED);
		graph.addEdge(6 + " " + 11, 6, 11, EdgeType.UNDIRECTED);
		graph.addEdge(6 + " " + 16, 6, 16, EdgeType.UNDIRECTED);
		graph.addEdge(6 + " " + 13, 6, 13, EdgeType.UNDIRECTED);
		graph.addEdge(16 + " " + 13, 16, 13, EdgeType.UNDIRECTED);
		graph.addEdge(13 + " " + 9, 13, 9, EdgeType.UNDIRECTED);
		graph.addEdge(11 + " " + 15, 11, 15, EdgeType.UNDIRECTED);
		graph.addEdge(7 + " " + 11, 7, 11, EdgeType.UNDIRECTED);
		graph.addEdge(15 + " " + 12, 15, 12, EdgeType.UNDIRECTED);
		graph.addEdge(12 + " " + 14, 12, 14, EdgeType.UNDIRECTED);
		graph.addEdge(8 + " " + 12, 8, 12, EdgeType.UNDIRECTED);
		graph.addEdge(8 + " " + 14, 8, 14, EdgeType.UNDIRECTED);
		graph.addEdge(10 + " " + 9, 10, 9, EdgeType.UNDIRECTED);
		graph.addEdge(17 + " " + 15, 17, 15, EdgeType.UNDIRECTED);
		graph.addEdge(17 + " " + 12, 17, 12, EdgeType.UNDIRECTED);
		graph.addEdge(17 + " " + 3, 17, 3, EdgeType.UNDIRECTED);
		graph.addEdge(17 + " " + 7, 17, 7, EdgeType.UNDIRECTED);
		graph.addEdge(17 + " " + 11, 17, 11, EdgeType.UNDIRECTED);

		return graph;
	}
	
	
	/*
	 * Funkcije: test* (gde zvezda menja Real, Erdos, Barabasi, CorePeriphery) koriste se za pokretanje svih
	 * ostalih funkcija za računanje metrika za dati graf
	 */
	public static void testReal(String name, UndirectedSparseGraph<Integer, String> graph) {
		File file = new File("realNetwork/" + name);
		file.mkdir();
		file = new File("realNetwork/" + name + "/Cores");
		file.mkdir();
		file = null;
		
		GraphIO.saveGraph("realNetwork/" + name + "/" + name + ".graphml", graph);
		GraphIO.saveDegreeDistribution(graph, "realNetwork/" + name + "/degreeDistribution.csv");
		
		graphAndNodeData(graph, "realNetwork/" + name);			
		
		BatageljZaversnik<Integer, String> bz = new BatageljZaversnik<>(graph);
		
		Set<Integer> cores = bz.decomposition().entrySet()
				.stream().map(x -> x.getValue()).distinct()
				.sorted().collect(toSet());
		
		for(Integer core : cores) {
			graphData(bz.kCore(core),
					"realNetwork/" + name + "/Cores" + "/Core" + core + ".txt");
		}
	}
	
	public static void testErdos(int generated) {
		File file = new File("GraphsGenerated/ErdosRenyi/Graph" + generated);
		file.mkdir();
		file = new File("GraphsGenerated/ErdosRenyi/Graph" + generated + "/Cores");
		file.mkdir();
		file = null;

		Transformer<Integer, Integer> vertexTransformer = getVertexTransformerTesting();
		Transformer<String, String> edgeTransformer = getEdgeTransformerTesting();
		
		ErdosRenyi<Integer, String> erGraph = 
				new ErdosRenyi<>(vertexTransformer, edgeTransformer);
		
		GraphIO.saveGraph("GraphsGenerated/ErdosRenyi/Graph" + 
				generated + "/graph.graphml", erGraph.getGraph());
		GraphIO.saveDegreeDistribution(erGraph.getGraph(),
				"GraphsGenerated/ErdosRenyi/Graph" + generated + "/degreeDistribution.csv");
		
		graphAndNodeData(erGraph.getGraph(), "GraphsGenerated/ErdosRenyi/Graph" + generated);
		
		BatageljZaversnik<Integer, String> bz = new BatageljZaversnik<>(erGraph.getGraph());
		
		Set<Integer> cores = bz.decomposition().entrySet()
				.stream().map(x -> x.getValue()).distinct()
				.sorted().collect(toSet());
		
		for(Integer core : cores) {
			graphData(bz.kCore(core),
					"GraphsGenerated/ErdosRenyi/Graph" + generated + "/Cores" + "/Core" + core + ".txt");
		}
	}
	
	public static void testBarabasi(int generated) {
		File file = new File("GraphsGenerated/BarabasiAlbert/Graph" + generated);
		file.mkdir();
		file = new File("GraphsGenerated/BarabasiAlbert/Graph" + generated + "/Cores");
		file.mkdir();
		file = null;

		Transformer<Integer, Integer> vertexTransformer = getVertexTransformerTesting();
		Transformer<String, String> edgeTransformer = getEdgeTransformerTesting();
		
		BarabasiAlbert<Integer, String> baGraph = 
				new BarabasiAlbert<>(vertexTransformer, edgeTransformer);
		
		GraphIO.saveGraph("GraphsGenerated/BarabasiAlbert/Graph" + 
				generated + "/graph.graphml", baGraph.getGraph());
		
		GraphIO.saveDegreeDistribution(baGraph.getGraph(),
				"GraphsGenerated/BarabasiAlbert/Graph" + generated + "/degreeDistribution.csv");
		
		graphAndNodeData(baGraph.getGraph(), "GraphsGenerated/BarabasiAlbert/Graph" + generated);
		
		BatageljZaversnik<Integer, String> bz = new BatageljZaversnik<>(baGraph.getGraph());
		
		Set<Integer> cores = bz.decomposition().entrySet()
				.stream().map(x -> x.getValue()).distinct()
				.sorted().collect(toSet());
		
		for(Integer core : cores) {
			graphData(bz.kCore(core),
					"GraphsGenerated/BarabasiAlbert/Graph" + generated + "/Cores" + "/Core" + core + ".txt");
		}
	}
	
	public static void testCorePeriphery(int generated) {
		double nodePercentage = 0;
		if(generated < 10) {
			nodePercentage = 0.1;
		} else if (generated < 20) {
			nodePercentage = 0.3;
		} else if (generated < 30) {
			nodePercentage = 0.5;
		} else if (generated < 40) {
			nodePercentage = 0.7;
		} else {
			nodePercentage = 0.9;
		}
		File file = new File("GraphsGenerated/CorePeriphery/Graph" + generated);
		file.mkdir();
		file = new File("GraphsGenerated/CorePeriphery/Graph" + generated + "/Cores");
		file.mkdir();
		file = null;

		Transformer<Integer, Integer> vertexTransformer = getVertexTransformerTesting();
		Transformer<String, String> edgeTransformer = getEdgeTransformerTesting();
		
		CorePeripheryGraph<Integer, String> cpGraph =
				new CorePeripheryGraph<>(vertexTransformer, edgeTransformer,
						new double[] {0.01, 0.001, 0.05}, nodePercentage, 2000);
		
		GraphIO.saveGraph("GraphsGenerated/CorePeriphery/Graph" +
				generated + "/graph.graphml", cpGraph.getGraph());
		GraphIO.saveDegreeDistribution(cpGraph.getGraph(),
				"GraphsGenerated/CorePeriphery/Graph" + generated + "/degreeDistribution.csv");
		
		graphAndNodeData(cpGraph.getGraph(), "GraphsGenerated/CorePeriphery/Graph" + generated);
		BatageljZaversnik<Integer, String> bz = new BatageljZaversnik<>(cpGraph.getGraph());
		
		Set<Integer> cores = bz.decomposition().entrySet()
				.stream().map(x -> x.getValue()).distinct()
				.sorted().collect(toSet());
		
		for(Integer core : cores) {
			graphData(bz.kCore(core),
					"GraphsGenerated/CorePeriphery/Graph" + generated + "/Cores" + "/Core" + core);
		}
	}
	
	
	/* 
	 * Funkcije graphAndNodeData i graphData: Obe računaju globalne metrike za graf (gustinu, 
	 * koeficijent klasterisanja, procenat čvorova u najvećoj komponenti...).
	 * Razlika je to što funkcija graphAndNodeData računa metrike za čvoreve i korelacije između šel indeksa
	 * i metrika centralnosti.
	 * Funkcija graphAndNodeData se poziva samo jednom, a funkcija graphData se poziva za svako K-jezgro
	 */
	public static void graphAndNodeData(UndirectedSparseGraph<Integer, String> graph, String saveTo) {
		int vertexNumber = graph.getVertexCount();
		System.out.println("vertex number: " + vertexNumber);
		int edgeNumber = graph.getEdgeCount();
		System.out.println("Edge number: " + edgeNumber);
		double graphDensity = (2 * (double) edgeNumber) / ((double) vertexNumber * (double) (vertexNumber - 1));
		System.out.println("Graph density: " + graphDensity);		

		UndirectedSparseGraph<Integer, String> copiedGraph = copyGraph(graph);
		//Broj komponenti povezanosti
		WeakComponentClusterer<Integer, String> compClus = new WeakComponentClusterer<>();
		Set<Set<Integer>> components = compClus.transform(copiedGraph);
		int numberOfComponents = components.size();

		//Izbaci najveću komponentu
		components = 
				components.stream()
				.sorted((x, y) -> y.size() - x.size()).skip(1)
				.collect(Collectors.toSet());

		//Obriši sve što nije u najvećoj komponenti
		for(Set<Integer> comp : components) {
			for(Integer vertex : comp) {
				copiedGraph.removeVertex(vertex);
			}
		}
		
		//Postavljanje na null radi oslobađanja memorije
		components = null;
		compClus = null;
		System.gc();
		
		
		//Centralnosti		
		Map<Integer, Double> betweenness = getBetweennessScores(copiedGraph);
		System.out.println("betweenness done");
		
		Map<Integer, Double> eigenvector = getEigenvectorScores(copiedGraph);
		System.out.println("Eigenvector done");
		
		Map<Integer, Double> closeness = getMyClosenessScores(copiedGraph);
		System.out.println("Closeness done");		
		
		//Jezgra
		Map<Integer, Integer> shellsBZ = getBatageljShells(copiedGraph);
		System.out.println("Batagelj done");
		Map<Integer, Integer> shellsSF = getStraightForwardShells(copiedGraph);
		System.out.println("Straight forward done");		
		
		List<NodeInfo> nodeInfos = new LinkedList<>();
		for(Integer vertex : graph.getVertices()) {
			int degree = graph.degree(vertex);
			double betweennessScore = betweenness.get(vertex);
			double closenessScore = closeness.get(vertex);
			double eigenvectorScore = eigenvector.get(vertex);
			int shellBZ = shellsBZ.get(vertex);
			int shellSF = shellsSF.get(vertex);
			NodeInfo nodeInfo = new NodeInfo(vertex, degree, betweennessScore,
					closenessScore, eigenvectorScore, shellBZ, shellSF);
			nodeInfos.add(nodeInfo);
		}

		System.out.println("Writing node data");
		GraphIO.saveNodeData(nodeInfos, saveTo + "/nodeData.csv");
		nodeInfos = null;	
		
		double[] closenessScores =  transformTo(transformDouble(closeness));
		
		double[] betweennessScores = transformTo(transformDouble(betweenness));
				
		double[] eigenvectorScores =  transformTo(transformDouble(eigenvector));
		
		double[] degrees =  transformTo(transformInteger(getDegrees(copiedGraph)));
		
		double shellIndex[] =  transformTo(transformInteger(shellsBZ));
		
		//Spermanovi indeksi korelacije
		double[] corelations = {getCorelation(shellIndex, degrees),
				getCorelation(shellIndex, betweennessScores),
				getCorelation(shellIndex, closenessScores),
				getCorelation(shellIndex, eigenvectorScores)};
		

		//Postavljanje na null radi oslobađanja memorije
		shellsSF = null;
		betweennessScores = null;
		closenessScores = null;
		eigenvectorScores = null;
		betweenness = null;
		closeness = null;
		eigenvector = null;
		degrees = null;
		shellIndex = null;
		shellsBZ = null;
		System.gc();
		
		double percentageOfVerticesInBiggest = ((double) copiedGraph.getVertexCount() / vertexNumber) * 100;
		double percentageOfEdgesInBiggest = ((double) copiedGraph.getEdgeCount() / (double) edgeNumber) * 100;


		//Dijametar i koeficijent malog sveta
		double[] smallWorlDiam = getSmallWorldKoefAndDiameter(copiedGraph);

		double smallWorldKoef = smallWorlDiam[0];
		int diameter = (int) smallWorlDiam[1];
		
		Map<Integer, Double> clusteringCoef = Metrics.clusteringCoefficients(copiedGraph);
		double clustKoef = 0;
		for(Integer vert : clusteringCoef.keySet()) {
			clustKoef += clusteringCoef.get(vert);
		}
		
		clustKoef /= (double) clusteringCoef.keySet().size();		
		clusteringCoef = null;
		
		Object[] data = {vertexNumber, edgeNumber, percentageOfVerticesInBiggest,
				percentageOfEdgesInBiggest, graphDensity, numberOfComponents,
				diameter, clustKoef, smallWorldKoef, corelations};
		
		System.out.println("Wtriting");
		GraphIO.saveGraphData(data, saveTo + "/globalData.txt");		
	}
	
	public static void graphData(UndirectedSparseGraph<Integer, String> graph, String saveTo) {
		int vertexNumber = graph.getVertexCount();
		System.out.println("Vert num:" + vertexNumber);
		int edgeNumber = graph.getEdgeCount();

		double graphDensity = (2 * (double) edgeNumber) / ((double) vertexNumber * (double) (vertexNumber - 1));
		
		//Broj komponenti povezanosti
		WeakComponentClusterer<Integer, String> compClus = new WeakComponentClusterer<>();
		Set<Set<Integer>> components = compClus.transform(graph);
		int numberOfComponents = components.size();

		components = 
				components.stream()
				.sorted((x, y) -> y.size() - x.size()).skip(1)
				.collect(Collectors.toSet());

		
		for(Set<Integer> comp : components) {
			for(Integer vertex : comp) {
				graph.removeVertex(vertex);
			}
		}
		
		//Postavljanje na null radi oslobađanja memorije
		components = null;
		compClus = null;
		System.gc();
		
		double percentageOfVerticesInBiggest = ((double) graph.getVertexCount() / (double) vertexNumber) * 100;
		double percentageOfEdgesInBiggest = ((double) graph.getEdgeCount() / (double) edgeNumber) * 100;

		double[] smallWorlDiam = getSmallWorldKoefAndDiameter(graph);
		double smallWorldKoef = smallWorlDiam[0];
		int diameter = (int) smallWorlDiam[1];
		
		//Average clusterCoef
		double clustKoef = 0;
		Map<Integer, Double> clusteringCoef = Metrics.clusteringCoefficients(graph);
		for(Integer vert : clusteringCoef.keySet()) {
			clustKoef += clusteringCoef.get(vert);
		}

		clustKoef /=(double) clusteringCoef.keySet().size();
		clusteringCoef = null;		
		
		Object[] data = {vertexNumber, edgeNumber, percentageOfVerticesInBiggest,
				percentageOfEdgesInBiggest, graphDensity, numberOfComponents,
				diameter, clustKoef, smallWorldKoef};
		
		System.out.println("Writing");
		GraphIO.saveGraphData(data, saveTo);
	}
	
	private static UndirectedSparseGraph<Integer, String> copyGraph(UndirectedSparseGraph<Integer, String> graph) {
		UndirectedSparseGraph<Integer, String> copy = new UndirectedSparseGraph<>();
		for(Integer vertex : graph.getVertices()) {
			copy.addVertex(vertex);
		}
		
		for(String edge : graph.getEdges()) {
			copy.addEdge(edge, graph.getIncidentVertices(edge));
		}
		
		return copy;
	}
	
	
	/*
	 * Računa spermanov indeks korelacije za date nizove
	 */
	private static double getCorelation(double[] arr1, double[] arr2) {
		SpearmansCorrelation spc = new SpearmansCorrelation();
		return spc.correlation(arr1, arr2);
	}
	
	/*
	 * transformTo funkcije menjaju niz datog tipa u tip double (zbog korelacija)
	 */
	private static double[] transformTo(Double[] arr) {
		double[] returnArr = new double[arr.length];
		for(int i = 0; i < arr.length; i++) {
			returnArr[i] = arr[i];
		}
		
		return returnArr;
	}
	
	private static double[] transformTo(Integer[] arr) {
		double[] returnArr = new double[arr.length];
		for(int i = 0; i < arr.length; i++) {
			returnArr[i] = arr[i];
		}
		
		return returnArr;
	}
	
	/*
	 * Funkcije get*Scores vraćaju mapu čvor - vrednost
	 * gde je vrednost neka metrika centralnosti za taj čvor
	 * Mapa je sortirana po čvorovima zbog korelacija (na istom indeksu nalaze se metrike za isti čvor)
	 */
	private static Map<Integer, Double> getBetweennessScores(UndirectedSparseGraph<Integer, String> graph){
		BetweennessCentrality<Integer, String> bc = new BetweennessCentrality<>(graph);
		
		Map<Integer, Double> scores = new HashMap<>();
		for(Integer vertex : graph.getVertices()) {
			scores.put(vertex, bc.getVertexScore(vertex));
		}
		
		scores = scores.entrySet().stream().sorted(Map.Entry.comparingByKey())
				.collect(toMap(x -> x.getKey(), x -> x.getValue()));
		
		return scores;
	}

	/*
	 * Ne koristi ("Out of memory error")
	 * Umesto ovoga korsiti se getMyClosenessScores koji pokreće moju implementaciju računanja
	 * koja koristi manje memorije (nema "Out of memory error")
	 */
	@SuppressWarnings("unused")
	private static Map<Integer, Double> getClosenessScores(UndirectedSparseGraph<Integer, String> graph){
		ClosenessCentrality<Integer, String> cs = new ClosenessCentrality<>(graph);
		Map<Integer, Double> scores = new HashMap<>();
		for(Integer vertex : graph.getVertices()) {
			scores.put(vertex, cs.getVertexScore(vertex));
		}
		
		scores = scores.entrySet().stream().sorted(Map.Entry.comparingByKey())
				.collect(toMap(x -> x.getKey(), x -> x.getValue()));
		
		return scores;
	}
	
	private static Map<Integer, Double> getMyClosenessScores(UndirectedSparseGraph<Integer, String> graph){
		ClosenessVertexCentrality cvc = new ClosenessVertexCentrality(graph);
		Map<Integer, Double> scores = cvc.getScores();
		
		scores = scores.entrySet().stream().sorted(Map.Entry.comparingByKey())
				.collect(toMap(x -> x.getKey(), x -> x.getValue()));
		
		return scores;
	}
	
	private static Map<Integer, Double> getEigenvectorScores(UndirectedSparseGraph<Integer, String> graph){
		EigenvectorCentrality<Integer, String> ec = new EigenvectorCentrality<>(graph);
		ec.evaluate();
		
		Map<Integer, Double> scores = new HashMap<>();
		for(Integer vertex : graph.getVertices()) {
			scores.put(vertex, ec.getVertexScore(vertex));
		}
		
		scores = scores.entrySet().stream().sorted(Map.Entry.comparingByKey())
				.collect(toMap(x -> x.getKey(), x -> x.getValue()));
		
		return scores;
	}
	
	/*
	 * Vraća mapu čvor - stepen
	 * Mapa je sortirana po čvorovima zbog korelacija
	 */
	private static Map<Integer, Integer> getDegrees(UndirectedSparseGraph<Integer, String> graph){
		Map<Integer, Integer> degrees = new HashMap<>();
		for(Integer vertex : graph.getVertices()) {
			degrees.put(vertex, graph.degree(vertex));
		}
		
		degrees = degrees.entrySet().stream().sorted(Map.Entry.comparingByKey())
				.collect(toMap(x -> x.getKey(), x -> x.getValue()));
		
		return degrees;
	}
	
	/*
	 * Funkcije get*Shells vraćaju mapu čvor - šel indeks
	 * šel indeks se računa po algoritmu iz imena (poređenje rezultata algoritama)
	 * Mape su sortirane po čvorovima
	 */
	private static Map<Integer, Integer> getBatageljShells(UndirectedSparseGraph<Integer, String> graph){
		BatageljZaversnik<Integer, String> batagelj = new BatageljZaversnik<>(graph);
		Map<Integer, Integer> shells = batagelj.decomposition();

		shells = shells.entrySet().stream().sorted(Map.Entry.comparingByKey())
				.collect(toMap(x -> x.getKey(), x -> x.getValue()));
		
		return shells;
	}
	
	private static Map<Integer, Integer> getStraightForwardShells(UndirectedSparseGraph<Integer, String> graph){
		StraightForward<Integer, String> straight = new StraightForward<>(graph);
		Map<Integer, Integer> shells = straight.decomposition();

		shells = shells.entrySet().stream().sorted(Map.Entry.comparingByKey())
				.collect(toMap(x -> x.getKey(), x -> x.getValue()));
		
		return shells;
	}

	/*
	 * Funkcije transformDouble i transformInteger skupljaju vrednosti metrika u odgovarajći niz
	 */
	private static Double[] transformDouble(Map<Integer, Double> scores) {
		Double[] arr = scores.entrySet().stream().map(x -> x.getValue())
				.collect(toList()).toArray(Double[]::new);
		
		return arr;
	}
	
	private static Integer[] transformInteger(Map<Integer, Integer> scores) {
		Integer[] arr = scores.entrySet().stream().map(x -> x.getValue())
				.collect(toList()).toArray(Integer[]::new);
		
		return arr;
	}
	
	/*
	 * Računa koeficijent malog sveta i dijametar grafa
	 */
	private static double[] getSmallWorldKoefAndDiameter(UndirectedSparseGraph<Integer, String> graph) {
		double sum = 0;
		double diametar = 0;
		for(Integer current : graph.getVertices()) {
			double[] arr = distanceToAll(graph, current);
			if(arr[1] > diametar) {
				diametar = arr[1];
			}
			sum += arr[0];
		}
		double vertSq = graph.getVertexCount() * (graph.getVertexCount() - 1);
		double koef = sum / vertSq;
		return new double[]{koef, diametar};
	}
	
	/*
	 * Računa distancu od nekog čvora do svih ostalih, a vraća i najduži put kao mogući dijametar
	 * (DistanceStatistics.diameter() dovodi do: outOfMemoryError)
	 */
	private static double[] distanceToAll(UndirectedSparseGraph<Integer, String> graph, Integer vertex) {
		UnweightedShortestPath<Integer, String> unSP = new UnweightedShortestPath<>(graph);
		Map<Integer, Number> wrSet = unSP.getDistanceMap(vertex);
		double distance = 0;
		int longest = wrSet.entrySet().stream()
				.mapToInt(x -> x.getValue().intValue()).max().orElse(-1);
		
		for(Integer other : wrSet.keySet()) {
			distance += (double) (int) wrSet.get(other);
		}
		
		return new double[]{distance, longest};
	}
	
	public static boolean compareClosness(Map<Integer, Double> map1, Map<Integer, Double> map2) {
		if(!map1.keySet().equals(map2.keySet())) return false;
	
		for(Integer key : map1.keySet()) {
			Double value1 = map1.get(key);
			Double value2 = map2.get(key);
			if(!value1.equals(value2)) return false;
		}
		
		return true;
	}
	
	public static boolean compareShell(Map<Integer, Integer> map1, Map<Integer, Integer> map2) {
		if(!map1.keySet().equals(map2.keySet())) return false;
		
		for(Integer key : map1.keySet()) {
			Integer value1 = map1.get(key);
			Integer value2 = map2.get(key);
			if(!value1.equals(value2)) return false;
		}
		
		return true;
	}
}
