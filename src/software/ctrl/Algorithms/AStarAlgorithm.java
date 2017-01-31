package software.ctrl.Algorithms;

import java.util.ArrayList;
import java.util.PriorityQueue;

import software.ctrl.Algorithms.AStar.AStarVertex;
import software.ctrl.Heuristics.AbsoluteDistance;
import software.ctrl.PathFinding.Map;

public class AStarAlgorithm extends HeuristicPathAlgorithm {

	private PriorityQueue<AStarVertex> fringeQueue = new PriorityQueue<AStarVertex>();

	public AStarAlgorithm(Map map) {
		super(map);
		
		heuristic = new AbsoluteDistance(map.goalX, map.goalY);

		fringeQueue.add(new AStarVertex(map.startX, map.startY, 0, 0, heuristic, null));
		fringe[map.startX][map.startY] = true;
	}

	@Override
	public boolean iterate() {
		super.iterate();		
		
		AStarVertex v = fringeQueue.poll();
		currentVertex = v;
		fringe[v.x][v.y] = false;
		visitedNodes[v.x][v.y] = true;
		
		//debug
		System.out.println("Chosen: x=" + v.x + " y=" + v.y + "   h(x)=" + v.distanceGoal + "   f(x)=" + v.distanceStart + "   sum=" + (v.distanceGoal + v.distanceGoal));


		//non diagonals
		for(byte[] i: Map.iter) {
			if(!map.walls[v.x + i[0] ][v.y + i[1] ][ i[2] ] && !visitedNodes[v.x  + i[3] ][v.y + i[4] ]) {
				if(visitNode(new AStarVertex( v.x + i[3], v.y + i[4], (int) v.distanceStart +1, v.distanceStart +1, heuristic, v))) return true;
			}
		}

		//diagonals
		if(diagonals) for(byte[] i: Map.iterDiag) {
			if(!map.walls[v.x + i[0] ][v.y + i[1] ][Map.HORIZONTAL] && !map.walls[v.x + i[0] ][v.y + i[1] ][Map.VERTICAL]
					&& !map.walls[v.x + i[0] -1][v.y + i[1] ][Map.HORIZONTAL] && !map.walls[v.x + i[0] ][v.y + i[1] -1][Map.VERTICAL]
							&& !visitedNodes[v.x  + i[2]][v.y + i[3]]) {
				if(visitNode(new AStarVertex( v.x + i[2], v.y + i[3], (int) v.distanceStart +1, v.distanceStart +1, heuristic, v))) return true;
			}
		}

		//error printing
		System.out.println("Fringe:");
		for(AStarVertex v2: fringeQueue) {
			System.out.println("x=" + v2.x + " y=" + v2.y + "   h(x)=" + v2.distanceGoal + "   f(x)=" + v2.distanceStart + "   sum=" + (v2.distanceGoal + v2.distanceStart));
		}
		
		return false;
	}

	private boolean visitNode(AStarVertex newVertex) {
		System.out.println(newVertex.distanceGoal + "\tx=" + newVertex.x + "\ty=" + newVertex.y);
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

	private void computePath(AStarVertex finalVertex) {
		ArrayList<Vertex> path = new ArrayList<Vertex>((int) finalVertex.distanceStart+1);
		path.add(finalVertex);

		AStarVertex node = finalVertex;
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
		fringeQueue.add(new AStarVertex(map.startX, map.startY, 0, 0, heuristic, null));
		fringe[map.startX][map.startY] = true;
	}
	
	@Override
	public void reset() {
		super.reset();
		fringeQueue.clear();
		fringeQueue.add(new AStarVertex(map.startX, map.startY, 0, 0, heuristic, null));
		fringe[map.startX][map.startY] = true;
	}
}
