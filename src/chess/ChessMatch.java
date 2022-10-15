package chess;

import boardlayer.Board;
import boardlayer.Piece;
import boardlayer.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	public final int DEFAULT_ROW_COLLUMN = 8;

	private int turn = 1;
	private Color currentPlayer = Color.WHITE;
	private Board board;

	public ChessMatch() {
		board = new Board(DEFAULT_ROW_COLLUMN, DEFAULT_ROW_COLLUMN);
		initialSetup();
	}

	public ChessMatch(int row, int collumn) {
		board = new Board(row, collumn);
	}

	public int getTurn() {
		return this.turn;
	}

	public Color getCurrentPlayer() {
		return this.currentPlayer;
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
		Piece selectedPiece = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(selectedPiece, target);
		return capturedPiece;
	}

	private void nextTurn() {
		turn++;
		this.currentPlayer = (this.currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
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
