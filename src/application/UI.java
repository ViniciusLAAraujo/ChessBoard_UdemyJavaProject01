package application;

//import java.io.PrintWriter; //Future plans
import chess.ChessPiece;

public class UI {
	public static void printBoard(ChessPiece[][] pieces) {
		for (int i = 0; i < pieces.length; i++) {
			System.out.print((pieces.length - i) + "  ");
			for (int j = 0; j < pieces[0].length; j++)
				printPiece(pieces[i][j]);
			System.out.println();
		}
		System.out.print(" ");
		for (int i = 0; i < pieces[0].length; i++)
			System.out.print("  " + (char) (97 + i));

	}

	private static void printPiece(ChessPiece piece) {
		// PrintWriter printWriter = new PrintWriter(System.out,true);//Future plan to
		// write Unicode
		if (piece == null)
			System.out.print("-");
		else
			System.out.print(piece);
		// printWriter.print(piece);//Future plan to write Unicode
		System.out.print("  ");
	}
}
