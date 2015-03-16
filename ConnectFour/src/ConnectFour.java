import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class ConnectFour 
{
	private int column;
	private int xCoordinate;
	private int yCoordinate;
	private int row;
	private int board[][];
	
	private PrintWriter writer;
	
	ConnectFour()
	{
		column = 7;
		row = 6;
		board = new int[row][column];
		setBoard();
		
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
		
		// Diagonal wins
		for (int j = 0; j < row; j++)
		{
			for (int i = 0; i < column; i++)
			{
				try
				{
					//bottom left to top right diagonal
					if(board[i][j] == 1) { 
						if(board[i+1][j-1] == 1) { 
							if(board[i+2][j-2] == 1) {
								if(board[i+3][j-3] == 1) {
									found = true;
									break; }}}}
					if(board[i][j] == 2) {
						if(board[i+1][j-1] == 2) {
							if(board[i+2][j-2] == 2) {
								if(board[i+3][j-3] == 2) {
									found = true;
									break; }}}}
					
					// top left to bottom right diagonal
					if(board[i][j] == 1) {
						if(board[i+1][j+1] == 1) {
							if(board[i+2][j+2] == 1) {
								if(board[i+3][j+3] == 1) {
									found = true;
									break; }}}}
					if(board[i][j] == 2) {
						if(board[i+1][j+1] == 2) {
							if(board[i+2][j+2] == 2) {
								if(board[i+3][j+3] == 2) {
									found = true;
									break; }}}}
				}
				catch(ArrayIndexOutOfBoundsException e) {}
			}
		}
			return found;
	}
}
