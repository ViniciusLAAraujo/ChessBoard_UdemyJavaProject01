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

	public abstract int[][] possibleMoves();

	// Hook methods . Concrete that uses an abstract
	public int possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}

	public boolean isThereAnyPossibleMove() {
		int[][] matx = possibleMoves();
		for (int i = 0; i < matx.length; i++) {
			for (int j = 0; j < matx[0].length; j++) {
				if (matx[i][j]>0) 
					return true;
			}
		}
		return false;
	}
}
