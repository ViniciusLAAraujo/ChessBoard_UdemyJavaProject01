package chess.pieces;

import boardlayer.Board;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {

	public Rook(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		/*
		StringBuilder sb = new StringBuilder();
		sb.append((char) 422);
		return sb.toString();
		*/
		return "R";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] matx = new boolean [getBoard().getRows()][getBoard().getColumns()];
		return matx;
	}
}
