package data;

public class NodeInfo implements Comparable<NodeInfo>{
	private int nodeId;
	private double betweenneesScore;
	private double closennesScore;
	private double eigenvectorScore;
	private int shellIndexBZ;
	private int shellIndexSF;
	private int degree;
	
	public NodeInfo(int nodeId, int degree, double betweenneesScore, double closennesScore,
			double eigenvectorScore, int shellIndexBZ, int shellIndexSF) {
		super();
		this.nodeId = nodeId;
		this.degree = degree;
		this.betweenneesScore = betweenneesScore;
		this.closennesScore = closennesScore;
		this.eigenvectorScore = eigenvectorScore;
		this.shellIndexBZ = shellIndexBZ;
		this.shellIndexSF = shellIndexSF;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public double getBetweenneesScore() {
		return betweenneesScore;
	}

	public void setBetweenneesScore(double betweenneesScore) {
		this.betweenneesScore = betweenneesScore;
	}

	public double getClosennesScore() {
		return closennesScore;
	}

	public void setClosennesScore(double closennesScore) {
		this.closennesScore = closennesScore;
	}

	public double getEigenvectorScore() {
		return eigenvectorScore;
	}

	public void setEigenvectorScore(double eigenvectorScore) {
		this.eigenvectorScore = eigenvectorScore;
	}

	public int getShellIndexBZ() {
		return shellIndexBZ;
	}

	public void setShellIndexBZ(int shellIndex) {
		this.shellIndexBZ = shellIndex;
	}
	
	public int getShellIndexSF() {
		return shellIndexSF;
	}

	public void setShellIndexSF(int shellIndex) {
		this.shellIndexSF = shellIndex;
	}
	
	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	@Override
	public String toString() {
		return "nodeId=" + nodeId + ", degree=" + degree + ", betweenneesScore=" + betweenneesScore + ", closennesScore="
				+ closennesScore + ", eigenvectorScore=" + eigenvectorScore + ", shell index batagelj=" + shellIndexBZ
				+ ", shell index straight forward=" + shellIndexSF;
	}

	@Override
	public int compareTo(NodeInfo o) {
		return nodeId - o.getNodeId();
	}
}
