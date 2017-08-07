package software.ctrl.Algorithms.GreedyBestFirst;

import software.ctrl.Algorithms.Heuristic;
import software.ctrl.Algorithms.Vertex;

public class GreedyBestFirstVertex extends Vertex implements Comparable<GreedyBestFirstVertex> {
	
	public final double distanceGoal;
	public final GreedyBestFirstVertex parent;
	
	public GreedyBestFirstVertex(int x, int y, int depth, Heuristic h, GreedyBestFirstVertex parent) {
		super(x, y, depth);
		this.parent = parent;
		
		this.distanceGoal = h.valueVertex(this);
	}

	@Override
	public int compareTo(GreedyBestFirstVertex v) {
		return java.lang.Double.compare(distanceGoal, v.distanceGoal);
	}
}
