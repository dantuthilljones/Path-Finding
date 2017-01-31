package software.ctrl.PathFinding.GUI;

import software.ctrl.Algorithms.AStarAlgorithm;
import software.ctrl.Algorithms.HeuristicPathAlgorithm;
import software.ctrl.Algorithms.PathAlgorithm;
import software.ctrl.PathFinding.Map;

public class AStarPanel extends HeuristicAlgorithmPanel {

	private static final long serialVersionUID = 858028400470285402L;

	public AStarPanel(PathFindingWindow pfw, PathTabbedPane ptp) {
		super(pfw, ptp, new AStarAlgorithm(pfw.getMap()));
		
	}

	@Override
	public void resetAlgorithm(Map m) {
		HeuristicPathAlgorithm a =  new AStarAlgorithm(m);
		heuristicAlgorithm = a;
		algorithm = a;
	}

}
