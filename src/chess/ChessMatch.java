package chess;

import boardlayer.Board;
import boardlayer.Position;
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
	
	private void initialSetup() {
		this.board.placePiece(new Rook(this.board, Color.BLACK), new Position(0,0));
		this.board.placePiece(new King(this.board, Color.BLACK), new Position(0,4));
		this.board.placePiece(new Rook(this.board, Color.BLACK), new Position(0,7));
		this.board.placePiece(new Rook(this.board, Color.WHITE), new Position(7,0));
		this.board.placePiece(new King(this.board, Color.WHITE), new Position(7,4));
		this.board.placePiece(new Rook(this.board, Color.WHITE), new Position(7,7));
	}
}
