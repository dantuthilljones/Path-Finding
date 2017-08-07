package software.ctrl.Algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.PriorityQueue;

import software.ctrl.Algorithms.Dijkstras.DijkstraVertex;
import software.ctrl.PathFinding.Map;

public class DijkstraAlgorithm extends PathAlgorithm {

	private PriorityQueue<DijkstraVertex> fringeQueue = new PriorityQueue<DijkstraVertex>();

	public DijkstraAlgorithm(Map map) {
		super(map);
		
		Point start = map.getStart();
		fringeQueue.add(new DijkstraVertex(start.x, start.y, 0, 0, null));
		fringe[start.x][start.y] = true;
	}

	@Override
	public boolean iterate() {
		super.iterate();

		DijkstraVertex v = fringeQueue.poll();
		currentVertex = v;
		fringe[v.x][v.y] = false;
		visitedNodes[v.x][v.y] = true;


		//non diagonals
		for(byte[] i: Map.iter) {
			if(!map.isWall(v.x + i[0], v.y + i[1], i[2]) && !visitedNodes[v.x  + i[3] ][v.y + i[4] ]) {
				if(visitNode(new DijkstraVertex( v.x + i[3], v.y + i[4], (int) v.distanceStart +1, v.distanceStart +1, v))) return true;
			}
		}

		//diagonals
		if(diagonals) for(byte[] i: Map.iterDiag) {
			if(!map.isWall(v.x + i[0], v.y + i[1], Map.HORIZONTAL) && !map.isWall(v.x + i[0], v.y + i[1],Map.VERTICAL)
					&& !map.isWall(v.x + i[0] -1, v.y + i[1], Map.HORIZONTAL) && !map.isWall(v.x + i[0], v.y + i[1] -1, Map.VERTICAL)
							&& !visitedNodes[v.x  + i[2]][v.y + i[3]]) {
				if(visitNode(new DijkstraVertex( v.x + i[2], v.y + i[3],  (int) v.distanceStart +1, v.distanceStart +1, v))) return true;
			}
		}

		return false;
	}

	private boolean visitNode(DijkstraVertex newVertex) {
		
		//if the adjacent nodes are already on the fringe, we don't need to replace it because
		//all the step costs are 1 so we won't find a shorter alternative path
		//to this vertex in the future
		if(!visitedNodes[newVertex.x][newVertex.y] && !fringe[newVertex.x][newVertex.y]) {
			fringeQueue.add(newVertex);
			fringe[newVertex.x][newVertex.y] = true;
		}
		
		if(newVertex.x == map.getGoal().x && newVertex.y == map.getGoal().y) {
			computePath(newVertex);
			return true;
		}
		return false;
	}

	private void computePath(DijkstraVertex finalVertex) {
		ArrayList<Vertex> path = new ArrayList<Vertex>(finalVertex.depth+1);
		path.add(finalVertex);

		DijkstraVertex node = finalVertex;
		while(node.parent != null) {
			node = node.parent;
			path.add(node);
		}

		this.path = path;
	}
	
	@Override
	public void reset(Map m) {
		super.reset(m);
		Point start = m.getStart();
		fringeQueue.clear();
		fringeQueue.add(new DijkstraVertex(start.x, start.y, 0, 0, null));
		fringe[start.x][start.y] = true;
	}
	
	@Override
	public void reset() {
		super.reset();
		Point start = map.getStart();
		fringeQueue.clear();
		fringeQueue.add(new DijkstraVertex(start.x, start.y, 0, 0, null));
		fringe[start.x][start.y] = true;
	}
}
