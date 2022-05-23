package boardGame;

public class Position {
	
	private int row;
	private int column;
	
	public Position(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	// opera��o que atualizar� os valores de uma posi��o
	public void setValues(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	@Override
	public String toString() { // usar� este m�todo para mostrar linha e coluna
		return row + ", " + column;
		
	}
	
	
	

}
