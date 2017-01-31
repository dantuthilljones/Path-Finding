package software.ctrl.PathFinding.GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import software.ctrl.Algorithms.PathAlgorithm;
import software.ctrl.Algorithms.Vertex;
import software.ctrl.PathFinding.Map;

public class MapPanel extends JPanel implements MouseWheelListener, MouseMotionListener, MouseListener {

	private static final long serialVersionUID = 1116923523730086506L;

	private static Stroke stroke1 = new BasicStroke(1);
	private static Stroke stroke3 = new BasicStroke(3);
	private static final Color PURPLE = new Color(102, 0, 204);
	private static final Color DARK_RED = new Color(153, 0, 0);
	private static final Color DARK_GREEN = new Color(0, 153, 0);

	private Point mousePoint;

	private Map map;

	private double offsetX, offsetY, scale;
	private int currentX, currentY, selectedX, selectedY;

	private boolean drawCurrent, drawSelected, holdingStart, holdingGoal;

	private PathAlgorithm algorithm;
	
	private final PathFindingWindow pfw;

	public MapPanel(Map map, PathFindingWindow pfw) {
		this.pfw = pfw;
		this.map = map;
		setScale(30);

		addMouseWheelListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	@Override
	public void paint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;

		int offsetX = (int) this.offsetX;
		int offsetY = (int) this.offsetY;
		int scale = (int) this.scale;

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect((int) offsetX + 2, offsetY + 2, map.x*scale, map.y*scale);

		g.setColor(Color.GRAY);
		g.setStroke(stroke1);

		//draw horizontal guide lines
		for(int i = 1; i <= map.x; i++) {
			int newX = offsetX + (i * scale +1);
			g.drawLine(newX, offsetY +  1, newX, offsetY + (map.y*scale));
		}

		//draw vertical guide lines
		for(int j = 1; j <= map.y; j++) {
			int newY = offsetY + (j * scale +1);
			g.drawLine(offsetX + 1, newY, offsetX + (map.x*scale), newY);
		}
		
		
		//draw fringe and visited nodes
		if(algorithm != null) {
			boolean[][] visited = algorithm.getVisitedNodes();
			boolean[][] fringe = algorithm.getFringe();

			if(visited != null && fringe != null) {

				for(int i = 0; i < map.x; i++) {
					for(int j = 0; j < map.y; j++) {
						if(visited[i][j]) {
							g.setColor(Color.ORANGE);
							g.fillRect(offsetX + (i*scale +2), offsetY + (j*scale +2), scale-1, scale -1);
						} else if(fringe[i][j]) {
							g.setColor(Color.PINK);
							g.fillRect(offsetX + (i*scale +2), offsetY + (j*scale +2), scale-1, scale -1);
						}
					}
				}
			}
			
			ArrayList<Vertex> path = algorithm.getPath();
			if(path != null) {
				g.setColor(Color.WHITE);
				g.setStroke(stroke3);
				for(int i = 1; i < path.size(); i++) {
					Vertex v1 = path.get(i-1);
					Vertex v2 = path.get(i);
					g.drawLine(offsetX + (v1.x*scale+scale/2), offsetY + (v1.y*scale+scale/2), offsetX + (v2.x*scale+scale/2), offsetY + (v2.y*scale+scale/2));
				}
			}
		}
		
		int reduce =  scale/6;
		
		//draw start
		g.setColor(Color.GREEN);
		g.fillRect(offsetX + map.startX*scale +2 + reduce, offsetY + map.startY*scale +2 + reduce, scale -1 -reduce*2, scale -1 -reduce*2);

		//draw goal
		g.setColor(Color.RED);
		g.fillRect(offsetX + map.goalX*scale +2 + reduce, offsetY + map.goalY*scale +2 + reduce, scale -1 -reduce*2, scale -1 -reduce*2);

		g.setStroke(stroke3);
		g.setColor(Color.BLACK);
		for(int i = 0; i <= map.x; i++) {
			for(int j = 0; j <= map.y; j++) {

				//draw maze walls;
				if(map.walls[i][j][Map.HORIZONTAL]) {
					int newX = offsetX + (i * scale +1);
					int newY = offsetY + (j * scale +1);
					g.drawLine(newX, newY, newX + scale, newY);
				}

				if(map.walls[i][j][Map.VERTICAL]) {
					int newX = offsetX + (i * scale +1);
					int newY = offsetY + (j * scale +1);
					g.drawLine(newX, newY, newX, newY + scale);
				}
			}
		}

		//draw example lines
		if(drawCurrent && drawSelected) {
			if(currentY == selectedY){
				if(currentX == selectedX +1) { //draw example horizontal right
					if(map.walls[selectedX][selectedY][Map.HORIZONTAL]) {
						g.setColor(DARK_RED);
					} else {
						g.setColor(DARK_GREEN);
					}
					int newX = offsetX + (selectedX * scale +1);
					int newY = offsetY + (selectedY * scale +1);
					g.drawLine(newX, newY, newX + scale, newY);
				} else if(currentX == selectedX -1) { //draw example horizontal left
					if(map.walls[currentX][selectedY][Map.HORIZONTAL]) {
						g.setColor(DARK_RED);
					} else {
						g.setColor(DARK_GREEN);
					}
					int newX = offsetX + ((selectedX-1) * scale +1);
					int newY = offsetY + (selectedY * scale +1);
					g.drawLine(newX, newY, newX + scale, newY);
				}
			} else if(currentX == selectedX){
				if(currentY == selectedY +1) { //draw example vertical down
					if(map.walls[selectedX][selectedY][Map.VERTICAL]) {
						g.setColor(DARK_RED);
					} else {
						g.setColor(DARK_GREEN);
					}
					int newY = offsetY + (selectedY * scale +1);
					int newX = offsetX + (selectedX * scale +1);
					g.drawLine(newX, newY, newX, newY + scale);
				} else if(currentY == selectedY -1) { //draw example vertical up
					if(map.walls[selectedX][currentY][Map.VERTICAL]) {
						g.setColor(DARK_RED);
					} else {
						g.setColor(DARK_GREEN);
					}
					int newY = offsetY + ((selectedY-1) * scale +1);
					int newX = offsetX + (selectedX * scale +1);
					g.drawLine(newX, newY, newX, newY + scale);
				}
			}
		}

		//draw current circle
		if(drawCurrent && currentX <= map.x && currentX >=0 && currentY <= map.y && currentY >= 0) {
			g.setColor(PURPLE);
			g.fillRect(offsetX + (currentX * scale -3), offsetY + (currentY * scale -3), 9, 9);
		}

		//draw selected circle
		if(drawSelected && selectedX <= map.x && selectedX >=0 && selectedY <= map.y && selectedY >= 0) {
			g.setColor(Color.GREEN);
			g.fillRect(offsetX + (selectedX * scale -3), offsetY + (selectedY * scale -3), 9, 9);
		}
		
	}

