package software.ctrl.PathFinding.GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import software.ctrl.Algorithms.PathAlgorithm;
import software.ctrl.PathFinding.Map;
import software.ctrl.PathFinding.GUI.MapPanel;

public class PathFindingWindow extends JFrame {

	private static final long serialVersionUID = 7719028744574691816L;

	private final MapPanel mapPanel;

	public ImageIcon startIcon, pauseIcon, stopIcon, nextIcon, folderIcon,
	saveIcon, newIcon, onIcon, offIcon, radioOffIcon, radioOnIcon, questionIcon,
	focusIcon, diceIcon, demolishIcon;

	public Image heuristicAbs, heuristicDiag, heuristicMan, questionImage;

	private final OptionPanel options;

	private final HeuristicInfo heuristicInfo;

	public PathFindingWindow() {

		//set look and feel to system default
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		//load icons
		try {
			int controlButtonSize = 55;
			int radioButtonSize = 25;
			this.setIconImage(ImageIO.read(getClass().getResource("/icons/png/map.png")));

			questionImage = ImageIO.read(getClass().getResource("/icons/png/question.png"));
			heuristicAbs =  ImageIO.read(getClass().getResource("/img/dist-abs.png"));
			heuristicDiag = ImageIO.read(getClass().getResource("/img/dist-diag.png"));
			heuristicMan =  ImageIO.read(getClass().getResource("/img/dist-man.png"));

			questionIcon = new ImageIcon(questionImage.getScaledInstance(radioButtonSize, radioButtonSize, Image.SCALE_SMOOTH));

			startIcon =    new ImageIcon(ImageIO.read(getClass().getResource("/icons/png/play-button.png")).getScaledInstance(controlButtonSize, controlButtonSize, Image.SCALE_SMOOTH));
			pauseIcon =    new ImageIcon(ImageIO.read(getClass().getResource("/icons/png/pause-button.png")).getScaledInstance(controlButtonSize, controlButtonSize, Image.SCALE_SMOOTH));
			stopIcon =     new ImageIcon(ImageIO.read(getClass().getResource("/icons/png/square.png")).getScaledInstance(controlButtonSize, controlButtonSize, Image.SCALE_SMOOTH));
			nextIcon =     new ImageIcon(ImageIO.read(getClass().getResource("/icons/png/arrows.png")).getScaledInstance(controlButtonSize, controlButtonSize, Image.SCALE_SMOOTH));
			onIcon =       new ImageIcon(ImageIO.read(getClass().getResource("/icons/png/switch-on.png")).getScaledInstance(controlButtonSize, controlButtonSize, Image.SCALE_SMOOTH));
			offIcon =      new ImageIcon(ImageIO.read(getClass().getResource("/icons/png/switch-off.png")).getScaledInstance(controlButtonSize, controlButtonSize, Image.SCALE_SMOOTH));
			folderIcon =   new ImageIcon(ImageIO.read(getClass().getResource("/icons/png/folder.png")).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
			saveIcon =     new ImageIcon(ImageIO.read(getClass().getResource("/icons/png/technology.png")).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
			newIcon =      new ImageIcon(ImageIO.read(getClass().getResource("/icons/png/add.png")).getScaledInstance(20, 20, Image.SCALE_SMOOTH));
			radioOnIcon =  new ImageIcon(ImageIO.read(getClass().getResource("/icons/png/radio-on-button.png")).getScaledInstance(radioButtonSize, radioButtonSize, Image.SCALE_SMOOTH));
			radioOffIcon = new ImageIcon(ImageIO.read(getClass().getResource("/icons/png/circle-outline.png")).getScaledInstance(radioButtonSize, radioButtonSize, Image.SCALE_SMOOTH));
			focusIcon =    new ImageIcon(ImageIO.read(getClass().getResource("/icons/png/focus.png")).getScaledInstance(controlButtonSize, controlButtonSize, Image.SCALE_SMOOTH));
			diceIcon =     new ImageIcon(ImageIO.read(getClass().getResource("/icons/png/dice.png")).getScaledInstance(controlButtonSize, controlButtonSize, Image.SCALE_SMOOTH));
			demolishIcon = new ImageIcon(ImageIO.read(getClass().getResource("/icons/png/demolish.png")).getScaledInstance(controlButtonSize, controlButtonSize, Image.SCALE_SMOOTH));

			
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		setTitle("Path Finding");
		setBounds(30, 30, 1280, 720);
		setResizable(true);	
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		Container content = new JPanel();
		content.setLayout(new BorderLayout());

		mapPanel = new MapPanel(new Map(10, 10), this);
		content.add(mapPanel, BorderLayout.CENTER);

		options = new OptionPanel(this);
		content.add(options, BorderLayout.EAST);

		JMenuBar menu = new PathMenuBar(this);
		setJMenuBar(menu);

		setContentPane(content);

		heuristicInfo = new HeuristicInfo(this);
		
		setVisible(true);
	}

	public void setMap(Map newMap, boolean rescale) {
		mapPanel.setMap(newMap, rescale);
		options.setMap(newMap);
		mapPanel.setAlgorythm(options.getAlgorithm());

	}

	public Map getMap() {
		return mapPanel.getMap();
	}

	public void repaintMap() {
		mapPanel.repaint();
	}

	public void setAlgorithm(PathAlgorithm a) {
		mapPanel.setAlgorythm(a);
	}

	public void showHeuristicInfo() {
		heuristicInfo.setVisible(true);
	}

	public void focusMap() {
		// TODO Auto-generated method stub
		
	}

}
