package software.ctrl.PathFinding.GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import software.ctrl.Algorithms.Heuristic;
import software.ctrl.Algorithms.HeuristicPathAlgorithm;

public abstract class HeuristicAlgorithmPanel extends AlgorithmPanel {
	
	protected HeuristicPathAlgorithm heuristicAlgorithm;

	private static final long serialVersionUID = 6345155528309261578L;

	private final JButton absoluteButton, manhattanButton, diagonalButton, infoButton;

	private static final int hX = 130, hY = 45;

	public HeuristicAlgorithmPanel(PathFindingWindow pfw, PathTabbedPane ptp, HeuristicPathAlgorithm a) {
		super(pfw, ptp, a);
		heuristicAlgorithm = a;

		JPanel heuristicPanel = new JPanel();
		heuristicPanel.setBorder(new TitledBorder("Heuristic"));
		heuristicPanel.setBounds(30, 167, 240, 240);
		add(heuristicPanel);

		infoButton = new JButton("Help", pfw.questionIcon);
		infoButton.setPreferredSize(new Dimension(hX, hY));
		infoButton.setToolTipText("Show information regarding the different heuristics");
		infoButton.setFocusable(false);
		infoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				pfw.showHeuristicInfo();
			}

		});

		heuristicPanel.add(infoButton);

		absoluteButton = new JButton("  Absolute   ", pfw.radioOnIcon);
		absoluteButton.setPreferredSize(new Dimension(hX, hY));
		absoluteButton.setToolTipText("Set the heuristic to diagonal");
		absoluteButton.setFocusable(false);
		absoluteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				heuristicAlgorithm.setHeuristic(Heuristic.ABSOLUTE);
				absoluteButton.setIcon(pfw.radioOnIcon);
				manhattanButton.setIcon(pfw.radioOffIcon);
				diagonalButton.setIcon(pfw.radioOffIcon);
			}

		});
		heuristicPanel.add(absoluteButton);

		manhattanButton = new JButton("  Manhattan", pfw.radioOffIcon);
		manhattanButton.setPreferredSize(new Dimension(hX, hY));
		manhattanButton.setToolTipText("Set the heuristic to manhattan");
		manhattanButton.setFocusable(false);
		manhattanButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				heuristicAlgorithm.setHeuristic(Heuristic.MANHATTAN);
				absoluteButton.setIcon(pfw.radioOffIcon);
				manhattanButton.setIcon(pfw.radioOnIcon);
				diagonalButton.setIcon(pfw.radioOffIcon);
			}

		});
		heuristicPanel.add(manhattanButton);

		diagonalButton = new JButton("  Diagonal   ", pfw.radioOffIcon);
		diagonalButton.setPreferredSize(new Dimension(hX, hY));
		diagonalButton.setToolTipText("Set the heuristic to diagonal");
		diagonalButton.setFocusable(false);
		diagonalButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				heuristicAlgorithm.setHeuristic(Heuristic.DIAGONAL);
				absoluteButton.setIcon(pfw.radioOffIcon);
				manhattanButton.setIcon(pfw.radioOffIcon);
				diagonalButton.setIcon(pfw.radioOnIcon);
			}

		});
		heuristicPanel.add(diagonalButton);
	}

}
