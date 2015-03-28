
public class State {
	
	private int column;
	private int heuristic;
	private int depth;
	
	public State(int column, int heuristic, int depth) {
		this.column = column;
		this.heuristic = heuristic;
		this.depth = depth;
	}
	
	public int getColumn() {
		return column;
	}
	
	public int getHeuristic(){
		return heuristic;
	}
	
	public int getDepth() {
		return depth;
	}

}
