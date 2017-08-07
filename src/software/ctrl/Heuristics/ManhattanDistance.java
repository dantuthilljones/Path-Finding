package software.ctrl.Heuristics;

import java.awt.Point;

import software.ctrl.Algorithms.Heuristic;
import software.ctrl.Algorithms.Vertex;

public class ManhattanDistance implements Heuristic {
	
	private final int goalX, goalY;
	
	public ManhattanDistance(int goalX, int goalY) {
		this.goalX = goalX;
		this.goalY = goalY;
	}
	
	public ManhattanDistance(Point goal) {
		goalX = goal.x;
		goalY = goal.y;
	}

	@Override
	public double valueVertex(Vertex v) {
		return Math.abs(v.x - goalX) + Math.abs(v.y - goalY) -1;
	}
}
