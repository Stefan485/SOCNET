package graphIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections15.Transformer;

import data.KcoreData;
import data.NodeInfo;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.io.GraphMLWriter;

public class GraphIO {

	public static boolean saveGraph(String fileName, UndirectedSparseGraph<Integer, String> graph) {
		
		GraphMLWriter<Integer, String> writer = new GraphMLWriter<>();
		
		writer.setVertexIDs(new Transformer<Integer, String>() {
			
			@Override
			public String transform(Integer id) {
				return id + "";
			}
		});
		
		writer.setEdgeIDs(new Transformer<String, String>() {
			
			@Override
			public String transform(String edge) {
				return edge;
			}
		});
		
		try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName)), true)) {
			writer.save(graph, pw);
			return true;
		} catch (IOException e) {
			System.out.println("Error during writing");
			e.printStackTrace();
			return false;
		}
	}
	
	public static void saveNodeData(List<NodeInfo> nodeInfos, String filePath) {
		try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath)), true)){
			
			nodeInfos.stream().sorted().map(x -> String.format("%d, %d, %d, %d, %.5f, %.5f, %.5f",
					x.getNodeId(), x.getDegree(), x.getShellIndexBZ(), x.getShellIndexSF(),
					x.getBetweenneesScore(), x.getClosennesScore(), x.getEigenvectorScore()))
					.forEach(x -> writer.println(x));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveDegreeDistribution(UndirectedSparseGraph<Integer, String> graph, String fileName) {
		Map<Integer, Integer> degreeDistribution = new HashMap<>();
		
		Iterator<Integer> vertexIterator = graph.getVertices().iterator();
		while(vertexIterator.hasNext()) {
			Integer vertex = vertexIterator.next();
			int currentDegree = graph.degree(vertex);
			if(degreeDistribution.containsKey(currentDegree)) {
				int oldValue = degreeDistribution.get(currentDegree);
				degreeDistribution.replace(currentDegree, oldValue + 1);
			} else {
				degreeDistribution.put(currentDegree, 1);
			}
		}
		
		try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
			
			degreeDistribution.entrySet()
				.stream().sorted(Map.Entry.comparingByKey())
				.map(x -> String.format("%d,%d", x.getKey(), x.getValue()))
				.forEach(x -> pw.println(x));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveGraphData(Object[] data, String fileName) {
		try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))){
			int vertexNumber = (int) data[0];
			int edgeNumber = (int) data[1];
			double percentageOfVerticesInBiggest = (double) data[2];
			double percentageOfEdgesInBiggest = (double) data[3];
			double graphDensity = (double) data[4];
			int numberOfComponents = (int) data[5];
			int diameter = (int) data[6];
			double clustKoef = (double) data[7];
			double smallWorldKoef = (double) data[8];
			pw.write("Number of vertices: " + vertexNumber + "\n");
			pw.println("Number of edges: " + edgeNumber);
			pw.println("Percentage of vertices in the biggest component: " + percentageOfVerticesInBiggest);
			pw.println("Percentage of Edges in the biggest component: " + percentageOfEdgesInBiggest);
			pw.println("Graph density: " + graphDensity);
			pw.println("Number of components: " + numberOfComponents);
			pw.println("Graph diameter: " + diameter);
			pw.println("Graph clustering coef: " + clustKoef);
			pw.println("Graph small world coefficient: " + smallWorldKoef);

			if(data.length == 10) {
				double[] corelations = (double[]) data[9];
				pw.println("Corelation shell index <> vertex degree: " + corelations[0]);
				pw.println("Corelation shell index <> vertex betweenness score: " + corelations[1]);
				pw.println("Corelation shell index <> vertex closeness score: " + corelations[2]);
				pw.println("Corelation shell index <> vertex eigenvector score: " + corelations[3]);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	//Ucitava Condensed matter collaboration 2005
	public static UndirectedSparseGraph<Integer, String> readInCond(String file) {
		UndirectedSparseGraph<Integer, String> graph = new UndirectedSparseGraph<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))){
			String line = null;
			while((line = reader.readLine()) != null) {
				if(line.contains("node")) {
					//[
					line = reader.readLine();
					
					line = reader.readLine().trim();
					String[] tokens = line.split(" ");
					Integer vertex = Integer.parseInt(tokens[1]);
					graph.addVertex(vertex);
					//label
					line = reader.readLine();
					//]
					line = reader.readLine();
				} else if(line.contains("edge")) {
					//[
					line = reader.readLine();
					//source
					line = reader.readLine().trim();
					Integer vertex1 = Integer.parseInt(line.split(" ")[1]);
					//target
					line = reader.readLine().trim();
					Integer vertex2 = Integer.parseInt(line.split(" ")[1]);
					graph.addEdge(vertex1 + " " + vertex2, vertex1, vertex2, EdgeType.UNDIRECTED);
				
					//value
					line = reader.readLine();
					//]
					line = reader.readLine();
					
				}
			}
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return graph;
	}
	
	//Ucitava Condensed matter collaboration 2003
	public static UndirectedSparseGraph<Integer, String> readInAsEdgeList(String fileName) {
		UndirectedSparseGraph<Integer, String> graph = new UndirectedSparseGraph<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){
			String line = null;
			while((line = reader.readLine()) != null) {
				String[] vertices = line.split("\\s+");
				Integer vertex1 = Integer.parseInt(vertices[0].trim());
				Integer vertex2 = Integer.parseInt(vertices[1].trim());
				
				graph.addVertex(vertex1);
				graph.addVertex(vertex2);
				graph.addEdge(vertex1 + " " + vertex2, vertex1, vertex2, EdgeType.UNDIRECTED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
		return graph;
	}

	//Ucitava Enron email
	public static UndirectedSparseGraph<Integer, String> reeadInInfoEdgeList(String fileName) {
		UndirectedSparseGraph<Integer, String> graph = new UndirectedSparseGraph<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){
			//Line1
			reader.readLine();
			//Line2
			reader.readLine();
			//Line3
			reader.readLine();
			//Line4
			reader.readLine();
			
			String line = null;
			while((line = reader.readLine()) != null) {
				String[] vertices = line.split("\\s+");
				Integer vertex1 = Integer.parseInt(vertices[0].trim());
				Integer vertex2 = Integer.parseInt(vertices[1].trim());
				
				graph.addVertex(vertex1);
				graph.addVertex(vertex2);
				
				graph.addEdge(vertex1 +  " " + vertex2, vertex1, vertex2, EdgeType.UNDIRECTED);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return graph;		
	}
	
	//Ucitava Github mrežu
	public static UndirectedSparseGraph<Integer, String> readInFromCSV(String fileName) {
		UndirectedSparseGraph<Integer, String> graph = new UndirectedSparseGraph<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){
			//Line1
			reader.readLine();
		
			String line = null;
			while((line = reader.readLine()) != null) {
				String[] vertices = line.split(",");
				Integer vertex1 = Integer.parseInt(vertices[0].trim());
				Integer vertex2 = Integer.parseInt(vertices[1].trim());
				
				graph.addVertex(vertex1);
				graph.addVertex(vertex2);
				
				graph.addEdge(vertex1 +  " " + vertex2, vertex1, vertex2, EdgeType.UNDIRECTED);
			}
			
		} catch (Exception e) {
			System.out.println("Greška pri čitanju: " + fileName);
			e.printStackTrace();
		}
		
		return graph;		
	}
	
	
	public static void saveSmallGraphShell(String saveTo, List<NodeInfo> nodeInfos) {
		try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(saveTo)))){
			nodeInfos.stream().sorted((x, y) -> (x.getNodeId() - y.getNodeId()))
			.map(x -> String.format("%5d|%5d|%5d", x.getNodeId(), x.getShellIndexBZ(), x.getShellIndexSF()))
			.forEach(x -> writer.println(x));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveKcoreData(String src) {
		Pattern patt = Pattern.compile("Core(?<Number>[0-9]+)");
		LinkedList<KcoreData> coreDataList = new LinkedList<>();
		File file = new File(src);
		for(File coreFile : file.listFiles()) {
			Matcher matcher = patt.matcher(coreFile.getName());
			matcher.find();
			KcoreData coreData = new KcoreData();
			int core = Integer.parseInt(matcher.group("Number").trim());
			coreData.setCore(core);
			try(BufferedReader reader = new BufferedReader(new FileReader(coreFile))) {
				//vert number
				String line = reader.readLine();
				int NumberInt = Integer.parseInt(line.split(":")[1].trim());
				coreData.setVertexNumber(NumberInt);
				
				//edge number
				line = reader.readLine();
				NumberInt = Integer.parseInt(line.split(":")[1].trim());
				coreData.setEdgeNumber(NumberInt);
				
				//vert percent
				line = reader.readLine();
				double NumberDouble = Double.parseDouble(line.split(":")[1].trim());
				coreData.setPercentageOfVerticesInBiggest(NumberDouble);
				
				//edge percent
				line = reader.readLine();
				NumberDouble = Double.parseDouble(line.split(":")[1].trim());
				coreData.setPercentageOfEdgesInBiggest(NumberDouble);
				
				//density
				line = reader.readLine();
				NumberDouble = Double.parseDouble(line.split(":")[1].trim());
				coreData.setGraphDensity(NumberDouble);
				
				//component number
				line = reader.readLine();
				NumberInt = Integer.parseInt(line.split(":")[1].trim());
				coreData.setNumberOfComponents(NumberInt);
				
				//diameter
				line = reader.readLine();
				NumberInt = Integer.parseInt(line.split(":")[1].trim());
				coreData.setDiameter(NumberInt);
				
				//clustering koef
				line = reader.readLine();
				NumberDouble = Double.parseDouble(line.split(":")[1].trim());
				coreData.setClusteringCoef(NumberDouble);
				
				//small world koef
				line = reader.readLine();
				NumberDouble = Double.parseDouble(line.split(":")[1].trim());
				coreData.setSmallWorldCoef(NumberDouble);
				
				coreDataList.add(coreData);
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		
		String saveTo = src + "\\KcoreData.csv";
		try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(saveTo)))) {
			coreDataList.stream().sorted()
				.forEach(x -> writer.println(x));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
