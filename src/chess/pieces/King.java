package chess.pieces;

import boardlayer.Board;
import boardlayer.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	private ChessMatch chessMatch;

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}

	private boolean canRookCastling(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p != null && p instanceof Rook && p.getColor() == this.getColor() && p.getMoveCount() == 0;
	}

	private boolean canPassCastling(Position position) {
		return getBoard().piece(position) == null && !chessMatch.testCheckCasteling(this.getColor(), position);
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
		// Castling
		if (getMoveCount() == 0 && !chessMatch.isCheck()) {
			// kingside
			Position posR1 = new Position(position.getRow(), position.getColumn() + 3);
			if (getBoard().positionExists(posR1) && canRookCastling(posR1)) {
				Position p1 = new Position(position.getRow(), position.getColumn() + 1);
				Position p2 = new Position(position.getRow(), position.getColumn() + 2);
				if (getBoard().positionExists(p1) && getBoard().positionExists(p2) && canPassCastling(p1)
						&& canPassCastling(p2)) {
					matx[position.getRow()][position.getColumn() + 2] = 1;
				}
			}

			// queenside
			Position posR2 = new Position(position.getRow(), position.getColumn() - 4);
			if (getBoard().positionExists(posR2) && canRookCastling(posR2)) {
				Position p1 = new Position(position.getRow(), position.getColumn() - 1);
				Position p2 = new Position(position.getRow(), position.getColumn() - 2);
				Position p3 = new Position(position.getRow(), position.getColumn() - 3);
				if (getBoard().positionExists(p1) && getBoard().positionExists(p2) && getBoard().positionExists(p3)
						&& getBoard().piece(p3) == null && canPassCastling(p1) && canPassCastling(p2)) {
					matx[position.getRow()][position.getColumn() - 2] = 1;
				}
			}

		}
		return matx;
	}

}
