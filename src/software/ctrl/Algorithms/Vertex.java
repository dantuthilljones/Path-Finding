package software.ctrl.Algorithms;

import java.awt.Point;

public class Vertex extends Point {
	
	public final int depth;
	
	public Vertex(int x, int y, int depth) {
		super(x, y);
		this.depth = depth;
	}
}
