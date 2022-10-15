package chess.pieces;

import boardlayer.Board;
import boardlayer.Position;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece {

	public Queen(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "Q";
	}

	@Override
	public int[][] possibleMoves() {
		int[][] matx = new int[getBoard().getRows()][getBoard().getColumns()];

		Position pos = new Position(0, 0);

		// Above
		pos.setValues(this.position.getRow() - 1, this.position.getColumn());
		while (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
			matx[pos.getRow()][pos.getColumn()] = 1;
			pos.setValues(pos.getRow() - 1, pos.getColumn());
		}
		if (getBoard().positionExists(pos) && isThereOpponentPiece(pos))
			matx[pos.getRow()][pos.getColumn()] = 2;
		// Below
		pos.setValues(this.position.getRow() + 1, this.position.getColumn());
		while (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
			matx[pos.getRow()][pos.getColumn()] = 1;
			pos.setValues(pos.getRow() + 1, pos.getColumn());
		}
		if (getBoard().positionExists(pos) && isThereOpponentPiece(pos))
			matx[pos.getRow()][pos.getColumn()] = 2;
		// Right
		pos.setValues(this.position.getRow(), this.position.getColumn() + 1);
		while (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
			matx[pos.getRow()][pos.getColumn()] = 1;
			pos.setValues(pos.getRow(), pos.getColumn() + 1);
		}
		if (getBoard().positionExists(pos) && isThereOpponentPiece(pos))
			matx[pos.getRow()][pos.getColumn()] = 2;
		// Left
		pos.setValues(this.position.getRow(), this.position.getColumn() - 1);
		while (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
			matx[pos.getRow()][pos.getColumn()] = 1;
			pos.setValues(pos.getRow(), pos.getColumn() - 1);
		}
		if (getBoard().positionExists(pos) && isThereOpponentPiece(pos))
			matx[pos.getRow()][pos.getColumn()] = 2;
		// Upper Left (NW)
		pos.setValues(this.position.getRow() - 1, this.position.getColumn() - 1);
		while (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
			matx[pos.getRow()][pos.getColumn()] = 1;
			pos.setValues(pos.getRow() - 1, pos.getColumn() - 1);
		}
		if (getBoard().positionExists(pos) && isThereOpponentPiece(pos))
			matx[pos.getRow()][pos.getColumn()] = 2;
		// Upper Right (NE)
		pos.setValues(this.position.getRow() - 1, this.position.getColumn() + 1);
		while (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
			matx[pos.getRow()][pos.getColumn()] = 1;
			pos.setValues(pos.getRow() - 1, pos.getColumn() + 1);
		}
		if (getBoard().positionExists(pos) && isThereOpponentPiece(pos))
			matx[pos.getRow()][pos.getColumn()] = 2;
		// Lower Left (SW)
		pos.setValues(this.position.getRow() + 1, this.position.getColumn() - 1);
		while (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
			matx[pos.getRow()][pos.getColumn()] = 1;
			pos.setValues(pos.getRow() + 1, pos.getColumn() - 1);
		}
		if (getBoard().positionExists(pos) && isThereOpponentPiece(pos))
			matx[pos.getRow()][pos.getColumn()] = 2;
		// Lower Right (SE)
		pos.setValues(this.position.getRow() + 1, this.position.getColumn() + 1);
		while (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
			matx[pos.getRow()][pos.getColumn()] = 1;
			pos.setValues(pos.getRow() + 1, pos.getColumn() + 1);
		}
		if (getBoard().positionExists(pos) && isThereOpponentPiece(pos))
			matx[pos.getRow()][pos.getColumn()] = 2;

		return matx;
	}
}
