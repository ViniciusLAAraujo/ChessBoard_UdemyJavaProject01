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
		/*Future idea to print Unicode cooler characters
		 * 
		 * StringBuilder sb = new StringBuilder();
		 * sb.append((char) 422);
		 * return sb.toString();
		 * 
		 */
		return "R";
	}
}
