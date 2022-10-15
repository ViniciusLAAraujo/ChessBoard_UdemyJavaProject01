package chess.pieces;

import boardlayer.Board;
import boardlayer.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	public King(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}

	@Override
	public int[][] possibleMoves() {
		int[][] matx = new int[getBoard().getRows()][getBoard().getColumns()];
		Position pos = new Position(0, 0);
		// Above
		pos.setValues(this.position.getRow() - 1, this.position.getColumn());
		if (getBoard().positionExists(pos) && canMove(pos)) 
			matx[pos.getRow()][pos.getColumn()] = (!isThereOpponentPiece(pos)) ? 1 : 2;
		// Below
		pos.setValues(this.position.getRow() + 1, this.position.getColumn());
		if (getBoard().positionExists(pos) && canMove(pos)) 
			matx[pos.getRow()][pos.getColumn()] = (!isThereOpponentPiece(pos)) ? 1 : 2;
		// Right
		pos.setValues(this.position.getRow(), this.position.getColumn() + 1);
		if (getBoard().positionExists(pos) && canMove(pos))
			matx[pos.getRow()][pos.getColumn()] = (!isThereOpponentPiece(pos)) ? 1 : 2;
		// Left
		pos.setValues(this.position.getRow(), this.position.getColumn() - 1);
		if (getBoard().positionExists(pos) && canMove(pos)) 
			matx[pos.getRow()][pos.getColumn()] = (!isThereOpponentPiece(pos)) ? 1 : 2;
		// Upper Left (NW)
		pos.setValues(this.position.getRow() - 1, this.position.getColumn() - 1);
		if (getBoard().positionExists(pos) && canMove(pos)) 
			matx[pos.getRow()][pos.getColumn()] = (!isThereOpponentPiece(pos)) ? 1 : 2;
		// Upper Right (NE)
		pos.setValues(this.position.getRow() - 1, this.position.getColumn() + 1);
		if (getBoard().positionExists(pos) && canMove(pos)) 
			matx[pos.getRow()][pos.getColumn()] = (!isThereOpponentPiece(pos)) ? 1 : 2;
		// Lower Left (SW)
		pos.setValues(this.position.getRow() + 1, this.position.getColumn() - 1);
		if (getBoard().positionExists(pos) && canMove(pos)) 
			matx[pos.getRow()][pos.getColumn()] = (!isThereOpponentPiece(pos)) ? 1 : 2;
		// Lower Right (SE)
		pos.setValues(this.position.getRow() + 1, this.position.getColumn() + 1);
		if (getBoard().positionExists(pos) && canMove(pos)) 
			matx[pos.getRow()][pos.getColumn()] = (!isThereOpponentPiece(pos)) ? 1 : 2;
		return matx;
	}

}
