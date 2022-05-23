package chess;

import boardGame.Position;

/*
 * Fará as conversões entre a posição (sistema de coordenadas) do xadrez e da posição comum por matriz 
 * Também terá programação defensiva com a exceção que não será aceita
 */

public class ChessPosition {
	
	private char column;
	private int row;
	
	public ChessPosition(char column, int row) {
		if (column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException("Error instantianting ChessPosition. Valid values are from a1 to h8.");
		}
		this.column = column;
		this.row = row;
	}

	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}
	
	// Fará os método para converter entre o sistema de posição do xadrez e a posição normal por matriz
	
	protected Position toPosition() {
		return new Position(8 - row, column - 'a');
	}
	
	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition((char)('a' - position.getColumn()), 8 - position.getRow());
	}
	
	@Override
	public String toString() {
		return "" + column + row; // botou "" na frente para que fosse possível concatenar column e row
	}
}
