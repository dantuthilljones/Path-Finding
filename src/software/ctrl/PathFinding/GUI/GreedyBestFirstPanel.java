package software.ctrl.PathFinding.GUI;

import software.ctrl.Algorithms.GreedyBestFirstAlgorithm;
import software.ctrl.Algorithms.HeuristicPathAlgorithm;
import software.ctrl.PathFinding.Map;

public class GreedyBestFirstPanel extends HeuristicAlgorithmPanel {

	private static final long serialVersionUID = 3937217937065855422L;

	public GreedyBestFirstPanel(PathFindingWindow pfw, PathTabbedPane ptp) {
		super(pfw, ptp, new GreedyBestFirstAlgorithm(pfw.getMap()));
	}

	@Override
	public void resetAlgorithm(Map m) {
		HeuristicPathAlgorithm a =  new GreedyBestFirstAlgorithm(m);
		heuristicAlgorithm = a;
		algorithm = a;
	}

}
