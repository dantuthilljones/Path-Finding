package software.ctrl.PathFinding.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

import software.ctrl.PathFinding.Map;

public class PathMenuBar extends JMenuBar {

	private static final long serialVersionUID = 8740152874801837070L;

	private final JMenu fileMenu;
	private final JMenuItem newItem, openItem, saveAsItem, saveItem;
	private final JFileChooser fc;
	private final JDialog newMapDialog;
	
	private File currentFile;

	public PathMenuBar(PathFindingWindow pfw) {
		fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("Map Files (.pfmf)", new String[] { "pfmf"}));

		fileMenu = new JMenu("File");

		newItem = new JMenuItem("New Map...", pfw.newIcon);
		newMapDialog = new NewMapDialog(pfw);
		newItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				newMapDialog.setVisible(true);
			}

		});

		openItem = new JMenuItem("Open Map...", pfw.folderIcon);
		openItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = fc.showOpenDialog(pfw);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					currentFile = fc.getSelectedFile();
					try {
						ObjectInputStream ois = new ObjectInputStream(new FileInputStream(currentFile));
						Map newMap = (Map) ois.readObject();
						ois.close();
						pfw.setMap(newMap, true);
					} catch (IOException | ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}	
		});


		saveAsItem = new JMenuItem("Save As...", pfw.saveIcon);
		saveAsItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = fc.showSaveDialog(pfw);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					try {
						if(!file.getName().toLowerCase().endsWith(".pfmf")) {
							file = new File(file.getPath() + ".pfmf");
						}
						ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file, false));
						oos.writeObject(pfw.getMap());
						oos.close();
						
						currentFile = file;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}	
		});

		saveItem = new JMenuItem("Save", pfw.saveIcon);

		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.addSeparator();
		fileMenu.add(saveAsItem);
		fileMenu.add(saveItem);

		add(fileMenu);
	}
}
