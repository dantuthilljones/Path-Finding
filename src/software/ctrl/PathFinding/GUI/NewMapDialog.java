package software.ctrl.PathFinding.GUI;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import software.ctrl.PathFinding.Map;

public class NewMapDialog extends JDialog {

	private static final long serialVersionUID = 7476691924996025066L;
	
	private final PathFindingWindow pathFindingWindow;
	
	public NewMapDialog(PathFindingWindow pathFindingWindow) {
		super(pathFindingWindow, "New Map", true);
		
		this.pathFindingWindow = pathFindingWindow;
		
		setBounds(0, 0, 165, 170);
		setResizable(false);
		
		Container content = new Container();
		content.setPreferredSize(new Dimension(160, 105));
		
		JLabel widthLabel = new JLabel("Width:");
		widthLabel.setBounds(12, 10, 40, 20);
		content.add(widthLabel);
		
		JLabel heightLabel = new JLabel("Height:");
		heightLabel.setBounds(12, 40, 40, 20);
		content.add(heightLabel);
		
		JSpinner widthSpinner = new JSpinner(new SpinnerNumberModel(10, 2, Integer.MAX_VALUE, 1));
		widthSpinner.setBounds(70, 10, 80, 20);
		content.add(widthSpinner);
		
		JSpinner heightSpinner = new JSpinner(new SpinnerNumberModel(10, 2, Integer.MAX_VALUE, 1));
		heightSpinner.setBounds(70, 40, 80, 20);
		content.add(heightSpinner);
		
		JButton okButton = new JButton("OK");
		okButton.setBounds(10, 70, 65, 25);
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int x = (int) widthSpinner.getValue();
				int y = (int) heightSpinner.getValue();
				pathFindingWindow.setMap(new Map(x,y), true);
				setVisible(false);
			}
			
		});
		content.add(okButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(85, 70, 65, 25);
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
			
		});
		content.add(cancelButton);
		
		setContentPane(content);
		
		//make the dialog the size of the content pane
		pack();
		
		//centre the dialog
		setLocationRelativeTo(pathFindingWindow);
	}

}
