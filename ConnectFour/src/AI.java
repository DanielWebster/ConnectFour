

public class AI {
	//private ConnectFour[] connectFour;
	private static final int MAX_DEPTH = 4;
	private State[] states;
	private int currentStates;
	private int id = 0;
	private int p1 = 1;
	private int myMove = -1;
	private int arrayIndices[];
	private int maxHeuristic = -90;
	private int minHeuristic = 90;
	String name = "AI";
	ConnectFour c4 = new ConnectFour();
	
	public void checkWinningMove(int[][] b) {
		c4.setBoard(b);

		states = new State[50000000];

		currentStates = 0;
		id = 0;
		//c4 = new ConnectFour();
		//setBoard(c4);

		//System.out.println("CurrentStates: " + currentStates);
		long startTime = System.nanoTime();
		// Initialize the top depth (column, heuristic, depth, id, parentId)
		states[currentStates++] = new State(-1, 0, 0, 0, -1);
		// Get the heuristics for each state of the game up to a set MAX_DEPTH
		evaluateState(c4, 1, 2);

		//System.out.println("totalStates: " + currentStates);

		long estimatedTime = System.nanoTime() - startTime;
		long timeElapsedInSeconds = (long) (estimatedTime / (Math.pow(10, 9)));
		//System.out.println("Time taken: " + timeElapsedInSeconds + " seconds");

		// Apply the minimax algorithm based on the heuristics of the states
		//for (int currentDepth = MAX_DEPTH; currentDepth > 0; currentDepth--) {
		// Initialize heuristic MAX/MIN

		startTime = System.nanoTime();

		for (int i = 0; i < currentStates; i++) {
			//System.out.println("currentState Id: " + states[i].getId());
			//System.out.println("currentState parentId: " + states[i].getParentId());
			for (int j = 0; j < currentStates; j++) {
				if (states[i].getId() == states[j].getParentId()) {
					states[i].addChild(states[j].getId());
					//System.out.println("child of [" + states[j].getId() + "]: " + states[i].getId());
				}
			}
		}
		
		estimatedTime = System.nanoTime() - startTime;
		timeElapsedInSeconds = (long) (estimatedTime / (Math.pow(10, 9)));
		//System.out.println("Time taken for 2 loops: " + timeElapsedInSeconds + " seconds");

//		for ( int i = 0; i < currentStates; i++) {
//			System.out.println("Heuristics " + i + " : " + states[i].getHeuristic());
//		}
		for(int currentDepth = MAX_DEPTH; currentDepth >= 0; currentDepth--) {
			//System.out.println("CurrentStates: " + currentStates);
			for(int i = 0; i < currentStates; i++) {
				if (states[i].getDepth() == currentDepth) {
//					System.out.println("State Depth: " + states[i].getDepth() + " currentDepth: " + currentDepth);
//					System.out.println("Id: " + states[i].getId() + " i: " + i);
					if (states[i].getDepth() == currentDepth) {
						//System.out.println("currentDepth: " + currentDepth);
						//states[i].printChildren();
						
						int children[] = new int[states[i].getNumChild()];
						children = states[i].getChildId();
						
//						for(int j = 0; j < children.length; j++) {
//							if(children[j] == -1) {
//								System.out.println("No Children");
//								break;
//							}
//							else {
//								System.out.println("children: " + children[j]);
//							}
//						}

						maxHeuristic = -90;
						minHeuristic = 90;
						for (int k = 0; k < children.length; k++) {
							// MAX's turn
							//System.out.println("Heuristic k: " + states[k].getHeuristic());
							//System.out.println("Heuristic child k: " + states[children[k]].getHeuristic());
							if (currentDepth % 2 == 0) {
//								System.out.println("Parent: " + states[i].getId()); //debugging print
//								System.out.println("Child Id: " + children[k]); //debugging print
								if(children[k] != -1) {
//									System.out.println("Checking Parent: " + states[children[k]].getParentId()); //debugging print
//									System.out.println("Heuristic: " + states[children[k]].getHeuristic());
									if (states[children[k]].getHeuristic() > maxHeuristic) {
										maxHeuristic = states[children[k]].getHeuristic();
										//System.out.println("Max Heuristic: " + maxHeuristic);
										states[i].setHeuristic(maxHeuristic);
										states[i].setNextBestMove(states[children[k]].getColumn());
										//System.out.println("Heuristic B: " + states[k].getHeuristic());
									}
								}
								
							}
							// MIN's turn
							else {
								if(children[k] != -1) {
									//System.out.println("Children: " + children[k] + " k: " + k);
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
		}

			//System.out.println("Best Heuristic: " + states[0].getHeuristic());
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
