
public class Game
{
    //C4_Frame frame = new C4_Frame(6, 7);
    private String p1name;
	private String p2name;
	private int currentPlayer;
	private	boolean gameOver;
	private	String winner;
	private	int p1Wins;
	private	int p2Wins;
	private	int ties; 
	private ConnectFour connectFour;
	
	public Game()
	{
	    connectFour = new ConnectFour();
	    p1name = "Player 1 (Red)";
	    p2name = "Player 2 (Blue)";
	    gameOver = false;
		winner = "";
		p1Wins = 0;
		p2Wins = 0;
		ties = 0; 
	}
	
	public String getWinner()
	{
		return winner;
	}
	
	public void resetStats() {
		p1Wins = 0;
		p2Wins = 0;
		ties = 0;
	}
	
	public void resetGame()
	{
		gameOver = false;
		connectFour.setBoard();
		winner = "";
	}
	
	public void setCurrentPlayer(int currentPlayer)
	{
	    this.currentPlayer = currentPlayer;
	}
	
	public int getCurrentPlayer()
	{
	    return currentPlayer;
	}
	
	public ConnectFour getConnectFour()
	{
	    return connectFour;
	}
	
	public boolean gameOutcome()
	{
			if(!gameOver)
			{
				if(connectFour.checkWin())
				{
					gameOver = true;
					if (currentPlayer == 1)
					{
						p1Wins++;
						winner = p1name;
					}
					else
					{
						p2Wins++;
						winner = p2name;
					}
				}

				if (connectFour.checkTie())
				{
					ties++;
					gameOver = true;
					winner = "no winner";
				}
			}
			return gameOver;
	}

	public int getP1Wins() 
	{
		return p1Wins;
	}

	public int getP2Wins() 
	{
		return p2Wins;
	}

	public int getTies()
	{
		return ties;
	}
	
	public void setNames(String p1, String p2) {
		p1name = p1;
		p2name = p2;
	}

}
