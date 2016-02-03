/*****************************************************************
A MineSweeper clone that has a fully functioning gui - contains
features such as custom board size, custom mine count, win/loss
display, and mine flagging.
@author David Masterson
@author Sarah Marcelin
@version February 1st 2016
*****************************************************************/
package project2;

import java.util.*;

public class MineSweeperGame {
	private Cell[][] board;
	private GameStatus status;
	private int adjMines;
	private int size;

	public MineSweeperGame(int size) {
		this.size = size;
		status = GameStatus.NotOverYet;
		board = new Cell[size][size];
		adjMines = 0;
		setEmpty();
		layMines (24);
	}

	public int getAdjMines(int rowPos, int colPos){
		int count = 0;
		
		for(int row = rowPos-1; row <= rowPos+1; row++){
			for(int col = colPos-1; col <= colPos + 1; col++) {
				//FIX: negative array indices/does not always work
				if(row < 0) 
					row = 0;
				else if(row > size - 1)
					row = size - 1;
				
				if(col < 0)
					col = 0;
				else if(col > size - 1)
					col = size - 1;
				if(board[row][col].isMine()) 
					count++;
			}
		}
		return count;
	}
	
	private void setEmpty() {
		for (int r = 0; r < size; r++)
			for (int c = 0; c < size; c++)
				board[r][c] = new Cell(false, false);  // totally clear.
	}

	public Cell getCell(int row, int col) {
		return board[row][col];
	}

	public void select(int row, int col) {
		board[row][col].setExposed(true);

		if (board[row][col].isMine())   // did I lose
			status = GameStatus.Lost;
		else {
			status = GameStatus.Won;    // did I win
			for (int r = 0; r < size; r++)     // are only mines left
				for (int c = 0; c < size; c++)
					if (!board[r][c].isExposed() && !board[r][c].isMine())
						status = GameStatus.NotOverYet;
		}
	}

	public GameStatus getGameStatus() {
		return status;
	}

	public void reset() {
		status = GameStatus.NotOverYet;
		setEmpty();
		layMines (10);
	}

	private void layMines(int mineCount) {
		int i = 0;		// ensure all mines are set in place

		Random random = new Random();
		while (i < mineCount) {			// perhaps the loop will never end :)
			int c = random.nextInt(size);
			int r = random.nextInt(size);

			if (!board[r][c].isMine()) {
				board[r][c].setMine(true);
				i++;
			}
		}
	}
}

	//  a non-recursive form .... it would be much easier to use recursion


