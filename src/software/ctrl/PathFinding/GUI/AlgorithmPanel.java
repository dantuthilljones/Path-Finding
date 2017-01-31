package software.ctrl.PathFinding.GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.NumberFormatter;

import software.ctrl.Algorithms.PathAlgorithm;
import software.ctrl.PathFinding.Map;

public abstract class AlgorithmPanel extends JPanel {

	private static final long serialVersionUID = -1231668364290617446L;

	protected final PathFindingWindow pfw;
	protected PathAlgorithm algorithm;

	JButton diagButton, startButton, nextButton, stopButton;
	JLabel infoText;
	JSpinner delaySpinner;
	JFormattedTextField delaySpinnerText;

	public AlgorithmPanel(PathFindingWindow pfw, PathTabbedPane ptp, PathAlgorithm a) {
		setLayout(null);

		this.pfw = pfw;
		algorithm = a;

		infoText = new JLabel("Iterations: 0");
		infoText.setBounds(114, 0, 100, 30);
		infoText.setFont(infoText.getFont().deriveFont(12f));
		add(infoText);

		startButton = new JButton(pfw.startIcon);
		nextButton = new JButton(pfw.nextIcon);
		stopButton = new JButton(pfw.stopIcon);

		startButton.setBounds(30,30,69,69);
		startButton.setToolTipText("Start/Pause");
		startButton.setFocusable(false);
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				nextButton.setEnabled(algorithm.isRunning());
				algorithm.setRunning(!algorithm.isRunning());

				if(algorithm.isRunning()) {
					startButton.setIcon(pfw.pauseIcon);

					Thread t = new Thread(new Runnable() {
						@Override
						public void run() {
							while(!algorithm.iterate() && algorithm.isRunning()) {
								infoText.setText("Iterations: " + algorithm.getIterations());
								pfw.repaintMap();

								Integer delay  = (Integer) delaySpinner.getValue();
								if(delay.intValue() > 0) {
									try {
										Thread.sleep(delay);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}

							}


							//if we stopped because we found a solution
							if(algorithm.isRunning()) {
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}

								nextButton.setEnabled(false);
								startButton.setEnabled(false);
								infoText.setText("Iterations: " + algorithm.getIterations());
								pfw.repaintMap();
							}
						}

					});

					t.start();
				} else {
					startButton.setIcon(pfw.startIcon);
				}
			}
		});
		add(startButton);

		stopButton.setBounds(113,30,69,69);
		stopButton.setToolTipText("Stop");
		stopButton.setFocusable(false);
		stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				algorithm.setRunning(false);

				infoText.setText("Iterations: 0");
				startButton.setIcon(pfw.startIcon);
				startButton.setEnabled(true);
				nextButton.setEnabled(true);

				algorithm.reset();
				pfw.repaintMap();
			}

		});
		add(stopButton);


		nextButton.setBounds(196,30,69,69);
		nextButton.setToolTipText("Next");
		nextButton.setFocusable(false);
		nextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(algorithm.iterate()) {
					nextButton.setEnabled(false);
					startButton.setEnabled(false);
				}
				infoText.setText("Iterations: " + algorithm.getIterations());
				pfw.repaintMap();
			}
		});

		add(nextButton);


		diagButton = new JButton("    Diagonals", pfw.offIcon);
		diagButton.setBounds(113,113,152,40);
		diagButton.setToolTipText("Toggle diagonals");
		diagButton.setFocusable(false);
		diagButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				algorithm.setDiagonals(!algorithm.diagonalsAllowed());
				if(algorithm.diagonalsAllowed()) {
					diagButton.setIcon(pfw.onIcon);
				} else {
					diagButton.setIcon(pfw.offIcon);
				}
			}
		});
		add(diagButton);

		JPanel delayPanel = new JPanel();

		//set the border to the same grey as the buttons
		delayPanel.setBorder(BorderFactory.createLineBorder(new Color(173, 173, 173)));
		delayPanel.setBounds(31, 114, 67, 38);
		delayPanel.setLayout(null);
		add(delayPanel);

		JLabel delayLabel = new JLabel("Delay (ms)");
		delayLabel.setBounds(8, 3, 61, 15);
		delayPanel.add(delayLabel);

		delaySpinner = new JSpinner(new SpinnerNumberModel(500, 0, Integer.MAX_VALUE, 100));
		delaySpinner.setBounds(3, 17, 61, 18);
		delaySpinnerText = ((JSpinner.NumberEditor) delaySpinner.getEditor()).getTextField();
		((NumberFormatter) delaySpinnerText.getFormatter()).setAllowsInvalid(false);
		delayPanel.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				delaySpinner.setValue(Math.max(0, Integer.parseInt(delaySpinnerText.getText().replaceAll("[^\\d.]", "")) - e.getWheelRotation()*100));
			}

		});
		delayPanel.add(delaySpinner);


	}

	public void setMap(Map m) {
		resetAlgorithm(m);
		infoText.setText("Iterations: 0");
		startButton.setEnabled(true);
		startButton.setIcon(pfw.startIcon);
		nextButton.setEnabled(true);
	}
	
	public abstract void resetAlgorithm(Map m);
	
}
