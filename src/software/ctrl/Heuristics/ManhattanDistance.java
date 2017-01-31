package software.ctrl.Heuristics;

import software.ctrl.Algorithms.Heuristic;
import software.ctrl.Algorithms.Vertex;

public class ManhattanDistance implements Heuristic {
	
	private final int goalX, goalY;
	
	public ManhattanDistance(int goalX, int goalY) {
		this.goalX = goalX;
		this.goalY = goalY;
	}

	@Override
	public double valueVertex(Vertex v) {
		return Math.abs(v.x - goalX) + Math.abs(v.y - goalY) -1;
	}
}
