package chess;

import boardlayer.Board;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	final int DEFAULT_ROW_COLLUMN = 8;
	private Board board;

	public ChessMatch() {
		board = new Board(DEFAULT_ROW_COLLUMN, DEFAULT_ROW_COLLUMN);
		initialSetup();
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

	private void placeNewPiece(char column,int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row, board.getRows(), board.getColumns()).toPosition());
	}

	private void initialSetup() {
		placeNewPiece('a',8,new Rook(this.board, Color.BLACK));
		placeNewPiece('e',8,new King(this.board, Color.BLACK));
		placeNewPiece('h',8,new Rook(this.board, Color.BLACK));
		placeNewPiece('a',1,new Rook(this.board, Color.WHITE));
		placeNewPiece('e',1,new King(this.board, Color.WHITE));
		placeNewPiece('h',1,new Rook(this.board, Color.WHITE));
	}
}
