package software.ctrl.PathFinding.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import software.ctrl.Algorithms.PathAlgorithm;
import software.ctrl.PathFinding.Map;

public class OptionPanel extends JPanel {

	private static final long serialVersionUID = -1425993411776065694L;
	
	private final PathTabbedPane ptp;
	
	public OptionPanel(PathFindingWindow pfw) {
		setMinimumSize(new Dimension(300,300));
		setPreferredSize(new Dimension(300,720));
		setLayout(new BorderLayout());
		
		add(new GeneralSettingsPanel(pfw), BorderLayout.NORTH);
		
		ptp = new PathTabbedPane(pfw);
		add(ptp, BorderLayout.CENTER);	
	}
	
	public void setMap(Map m) {
		ptp.setMap(m);
	}
	
	public PathAlgorithm getAlgorithm() {
		AlgorithmPanel panel = (AlgorithmPanel) ptp.getSelectedComponent();
		return panel.algorithm;
	}
}
