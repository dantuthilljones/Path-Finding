package software.ctrl.Heuristics;

import software.ctrl.Algorithms.Heuristic;
import software.ctrl.Algorithms.Vertex;

public class AbsoluteDistance implements Heuristic {

	private final int goalX, goalY;
	
	public AbsoluteDistance(int goalX, int goalY) {
		this.goalX = goalX;
		this.goalY = goalY;
	}
	
	@Override
	public double valueVertex(Vertex v) {
		int x = v.x - goalX;
		int y = v.y - goalY;
		
		//we scale the value to prevent errors due to rounding
		return Math.sqrt(x*x + y*y);
	}

}
