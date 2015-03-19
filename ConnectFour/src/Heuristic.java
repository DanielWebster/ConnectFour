
public class Heuristic {
	
	private int winningOptionsP1 = -1;
	private int winningOptionsP2 = -1;
	private int winningRowsP1 = -1;
	private int winningRowsP2 = -1;
	private int winningColumnsP1 = -1;
	private int winningColumnsP2 = -1;
	private int winningDiagonalsP1 = -1;
	private int winningDiagonalsP2 = -1;
	private int diagonals[][][] = new int[12][6][2]; // 12 = number of diagonals; 6 = max coordinate positions; 2 = xy coordinates
	private int heuristic;
	private int board[][];
	
	public int heuristic(int board[][]) {

		this.board = board;
		setDiagonals();
		
		// Calculate winning rows
		winningRowsP1 = calculateWinningRows(1);
		winningRowsP2 = calculateWinningRows(2);
		
		// Calculate winning columns
		winningColumnsP1 = calculateWinningColumns(1);
		winningColumnsP2 = calculateWinningColumns(2);
		
		// Calculate winning diagonals
		winningDiagonalsP1 = calculateWinningDiagonals(1);
		winningDiagonalsP2 = calculateWinningDiagonals(2);
		
		System.out.println("winningD1: " + winningDiagonalsP1 + " winningD2: " + winningDiagonalsP2);
		
		winningOptionsP1 = winningRowsP1 + winningColumnsP1 + winningDiagonalsP1;
		winningOptionsP2 = winningRowsP2 + winningColumnsP2 + winningDiagonalsP2;
		
		return winningOptionsP1 - winningOptionsP2;
	}
	
	public int calculateWinningRows(int player) {
		int winningRows = 0;
		int countEmptyOrPlayer = 0;
		
		for(int j = 0; j < board[0].length; j++) {
			countEmptyOrPlayer = 0;
			for( int i = 0; i < board.length; i++) {
				if(board[i][j] == player || board[i][j] == 0) {
					countEmptyOrPlayer++;
				}
				else {
					countEmptyOrPlayer = 0;
				}
				if(countEmptyOrPlayer == 4) {
					winningRows++;
					break;
				}
			}
		}
		
		return winningRows;
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
		
		return winningColumns;
	}
	

