package chess;

import boardGame.Position;

/*
 * Far� as convers�es entre a posi��o (sistema de coordenadas) do xadrez e da posi��o comum por matriz 
 * Tamb�m ter� programa��o defensiva com a exce��o que n�o ser� aceita
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
	
	// Far� os m�todo para converter entre o sistema de posi��o do xadrez e a posi��o normal por matriz
	
	protected Position toPosition() {
		return new Position(8 - row, column - 'a');
	}
	
	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition((char)('a' - position.getColumn()), 8 - position.getRow());
	}
	
	@Override
	public String toString() {
		return "" + column + row; // botou "" na frente para que fosse poss�vel concatenar column e row
	}
}