	public void setMap(Map newMap, boolean rescale) {
		map = newMap;
		if(algorithm != null) {
			algorithm.reset(map);
		}
		if(rescale) rescale();
		repaint();
	}

	public Map getMap() {
		return map;
	}	

	public double getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
		repaint();
	}

	public int getOffsetX() {
		return (int) offsetX;
	}

	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
		repaint();
	}

	public int getOffsetY() {
		return (int) offsetY;
	}

	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
		repaint();
	}

	public void resetOffset() {
		offsetY = offsetX = 0;
	}

	public void rescale() {
		resetOffset();
		scale = Math.min( (getWidth()-2) / map.x, (getHeight()-2) /map.y);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		double scaledX = ( (double) (e.getX() - offsetX)) / (map.x*scale);
		double scaledY = ( (double) (e.getY() - offsetY)) / (map.y*scale);

		scale *= (1d - 0.1*e.getWheelRotation());
		if(scale < 6d) scale = 6d;

		offsetX = (((double) e.getX()) - (scaledX*map.x*scale));
		offsetY = (((double) e.getY()) - (scaledY*map.y*scale));

		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(SwingUtilities.isMiddleMouseButton(e) || SwingUtilities.isRightMouseButton(e)) {
			offsetX += e.getX() - mousePoint.x;
			offsetY += e.getY() - mousePoint.y;
			mousePoint = e.getPoint();
			repaint();
		} else if(SwingUtilities.isLeftMouseButton(e)) {

			
			//if start box
			if(holdingStart) {	
				
				int oldX = map.startX, oldY = map.startY;
				map.startX = ( (e.getX() - (int)scale/2 - ((int) offsetX) + ((int) scale/2)) / (int) scale);
				map.startY = ( (e.getY() - (int) scale/2 - ((int) offsetY) + ((int) scale/2)) / (int) scale);		
				
				if(oldX != map.startX || oldY != map.startY) {
					repaint();
					pfw.setMap(map, false);
				}
				
				//if goal box
			} else if(holdingGoal) {
				int oldX = map.goalX, oldY = map.goalY;
				map.goalX = ( (e.getX() - (int)scale/2 - ((int) offsetX) + ((int) scale/2)) / (int) scale);
				map.goalY = ( (e.getY() - (int) scale/2 - ((int) offsetY) + ((int) scale/2)) / (int) scale);		
				
				if(oldX != map.goalX || oldY != map.goalY) {
					repaint();
					pfw.setMap(map, false);
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mousePoint = e.getPoint();
		if(SwingUtilities.isMiddleMouseButton(e) || SwingUtilities.isRightMouseButton(e)) {
			setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		} else {
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			if(SwingUtilities.isLeftMouseButton(e)) {
				int offsetX = (int) this.offsetX;
				int offsetY = (int) this.offsetY;
				int scale = (int) this.scale;
				
				int reduce = scale/6;
				
				//draw start
				Rectangle start = new Rectangle(offsetX + map.startX*scale +2 + reduce, offsetY + map.startY*scale +2 + reduce, scale -1 -reduce*2, scale -1 -reduce*2);

				//draw goal
				Rectangle goal = new Rectangle(offsetX + map.goalX*scale +2 + reduce, offsetY + map.goalY*scale +2 + reduce, scale -1 -reduce*2, scale -1 -reduce*2);
				
				//if start box
				if(start.contains(mousePoint)) {
					holdingStart = true;
					holdingGoal = false;
					
					//if goal box
				} else if(goal.contains(mousePoint)) {
					holdingStart = false;
					holdingGoal = true;
					
				//if near a corner
				} else if(Math.pow(Math.pow(offsetX + (currentX * scale -3) - e.getX(), 2) + Math.pow(offsetY + (currentY * scale -3) - e.getY(), 2), 0.5) < scale/2) {

					if(currentX == selectedX && currentY == selectedY) {
						drawSelected = !drawSelected;
					} else {

						if(drawCurrent && drawSelected) {

							if(currentY == selectedY){
								if(currentX == selectedX +1) { //horizontal rightf
									map.walls[selectedX][selectedY][Map.HORIZONTAL] = !map.walls[selectedX][selectedY][Map.HORIZONTAL];
								} else if(currentX == selectedX -1) { //horizontal left
									map.walls[currentX][selectedY][Map.HORIZONTAL] = !map.walls[currentX][selectedY][Map.HORIZONTAL];
								}
							} else if(currentX == selectedX){
								if(currentY == selectedY +1) { //vertical down
									map.walls[selectedX][selectedY][Map.VERTICAL] = !map.walls[selectedX][selectedY][Map.VERTICAL];
								} else if(currentY == selectedY -1) { //vertical up
									map.walls[selectedX][currentY][Map.VERTICAL] = !map.walls[selectedX][currentY][Map.VERTICAL];
								}
							}
						}

						selectedX = currentX;
						selectedY = currentY;
						drawSelected = true;
					}
					repaint();
				}
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		currentX = ( (e.getX() - ((int) offsetX) + ((int) scale/2)) / (int) scale);
		currentY = ( (e.getY() - ((int) offsetY) + ((int) scale/2)) / (int) scale);
		drawCurrent = Math.pow(Math.pow(offsetX + (currentX * scale -3) - e.getX(), 2) + Math.pow(offsetY + (currentY * scale -3) - e.getY(), 2), 0.5) < scale/2;

		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		if(SwingUtilities.isLeftMouseButton(e)) {
			holdingStart = false;
			holdingGoal = false;
		}
	}

	public PathAlgorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorythm(PathAlgorithm algorithm) {
		this.algorithm = algorithm;
	}
}
