package software.ctrl.PathFinding.GUI;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import software.ctrl.PathFinding.Map;

public class PathTabbedPane extends JTabbedPane {
	
	private static final long serialVersionUID = 5482569171395858309L;
	
	ArrayList<AlgorithmPanel> panels = new ArrayList<AlgorithmPanel>();

	public PathTabbedPane(PathFindingWindow pfw) {
		//setBorder(BorderFactory.createLoweredSoftBevelBorder());
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		
		
		DijkstraPanel dijkstra = new DijkstraPanel(pfw, this);
		panels.add(dijkstra);
		addTab("Dijkstra", dijkstra);
		
		AStarPanel aStar = new AStarPanel(pfw, this);
		panels.add(aStar);
		addTab("A*", aStar);
		
		GreedyBestFirstPanel greedyBestFirst = new GreedyBestFirstPanel(pfw, this);
		panels.add(greedyBestFirst);
		addTab("Greedy Best First", greedyBestFirst);
		
		addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				AlgorithmPanel panel = (AlgorithmPanel) getSelectedComponent();
				pfw.setAlgorithm(panel.algorithm);
				pfw.repaintMap();
			}
			
		});
		
		
	}
	
	public void setMap(Map m) {
		for(AlgorithmPanel p: panels) {
			p.setMap(m);
		}
	}
}
