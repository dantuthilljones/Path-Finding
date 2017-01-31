package software.ctrl.PathFinding;

import javax.swing.SwingUtilities;

import software.ctrl.PathFinding.GUI.PathFindingWindow;

public class PathFinding {

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new PathFindingWindow();
			}
		});

	}
}
