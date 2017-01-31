package software.ctrl.Algorithms;

import java.util.ArrayList;
import java.util.PriorityQueue;

import software.ctrl.Algorithms.GreedyBestFirst.GreedyBestFirstVertex;
import software.ctrl.Heuristics.AbsoluteDistance;
import software.ctrl.PathFinding.Map;

public class GreedyBestFirstAlgorithm extends HeuristicPathAlgorithm {
	
	private PriorityQueue<GreedyBestFirstVertex> fringeQueue = new PriorityQueue<GreedyBestFirstVertex>();

	public GreedyBestFirstAlgorithm(Map map) {
		super(map);
		
		heuristic = new AbsoluteDistance(map.goalX, map.goalY);

		fringe[map.startX][map.startY] = true;
		fringeQueue.add(new GreedyBestFirstVertex(map.startX, map.startY, 0, heuristic, null));
	}
	
	@Override
	public boolean iterate() {
		super.iterate();		
		
		GreedyBestFirstVertex v = fringeQueue.poll();
		currentVertex = v;
		fringe[v.x][v.y] = false;
		visitedNodes[v.x][v.y] = true;

		//non diagonals
		for(byte[] i: Map.iter) {
			if(!map.walls[v.x + i[0] ][v.y + i[1] ][ i[2] ] && !visitedNodes[v.x  + i[3] ][v.y + i[4] ]) {
				if(visitNode(new GreedyBestFirstVertex( v.x + i[3], v.y + i[4], v.depth +1, heuristic, v))) return true;
			}
		}

		//diagonals
		if(diagonals) for(byte[] i: Map.iterDiag) {
			if(!map.walls[v.x + i[0] ][v.y + i[1] ][Map.HORIZONTAL] && !map.walls[v.x + i[0] ][v.y + i[1] ][Map.VERTICAL]
					&& !map.walls[v.x + i[0] -1][v.y + i[1] ][Map.HORIZONTAL] && !map.walls[v.x + i[0] ][v.y + i[1] -1][Map.VERTICAL]
							&& !visitedNodes[v.x  + i[2]][v.y + i[3]]) {
				if(visitNode(new GreedyBestFirstVertex( v.x + i[2], v.y + i[3], v.depth +1, heuristic, v))) return true;
			}
		}
		
		return false;
	}

	private boolean visitNode(GreedyBestFirstVertex newVertex) {
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

	private void computePath(GreedyBestFirstVertex finalVertex) {
		ArrayList<Vertex> path = new ArrayList<Vertex>((int) finalVertex.depth +1);
		path.add(finalVertex);

		GreedyBestFirstVertex node = finalVertex;
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
		fringeQueue.add(new GreedyBestFirstVertex(map.startX, map.startY, 0, heuristic, null));
		fringe[map.startX][map.startY] = true;
	}
	
	@Override
	public void reset() {
		super.reset();
		fringeQueue.clear();
		fringeQueue.add(new GreedyBestFirstVertex(map.startX, map.startY, 0, heuristic, null));
		fringe[map.startX][map.startY] = true;
	}

}
