import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
	private final int GAMES = 5; // games x 2 (each player goes first equal times)

	public C4_Frame(int row, int column)
	{ 
		game = new Game();
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnGameMode = new JMenu("Game Mode");
		menuBar.add(mnGameMode);

		JMenu mnManual = new JMenu("Manual");
		mnGameMode.add(mnManual);

		JMenuItem mntmVsAlgorithm = new JMenuItem("vs. Algorithm 1");
		mnManual.add(mntmVsAlgorithm);

		JMenuItem mntmVsAlgorithm_1 = new JMenuItem("vs. Algorithm 2");
		mnManual.add(mntmVsAlgorithm_1);

		JMenuItem mntmVsPlayer = new JMenuItem("vs. Player");
		mnManual.add(mntmVsPlayer);

		JMenuItem mntmAutomatic = new JMenuItem("Automatic");
		mnGameMode.add(mntmAutomatic);

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

        /******************** PLAYER VS. PLAYER ********************/
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

		/****** PLAYER VS. ALGORITHM 1 ********/
		mntmVsAlgorithm.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{ 
				/*** CLEAR BOARD ***/
				game.resetGame();
				resetBoard();

				/*** CREATE PLAYER ALGORITHM 1 ***/
				p1 player1 = new p1();

				final int row = game.getConnectFour().getBoard()[0].length;
				final int column = game.getConnectFour().getBoard().length;

				for(int j = 0; j < game.getConnectFour().getRow(); j++)
				{
					for(int i = 0; i < game.getConnectFour().getColumn(); i++)
					{
						final p1 player = player1;
						final int temp = i;
						game.setCurrentPlayer(1);
						grid[j][i].addMouseListener(new MouseAdapter()
						{
							@Override
							public void mouseClicked(MouseEvent e)
							{
								game.setCurrentPlayer(1);
								game.getConnectFour().takeTurn(temp+1, game.getCurrentPlayer());
								makeMove(game.getConnectFour().getXCoordinate(),game.getConnectFour().getYCoordinate(), game.getCurrentPlayer());

								manualDialogue();
							
								// Algorithm 1 takes turn
								game.setCurrentPlayer(2);
								game.getConnectFour().takeTurn(player.run(game.getConnectFour().getBoard(), row, column), game.getCurrentPlayer());
								makeMove(game.getConnectFour().getXCoordinate(), game.getConnectFour().getYCoordinate(), game.getCurrentPlayer());

								// check for winner
								manualDialogue();

							}
						});
					}
				}
			}
		});
		/**********************************************/
		
			/****** PLAYER VS. ALGORITHM 2 ********/
		mntmVsAlgorithm_1.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{ 
				/*** CLEAR BOARD ***/
				game.resetGame();
				resetBoard();

				/*** CREATE PLAYER ALGORITHM 2 ***/
				AI player2 = new AI();

				final int row = game.getConnectFour().getBoard()[0].length;
				final int column = game.getConnectFour().getBoard().length;

				for(int j = 0; j < game.getConnectFour().getRow(); j++)
				{
					for(int i = 0; i < game.getConnectFour().getColumn(); i++)
					{
						final AI player = player2;
						final int temp = i;
						game.setCurrentPlayer(1);
						grid[j][i].addMouseListener(new MouseAdapter()
						{
							@Override
							public void mouseClicked(MouseEvent e)
							{
								game.setCurrentPlayer(1);
								game.getConnectFour().takeTurn(temp+1, game.getCurrentPlayer());
								makeMove(game.getConnectFour().getXCoordinate(),game.getConnectFour().getYCoordinate(), game.getCurrentPlayer());
								manualDialogue();
							
								
								// Algorithm 1 takes turn
								game.setCurrentPlayer(2);
								game.getConnectFour().takeTurn(player.run(game.getConnectFour().getBoard(), row, column), game.getCurrentPlayer());
								makeMove(game.getConnectFour().getXCoordinate(), game.getConnectFour().getYCoordinate(), game.getCurrentPlayer());
								// check for winner
								manualDialogue();

							}
						});
					}
				}
			}
		});
		/**********************************************/

        /****** ALGORITHM 1 VS. ALGORITHM 2 ***********/
		mntmAutomatic.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				game.resetGame();
				resetBoard();

				/*** CHANGE CLASS NAMES FOR PLAYERS ***/
				p1 player1 = new p1();
				AI player2 = new AI();
				/**************************************/

				// have name functions in individual classes, insert as parameters for constructor
				int row = game.getConnectFour().getBoard()[0].length;
				int column = game.getConnectFour().getBoard().length;

				for(int i = 0; i < GAMES; i++)
				{

					game.resetGame();
					resetBoard();

					while(!game.gameOutcome())
					{

						// Player 1 takes turn
						game.setCurrentPlayer(1);
						game.getConnectFour().takeTurn(player1.run(game.getConnectFour().getBoard(), row, column), 1);
						makeMove(game.getConnectFour().getXCoordinate(), game.getConnectFour().getYCoordinate(), 1);


						// If player 1 wins, don't take player 2 turn
						if(game.gameOutcome())
						{
							System.out.println("Winner: " + game.getWinner());
							break;
						}

						// Player 2 takes turn
						game.setCurrentPlayer(2);
						game.getConnectFour().takeTurn(player2.run(game.getConnectFour().getBoard(), row, column), 2);
						makeMove(game.getConnectFour().getXCoordinate(), game.getConnectFour().getYCoordinate(), 2);
					}
				}

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
					}
				}

				Object [] options2 = {"Run Again", "Exit Game"};
				JOptionPane jop = new JOptionPane(( player1.getName() + " (red) total wins: " + game.getP1Wins() + "\n"
						+ player2.getName() + " (blue) total wins: " + game.getP2Wins() + "\n"
						+"Total tie games: " + game.getTies() + "\n"),
						JOptionPane.PLAIN_MESSAGE, JOptionPane.YES_NO_OPTION, null, options2, options2[0]);
				JDialog dialog = jop.createDialog(null, "Title");
				dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
				// In real code, you should invoke this from AWT-EventQueue using invokeAndWait() or something
				dialog.setVisible(true);
				// and would cast in a safe manner
				String a3 = (String) jop.getValue();
				if (a3.equals("Exit Game")) 
				{
					System.exit(0);
				}
				else if (a3.equals("Run Again"))
				{
					// too be implemented
				}

				// don't forget to dispose of the dialog
				dialog.dispose();

			}
		});
		/******************************************************/


		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Sets appropriate size for frame
		//frame.pack(); 
		frame.setBounds(0,0,720,480);
		// Makes frame visible
		frame.setVisible(true); 
	}

	public void resetBoard()
	{
		for(int i = 0; i < grid.length; i++)
		{
			for(int j = 0; j < grid[0].length; j++)
			{
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
		new C4_Frame(6,7);
	}
}