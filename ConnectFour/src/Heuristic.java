
public class Heuristic {
	
	private int winningOptionsP1 = -1;
	private int winningOptionsP2 = -1;
	private int winningRowsP1 = -1;
	private int winningRowsP2 = -1;
	private int winningColumnsP1 = -1;
	private int winningColumnsP2 = -1;
	private int winningDiagonalsP1 = -1;
	private int winningDiagonalsP2 = -1;
	private int heuristic;
	private int board[][];
	
	public int heuristic(int board[][]) {

		this.board = board;
		
		// Calculate winning rows
		winningRowsP1 = calculateWinningRows(1);
		winningRowsP2 = calculateWinningRows(2);
		
		// Calculate winning columns
		winningColumnsP1 = calculateWinningColumns(1);
		winningColumnsP2 = calculateWinningColumns(2);
		
		// Calculate winning diagonals
		winningDiagonalsP1 = calculateWinningDiagonals(1);
		winningDiagonalsP2 = calculateWinningDiagonals(2);
		
		winningOptionsP1 = winningRowsP1 + winningColumnsP1 + winningDiagonalsP1;
		winningOptionsP2 = winningRowsP2 + winningColumnsP2 + winningDiagonalsP2;
		
		return winningOptionsP1 - winningOptionsP2;
	}
	
	public int calculateWinningRows(int player) {
		
		return 0;
	}
	
	public int calculateWinningColumns(int player) {
		int winningColumns = 0;
		int countEmptyOrPlayer = 0;
		
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j++) {
				if(board[i][j] == 0 || board[i][j] == player) {
					countEmptyOrPlayer++;
				}
				else {
					countEmptyOrPlayer = 0;
				}
					
				if(countEmptyOrPlayer == 4) {
					winningColumns++;
					countEmptyOrPlayer = 0;
					break;
				}
			}
		}
		
		
		
		return 0;
	}
	
	public int calculateWinningDiagonals(int player) {
		
		return 0;
	}

}
