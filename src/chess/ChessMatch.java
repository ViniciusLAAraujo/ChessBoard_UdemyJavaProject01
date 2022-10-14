package chess;

import boardlayer.Board;

public class ChessMatch {
	final int DEFAULT_ROW_COLLUMN = 8;
	private Board board;

	public ChessMatch() {
		board = new Board(DEFAULT_ROW_COLLUMN, DEFAULT_ROW_COLLUMN);
	}
	
	public ChessMatch(int row, int collumn) {
		board = new Board(row, collumn);
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] matx = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				matx[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return matx;
	}
}
