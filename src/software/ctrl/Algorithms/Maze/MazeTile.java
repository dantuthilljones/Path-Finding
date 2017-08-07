package software.ctrl.Algorithms.Maze;

public class MazeTile implements Comparable<MazeTile> {
	private int x, y, value;
	private boolean addedToMap;
	
	public MazeTile(int x, int y, int value) {
		this.x = x;
		this.y = y;
		this.value = value;
		addedToMap = false;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean addedToMap() {
		return addedToMap;
	}
	
	public void addToMap() {
		addedToMap = true;
	}
	
	public int getValue() {
		return value;
	}

	@Override
	public int compareTo(MazeTile t) {
		return value - t.getValue();
	}
}
