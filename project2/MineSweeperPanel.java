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

	private MineSweeperGame game;  // model

	public MineSweeperPanel() {
		// create JPanels
		JPanel bottom = new JPanel();
		JPanel center = new JPanel();
		
		// create buttons/sliders/panes
		butQuit = new JButton("Quit");
		boardSize = new JSlider(JSlider.HORIZONTAL);
		boardSize.setMinimum(3);
		boardSize.setMaximum(30);
		boardSize.setMajorTickSpacing(3);
		boardSize.setMinorTickSpacing(1);
		boardSize.setPaintTicks(true);
		boardSize.setPaintLabels(true);
		
		// prompt user for board size using JOptionPane and slider
		JOptionPane.showInputDialog(null, new Object[] { "Select a value: ", boardSize});
		
		// create button listener
		ButtonListener listener = new ButtonListener();

		// add listener for quit button
		butQuit.addActionListener(listener);
		
		// instantiate new MineSweeperGame
		game = new MineSweeperGame();

		// create the board
		center.setLayout(new GridLayout(5, 5));
		board = new JButton[5][5];

		for (int row = 0; row < 5; row++)
			for (int col = 0; col < 5; col++) {
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

		for (int r = 0; r < 5; r++)
			for (int c = 0; c < 5; c++) {
				iCell = game.getCell(r, c);

				board[r][c].setText("");

				// readable, ifs are verbose
					
				if (iCell.isMine())
					board[r][c].setText("!");

				if (iCell.isExposed())
					board[r][c].setEnabled(false);
				else
					board[r][c].setEnabled(true);
			}
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			for (int r = 0; r < 5; r++)
				for (int c = 0; c < 5; c++)
					if (board[r][c] == e.getSource())
						game.select(r, c);
			
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