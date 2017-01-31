package software.ctrl.Heuristics;

import software.ctrl.Algorithms.Heuristic;
import software.ctrl.Algorithms.Vertex;

public class DiagonalDistance implements Heuristic {

	private final int goalX, goalY;
	
	public DiagonalDistance(int x, int y) {
		goalX = x;
		goalY = y;
	}
	
	@Override
	public double valueVertex(Vertex v) {
		int x = Math.abs(goalX - v.x);
		int y = Math.abs(goalY - v.y);
		int min = Math.min(x, y);
		int max = Math.max(x, y);
		double diag = Math.sqrt(2*Math.pow(min, 2));
		
		return max - min + diag;
	}

}
