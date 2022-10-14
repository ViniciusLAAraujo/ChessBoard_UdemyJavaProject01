package boardlayer;

public class Board {
	private int rows;
	private int columns;
	private Piece[][] pieces;

	public Board(int rows, int columns) {
		if (rows < 1 || columns < 1)
			throw new BoardException("Error! board can't be created unless number of rows and columns are >= 1");
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Piece piece(int row, int column) {
		if (!positionExists(row, column))
			throw new BoardException("Invalid position!");
		return this.pieces[row][column];
	}

	public Piece piece(Position position) {
		if (!positionExists(position))
			throw new BoardException("Invalid position!");
		return this.pieces[position.getRow()][position.getColumn()];
	}

	public void placePiece(Piece piece, Position position) {
		if (thereIsAPiece(position))
			throw new BoardException("There's already a pice on position " + position);
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}

	public Piece removePiece(Position position) {
		if (!positionExists(position))
			throw new BoardException("Invalid position!");
		if(piece(position)==null)
			return null;
		Piece tmp = piece(position);
		tmp.position=null;
		pieces[position.getRow()][position.getColumn()]=null;
		return tmp;
	}

	private boolean positionExists(int row, int column) {
		return row >= 0 && row < this.rows && column >= 0 && column < this.columns;
	}

	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}

	public boolean thereIsAPiece(Position position) {
		if (!positionExists(position))
			throw new BoardException("Invalid position!");
		return this.piece(position) != null;
	}
}
