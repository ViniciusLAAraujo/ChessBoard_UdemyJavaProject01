package application;

import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class Program {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		boolean exit = false;
		while (!exit) {
			UI.printBoard(chessMatch.getPieces());
			System.out.println();
			System.out.print("Source: ");
			ChessPosition source = UI.readChessPosition(sc, chessMatch.DEFAULT_ROW_COLLUMN, chessMatch.DEFAULT_ROW_COLLUMN);
			
			System.out.println();
			System.out.print("Target: ");
			ChessPosition target = UI.readChessPosition(sc, chessMatch.DEFAULT_ROW_COLLUMN, chessMatch.DEFAULT_ROW_COLLUMN);
			
			ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
			System.out.println();
			System.out.print("captured piece ");
			if (capturedPiece.getColor()==Color.BLACK)
				System.out.println(UI.ANSI_PURPLE+capturedPiece.toString()+UI.ANSI_RESET);
			else 
				System.out.println(capturedPiece.toString());
		}
		sc.close();
	}
}
