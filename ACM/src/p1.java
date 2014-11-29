

public class p1 {


	private String name = "Jacob";

	private int myMove;

	private int checkWinningMove(int board[][])
	{

	
		// for vertical wins
		for(int i = 0; i < board.length; i++)
		{
			for( int j = 0; j < board[0].length; j++)
			{
				try
				{
					if(board[i][j] == 1)
					{
						if (board[i-1][j] == 1)
						{
							if (board[i-2][j] == 1)
							{
								if (board[i-3][j] == 0)
								{
									return j+1;
								}
							}
						}
					}

					if(board[i][j] == 2)
					{
						if (board[i-1][j] == 2)
						{
							if (board[i-2][j] == 2)
							{
								if (board[i-3][j] == 0)
								{
									return j+1;
								}
							}
						}
					}
				}
				catch(ArrayIndexOutOfBoundsException e) {}
			}
		}
		// Diagonal wins
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[0].length; j++)
			{
				try
				{
					//bottom left to top right diagonal
					if(board[i][j] == 1) { 
						if(board[i-1][j-1] == 1) { 
							if(board[i-2][j-2] == 1) {
								//System.out.println("diagonal found 1");
								return j-2; }}}
					if(board[i][j] == 2) {
						if(board[i-1][j-1] == 2) {
							if(board[i-2][j-2] == 2) {
								//System.out.println("diagonal found 2");
								return j-2; }}}

					// top left to bottom right diagonal
					if(board[i][j] == 1) {
						if(board[i+1][j+1] == 1) {
							if(board[i+2][j+2] == 1) {
								//System.out.println("diagonal found 3");
								return j;}}}
					if(board[i][j] == 2) {
						if(board[i+1][j+1] == 2) {
							if(board[i+2][j+2] == 2) {
								//System.out.println("diagonal found 4");
								return j; }}}

				}
				catch(ArrayIndexOutOfBoundsException e) {}
			}
		}

		return 3;
	}

	public String getName()
	{
		return name;
	}

	public  int run(int b[][], int c, int r)
	{
		
		
		myMove = checkWinningMove(b);

		if (myMove == 3)
		{
			myMove = (int)(Math.random()*3+3);
		}

		return myMove;
	}

}

