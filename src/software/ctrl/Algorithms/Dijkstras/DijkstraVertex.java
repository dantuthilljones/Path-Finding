package software.ctrl.Algorithms.Dijkstras;

import software.ctrl.Algorithms.Vertex;

public class DijkstraVertex extends Vertex implements Comparable<DijkstraVertex> {
	
	public final DijkstraVertex parent;
	public final double distanceStart;
	
	public DijkstraVertex(int x, int y, int depth, double distanceStart, DijkstraVertex parent) {
		super(x, y, depth);
		this.parent = parent;
		this.distanceStart = distanceStart;
	}

	@Override
	public int compareTo(DijkstraVertex v) {
		return Double.compare(distanceStart, v.distanceStart);
	}
}
