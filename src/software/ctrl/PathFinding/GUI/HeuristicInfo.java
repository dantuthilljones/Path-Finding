package software.ctrl.PathFinding.GUI;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class HeuristicInfo extends JFrame {

	private static final long serialVersionUID = -7815348471955039938L;
	
	private static final Font font = new Font("Arial", Font.BOLD, 20);

	public HeuristicInfo(PathFindingWindow pfw) {
		int imageWidth = pfw.heuristicAbs.getWidth(null);
		int imageHeight = pfw.heuristicAbs.getHeight(null);
		
		this.setBounds(100, 100, imageWidth+50, 500);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setTitle("Heuristics");
		this.setIconImage(pfw.questionImage);
		
		JPanel c = new JPanel();
		c.setLayout(null);
		c.setPreferredSize(new Dimension(imageWidth, imageHeight*3+ 90));
		JScrollPane sp = new JScrollPane(c);
		
		
		JLabel abs = new JLabel(new ImageIcon(pfw.heuristicAbs));
		abs.setBounds(0,20,imageWidth,imageHeight);
		
		JLabel absLabel = new JLabel("Absolute");
		absLabel.setBounds(10,0,imageWidth,30);
		absLabel.setFont(font);
		
		c.add(absLabel);
		c.add(abs);
		
		
		JLabel diag = new JLabel(new ImageIcon(pfw.heuristicDiag));
		diag.setBounds(0,50+imageHeight,imageWidth,imageHeight);
		
		JLabel diagLabel = new JLabel("Diagonal");
		diagLabel.setBounds(10,30+imageHeight,imageWidth,30);
		diagLabel.setFont(font);
		
		c.add(diagLabel);
		c.add(diag);
		
		
		
		JLabel man = new JLabel(new ImageIcon(pfw.heuristicMan));
		man.setBounds(0, 80+imageHeight*2,imageWidth,imageHeight);
		
		JLabel manLabel = new JLabel("Manhattan");
		manLabel.setBounds(10,(30+imageHeight)*2,imageWidth,30);
		manLabel.setFont(font);
		
		c.add(manLabel);
		c.add(man);
		
	//.setLayout(new BorderLayout());
		//c.add(sp, BorderLayout.CENTER);
		this.setContentPane(sp);
		
	}
	
}
