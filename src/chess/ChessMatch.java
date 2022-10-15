package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardlayer.Board;
import boardlayer.Piece;
import boardlayer.Position;
import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Rook;

public class ChessMatch {
	public final int DEFAULT_ROW_COLLUMN = 8;

	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;

	private List<Piece> piecesOnBoard;
	private List<Piece> capturedPieces;

	public ChessMatch() {
		board = new Board(DEFAULT_ROW_COLLUMN, DEFAULT_ROW_COLLUMN);
		this.turn = 1;
		this.currentPlayer = Color.WHITE;
		this.piecesOnBoard = new ArrayList<>();
		this.capturedPieces = new ArrayList<>();
		initialSetup();
	}

	public ChessMatch(int row, int collumn) {
		board = new Board(row, collumn);
		this.turn = 1;
		this.currentPlayer = Color.WHITE;
		this.piecesOnBoard = new ArrayList<>();
		this.capturedPieces = new ArrayList<>();
	}

	public int getTurn() {
		return this.turn;
	}

	public Color getCurrentPlayer() {
		return this.currentPlayer;
	}

	public boolean isCheck() {
		return check;
	}

	public boolean isCheckMate() {
		return checkMate;
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

	public int[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);

		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check!");
		}

		check = (testCheck(opponent(currentPlayer))) ? true : false;

		if (this.check == true && testCheckMate(opponent(currentPlayer)))
			this.checkMate = true;

		nextTurn();
		return (ChessPiece) capturedPiece;
	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position))
			throw new ChessException("There is no piece in source position!");
		if (currentPlayer != ((ChessPiece) this.board.piece(position)).getColor())
			throw new ChessException("Selected piece it's not yours");
		if (!board.piece(position).isThereAnyPossibleMove())
			throw new ChessException("There is no possibles moves for this selected piece");

	}

	private void validateTargetPosition(Position source, Position target) {
		if (this.board.piece(source).possibleMove(target) == 0)
			throw new ChessException("Selected piece can't be moved to this target position");
	}

	private Piece makeMove(Position source, Position target) {
		ChessPiece selectedPiece = (ChessPiece) board.removePiece(source);
		selectedPiece.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		if (capturedPiece != null) {
			piecesOnBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		board.placePiece(selectedPiece, target);
		return capturedPiece;
	}

	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece selectedPiece = (ChessPiece) board.removePiece(target);
		selectedPiece.decreaseMoveCount();
		board.placePiece(selectedPiece, source);

		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnBoard.add(capturedPiece);
		}
	}

	private void nextTurn() {
		turn++;
		this.currentPlayer = (this.currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : list)
			if (p instanceof King)
				return (ChessPiece) p;
		throw new IllegalStateException("There is no " + color + " king on the board");
	}

	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnBoard.stream().filter(x -> ((ChessPiece) x).getColor() == opponent(color))
				.collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			int[][] matx = p.possibleMoves();
			if (matx[kingPosition.getRow()][kingPosition.getColumn()] == 2)
				return true;
		}
		return false;
	}

	private boolean testCheckMate(Color color) {
		List<Piece> allSelfPieces = piecesOnBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : allSelfPieces) {
			int[][] matx = p.possibleMoves();
			for (int i = 0; i < matx.length; i++)
				for (int j = 0; j < matx[i].length; j++)
					if (matx[i][j] > 0) {
						Position source = ((ChessPiece) p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						if (!testCheck)
							return false;
					}

		}
		return true;
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		this.board.placePiece(piece, new ChessPosition(column, row, board.getRows(), board.getColumns()).toPosition());
		this.piecesOnBoard.add(piece);
	}

	private void initialSetup() {
		placeNewPiece('a', 8, new Rook(this.board, Color.BLACK));
		placeNewPiece('e', 8, new King(this.board, Color.BLACK));
		placeNewPiece('h', 8, new Rook(this.board, Color.BLACK));
		placeNewPiece('a', 7, new Pawn(this.board, Color.BLACK));
		placeNewPiece('b', 7, new Pawn(this.board, Color.BLACK));
		placeNewPiece('c', 7, new Pawn(this.board, Color.BLACK));
		placeNewPiece('d', 7, new Pawn(this.board, Color.BLACK));
		placeNewPiece('e', 7, new Pawn(this.board, Color.BLACK));
		placeNewPiece('f', 7, new Pawn(this.board, Color.BLACK));
		placeNewPiece('g', 7, new Pawn(this.board, Color.BLACK));
		placeNewPiece('h', 7, new Pawn(this.board, Color.BLACK));
		
		placeNewPiece('a', 2, new Pawn(this.board, Color.WHITE));
		placeNewPiece('b', 2, new Pawn(this.board, Color.WHITE));
		placeNewPiece('c', 2, new Pawn(this.board, Color.WHITE));
		placeNewPiece('d', 2, new Pawn(this.board, Color.WHITE));
		placeNewPiece('e', 2, new Pawn(this.board, Color.WHITE));
		placeNewPiece('f', 2, new Pawn(this.board, Color.WHITE));
		placeNewPiece('g', 2, new Pawn(this.board, Color.WHITE));
		placeNewPiece('h', 2, new Pawn(this.board, Color.WHITE));
		placeNewPiece('a', 1, new Rook(this.board, Color.WHITE));
		placeNewPiece('e', 1, new King(this.board, Color.WHITE));
		placeNewPiece('h', 1, new Rook(this.board, Color.WHITE));
	}
}
