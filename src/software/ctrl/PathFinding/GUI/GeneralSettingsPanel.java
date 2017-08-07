package software.ctrl.PathFinding.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GeneralSettingsPanel extends JPanel {
	
	private static final long serialVersionUID = 3327827120712298541L;
	
	JButton randomButton, clearButton, focusButton;

	public GeneralSettingsPanel(PathFindingWindow pfw) {
		//setPreferredSize(new Dimension(300, 100));
		setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.BLACK));
		
		JPanel content = new JPanel();
		//content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		//content.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.BLACK));
		
		content.setLayout(new GridLayout( 1, 3, 10, 10));
		//content.setBackground(Color.green);
		
		randomButton = new JButton(pfw.diceIcon);
		randomButton.setToolTipText("Generate random maze");
		randomButton.setFocusable(false);
		randomButton.setMaximumSize(new Dimension(69, 69));
		content.add(randomButton);
		
		clearButton = new JButton(pfw.demolishIcon);
		clearButton.setToolTipText("Clear maze");
		clearButton.setFocusable(false);
		clearButton.setMaximumSize(new Dimension(69, 69));
		clearButton.addActionListener(e -> {
			pfw.getMap().demolish();
			pfw.repaintMap();
		});
		content.add(clearButton);
		
		focusButton = new JButton(pfw.focusIcon);
		focusButton.setToolTipText("Focus maze");
		focusButton.setFocusable(false);
		focusButton.setMaximumSize(new Dimension(69, 69));
		focusButton.addActionListener(e -> pfw.focusMap());
		content.add(focusButton);
		
		add(content, BorderLayout.CENTER);
	}
}
