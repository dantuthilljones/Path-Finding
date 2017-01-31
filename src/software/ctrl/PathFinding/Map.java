package software.ctrl.PathFinding;

import java.io.Serializable;

public class Map implements Serializable {

	private static final long serialVersionUID = 4481246001562697099L;
	
	public static final byte HORIZONTAL = 0;
	public static final byte VERTICAL = 1;
	
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
	
	
	public boolean[][][] walls;

	public int startX, startY, goalX, goalY, x, y;

	public Map(int x, int y) {
		walls = new boolean[x+1][y+1][2];

		//set outside edges (top and bottom)
		for(int i = 0; i < x; i ++) {
			walls[i][0][HORIZONTAL] = true;
			walls[i][y][HORIZONTAL] = true;
		}

		//set outside edges (left and right)
		for(int j = 0; j < y; j ++) {
			walls[0][j][VERTICAL] = true;
			walls[x][j][VERTICAL] = true;
		}

		startX = x/3;
		startY = y/2;

		goalX = 2*x/3;
		goalY = y/2;

		this.x = x;
		this.y = y;
	}
}
