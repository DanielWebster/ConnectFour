

public class AI {
	//private ConnectFour[] connectFour;
	private static final int MAX_DEPTH = 5;
	private State[] states;
	private int currentStates;
	private int id = 0;
	private int myMove = -1;
	private int maxHeuristic = -90;
	private int minHeuristic = 90;
	String name = "AI";
	ConnectFour c4 = new ConnectFour();

	public void checkWinningMove(int[][] b) {
		c4.setBoard(b);

		states = new State[50000000];

		currentStates = 0;
		id = 0;

		//System.out.println("CurrentStates: " + currentStates);
		long startTime = System.nanoTime();
		// Initialize the top depth (column, heuristic, depth, id, parentId)
		states[currentStates++] = new State(-1, 0, 0, 0, -1);
		// Get the heuristics for each state of the game up to a set MAX_DEPTH
		evaluateState(c4, 1, 2);

		//System.out.println("totalStates: " + currentStates);
		//long estimatedTime = System.nanoTime() - startTime;
		//System.out.println("Time taken: " + timeElapsedInSeconds + " seconds");
		//startTime = System.nanoTime();

		// Adding the children to each of the states
		for (int i = 0; i < currentStates; i++) {
			for (int j = 0; j < currentStates; j++) {
				if (states[i].getId() == states[j].getParentId()) {
					states[i].addChild(states[j].getId());
				}
			}
		}
//		estimatedTime = System.nanoTime() - startTime;
//		long timeElapsedInSeconds = (long) (estimatedTime / (Math.pow(10, 9)));
//		System.out.println("Time taken for 2 loops: " + timeElapsedInSeconds + " seconds");


		// Apply the minimax algorithm based on the heuristics of the states
		for(int currentDepth = MAX_DEPTH; currentDepth >= 0; currentDepth--) {
			
			// Loop through all of the states and compare for max and min heuristic
			for(int i = 0; i < currentStates; i++) {

				// Check if the state is on the current depth
				if (states[i].getDepth() == currentDepth) {

					int children[] = new int[states[i].getNumChild()];
					children = states[i].getChildId();

					// Reset the MAX and MIN heuristic
					maxHeuristic = -90;
					minHeuristic = 90;
					
					// Loop through all of the children of the given state
					for (int k = 0; k < children.length; k++) {

						// The node must have children, -1 indicates children does not exist
						if(children[k] != -1) {

							// MAX's turn
							if (currentDepth % 2 == 0) {

								// If this nodes heuristic is greater than MAX, then assign it the MAX
								if (states[children[k]].getHeuristic() > maxHeuristic) {
									maxHeuristic = states[children[k]].getHeuristic();
									states[i].setHeuristic(maxHeuristic);
									states[i].setNextBestMove(states[children[k]].getColumn());
								}
							}

							// MIN's turn
							else {
								// If this nodes heuristic is less than MIN, then assign it the MIN
								if (states[children[k]].getHeuristic() < minHeuristic) {
									minHeuristic = states[children[k]].getHeuristic();
									states[i].setHeuristic(minHeuristic);
								}
							}
						}
					}
				}
			}
		}

		myMove = states[0].getNextBestMove();
		//System.out.println("myMove: " + myMove);
	}

	public void evaluateState(ConnectFour connectFour, int currentDepth, int lastPlayer){
		ConnectFour[] newBoard = new ConnectFour[7];

		int currentPlayer;

		if (lastPlayer == 1) {
			currentPlayer = 2;
		}
		else {
			currentPlayer = 1;
		}

		//System.out.println("currentDepth: " + currentDepth);
		//System.out.println("maxDepth: " + MAX_DEPTH);

		if (currentDepth <= MAX_DEPTH) {
			currentDepth++;
			int parentId = id;
			//System.out.println("currentStates: " + currentStates);
			for (int i = 0; i < 7; i++) {
				//System.out.println("i: "+ i);

				newBoard[i] = new ConnectFour();

				// If a column fills up, do not add another state to it
				if (connectFour.getBoard()[0][i] == 0) {

					newBoard[i].makeTurn(connectFour.getBoard(), i+1, currentPlayer);

					int heuristic = new Heuristic_2().heuristic(newBoard[i].getBoard());
					states[currentStates] = new State(i+1, heuristic, currentDepth-1, ++id, parentId);
					currentStates++;
					evaluateState(newBoard[i], currentDepth, currentPlayer);
				}
			}

		}
		else {
			//System.out.println("maxDepth reached");
		}
	}


	public static void main(String[] args) {
		//AI ai = new AI();
	}

	public int run(int b[][], int c, int r) {
		checkWinningMove(b);
		return myMove;
	}

	public String getName()
	{
		return name;
	}

}
