package software.ctrl.Algorithms.AStar;

import software.ctrl.Algorithms.Heuristic;
import software.ctrl.Algorithms.Vertex;

public class AStarVertex extends Vertex implements Comparable<AStarVertex> {
	
	public final double distanceGoal;
	public final double distanceStart;
	public final AStarVertex parent;
	
	public AStarVertex(int x, int y, int depth, double distanceStart, Heuristic h, AStarVertex parent) {
		super(x, y, depth);
		this.distanceStart = distanceStart;
		this.parent = parent;
		
		//we reduce the heuristic slightly to resolve tie breakers when the heuristic produces the correct distance and there are many correct paths
		this.distanceGoal = h.valueVertex(this) - 0.01*depth;
	}

	@Override
	public int compareTo(AStarVertex v) {
		return Double.compare(distanceGoal + distanceStart, v.distanceGoal + v.distanceStart);
	}
}
