

public class AI {
	//private ConnectFour[] connectFour;
	private static final int MAX_DEPTH = 2;
	private State[] states;
	private int currentStates;
	private int id = 0;
	private int p1 = 1;
	private int myMove = -1;
	private int arrayIndices[];
	private int maxHeuristic = -90;
	private int minHeuristic = 90;
	public AI() {

		//		arrayIndices = new int[MAX_DEPTH+1];
		//		for (int i = 1; i < MAX_DEPTH+1; i++) {
		//			arrayIndices[i] = (int) Math.pow(7, i);
		//		}

		states = new State[50000000];

		// Allocate the necessary space for each depth
		//states[1] = new State[7];
		//states[2] = new State[49];
		//states[3] = new State[399];
		//states[4] = new State[2800];

		//		for (int i = 1; i < states.length; i++) {
		//			System.out.println("length: " + states[i].length);
		//		}

		currentStates = 0;
		ConnectFour c4 = new ConnectFour();


		long startTime = System.nanoTime();

		// Get the heuristics for each state of the game up to a set MAX_DEPTH
		evaluateState(c4, 1, 2);
		System.out.println("totalStates: " + currentStates);

		long estimatedTime = System.nanoTime() - startTime;
		long timeElapsedInSeconds = (long) (estimatedTime / (Math.pow(10, 9)));
		System.out.println("Time taken: " + timeElapsedInSeconds + " seconds");

		// Apply the minimax algorithm based on the heuristics of the states
		//for (int currentDepth = MAX_DEPTH; currentDepth > 0; currentDepth--) {
		// Initialize heuristic MAX/MIN

		//System.out.println("currentDepth: " + currentDepth);

		//			int start = 0;
		//			if (currentDepth == 2) {
		//				start = 7;
		//			}
		//			long startTime = System.nanoTime();


		startTime = System.nanoTime();


		for (int i = 0; i < currentStates; i++) {
			//System.out.println("currentState Id: " + states[i].getId());
			//System.out.println("currentState parentId: " + states[i].getParentId());
			for (int j = 0; j < currentStates; j++) {
				if (states[j].getId() == states[i].getParentId()) {
					states[j].addChild(states[i].getId());
					//System.out.println("child of [" + states[j].getId() + "]: " + states[i].getId());
				}

			}



		}
		estimatedTime = System.nanoTime() - startTime;
		timeElapsedInSeconds = (long) (estimatedTime / (Math.pow(10, 9)));
		System.out.println("Time taken for 2 loops: " + timeElapsedInSeconds + " seconds");

//		for ( int i = 0; i < currentStates; i++) {
//			System.out.println("Heuristics " + i + " : " + states[i].getHeuristic());
//		}
		for(int currentDepth = MAX_DEPTH; currentDepth > 0; currentDepth--) {
			for(int i = 0; i < currentStates; i++) {
				if (states[i].getDepth() == currentDepth) {

					if (states[i].getDepth() == currentDepth) {
						//System.out.println("currentDepth: " + currentDepth);
						//states[i].printChildren();
						
						int children[] = new int[states[i].getNumChild()];
						children = states[i].getChildId();
						

						

						for (int k = 0; k < children.length; k++) {
							// MAX's turn
							System.out.println("Heuristic k: " + states[k].getHeuristic());
							//System.out.println("Heuristic child k: " + states[children[k]].getHeuristic());
							if (currentDepth % 2 == 0) {
								maxHeuristic = -90;
								if (states[children[k]].getHeuristic() > maxHeuristic) {
									maxHeuristic = states[children[k]].getHeuristic();
									System.out.println("Max Heuristic: " + maxHeuristic);
									states[i].setHeuristic(maxHeuristic);
									//System.out.println("Heuristic B: " + states[k].getHeuristic());
								}
								
							}
							// MIN's turn
							else {
								minHeuristic = 90;
								if (states[children[k]-1].getHeuristic() < minHeuristic) {
									minHeuristic = states[children[k]-1].getHeuristic();
									states[i].setHeuristic(minHeuristic);

								}
							}
						}
					}
				}
			}
		}

		
			System.out.println("Max Heuristic: " + maxHeuristic);
			for (int h = 0; h < states[0].getChildId().length; h++) {
				//System.out.println("children: " + children[h]);
				//System.out.println("children state id: " + states[children[h]-1].getId());
				if (states[states[0].getChildId()[h]].getHeuristic() == maxHeuristic) {
					myMove = states[states[0].getChildId()[h]].getColumn();
					System.out.println("myMove: " + myMove);
				}
			
		}
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

					int heuristic = new Heuristic().heuristic(newBoard[i].getBoard());
					states[currentStates] = new State(i+1, heuristic, currentDepth-1, ++id, parentId);
					//System.out.println("Heuristic for state[" + currentStates + "]: " + heuristic);
					//					if(currentDepth <= MAX_DEPTH) {
					//						//System.out.println("parentState: " + parentState);
					//						states[parentId].addChild(id);
					//					}
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
		AI ai = new AI();
	}

}
