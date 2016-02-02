package project2;

import java.util.*;

public class MineSweeperGame {
	private Cell[][] board;
	private GameStatus status;

	public MineSweeperGame() {
		status = GameStatus.NotOverYet;
		board = new Cell[5][5];
		setEmpty();
		layMines (24);
	}

	private void setEmpty() {
		for (int r = 0; r < 5; r++)
			for (int c = 0; c < 5; c++)
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
			for (int r = 0; r < 5; r++)     // are only mines left
				for (int c = 0; c < 5; c++)
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
			int c = random.nextInt(5);
			int r = random.nextInt(5);

			if (!board[r][c].isMine()) {
				board[r][c].setMine(true);
				i++;
			}
		}
	}
}




	//  a non-recursive from .... it would be much easier to use recursion


