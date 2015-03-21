
public class State {
	
	private int column;
	private int heuristic;
	
	public State(int column, int heuristic) {
		this.column = column;
		this.heuristic = heuristic;
	}
	
	public int getColumn() {
		return column;
	}
	
	public int getHeuristic(){
		return heuristic;
	}

}
