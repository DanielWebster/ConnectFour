

public class p22 {

	private String name = "Simple Dan";
	static int y = -1;
	private int random = 0;
	static int x[] = {1, 2, 3, 3, 4, 4, 5, 5, 5, 5};
	static boolean first = true;
	public String getName() {

		return name;
	}

	public int run(int[][] board, int row, int column) {


//			return preventVertical(board);

		random = preventHorizontal(board);
		if(random == -1)
			random = preventVertical(board);
		//		else if(preventHorizontal(board) != 0)
		System.out.println(random);
		
					return random;
	

		//		System.out.println("obviously didn't work right");
		//		return 1;
	}

	

	public int preventHorizontal(int[][] board)
	{
		int countP1 = 0;
		int countP2 = 0;
		boolean found = false;
		for(int j = 0; j < 6; j++)
		{
			for( int i = 0; i < 7; i++)
			{

				if(board[5-j][6-i] == 1)
				{
					countP1++;
					countP2 = 0;
				}
				else if(board[5-j][6-i] == 2)
				{
					countP2++;
					countP1 = 0;
				}
				else if (board[5-j][6-i] == 0)
				{
					countP1 = 0;
					countP2 = 0;
				}
				if(countP1 == 3 || countP2 == 3)
				{

					//					System.out.println("j: " + (j) + " i: " + (i) );
					//					System.out.println("5-j: " + (5-j) + " 5-i: " + (5-i));
					//					System.out.println("board: " + board[5-j][5-i]);
					found = true;
					try{
					if(board[5-j][5-i]!=2)
					{
						//						System.out.println(countP1 + " " + countP2);
						//						System.out.println("if statement column: " + (6-i));
						//						System.out.println("if statement row: " + j);
						return 6-i;
					}
					else if(board[5-j][9-i]!=2)
					{
												System.out.println(countP1 + " " + countP2);
												System.out.println("else statement row: " + (5-j));
						System.out.println("else " + (10-i));
						return 10-i;
					}
					else
					{
						System.out.println("both sides blocked");
						System.out.println("continue looping");
						countP1 = 0;
						countP2 = 0;
					}
					}
					catch(ArrayIndexOutOfBoundsException e){ System.out.println("Exception: ");
					System.out.println("else " + (10-i));
					return 10-i;
					}
					//					else
					//					{
					//						System.out.println("else statement");
					//						return 6-i;
					//					}

				}
			}
			countP1 = 0;
			countP2 = 0;
		}

		return -1;
	}


	public int preventVertical(int[][] board)
	{

		int countP1 = 0;
		int countP2 = 0;
		boolean found = false;
		for(int j = 0; j < 7; j++)
		{

			for( int i = 0; i < 6; i++)
			{
				if(board[5-i][6-j] == 1)
				{
					countP1++;
					countP2 = 0;
				}
				else if(board[5-i][6-j] == 2)
				{
					countP2++;
					countP1 = 0;
				}
				else if (board[5-i][6-j] == 0)
				{
					countP1 = 0;
					countP2 = 0;
				}
				if(countP1 == 3 || countP2 == 3)
				{
					//					System.out.println("i: " + (i) + " j: " + (j) );
					//					System.out.println("board: " + board[i][j]);
					found = true;
					if(board[i][6-j]!=2)
					{
						//						System.out.println("if statement");
						return 7-j;
					}

				}
			}
			countP1 = 0;
			countP2 = 0;
		}
		return 0;
	}

}