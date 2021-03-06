import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class C4_Frame 
{
	private static JFrame frame = new JFrame();
	private static JButton[][] grid; 
	private static Game game;
	private final int GAMES = 10; 
	private int row = 6;
	private int column = 7;

	public C4_Frame()
	{ 
		game = new Game();
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnGameMode = new JMenu("Game Mode");
		menuBar.add(mnGameMode);

		JMenu mnManual = new JMenu("Manual");
		mnGameMode.add(mnManual);

		JMenuItem mntmVsAI = new JMenuItem("vs. AI");
		mnManual.add(mntmVsAI);

		JMenuItem mntmVsJacob = new JMenuItem("vs. Jacob");
		mnManual.add(mntmVsJacob);

		JMenuItem mntmVsDan = new JMenuItem("vs. Dan");
		mnManual.add(mntmVsDan);

		JMenuItem mntmVsRandom = new JMenuItem("vs. Random");
		mnManual.add(mntmVsRandom);

		JMenuItem mntmVsPlayer = new JMenuItem("vs. Friend");
		mnManual.add(mntmVsPlayer);

		JMenu mnAutomatic = new JMenu("Automatic");
		mnGameMode.add(mnAutomatic);

		JMenuItem mntmAiVsJacob = new JMenuItem("AI vs. Jacob");
		mnAutomatic.add(mntmAiVsJacob);

		JMenuItem mntmAiVsDan = new JMenuItem("AI vs. Dan");
		mnAutomatic.add(mntmAiVsDan);

		JMenuItem mntmAiVsRandom = new JMenuItem("AI vs. Random");
		mnAutomatic.add(mntmAiVsRandom);

		//set layout
		frame.getContentPane().setLayout(new GridLayout(row,column)); 

		grid=new JButton[row][column]; //allocate the size of grid

		for(int i = 0; i < row; i++)
		{
			for(int j = 0; j < column; j++)
			{
				// Creates new button 
				grid[i][j]=new JButton(); 
				// Set all buttons to the color white
				grid[i][j].setBackground(Color.WHITE);
				// Adds button to grid
				frame.getContentPane().add(grid[i][j]); 
			}
		}

		/******************** PLAYER VS. AI ********************/
		mntmVsAI.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{ 
				/*** CLEAR BOARD ***/
				game.resetGame();
				resetBoard();

				playerVs("AI");
			}
		});


		/**************************************************************/

		/****** PLAYER VS. JACOB ********/
		mntmVsJacob.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{ 
				/*** CLEAR BOARD ***/
				game.resetGame();
				resetBoard();
				
				game.setNames("Player1", "JacobAlgorithm");

				playerVs("Jacob");
			}
		});
		/**************************************************************/

		/****** PLAYER VS. DAN ********/
		mntmVsDan.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{ 
				/*** CLEAR BOARD ***/
				game.resetGame();
				resetBoard();
				
				game.setNames("Player1", "DanAlgorithm");

				playerVs("Dan");
			}
		});
		/**************************************************************/

		/****** PLAYER VS. RANDOM ********/
		mntmVsRandom.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{ 
				/*** CLEAR BOARD ***/
				game.resetGame();
				resetBoard();
				
				game.setNames("Player1", "RandomAlgorithm");

				playerVs("Random");
			}
		});
		/**************************************************************/

		/******************** PLAYER VS. FRIEND ********************/
		mntmVsPlayer.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				for(int j = 0; j < game.getConnectFour().getRow(); j++)
				{
					for(int i = 0; i < game.getConnectFour().getColumn(); i++)
					{
						final int temp = i;
						game.setCurrentPlayer(1);
						grid[j][i].addMouseListener(new MouseAdapter()
						{
							@Override
							public void mouseClicked(MouseEvent e)
							{
								game.getConnectFour().takeTurn(temp+1, game.getCurrentPlayer());
								makeMove(game.getConnectFour().getXCoordinate(),game.getConnectFour().getYCoordinate(), game.getCurrentPlayer());

								// Print the board
								game.getConnectFour().printBoard();

								// Print the heuristic
								int heuristic = new Heuristic().heuristic(game.getConnectFour().getBoard());
								System.out.println("Heuristic: " + heuristic);

								manualDialogue();
								if (game.getCurrentPlayer() == 1)
								{
									game.setCurrentPlayer(2);
								}
								else
								{
									game.setCurrentPlayer(1);
								}
							}
						});
					}
				}
			}
		});

		/**************************************************************/

		/******************** AI VS. JACOB ********************/
		mntmAiVsJacob.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				game.resetGame();
				resetBoard();
				game.setNames("AI", "JacobAlgorithm");
				automaticVs("AI","Jacob");
			}
		});
		/**************************************************************/


		/******************** AI VS. DAN ********************/
		mntmAiVsDan.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				game.resetGame();
				resetBoard();
				game.setNames("AI", "DanAlgorithm");
				automaticVs("AI","Dan");
			}
		});
		/**************************************************************/

		/******************** AI VS. RANDOM ********************/
		mntmAiVsRandom.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				game.resetGame();
				resetBoard();
				game.setNames("AI", "RandomAlgorithm");
				automaticVs("AI","Random");
			}
		});
		/**************************************************************/



		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Sets appropriate size for frame
		//frame.pack(); 
		frame.setBounds(0,0,720,480);
		// Makes frame visible
		frame.setVisible(true); 
	}
	
	public ParentAlgorithm setAlgorithm(String player) {
		ParentAlgorithm players = null;
		
		/*** CREATE THE CORRESPONDING ALGORITHM FOR MANUAL ***/
		if (player.equals("AI")) {
			players = new AI();
		}
		else if (player.equals("Jacob")) {
			players = new JacobAlgorithm();
		}
		else if (player.equals("Dan")) {
			players = new DanAlgorithm();
		}
		else if (player.equals("Random")) {
			players = new RandomAlgorithm();
		}
		
		final ParentAlgorithm player2 = players;
		
		return player2;
	}

	public void playerVs(String opponent) {
		final ParentAlgorithm player2 = setAlgorithm(opponent);

		final int row = game.getConnectFour().getBoard()[0].length;
		final int column = game.getConnectFour().getBoard().length;

		game.setCurrentPlayer(1);
		game.getConnectFour().takeTurn(player2.run(game.getConnectFour().getBoard(), row, column), game.getCurrentPlayer());
		makeMove(game.getConnectFour().getXCoordinate(), game.getConnectFour().getYCoordinate(), game.getCurrentPlayer());
		// check for winner
		manualDialogue();

		for(int j = 0; j < game.getConnectFour().getRow(); j++)
		{
			for(int i = 0; i < game.getConnectFour().getColumn(); i++)
			{
				final int temp = i;
				game.setCurrentPlayer(2);
				grid[j][i].addMouseListener(new MouseAdapter()
				{
					@Override
					public void mouseClicked(MouseEvent e)
					{
						game.setCurrentPlayer(2);
						game.getConnectFour().takeTurn(temp+1, game.getCurrentPlayer());
						makeMove(game.getConnectFour().getXCoordinate(),game.getConnectFour().getYCoordinate(), game.getCurrentPlayer());
						manualDialogue();


						// AI takes turn
						game.setCurrentPlayer(1);
						game.getConnectFour().takeTurn(player2.run(game.getConnectFour().getBoard(), row, column), game.getCurrentPlayer());
						makeMove(game.getConnectFour().getXCoordinate(), game.getConnectFour().getYCoordinate(), game.getCurrentPlayer());
						// check for winner
						manualDialogue();
					}
				});
			}
		}
	}
	
	public void automaticVs(String p1, String p2) {
		
		final ParentAlgorithm player1 = setAlgorithm(p1);
		final ParentAlgorithm player2 = setAlgorithm(p2);

		// have name functions in individual classes, insert as parameters for constructor
		int row = game.getConnectFour().getBoard()[0].length;
		int column = game.getConnectFour().getBoard().length;

		for(int i = 0; i < GAMES; i++)
		{
			game.resetGame();
			resetBoard();

			while(!game.gameOutcome())
			{
				// Player 2 takes turn
				game.setCurrentPlayer(2);
				game.getConnectFour().takeTurn(player2.run(game.getConnectFour().getBoard(), row, column), 2);
				makeMove(game.getConnectFour().getXCoordinate(), game.getConnectFour().getYCoordinate(), 2);

				// If player 1 wins, don't take player 2 turn
				if(game.gameOutcome())
				{
					System.out.println("Winner: " + game.getWinner());
					break;
				}

				// Player 1 takes turn
				game.setCurrentPlayer(1);
				game.getConnectFour().takeTurn(player1.run(game.getConnectFour().getBoard(), row, column), 1);
				makeMove(game.getConnectFour().getXCoordinate(), game.getConnectFour().getYCoordinate(), 1);
				
				if(game.gameOutcome())
				{
					System.out.println("Winner: " + game.getWinner());
					break;
				}
			}
		}

		Object [] options2 = {"Run Again", "Exit Game"};
		JOptionPane jop = new JOptionPane(( player1.getName() + " (red) total wins: " + game.getP1Wins() + "\n"
				+ player2.getName() + " (blue) total wins: " + game.getP2Wins() + "\n"
				+"Total tie games: " + game.getTies() + "\n"),
				JOptionPane.PLAIN_MESSAGE, JOptionPane.YES_NO_OPTION, null, options2, options2[0]);
		JDialog dialog = jop.createDialog(null, "Title");
		dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		dialog.setVisible(true);

		String a3 = (String) jop.getValue();
		if (a3.equals("Exit Game")) 
		{
			System.exit(0);
		}
		else if (a3.equals("Run Again"))
		{
			game.resetStats();
		}

		// don't forget to dispose of the dialog
		dialog.dispose();
	}

	public void resetBoard()
	{
		for(int i = 0; i < grid.length; i++)
		{
			for(int j = 0; j < grid[0].length; j++)
			{
				//grid[i][j].removeMouseListener(l)
				// Set all buttons to the color white
				grid[i][j].setBackground(Color.WHITE);
			}
		}
	}

	// Takes in coordinates of the move and which player is making the move
	public static void makeMove(int row, int column, int player)
	{
		if (player == 1)
		{
			grid[row][column].setBackground(Color.RED);
		}
		else
		{
			grid[row][column].setBackground(Color.BLUE);
		}
	}

	public void manualDialogue()
	{
		if(game.gameOutcome())
		{

			Object [] options1 = {"Restart Game", "Exit Game"};
			JOptionPane jop = new JOptionPane((game.getWinner() + " wins the game!"), JOptionPane.PLAIN_MESSAGE, JOptionPane.YES_NO_OPTION, null, options1, options1[0]);
			JDialog dialog = jop.createDialog(null, "Game Over");
			dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

			dialog.setVisible(true);

			String a3 = (String) jop.getValue();
			if (a3.equals("Restart Game")) 
			{
				game.resetGame();
				resetBoard();
			} else if (a3.equals("Exit Game")) 
			{
				System.exit(0);
			}

			dialog.dispose();

		}
	}
	public static void main(String[] args) 
	{
		new C4_Frame();
	}
}
