package chess.pieces;

import boardlayer.Board;
import boardlayer.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

	public Knight(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "N";
	}

	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}

	@Override
	public int[][] possibleMoves() {
		int[][] matx = new int[getBoard().getRows()][getBoard().getColumns()];
		Position pos = new Position(0, 0);
		// Up Left
		pos.setValues(this.position.getRow() - 2, this.position.getColumn() - 1);
		if (getBoard().positionExists(pos) && canMove(pos))
			matx[pos.getRow()][pos.getColumn()] = (!isThereOpponentPiece(pos)) ? 1 : 2;
		// Up Right
		pos.setValues(this.position.getRow() - 2, this.position.getColumn() + 1);
		if (getBoard().positionExists(pos) && canMove(pos))
			matx[pos.getRow()][pos.getColumn()] = (!isThereOpponentPiece(pos)) ? 1 : 2;
		// Right Up
		pos.setValues(this.position.getRow() - 1, this.position.getColumn() + 2);
		if (getBoard().positionExists(pos) && canMove(pos))
			matx[pos.getRow()][pos.getColumn()] = (!isThereOpponentPiece(pos)) ? 1 : 2;
		// Right Down
		pos.setValues(this.position.getRow() + 1, this.position.getColumn() + 2);
		if (getBoard().positionExists(pos) && canMove(pos))
			matx[pos.getRow()][pos.getColumn()] = (!isThereOpponentPiece(pos)) ? 1 : 2;
		// Down Left
		pos.setValues(this.position.getRow() + 2, this.position.getColumn() + 1);
		if (getBoard().positionExists(pos) && canMove(pos))
			matx[pos.getRow()][pos.getColumn()] = (!isThereOpponentPiece(pos)) ? 1 : 2;
		// Down Right
		pos.setValues(this.position.getRow() + 2, this.position.getColumn() - 1);
		if (getBoard().positionExists(pos) && canMove(pos))
			matx[pos.getRow()][pos.getColumn()] = (!isThereOpponentPiece(pos)) ? 1 : 2;
		// Left Down
		pos.setValues(this.position.getRow() + 1, this.position.getColumn() - 2);
		if (getBoard().positionExists(pos) && canMove(pos))
			matx[pos.getRow()][pos.getColumn()] = (!isThereOpponentPiece(pos)) ? 1 : 2;
		// Left Up
		pos.setValues(this.position.getRow() - 1, this.position.getColumn() - 2);
		if (getBoard().positionExists(pos) && canMove(pos))
			matx[pos.getRow()][pos.getColumn()] = (!isThereOpponentPiece(pos)) ? 1 : 2;
		return matx;
	}
}