	public void setDiagonals() {
		//int diagonals[12][6][2];
		
		diagonals[0][0][0] = 2; diagonals[0][0][1] = 0;
		diagonals[0][1][0] = 3; diagonals[0][1][1] = 1;
		diagonals[0][2][0] = 4; diagonals[0][2][1] = 2;
		diagonals[0][3][0] = 5; diagonals[0][3][1] = 3;
		diagonals[0][4][0] = -1; diagonals[0][4][1] = -1;
		diagonals[0][5][0] = -1; diagonals[0][5][1] = -1;

		diagonals[1][0][0] = 1; diagonals[1][0][1] = 0;
		diagonals[1][1][0] = 2; diagonals[1][1][1] = 1;
		diagonals[1][2][0] = 3; diagonals[1][2][1] = 2;
		diagonals[1][3][0] = 4; diagonals[1][3][1] = 3;
		diagonals[1][4][0] = 5; diagonals[1][4][1] = 4;
		diagonals[1][5][0] = -1; diagonals[1][5][1] = -1;

		diagonals[2][0][0] = 0; diagonals[2][0][1] = 0;
		diagonals[2][1][0] = 1; diagonals[2][1][1] = 1;
		diagonals[2][2][0] = 2; diagonals[2][2][1] = 2;
		diagonals[2][3][0] = 3; diagonals[2][3][1] = 3;
		diagonals[2][4][0] = 4; diagonals[2][4][1] = 4;
		diagonals[2][5][0] = 5; diagonals[2][5][1] = 5;

		diagonals[3][0][0] = 0; diagonals[3][0][1] = 1;
		diagonals[3][1][0] = 1; diagonals[3][1][1] = 2;
		diagonals[3][2][0] = 2; diagonals[3][2][1] = 3;
		diagonals[3][3][0] = 3; diagonals[3][3][1] = 4;
		diagonals[3][4][0] = 4; diagonals[3][4][1] = 5;
		diagonals[3][5][0] = 5; diagonals[3][5][1] = 6;

		diagonals[4][0][0] = 0; diagonals[4][0][1] = 2;
		diagonals[4][1][0] = 1; diagonals[4][1][1] = 3;
		diagonals[4][2][0] = 2; diagonals[4][2][1] = 4;
		diagonals[4][3][0] = 3; diagonals[4][3][1] = 5;
		diagonals[4][4][0] = 4; diagonals[4][4][1] = 6;
		diagonals[4][5][0] = -1; diagonals[4][5][1] = -1;

		diagonals[5][0][0] = 0; diagonals[5][0][1] = 3;
		diagonals[5][1][0] = 1; diagonals[5][1][1] = 4;
		diagonals[5][2][0] = 2; diagonals[5][2][1] = 5;
		diagonals[5][3][0] = 3; diagonals[5][3][1] = 6;
		diagonals[5][4][0] = -1; diagonals[5][4][1] = -1;
		diagonals[5][5][0] = -1; diagonals[5][5][1] = -1;

		diagonals[6][0][0] = 3; diagonals[6][0][1] = 0;
		diagonals[6][1][0] = 2; diagonals[6][1][1] = 1;
		diagonals[6][2][0] = 1; diagonals[6][2][1] = 2;
		diagonals[6][3][0] = 0; diagonals[6][3][1] = 3;
		diagonals[6][4][0] = -1; diagonals[6][4][1] = -1;
		diagonals[6][5][0] = -1; diagonals[6][5][1] = -1;

		diagonals[7][0][0] = 4; diagonals[7][0][1] = 0;
		diagonals[7][1][0] = 3; diagonals[7][1][1] = 1;
		diagonals[7][2][0] = 2; diagonals[7][2][1] = 2;
		diagonals[7][3][0] = 1; diagonals[7][3][1] = 3;
		diagonals[7][4][0] = 0; diagonals[7][4][1] = 4;
		diagonals[7][5][0] = -1; diagonals[7][5][1] = -1;

		diagonals[8][0][0] = 5; diagonals[8][0][1] = 0;
		diagonals[8][1][0] = 4; diagonals[8][1][1] = 1;
		diagonals[8][2][0] = 3; diagonals[8][2][1] = 2;
		diagonals[8][3][0] = 2; diagonals[8][3][1] = 3;
		diagonals[8][4][0] = 1; diagonals[8][4][1] = 4;
		diagonals[8][5][0] = 0; diagonals[8][5][1] = 5;

		diagonals[9][0][0] = 5; diagonals[9][0][1] = 1;
		diagonals[9][1][0] = 4; diagonals[9][1][1] = 2;
		diagonals[9][2][0] = 3; diagonals[9][2][1] = 3;
		diagonals[9][3][0] = 2; diagonals[9][3][1] = 4;
		diagonals[9][4][0] = 1; diagonals[9][4][1] = 5;
		diagonals[9][5][0] = 0; diagonals[9][5][1] = 6;

		diagonals[10][0][0] = 5; diagonals[10][0][1] = 2;
		diagonals[10][1][0] = 4; diagonals[10][1][1] = 3;
		diagonals[10][2][0] = 3; diagonals[10][2][1] = 4;
		diagonals[10][3][0] = 2; diagonals[10][3][1] = 5;
		diagonals[10][4][0] = 1; diagonals[10][4][1] = 6;
		diagonals[10][5][0] = -1; diagonals[10][5][1] = -1;

		diagonals[11][0][0] = 5; diagonals[11][0][1] = 3;
		diagonals[11][1][0] = 4; diagonals[11][1][1] = 4;
		diagonals[11][2][0] = 3; diagonals[11][2][1] = 5;
		diagonals[11][3][0] = 2; diagonals[11][3][1] = 6;
		diagonals[11][4][0] = -1; diagonals[11][4][1] = -1;
		diagonals[11][5][0] = -1; diagonals[11][5][1] = -1;
		
	}
	
	public int calculateWinningDiagonals(int player) {
		int winningDiagonals = 0;
		int countEmptyOrPlayer = 0;
		int row, column;
		
		for(int i = 0; i < diagonals.length; i++) {
			for(int j = 0; j < diagonals[0].length; j++) {
				row = diagonals[i][j][0];
				column = diagonals[i][j][1];
				if(row != -1 && column != -1) {
					if(board[row][column] == 0 || board[row][column] == player) {
						countEmptyOrPlayer++;
						//System.out.println("row: " + row + " column: " + column);
						//System.out.println("player: " + player + " value: " + board[row][column]);
					}
					else {
						countEmptyOrPlayer = 0;
					}
						
					if(countEmptyOrPlayer == 4) {
						winningDiagonals++;
						countEmptyOrPlayer = 0;
						break;
					}
				}
			}
		}
		
		return winningDiagonals;
	}
	
	
}
