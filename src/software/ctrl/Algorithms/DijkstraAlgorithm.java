package software.ctrl.Algorithms;

import java.util.ArrayList;
import java.util.PriorityQueue;

import software.ctrl.Algorithms.Dijkstras.DijkstraVertex;
import software.ctrl.PathFinding.Map;

public class DijkstraAlgorithm extends PathAlgorithm {

	private PriorityQueue<DijkstraVertex> fringeQueue = new PriorityQueue<DijkstraVertex>();

	public DijkstraAlgorithm(Map map) {
		super(map);

		fringeQueue.add(new DijkstraVertex(map.startX, map.startY, 0, 0, null));
		fringe[map.startX][map.startY] = true;
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
			if(!map.walls[v.x + i[0] ][v.y + i[1] ][ i[2] ] && !visitedNodes[v.x  + i[3] ][v.y + i[4] ]) {
				if(visitNode(new DijkstraVertex( v.x + i[3], v.y + i[4], (int) v.distanceStart +1, v.distanceStart +1, v))) return true;
			}
		}

		//diagonals
		if(diagonals) for(byte[] i: Map.iterDiag) {
			if(!map.walls[v.x + i[0] ][v.y + i[1] ][Map.HORIZONTAL] && !map.walls[v.x + i[0] ][v.y + i[1] ][Map.VERTICAL]
					&& !map.walls[v.x + i[0] -1][v.y + i[1] ][Map.HORIZONTAL] && !map.walls[v.x + i[0] ][v.y + i[1] -1][Map.VERTICAL]
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
		
		if(newVertex.x == map.goalX && newVertex.y == map.goalY) {
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
		fringeQueue.clear();
		fringeQueue.add(new DijkstraVertex(map.startX, map.startY, 0, 0, null));
		fringe[map.startX][map.startY] = true;
	}
	
	@Override
	public void reset() {
		super.reset();
		fringeQueue.clear();
		fringeQueue.add(new DijkstraVertex(map.startX, map.startY, 0, 0, null));
		fringe[map.startX][map.startY] = true;
	}
}
