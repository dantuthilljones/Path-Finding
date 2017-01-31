package software.ctrl.Algorithms;

import java.util.ArrayList;

import software.ctrl.PathFinding.Map;

public abstract class PathAlgorithm {
	
	private boolean running;
	
	private int iterations;
	
	protected Vertex currentVertex;
	protected ArrayList<Vertex> path;
	protected boolean[][] visitedNodes, fringe;
	
	protected Map map;
	protected boolean diagonals;
	
	public PathAlgorithm(Map map) {
		running = false;
		
		this.map = map;
		visitedNodes = new boolean[map.x][map.y];
		fringe = new boolean[map.x][map.y];
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public Vertex getCurrentVertex() {
		return currentVertex;
	}
	
	public ArrayList<Vertex> getPath() {
		return path;
	}
	
	public boolean iterate() {
		iterations++;
		return false;
	}
	
	public int getIterations() {
		return iterations;
	}
	
	public boolean[][] getVisitedNodes() {
		return visitedNodes;
	}
	
	public boolean[][] getFringe() {
		return fringe;
	}
	
	public void reset(Map m) {
		map = m;
		reset();
	}
	
	public void reset() {
		visitedNodes = new boolean[map.x][map.y];
		fringe = new boolean[map.x][map.y];
		currentVertex = null;
		running = false;
		iterations = 0;
		path = null;
	}
	
	public void setDiagonals(boolean d) {
		diagonals = d;
	}
	
	public boolean diagonalsAllowed() {
		return diagonals;
	}
	

}
