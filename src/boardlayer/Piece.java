package boardlayer;

public abstract class Piece {
	protected Position position;
	private Board board;

	public Piece(Board board) {
		this.board = board;
		position = null;
	}

	protected Board getBoard() {
		return board;
	}

	public abstract boolean[][] possibleMoves();

	// Hook methods . Concrete that uses an abstract
	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}

	public boolean isThereAnyPossibleMove() {
		boolean[][] matx = possibleMoves();
		for (int i = 0; i < matx.length; i++) {
			for (int j = 0; j < matx[0].length; j++) {
				if (matx[i][j]) 
					return true;
			}
		}
		return false;
	}
}
