package software.ctrl.Algorithms;

public interface Heuristic {
	
	public static int ABSOLUTE = 0, DIAGONAL = 1, MANHATTAN = 2;
	
	public double valueVertex(Vertex v);
	
}
