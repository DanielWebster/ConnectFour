import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class ConnectFour 
{
	private int column;
	private int xCoordinate;
	private int yCoordinate;
	private int row;
	private int board[][];
	private int diagonals[][][] = new int[12][6][2]; // 12 = number of diagonals; 6 = max coordinate positions; 2 = xy coordinates
	
	private PrintWriter writer;
	
	ConnectFour()
	{
		column = 7;
		row = 6;
		board = new int[row][column];
		setBoard();
		setDiagonals();
		writer = null;
		
		try 
		{
			writer = new PrintWriter("connectFour.txt");
		} 
		catch (FileNotFoundException e) 
		{	
			e.printStackTrace();
		}
	}
	
	public int getXCoordinate()
	{
		return xCoordinate;	
	}
	
	public int getYCoordinate()
	{
		return yCoordinate;
	}
	
	public void takeTurn(int retCol, int player)
	{
		// If the column entered is not a valid column [1-7], 
		// then compute a random column in the range
		if(retCol < 1 || retCol > 7)
		{
			retCol = (int)(Math.random()*6+1);
		}
		
		// Decrement column by 1 for array purposes
		retCol--;
		
		// If the column is filled, 
		// then randomly assign a column until a valid column is selected
		while (board[0][retCol] != 0)
		{
			retCol = (int)(Math.random()*6+1);
		}
		
		// Start at the bottom row [row 5 in the array]
		for(int i = row-1; i >= 0; i--)
		{
			if(board[i][retCol] == 0)
			{
				board[i][retCol] = player;
				xCoordinate = i;
				yCoordinate = retCol;
//				System.out.println("x-coord: " + xCoordinate);
//				System.out.println("y-coord: " + yCoordinate);

				break;
			}
		}
	}
	
	public void makeTurn(int[][] currentBoard, int retCol, int player)
	{

		int[][] newBoard = new int[row][column];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				newBoard[i][j] = currentBoard[i][j];
			}
		}
		//board2 = currentBoard;
		// If the column entered is not a valid column [1-7], 
		// then compute a random column in the range
		if(retCol < 1 || retCol > 7)
		{
			retCol = (int)(Math.random()*6+1);
		}
		
		// Decrement column by 1 for array purposes
		retCol--;
		
		// If the column is filled, 
		// then randomly assign a column until a valid column is selected
		while (newBoard[0][retCol] != 0)
		{
			retCol = (int)(Math.random()*6+1);
		}
		
		// Start at the bottom row [row 5 in the array]
		for(int i = row-1; i >= 0; i--)
		{
			if(newBoard[i][retCol] == 0)
			{
				newBoard[i][retCol] = player;
				//xCoordinate = i;
				//yCoordinate = retCol;
//				System.out.println("x-coord: " + xCoordinate);
//				System.out.println("y-coord: " + yCoordinate);

				break;
			}
		}
		
		setBoard(newBoard);
		//newBoard.printBoard();
		
		//return newBoard;
	}
	
	
	
	public int getColumn() 
	{
		return column;
	}

	public int getRow() 
	{
		return row;
	}

	public int[][] getBoard()
	{
		return board;
	}
	
	public void printBoard()
	{
		for(int i = 0; i < row; i++)
		{
			for(int j = 0; j < column; j++)
			{
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void setBoard()
	{
		for(int i = 0; i < row; i++)
		{
			for(int j = 0; j < column; j++)
			{
				board[i][j] = 0;
			}
		} 
	}
	
	public void setBoard(int[][] board)
	{
		this.board = board;
	}
	
	public boolean checkTie()
	{
		boolean isTie = true;
		for(int i = 0; i < column; i++)
		{
			if (board[0][i] == 0)
			{
				isTie = false;
			}
		}
		
		return isTie;
	}
	
	public boolean checkWin()
	{
		int countP1 = 0;
		int countP2 = 0;
		boolean found = false;
		
		// for vertical wins
		for(int i = 0; i < row; i++)
		{
			for( int j = 0; j < column; j++)
			{
				if(board[i][j] == 1)
				{
					countP1++;
					countP2 = 0;
				}
				else if(board[i][j] == 2)
				{
					countP2++;
					countP1 = 0;
				}
				else if (board[i][j] == 0)
				{
					countP1 = 0;
					countP2 = 0;
				}
				if(countP1 == 4 || countP2 == 4)
				{
					found = true;
					break;
				}
			}
			countP1 = 0;
			countP2 = 0;
		}
		// for horizontal wins
		for(int j = 0; j < column; j++)
		{
			for( int i = 0; i < row; i++)
			{
				if(board[i][j] == 1)
				{
					countP1++;
					countP2 = 0;
				}
				else if(board[i][j] == 2)
				{
					countP2++;
					countP1 = 0;
				}
				else if (board[i][j] == 0)
				{
					countP1 = 0;
					countP2 = 0;
				}
				if(countP1 == 4 || countP2 == 4)
				{
					found = true;
					break;
				}
			}
			countP1 = 0;
			countP2 = 0;
		}
		
		int countPlayer1 = 0;
		int countPlayer2 = 0;
		int row, column;
		
		for(int i = 0; i < diagonals.length; i++) {
			for(int j = 0; j < diagonals[0].length; j++) {
				row = diagonals[i][j][0];
				column = diagonals[i][j][1];
				if(row != -1 && column != -1) {
					if(board[row][column] == 1) { 
						countPlayer1++; 
						countPlayer2 = 0;
					}
					else if(board[row][column] == 2) { 
						countPlayer2++; 
						countPlayer1 = 0;
					}
					else { 
						countPlayer2 = 0;
						countPlayer1 = 0;
					}
					if(countPlayer1 == 4 || countPlayer2 == 4) {
						found = true;
						break;
					}
				}
			}
		}
			return found;
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
	
}
