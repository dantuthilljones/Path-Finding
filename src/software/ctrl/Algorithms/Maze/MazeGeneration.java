package software.ctrl.Algorithms.Maze;

import java.util.Random;
import java.util.TreeSet;

import software.ctrl.PathFinding.Map;

public class MazeGeneration {

	public static Map generateBag(int width, int height, int seed) {
		MazeTile[][] tiles = new MazeTile[width][height];
		boolean[][] addedToMap = new boolean[width][height];
		
		TreeSet<MazeTile> fringe = new TreeSet<MazeTile>();
		
		Random random = new Random(seed);
		//assign each square a value
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				tiles[i][j] = new MazeTile(i, j, random.nextInt());
			}
		}
		
		//add tile 0,0
		
		//while not all tiles added
		//add tile on fringe with lowest distance
		
		return null;
	}
}
