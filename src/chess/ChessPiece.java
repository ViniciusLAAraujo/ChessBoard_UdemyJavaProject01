package chess;

import boardlayer.Board;
import boardlayer.Piece;
import boardlayer.Position;

public abstract class ChessPiece extends Piece {
	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(this.position, getBoard().getRows(), getBoard().getColumns());
	}

	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece tmpPiece = (ChessPiece) getBoard().piece(position);
		return tmpPiece != null && tmpPiece.getColor() != this.color;
	}
}
