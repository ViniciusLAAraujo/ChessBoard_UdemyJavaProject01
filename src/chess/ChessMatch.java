package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardlayer.Board;
import boardlayer.Piece;
import boardlayer.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
	public final int DEFAULT_ROW_COLLUMN = 8;

	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;

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

	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}

	public ChessPiece getPromoted() {
		return promoted;
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

		ChessPiece movedPiece = (ChessPiece) board.piece(target);

		// Promotion
		promoted = null;
		if (movedPiece instanceof Pawn)
			if ((movedPiece.getColor() == Color.WHITE && target.getRow() == 0)
					|| (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
				promoted = (ChessPiece) board.piece(target);
				promoted = replacePromotedPiece("Q");
			}

		check = (testCheck(opponent(currentPlayer))) ? true : false;

		if (this.check == true && testCheckMate(opponent(currentPlayer)))
			this.checkMate = true;
		else
			nextTurn();

		// En Passant
		if (movedPiece instanceof Pawn
				&& ((target.getRow() == source.getRow() - 2) || (target.getRow() == source.getRow() + 2)))
			this.enPassantVulnerable = movedPiece;
		else
			this.enPassantVulnerable = null;

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

	public ChessPiece replacePromotedPiece(String type) {
		if (promoted == null)
			throw new IllegalStateException("There is no piece to be promoted");
		if (type.equals("Q") && type.equals("R") && type.equals("N") && type.equals("B"))
			return promoted;
		Position pos = promoted.getChessPosition().toPosition();
		Piece selectedPiece = board.removePiece(pos);
		piecesOnBoard.remove(selectedPiece);
		
		ChessPiece newPiece = newPiece(type, promoted.getColor());
		board.placePiece(newPiece, pos);
		piecesOnBoard.add(newPiece);
		
		return newPiece;
	}

	private ChessPiece newPiece(String type, Color color) {
		if (type.equals("B"))
			return new Bishop(board, color);
		if (type.equals("R"))
			return new Rook(board, color);
		if (type.equals("N"))
			return new Knight(board, color);
		return new Queen(board, color);

	}

	private Piece makeMove(Position source, Position target) {
		ChessPiece selectedPiece = (ChessPiece) board.removePiece(source);

		selectedPiece.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(selectedPiece, target);
		if (capturedPiece != null) {
			piecesOnBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		// Castling kingside rook
		if (selectedPiece instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceR = new Position(source.getRow(), source.getColumn() + 3);
			Position targetR = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceR);
			board.placePiece(rook, targetR);
			rook.increaseMoveCount();
		}
		// Castling Queenside rook
		if (selectedPiece instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceR = new Position(source.getRow(), source.getColumn() - 4);
			Position targetR = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceR);
			board.placePiece(rook, targetR);
			rook.increaseMoveCount();
		}

		// En Passant
		if (selectedPiece instanceof Pawn && source.getColumn() != target.getColumn() && capturedPiece == null) {
			Position pawnPosition;
			if (selectedPiece.getColor() == Color.WHITE)
				pawnPosition = new Position(target.getRow() + 1, target.getColumn());
			else
				pawnPosition = new Position(target.getRow() - 1, target.getColumn());

			capturedPiece = board.removePiece(pawnPosition);
			capturedPieces.add(capturedPiece);
			piecesOnBoard.remove(capturedPiece);
		}

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
		// Castling kingside rook
		if (selectedPiece instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceR = new Position(source.getRow(), source.getColumn() + 3);
			Position targetR = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetR);
			board.placePiece(rook, sourceR);
			rook.decreaseMoveCount();
		}
		// Castling Queenside rook
		if (selectedPiece instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceR = new Position(source.getRow(), source.getColumn() - 4);
			Position targetR = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetR);
			board.placePiece(rook, sourceR);
			rook.decreaseMoveCount();
		}

		// En Passant
		if (selectedPiece instanceof Pawn && source.getColumn() != target.getColumn()
				&& capturedPiece == this.enPassantVulnerable) {
			ChessPiece pawn = (ChessPiece) board.removePiece(target);
			Position pawnPosition;
			if (selectedPiece.getColor() == Color.WHITE)
				pawnPosition = new Position(target.getRow() + 1, target.getColumn());
			else
				pawnPosition = new Position(target.getRow() - 1, target.getColumn());

			board.placePiece(pawn, pawnPosition);
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

	public boolean testCheckCasteling(Color color, Position kingPosition) {
		List<Piece> opponentPieces = piecesOnBoard.stream().filter(x -> ((ChessPiece) x).getColor() == opponent(color))
				.collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			if (!(p instanceof King)) {
				int[][] matx = p.possibleMoves();
				if (matx[kingPosition.getRow()][kingPosition.getColumn()] == 2
						|| matx[kingPosition.getRow()][kingPosition.getColumn()] == 1)
					return true;
			}
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
		placeNewPiece('b', 8, new Knight(this.board, Color.BLACK));
		placeNewPiece('c', 8, new Bishop(this.board, Color.BLACK));
		placeNewPiece('d', 8, new Queen(this.board, Color.BLACK));
		placeNewPiece('e', 8, new King(this.board, Color.BLACK, this));
		placeNewPiece('f', 8, new Bishop(this.board, Color.BLACK));
		placeNewPiece('g', 8, new Knight(this.board, Color.BLACK));
		placeNewPiece('h', 8, new Rook(this.board, Color.BLACK));
		placeNewPiece('a', 7, new Pawn(this.board, Color.BLACK, this));
		placeNewPiece('b', 7, new Pawn(this.board, Color.BLACK, this));
		placeNewPiece('c', 7, new Pawn(this.board, Color.BLACK, this));
		placeNewPiece('d', 7, new Pawn(this.board, Color.BLACK, this));
		placeNewPiece('e', 7, new Pawn(this.board, Color.BLACK, this));
		placeNewPiece('f', 7, new Pawn(this.board, Color.BLACK, this));
		placeNewPiece('g', 7, new Pawn(this.board, Color.BLACK, this));
		placeNewPiece('h', 7, new Pawn(this.board, Color.BLACK, this));

		placeNewPiece('a', 2, new Pawn(this.board, Color.WHITE, this));
		placeNewPiece('b', 2, new Pawn(this.board, Color.WHITE, this));
		placeNewPiece('c', 2, new Pawn(this.board, Color.WHITE, this));
		placeNewPiece('d', 2, new Pawn(this.board, Color.WHITE, this));
		placeNewPiece('e', 2, new Pawn(this.board, Color.WHITE, this));
		placeNewPiece('f', 2, new Pawn(this.board, Color.WHITE, this));
		placeNewPiece('g', 2, new Pawn(this.board, Color.WHITE, this));
		placeNewPiece('h', 2, new Pawn(this.board, Color.WHITE, this));
		placeNewPiece('a', 1, new Rook(this.board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(this.board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(this.board, Color.WHITE));
		placeNewPiece('d', 1, new Queen(this.board, Color.WHITE));
		placeNewPiece('e', 1, new King(this.board, Color.WHITE, this));
		placeNewPiece('f', 1, new Bishop(this.board, Color.WHITE));
		placeNewPiece('g', 1, new Knight(this.board, Color.WHITE));
		placeNewPiece('h', 1, new Rook(this.board, Color.WHITE));
	}
}
