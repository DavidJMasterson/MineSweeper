package project2;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MineSweeperPanel extends JPanel {

	private JButton[][] board;
	private JButton butQuit;
	private Cell iCell;
	private JSlider boardSize;
	private int size, mines;

	private MineSweeperGame game;  // model

	public MineSweeperPanel() {
		// create JPanels
		JPanel bottom = new JPanel();
		JPanel center = new JPanel();
		
		// create buttons/sliders/panes
		butQuit = new JButton("Quit");
	/*	boardSize = new JSlider(JSlider.HORIZONTAL);
		boardSize.setMinimum(3);
		boardSize.setMaximum(30);
		boardSize.setMajorTickSpacing(3);
		boardSize.setMinorTickSpacing(1);
		boardSize.setPaintTicks(true);
		boardSize.setPaintLabels(true);*/
		
		// prompt user for board size using JOptionPane and slider
		//JOptionPane.showInputDialog(null, new Object[] { "Select a value: ", boardSize});
		
		// create button listener
		ButtonListener listener = new ButtonListener();

		// add listener for quit button
		butQuit.addActionListener(listener);
		
/*		// add listener for slider
		boardSize.addChangeListener(new ChangeListener() {
			@Override 
			public void stateChanged(ChangeEvent e) {
				size = boardSize.getValue();
			}
		});*/
		
		JOptionPane.showMessageDialog(null, "Welcome to MineSweeper! Board size must be between 3 and 30, mine count must not "
				+ "exceed cell count, and mine count must be a positive number.");
		
		try{
			size = Integer.parseInt(JOptionPane.showInputDialog("Board size?"));
			mines = Integer.parseInt(JOptionPane.showInputDialog("Mine count?"));
			if(mines > (size*size))
				throw new IllegalArgumentException();
			if(mines <= 0)
				throw new IllegalArgumentException();
			if(size < 3 || size > 30)
				throw new IllegalArgumentException();
		} catch(IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null,  "Invalid input, closing.");
			System.exit(0);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Input error, closing.");
			System.exit(0);
		}
		
		// instantiate new MineSweeperGame
		game = new MineSweeperGame(size, mines);

		// create the board
		center.setLayout(new GridLayout(size, size));
		board = new JButton[size][size];

		for (int row = 0; row < size; row++)
			for (int col = 0; col < size; col++) {
				board[row][col] = new JButton("");
				board[row][col].addActionListener(listener);
				center.add(board[row][col]);
			}
		
		// add quit button to layout
		bottom.add(butQuit);
	
		displayBoard();

		// 
		bottom.setLayout(new GridLayout(3, 2));

		// add all to contentPane
		add(center, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);
	}

	private void displayBoard() {

		for (int r = 0; r < size; r++)
			for (int c = 0; c < size; c++) {
				iCell = game.getCell(r, c);

				board[r][c].setText("");

				// readable, ifs are verbose
					
				if (iCell.isMine())
					board[r][c].setText("!");

				else if (iCell.isExposed()) {

					board[r][c].setEnabled(false);
					board[r][c].setText(Integer.toString(game.getAdjMines(r, c)));
				}
				else
					board[r][c].setEnabled(true);
			}
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			for (int r = 0; r < size; r++)
				for (int c = 0; c < size; c++)
					if (board[r][c] == e.getSource()){
						game.select(r, c);
					}
			
			if(butQuit == e.getSource())
				System.exit(0);
				
			displayBoard();
								
			if (game.getGameStatus() == GameStatus.Lost) {
				displayBoard();
				JOptionPane.showMessageDialog(null, "You Lose \n The game will reset");
				//exposeMines = false;
				game.reset();
				displayBoard();

			}

			if (game.getGameStatus() == GameStatus.Won) {
				JOptionPane.showMessageDialog(null, "You Win: all mines have been found!\n"
						+ "The game will reset");
				game.reset();
				displayBoard();
			}
		}
	}
}