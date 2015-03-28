

public class AI {
	//private ConnectFour[] connectFour;
	private static final int MAX_DEPTH = 3;
	private State[][] states;
	private int currentStates;
	private int p1 = 1;
	
	public AI() {
		
		states = new State[MAX_DEPTH+1][7];
		
		// Allocate the necessary space for each depth
		states[1] = new State[7];
		states[2] = new State[56];
		states[3] = new State[399];
		states[4] = new State[2800];
		
		for (int i = 1; i < states.length; i++) {
			System.out.println("length: " + states[i].length);
		}
		
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
		for (int currentDepth = MAX_DEPTH; currentDepth < MAX_DEPTH; currentDepth++) {
			// Initialize heuristic MAX/MIN
			int maxHeuristic = -90;
			int minHeuristic = 90;
			
			for (int i = 0; i < currentStates; i++) {
//				if (states[currentDepth][i].getDepth() == currentDepth) {
//
//					// MAX's turn
//					if (currentDepth % 2 == 0) {
//						if (states[currentDepth][i].getHeuristic() > maxHeuristic) {
//							
//						}
//					}
//					// MIN's turn
//					else {
//						if (states[currentDepth][i].getHeuristic() < minHeuristic) {
//							
//						}
//
//					}
//				}
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

			for (int i = 0; i < 7; i++) {
				//System.out.println("i: "+ i);
				
				newBoard[i] = new ConnectFour();
				
				// If a column fills up, do not add another state to it
				if (connectFour.getBoard()[0][i] == 0) {

					newBoard[i].makeTurn(connectFour.getBoard(), i+1, currentPlayer);

					int heuristic = new Heuristic().heuristic(newBoard[i].getBoard());
					states[currentDepth-1][currentStates++] = new State(i+1, heuristic, currentDepth-1);
					//System.out.println("Heuristic for state[" + currentStates + "]: " + heuristic);

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
