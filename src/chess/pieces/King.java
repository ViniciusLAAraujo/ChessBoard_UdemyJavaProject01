package chess.pieces;

import boardlayer.Board;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	public King(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		/*
		StringBuilder sb = new StringBuilder();
		sb.append((char) 488);
		return sb.toString();
		*/
		return "K";
	}

}
