package application;

import boardlayer.Board;
import boardlayer.Piece;


public class Program {

	public static void main(String[] args) {
		Board board = new Board(8, 8);
		Piece piece = new Piece(board);
	}

}
