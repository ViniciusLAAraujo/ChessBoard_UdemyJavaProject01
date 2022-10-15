package chess.pieces;

import boardlayer.Board;
import boardlayer.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	private ChessMatch chessMatch;

	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
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
			// En Passant White
			if (this.position.getRow() == 3) {
				Position left = new Position(this.position.getRow(), this.position.getColumn() - 1);
				if (getBoard().positionExists(left) && isThereOpponentPiece(left)
						&& getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					matx[left.getRow() - 1][left.getColumn()] = 2;
				}
				Position right = new Position(this.position.getRow(), this.position.getColumn() + 1);
				if (getBoard().positionExists(right) && isThereOpponentPiece(right)
						&& getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					matx[right.getRow() - 1][right.getColumn()] = 2;
				}

			}

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

			// En Passant Black
			if (this.position.getRow() == 4) {
				Position left = new Position(this.position.getRow(), this.position.getColumn() - 1);
				if (getBoard().positionExists(left) && isThereOpponentPiece(left)
						&& getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					matx[left.getRow() + 1][left.getColumn()] = 2;
				}

				Position right = new Position(this.position.getRow(), this.position.getColumn() + 1);
				if (getBoard().positionExists(right) && isThereOpponentPiece(right)
						&& getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					matx[right.getRow() + 1][right.getColumn()] = 2;
				}

			}
		}
		return matx;
	}

}
