package software.ctrl.Algorithms;

import software.ctrl.Heuristics.AbsoluteDistance;
import software.ctrl.Heuristics.DiagonalDistance;
import software.ctrl.Heuristics.ManhattanDistance;
import software.ctrl.PathFinding.Map;

public abstract class HeuristicPathAlgorithm extends PathAlgorithm {
	
	Heuristic heuristic;
	
	public HeuristicPathAlgorithm(Map map) {
		super(map);
		// TODO Auto-generated constructor stub
	}
	
	
	public Heuristic getHeuristic() {
		return heuristic;
	}

	public void setHeuristic(int type) {
		if(type == Heuristic.DIAGONAL) {
			heuristic = new DiagonalDistance(map.goalX, map.goalY);
		} else if(type == Heuristic.ABSOLUTE) {
			heuristic = new AbsoluteDistance(map.goalX, map.goalY);
		} else if(type == Heuristic.MANHATTAN) {
			heuristic = new ManhattanDistance(map.goalX, map.goalY);
		}
	}

}
