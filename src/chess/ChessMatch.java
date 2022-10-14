package chess;

import boardlayer.Board;
import boardlayer.Piece;
import boardlayer.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	public final int DEFAULT_ROW_COLLUMN = 8;
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

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		return (ChessPiece) capturedPiece;
	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position))
			throw new ChessException("There is no piece in source position!");
		if (!board.piece(position).isThereAnyPossibleMove())
			throw new ChessException("There is no possibles moves for this selected piece");

	}

	private void validateTargetPosition(Position source, Position target) {
		if (!this.board.piece(source).possibleMove(target))
			throw new ChessException("Selected piece can't be moved to this target position");
	}

	private Piece makeMove(Position source, Position target) {
		Piece selectedPiece = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(selectedPiece, target);
		return capturedPiece;
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row, board.getRows(), board.getColumns()).toPosition());
	}

	private void initialSetup() {
		placeNewPiece('a', 8, new Rook(this.board, Color.BLACK));
		placeNewPiece('e', 8, new King(this.board, Color.BLACK));
		placeNewPiece('h', 8, new Rook(this.board, Color.BLACK));
		placeNewPiece('a', 1, new Rook(this.board, Color.WHITE));
		placeNewPiece('e', 1, new King(this.board, Color.WHITE));
		placeNewPiece('h', 1, new Rook(this.board, Color.WHITE));
	}
}
