package software.ctrl.PathFinding.GUI;

import software.ctrl.Algorithms.DijkstraAlgorithm;
import software.ctrl.PathFinding.Map;

public class DijkstraPanel extends AlgorithmPanel {

	private static final long serialVersionUID = 858028400470285402L;

	public DijkstraPanel(PathFindingWindow pfw, PathTabbedPane ptp) {
		super(pfw, ptp, new DijkstraAlgorithm(pfw.getMap()));
		pfw.setAlgorithm(algorithm);
	}

	@Override
	public void resetAlgorithm(Map m) {
		algorithm = new DijkstraAlgorithm(m);
	}

}
