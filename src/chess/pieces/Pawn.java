package chess.pieces;

import boardlayer.Board;
import boardlayer.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	public Pawn(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "p";
	}

	@Override
	public int[][] possibleMoves() {
		int[][] matx = new int[getBoard().getRows()][getBoard().getColumns()];

		Position pos = new Position(0, 0);

		if (this.getColor() == Color.WHITE) {
			pos.setValues(position.getRow() - 1, position.getColumn());
			if (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos))
				matx[pos.getRow()][pos.getColumn()] = 1;

			pos.setValues(position.getRow() - 2, position.getColumn());
			Position tmpPos = new Position(position.getRow() - 1, position.getColumn());
			if (getBoard().positionExists(pos) && getBoard().positionExists(tmpPos) && !getBoard().thereIsAPiece(pos)
					&& !getBoard().thereIsAPiece(tmpPos) && this.getMoveCount() == 0)
				matx[pos.getRow()][pos.getColumn()] = 1;

			pos.setValues(position.getRow() - 1, position.getColumn() - 1);
			if (getBoard().positionExists(pos) && isThereOpponentPiece(pos))
				matx[pos.getRow()][pos.getColumn()] = 2;

			pos.setValues(position.getRow() - 1, position.getColumn() + 1);
			if (getBoard().positionExists(pos) && isThereOpponentPiece(pos))
				matx[pos.getRow()][pos.getColumn()] = 2;
		} else {
			pos.setValues(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos))
				matx[pos.getRow()][pos.getColumn()] = 1;

			pos.setValues(position.getRow() + 2, position.getColumn());
			Position tmpPos = new Position(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(pos) && getBoard().positionExists(tmpPos) && !getBoard().thereIsAPiece(pos)
					&& !getBoard().thereIsAPiece(tmpPos) && this.getMoveCount() == 0)
				matx[pos.getRow()][pos.getColumn()] = 1;

			pos.setValues(position.getRow() + 1, position.getColumn() + 1);
			if (getBoard().positionExists(pos) && isThereOpponentPiece(pos))
				matx[pos.getRow()][pos.getColumn()] = 2;

			pos.setValues(position.getRow() + 1, position.getColumn() - 1);
			if (getBoard().positionExists(pos) && isThereOpponentPiece(pos))
				matx[pos.getRow()][pos.getColumn()] = 2;
		}
		return matx;
	}

}
