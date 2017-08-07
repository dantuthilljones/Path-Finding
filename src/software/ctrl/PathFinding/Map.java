package software.ctrl.PathFinding;

import java.awt.Point;
import java.io.Serializable;

public class Map implements Serializable {

	private static final long serialVersionUID = 4481246001562697099L;
	
	public static final byte HORIZONTAL = 0;
	public static final byte VERTICAL = 1;
	public static final String[] DIRECTION = {"horizontal", "vertical"};
	
	private boolean[][][] walls;
	private Point start, goal;
	private final int width, height;

	
	//for looping around neighbors
	//outer arrays = up, down, left, right
	//inner arrays = wallX, wallY, wallOrientation, neighborX, neighborY
	public static final byte[][] iter = {
		{ 0, 0, HORIZONTAL, 0,-1},//up
		{ 1, 0, VERTICAL,   1, 0},//right
		{ 0, 1, HORIZONTAL, 0, 1},//down
		{ 0, 0, VERTICAL,  -1, 0}//left
	};
	
	//for looping around diagonal neighbors
	//outer arrays = up, down, left, right
	//inner arrays = intersectX, interectY, neighborX, neighborY
	public static final byte[][] iterDiag = {
		{  0,  0, -1, -1},//left up
		{  1,  0,  1, -1},//right up
		{  1,  1,  1,  1},//right down
		{  0,  1, -1,  1} //left down
	};
	
	public Map(int width, int height) {
		walls = new boolean[width+1][height+1][2];
		this.width = width;
		this.height = height;

		//set outside edges (top and bottom)
		for(int i = 0; i < width; i ++) {
			walls[i][0][HORIZONTAL] = true;
			walls[i][height][HORIZONTAL] = true;
		}

		//set outside edges (left and right)
		for(int j = 0; j < height; j ++) {
			walls[0][j][VERTICAL] = true;
			walls[width][j][VERTICAL] = true;
		}

		start = new Point(width/3, height/2);
		goal = new Point(2*width/3, height/2);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public Point getStart() {
		return start;
	}

	public Point getGoal() {
		return goal;
	}

	public void setGoal(Point p) {
		setGoal(p.x, p.y);
	}
	
	public void setGoal(int x, int y) {
		if(x > width || x < 0) {
			String errorString = "X co-ordinate (" + x + ") is out of bounds (0->" + width + ")";
			throw new IllegalArgumentException(errorString);
		} else if(y > height || y < 0) {
			String errorString = "Y co-ordinate (" + y + ") is out of bounds (0->" + height + ")";
			throw new IllegalArgumentException(errorString);
		}
		goal.x = x;
		goal.y = y;
	}
	
	public void setStart(Point p) {
		setStart(p.x, p.y);
	}
	
	public void setStart(int x, int y) {
		if(x > width || x < 0) {
			String errorString = "X co-ordinate (" + x + ") is out of bounds (0->" + width + ")";
			throw new IllegalArgumentException(errorString);
		} else if(y > height || y < 0) {
			String errorString = "Y co-ordinate (" + y + ") is out of bounds (0->" + height + ")";
			throw new IllegalArgumentException(errorString);
		}
		start.x = x;
		start.y = y;
	}
	
	public boolean isWall(int x, int y, int direction) {
		if(x > width || x < 0) {
			String errorString = "X co-ordinate (" + x + ") is out of bounds (0->" + width + ")";
			throw new IllegalArgumentException(errorString);
		} else 	if(y > width || y < 0) {
			String errorString = "Y co-ordinate (" + y + ") is out of bounds (0->" + height + ")";
			throw new IllegalArgumentException(errorString);
		} else if(direction != HORIZONTAL && direction != VERTICAL) {
			String errorString = "Direction (" + direction + ") is not horizontal + (" + HORIZONTAL + ") or vertical (" + VERTICAL + ")";
			throw new IllegalArgumentException(errorString);
		}
		return walls[x][y][direction];
	}
	
	public boolean isWall(Point p, int direction) {
		return isWall(p.x, p.y, direction);
	}
	
	public boolean toggleWall(int x, int y, int direction) {
		if(x > width || x < 0) {
			String errorString = "X co-ordinate (" + x + ") is out of bounds (0->" + width + ")";
			throw new IllegalArgumentException(errorString);
		} else 	if(y > width || y < 0) {
			String errorString = "Y co-ordinate (" + y + ") is out of bounds (0->" + height + ")";
			throw new IllegalArgumentException(errorString);
		} else if(direction != HORIZONTAL && direction != VERTICAL) {
			String errorString = "Direction (" + direction + ") is not horizontal + (" + HORIZONTAL + ") or vertical (" + VERTICAL + ")";
			throw new IllegalArgumentException(errorString);
		} else if(isBorderWall(x, y, direction)) {
			String errorString = "Wall at position (" + x + "," + y + ") in direction " + DIRECTION[direction] + " is on the border of the map";
			throw new IllegalArgumentException(errorString);
		}
		return walls[x][y][direction] = !walls[x][y][direction];
	}
	
	public boolean toggleWall(Point p, int direction) {
		return toggleWall(p.x, p.y, direction);
	}
	
	public boolean setWall(int x, int y, int direction, boolean on) {
		if(x > width || x < 0) {
			String errorString = "X co-ordinate (" + x + ") is out of bounds (0->" + width + ")";
			throw new IllegalArgumentException(errorString);
		} else 	if(y > width || y < 0) {
			String errorString = "Y co-ordinate (" + y + ") is out of bounds (0->" + height + ")";
			throw new IllegalArgumentException(errorString);
		} else if(direction != HORIZONTAL && direction != VERTICAL) {
			String errorString = "Direction (" + direction + ") is not horizontal + (" + HORIZONTAL + ") or vertical (" + VERTICAL + ")";
			throw new IllegalArgumentException(errorString);
		} else if(isBorderWall(x, y, direction)) {
			String errorString = "Wall at position (" + x + "," + y + ") in direction " + DIRECTION[direction] + " is on the border of the map";
			throw new IllegalArgumentException(errorString);
		}
		return walls[x][y][direction] = on;
	}
	
	public void demolish() {
		walls = new boolean[width+1][height+1][2];

		//set outside edges (top and bottom)
		for(int i = 0; i < width; i ++) {
			walls[i][0][HORIZONTAL] = true;
			walls[i][height][HORIZONTAL] = true;
		}

		//set outside edges (left and right)
		for(int j = 0; j < height; j ++) {
			walls[0][j][VERTICAL] = true;
			walls[width][j][VERTICAL] = true;
		}
	}
	
	public boolean setWall(Point p, int direction, boolean on) {
		return setWall(p.x, p.y, direction, on);
	}
	
	public boolean isBorderWall(int x, int y, int direction) {
		return (x == 0 || (x == width && direction == Map.VERTICAL)) || (y == 0 || (y == height && direction == Map.HORIZONTAL));
	}
	
	public boolean isBorderWall(Point p, int direction) {
		return isBorderWall(p.x, p.y, direction);
	}
	
	public boolean wallInBounds(int x, int y) {
		return x <= width && x >= 0 && y <= height && y >= 0;
	}
	
	public boolean wallInBounds(Point p) {
		return wallInBounds(p.x, p.y);
	}
	
	public boolean pointInBounds(int x, int y) {
		return x < width && x >= 0 && y < height && y >= 0;
	}
	
	public boolean pointInBounds(Point p) {
		return pointInBounds(p.x, p.y);
	}
	
}
