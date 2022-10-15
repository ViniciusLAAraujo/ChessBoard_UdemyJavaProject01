package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import boardlayer.BoardException;
import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class Program {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();
		boolean exit = false;
		while (!exit) {
			try {
				UI.clearScreen();
				UI.printMatch(chessMatch, captured);
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc, chessMatch.DEFAULT_ROW_COLLUMN,
						chessMatch.DEFAULT_ROW_COLLUMN);

				int[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);

				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc, chessMatch.DEFAULT_ROW_COLLUMN,
						chessMatch.DEFAULT_ROW_COLLUMN);

				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				System.out.println();
				if (capturedPiece == null)
					System.out.println("No pieces were captured");
				else {
					captured.add(capturedPiece);
					System.out.print("captured piece ");
					if (capturedPiece.getColor() == Color.BLACK)
						System.out.println(UI.ANSI_PURPLE + capturedPiece.toString() + UI.ANSI_RESET);
					else
						System.out.println(capturedPiece.toString());
				}
				sc.nextLine();
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (BoardException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (Exception e) {
				System.out.println("Unexpected Error!");
				sc.nextLine();
			}
		}
		sc.close();
	}
}
