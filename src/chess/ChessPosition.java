package chess;

import boardlayer.Position;

public class ChessPosition {
	private char column;
	private int row;
	private int max_rows;
	private int max_columns;

	public ChessPosition(char column, int row, int max_rows, int max_columns) {
		if (column < 'a' || column > 97 + max_rows - 1 || row < 1 || row > max_columns)
			throw new ChessException("Error! Could not initialize Chess Position. Valid values are a1 to "+(char)(97 + max_rows - 1)+max_rows);
		this.column = column;
		this.row = row;
		this.max_rows = max_rows;
		this.max_columns = max_columns;
	}

	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	public int getMAX_ROWS() {
		return max_rows;
	}

	public int getMAX_COLUMNS() {
		return max_columns;
	}

	protected Position toPosition() {
		return new Position(this.max_rows - row, column - 'a');
	}

	protected static ChessPosition fromPosition(Position position, int max_rows, int max_columns) {
		return new ChessPosition((char) ('a' - position.getColumn()), max_rows - position.getRow(), max_rows,
				max_columns);
	}
	
	@Override
	public String toString() {
		return ""+column+row;
	}
}
