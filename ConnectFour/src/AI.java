
public class AI {
	//private ConnectFour[] connectFour;
	private static final int MAX_DEPTH = 4;
	private State[][] states;
	private int currentStates;
	private int p1 = 1;
	
	public AI() {
		states = new State[MAX_DEPTH+1][9999999];
		currentStates = 0;
		ConnectFour c4 = new ConnectFour();
		evaluateState(c4, 1, 2);
		

//		connectFour = new ConnectFour[7];

//		
//
//		for (int currentDepth = 0; currentDepth < MAX_DEPTH; currentDepth++) {
//		
//			int p1 = 1;
//			
//			// Get all possible states for this depth (dropping a token in each column)
//			for (int i = 0; i < 7; i++) {
//				connectFour[currentStates] = new ConnectFour();
//				connectFour[currentStates].takeTurn(i+1, p1);
//				
//				int heuristic = new Heuristic().heuristic(connectFour[currentStates].getBoard());
//				System.out.println("Heuristic for state[" + currentStates + "]: " + heuristic);
//				
//				states[currentDepth][currentStates] = new State(i+1, heuristic);
//	
//				currentStates++;
//			}
//		}
		
		

		
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
		
		System.out.println("currentDepth: " + currentDepth);
		System.out.println("maxDepth: " + MAX_DEPTH);
		
		
		if (currentDepth <= MAX_DEPTH) {
			currentDepth++;

			for (int i = 0; i < 7; i++) {
				//System.out.println("i: "+ i);
				
				newBoard[i] = new ConnectFour();
				
				// If a column fills up, do not add another state to it
				if (connectFour.getBoard()[0][i] == 0) {

					newBoard[i].makeTurn(connectFour.getBoard(), i+1, currentPlayer);
					//System.out.println("connectFour Board: ");	
					//connectFour.printBoard();
					//System.out.println("newBoard: ");
					newBoard[i].printBoard();

					int heuristic = new Heuristic().heuristic(newBoard[i].getBoard());
					states[currentDepth-1][currentStates++] = new State(currentDepth, heuristic);
					System.out.println("Heuristic for state[" + currentStates + "]: " + heuristic);

					evaluateState(newBoard[i], currentDepth, currentPlayer);
				}
			}

		}
		else {

			System.out.println("maxDepth reached");
		}
	}
	
	
	public static void main(String[] args) {
		AI ai = new AI();
	}

}
