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
	private Point hoveringPoint, selectedPoint;

	private boolean drawHovering;//draw the corner that the mouse is currently hovering over
	private boolean drawSelected;//draw the corner that we have selected
	private boolean holdingStart;//if the user is dragging the start location
	private boolean holdingGoal;//if the user is dragging the goal location

	private PathAlgorithm algorithm;

	private final PathFindingWindow pfw;

	public MapPanel(Map map, PathFindingWindow pfw) {
		this.pfw = pfw;
		this.map = map;
		setScale(30);

		hoveringPoint = new Point();
		selectedPoint = new Point();

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
		g.fillRect((int) offsetX + 2, offsetY + 2, map.getWidth()*scale, map.getHeight()*scale);

		g.setColor(Color.GRAY);
		g.setStroke(stroke1);

		//draw horizontal guide lines
		for(int i = 1; i <= map.getWidth(); i++) {
			int newX = offsetX + (i * scale +1);
			g.drawLine(newX, offsetY +  1, newX, offsetY + (map.getHeight()*scale));
		}

		//draw vertical guide lines
		for(int j = 1; j <= map.getHeight(); j++) {
			int newY = offsetY + (j * scale +1);
			g.drawLine(offsetX + 1, newY, offsetX + (map.getWidth()*scale), newY);
		}


		//draw fringe and visited nodes
		if(algorithm != null) {
			boolean[][] visited = algorithm.getVisitedNodes();
			boolean[][] fringe = algorithm.getFringe();

			if(visited != null && fringe != null) {

				for(int i = 0; i < map.getWidth(); i++) {
					for(int j = 0; j < map.getHeight(); j++) {
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
		Point start = map.getStart();
		g.fillRect(offsetX + start.x*scale +2 + reduce, offsetY + start.y*scale +2 + reduce, scale -1 -reduce*2, scale -1 -reduce*2);

		//draw goal
		g.setColor(Color.RED);
		Point goal = map.getGoal();
		g.fillRect(offsetX + goal.x*scale +2 + reduce, offsetY + goal.y*scale +2 + reduce, scale -1 -reduce*2, scale -1 -reduce*2);


		//draw maze walls
		g.setStroke(stroke3);
		g.setColor(Color.BLACK);
		for(int i = 0; i <= map.getWidth(); i++) {
			for(int j = 0; j <= map.getHeight(); j++) {

				if(map.isWall(i,j,Map.HORIZONTAL)) {
					int newX = offsetX + (i * scale +1);
					int newY = offsetY + (j * scale +1);
					g.drawLine(newX, newY, newX + scale, newY);
				}

				if(map.isWall(i,j,Map.VERTICAL)) {
					int newX = offsetX + (i * scale +1);
					int newY = offsetY + (j * scale +1);
					g.drawLine(newX, newY, newX, newY + scale);
				}
			}
		}

		//draw example lines
		if(drawHovering && drawSelected) {
			if(hoveringPoint.y == selectedPoint.y){
				//draw example horizontal right
				if(hoveringPoint.x == selectedPoint.x +1) {
					if(map.isWall(selectedPoint.x,selectedPoint.y,Map.HORIZONTAL)) {
						g.setColor(DARK_RED);
					} else {
						g.setColor(DARK_GREEN);
					}
					int newX = offsetX + (selectedPoint.x * scale +1);
					int newY = offsetY + (selectedPoint.y * scale +1);
					g.drawLine(newX, newY, newX + scale, newY);

					//draw example horizontal left
				} else if(hoveringPoint.x == selectedPoint.x -1) {
					if(map.isWall(hoveringPoint.x,selectedPoint.y,Map.HORIZONTAL)) {
						g.setColor(DARK_RED);
					} else {
						g.setColor(DARK_GREEN);
					}
					int newX = offsetX + ((selectedPoint.x-1) * scale +1);
					int newY = offsetY + (selectedPoint.y * scale +1);
					g.drawLine(newX, newY, newX + scale, newY);
				}
			} else if(hoveringPoint.x == selectedPoint.x){
				if(hoveringPoint.y == selectedPoint.y +1) { //draw example vertical down
					if(map.isWall(selectedPoint.x,selectedPoint.y,Map.VERTICAL)) {
						g.setColor(DARK_RED);
					} else {
						g.setColor(DARK_GREEN);
					}
					int newY = offsetY + (selectedPoint.y * scale +1);
					int newX = offsetX + (selectedPoint.x * scale +1);
					g.drawLine(newX, newY, newX, newY + scale);
				} else if(hoveringPoint.y == selectedPoint.y -1) { //draw example vertical up
					if(map.isWall(selectedPoint.x,hoveringPoint.y,Map.VERTICAL)) {
						g.setColor(DARK_RED);
					} else {
						g.setColor(DARK_GREEN);
					}
					int newY = offsetY + ((selectedPoint.y-1) * scale +1);
					int newX = offsetX + (selectedPoint.x * scale +1);
					g.drawLine(newX, newY, newX, newY + scale);
				}
			}
		}

		//draw current circle
		if(drawHovering && hoveringPoint.x <= map.getWidth() && hoveringPoint.x >=0 && hoveringPoint.y <= map.getHeight() && hoveringPoint.y >= 0) {
			g.setColor(PURPLE);
			g.fillRect(offsetX + (hoveringPoint.x * scale -3), offsetY + (hoveringPoint.y * scale -3), 9, 9);
		}

		//draw selected circle
		if(drawSelected && selectedPoint.x <= map.getWidth() && selectedPoint.x >=0 && selectedPoint.y <= map.getHeight() && selectedPoint.y >= 0) {
			g.setColor(Color.GREEN);
			g.fillRect(offsetX + (selectedPoint.x * scale -3), offsetY + (selectedPoint.y * scale -3), 9, 9);
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
		scale = Math.min( (getWidth()-2) / map.getWidth(), (getHeight()-2) /map.getHeight());
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		double scaledX = ( (double) (e.getX() - offsetX)) / (map.getWidth()*scale);
		double scaledY = ( (double) (e.getY() - offsetY)) / (map.getHeight()*scale);

		scale *= (1d - 0.1*e.getWheelRotation());
		if(scale < 6d) scale = 6d;

		offsetX = (((double) e.getX()) - (scaledX*map.getWidth()*scale));
		offsetY = (((double) e.getY()) - (scaledY*map.getHeight()*scale));

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
				Point oldStart = map.getStart();
				int x = (e.getX() - (int)scale/2 - ((int) offsetX) + ((int) scale/2)) / (int) scale;
				int y =  (e.getY() - (int) scale/2 - ((int) offsetY) + ((int) scale/2)) / (int) scale;
				Point newStart = new Point(x, y);

				if(!newStart.equals(oldStart) && map.pointInBounds(newStart)) {
					map.setStart(newStart);
					repaint();
					pfw.setMap(map, false);
				}

				//if goal box
			} else if(holdingGoal) {
				Point oldGoal = map.getGoal();
				int x = (e.getX() - (int)scale/2 - ((int) offsetX) + ((int) scale/2)) / (int) scale;
				int y =  (e.getY() - (int) scale/2 - ((int) offsetY) + ((int) scale/2)) / (int) scale;
				Point newGoal = new Point(x, y);

				if(!newGoal.equals(oldGoal) && map.pointInBounds(newGoal)) {
					map.setGoal(newGoal);
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

				//start box
				Point start = map.getStart();
				Rectangle startRect = new Rectangle(offsetX + start.x*scale +2 + reduce, offsetY + start.y*scale +2 + reduce, scale -1 -reduce*2, scale -1 -reduce*2);

				//goal box
				Point goal = map.getGoal();
				Rectangle goalRect = new Rectangle(offsetX + goal.x*scale +2 + reduce, offsetY + goal.y*scale +2 + reduce, scale -1 -reduce*2, scale -1 -reduce*2);

				//if start box
				if(startRect.contains(mousePoint)) {
					holdingStart = true;
					holdingGoal = false;

					//if goal box
				} else if(goalRect.contains(mousePoint)) {
					holdingStart = false;
					holdingGoal = true;

					//if near a corner
				} else if(Math.pow(Math.pow(offsetX + (hoveringPoint.x * scale -3) - e.getX(), 2) + Math.pow(offsetY + (hoveringPoint.y * scale -3) - e.getY(), 2), 0.5) < scale/2) {

					if(hoveringPoint.x == selectedPoint.x && hoveringPoint.y == selectedPoint.y) {
						drawSelected = !drawSelected;
					} else {
						if(drawHovering && drawSelected) {
							if(hoveringPoint.y == selectedPoint.y) {
								if(hoveringPoint.x == selectedPoint.x +1 && !map.isBorderWall(selectedPoint.x, selectedPoint.y, Map.HORIZONTAL)) {
									//horizontal right
									map.toggleWall(selectedPoint.x, selectedPoint.y, Map.HORIZONTAL);
								} else if(hoveringPoint.x == selectedPoint.x -1 && !map.isBorderWall(hoveringPoint.x, selectedPoint.y, Map.HORIZONTAL)) {
									//horizontal left
									map.toggleWall(hoveringPoint.x, selectedPoint.y, Map.HORIZONTAL);
								}
							} else if(hoveringPoint.x == selectedPoint.x) {
								if(hoveringPoint.y == selectedPoint.y +1 && !map.isBorderWall(selectedPoint.x, selectedPoint.y, Map.VERTICAL)) {
									//vertical down
									map.toggleWall(selectedPoint.x, selectedPoint.y, Map.VERTICAL);
								} else if(hoveringPoint.y == selectedPoint.y -1 && !map.isBorderWall(selectedPoint.x, hoveringPoint.y, Map.VERTICAL)) {
									//vertical up
									map.toggleWall(selectedPoint.x, hoveringPoint.y, Map.VERTICAL);
								}
							}
						}
						if(map.pointInBounds(hoveringPoint)) {
							selectedPoint.setLocation(hoveringPoint);
							drawSelected = true;
						}
					}
					repaint();
				}
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		hoveringPoint.x = ( (e.getX() - ((int) offsetX) + ((int) scale/2)) / (int) scale);
		hoveringPoint.y = ( (e.getY() - ((int) offsetY) + ((int) scale/2)) / (int) scale);
		drawHovering = Math.pow(Math.pow(offsetX + (hoveringPoint.x * scale -3) - e.getX(), 2) + Math.pow(offsetY + (hoveringPoint.y * scale -3) - e.getY(), 2), 0.5) < scale/2;

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
