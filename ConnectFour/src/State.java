
public class State {
	
	private int column;
	private int heuristic;
	private int depth;
	private int id;
	private int parentId;
	private int childId[];
	private int numChild;
	
	public State(int column, int heuristic, int depth, int id, int parentId) {
		this.column = column;
		this.heuristic = heuristic;
		this.depth = depth;
		this.id = id;
		this.numChild = 0;
		this.childId = new int[7];
		this.parentId = parentId;
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

	public int getId() {
		return id;
	}
	
	public int getParentId() {
		return parentId;
	}
	
	public void addChild(int id) {
		childId[numChild++] = id;
	}
	
	public int[] getChildId(){
		return childId;
	}
	
	public int getNumChild() {
		return numChild;
	}
	
	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
	}
	
	public void printChildren() {
		//System.out.println("NumChild: " + numChild);
		if(numChild == 0) {
			System.out.println("Id " + id + " has no children");
		}
		for (int i = 0; i < numChild; i++) {
			System.out.println("Id " + id + "'s child id: " + childId[i]);
		}
	}
}
