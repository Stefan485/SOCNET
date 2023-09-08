package data;

public class KcoreData implements Comparable<KcoreData>{
	private int core;
	private int vertexNumber;
	private int edgeNumber;
	private double percentageOfVerticesInBiggest;
	private double percentageOfEdgesInBiggest;
	private double graphDensity;
	private int numberOfComponents;
	private int diameter;
	private double clusteringCoef;
	private double smallWorldCoef;
	
	public KcoreData() {
		super();
	}
	
	public int getCore() {
		return core;
	}
	public void setCore(int core) {
		this.core = core;
	}
	public int getVertexNumber() {
		return vertexNumber;
	}
	public void setVertexNumber(int vertexNumber) {
		this.vertexNumber = vertexNumber;
	}
	public int getEdgeNumber() {
		return edgeNumber;
	}
	public void setEdgeNumber(int edgeNumber) {
		this.edgeNumber = edgeNumber;
	}
	public double getPercentageOfVerticesInBiggest() {
		return percentageOfVerticesInBiggest;
	}
	public void setPercentageOfVerticesInBiggest(double percentageOfVerticesInBiggest) {
		this.percentageOfVerticesInBiggest = percentageOfVerticesInBiggest;
	}
	public double getPercentageOfEdgesInBiggest() {
		return percentageOfEdgesInBiggest;
	}
	public void setPercentageOfEdgesInBiggest(double percentageOfEdgesInBiggest) {
		this.percentageOfEdgesInBiggest = percentageOfEdgesInBiggest;
	}
	public double getGraphDensity() {
		return graphDensity;
	}
	public void setGraphDensity(double graphDensity) {
		this.graphDensity = graphDensity;
	}
	public int getNumberOfComponents() {
		return numberOfComponents;
	}
	public void setNumberOfComponents(int numberOfComponents) {
		this.numberOfComponents = numberOfComponents;
	}
	public int getDiameter() {
		return diameter;
	}
	public void setDiameter(int diameter) {
		this.diameter = diameter;
	}
	public double getClusteringCoef() {
		return clusteringCoef;
	}
	public void setClusteringCoef(double clusteringCoef) {
		this.clusteringCoef = clusteringCoef;
	}
	public double getSmallWorldCoef() {
		return smallWorldCoef;
	}
	public void setSmallWorldCoef(double smallWorldCoef) {
		this.smallWorldCoef = smallWorldCoef;
	}

	@Override
	public String toString() {
		return core + "," + vertexNumber + "," + edgeNumber
				+ "," + percentageOfVerticesInBiggest + "," + percentageOfEdgesInBiggest 
				+ "," + graphDensity + ","
				+ numberOfComponents + "," + diameter + "," + clusteringCoef
				+ "," + smallWorldCoef;
	}

	@Override
	public int compareTo(KcoreData o) {
		return core - o.getCore();
	}
}
