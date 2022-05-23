package boardGame;

public class Board {


	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int rows, int columns) {
		if (rows < 1 || columns < 1) { //lançou a exceção criada para sempre ter pelo menos 1 linha e 1 coluna
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}
	
	
	// vai retirar setRows e setColumns, para impedir que sejam alterados esses dados
	
	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
	
	public Piece piece(int row, int column) {
		if (!positionExists(row, column)) { //exceção para se a posição não existir
			throw new BoardException("Position not on the board");
		}
		return pieces[row][column];
	}
	
	public Piece piece(Position position) {
		if (!positionExists(position)) { //exceção para se a posição não existir
			throw new BoardException("Position not on the board");
		}	
		return pieces[position.getRow()][position.getColumn()];
	}
	
	/*
	 * Fará o método responsável por atribuir (colocar) uma peça na posição do tabuleiro na matriz de peça
	 * pegará a matriz pieces na posição dada, linha e coluna, e atribuiu a ela a peça piece informada
	 * Por último, falara que a peça não estará mais na posição nula, mas sim receberá a nova posição position informada
	 */
	 
	public void placePiece(Piece piece, Position position) { 
		if (thereIsAPiece(position)) { //exceção para se já tiver uma peça no lugar quando for colocar outra
			throw new BoardException("There is already a piece on position" + position);
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	
	// fará o método para remover as peças e informar que removeu, lançando exceção caso posição não exista
	
	public Piece removePiece(Position position) { // é um método public com o nome removePiece, retornando uma peça que receberá uma posição como argumento
		if (!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		if (piece(position) == null) { // se a peça do tabuleiro nessa posição é igual a nulo
			return null;
		}
		Piece aux = piece(position);
		aux.position = null; // falou que a posição da peça aux é nula, ou seja, foi retirada do tabuleiro
		pieces[position.getRow()][position.getColumn()] = null; // falou que a matriz na posição de onde revomeu a peça é nula
		return aux; // retornou a variável que contem a peça removida
	}
	
	/*
	 * Fará método para confirmar que a posição existe, se está dentro do tabuleiro
	 * A linha tem que ser maior ou igual a 0 e menor que rows, que é a quantidade de linhas do tabuleiro
	 * Além disso a coluna tem que ser maior ou igual a 0 e menor que columns, que é a quantidade de colunas
	 */
	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}

	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	/*
	 * Fará método para confirmar se existe uma peça em uma posição
	 * A peça que estará na matriz em uma posição tem que ser diferente de nulo
	 * Usará o método piece(position) feito acima, que retornará a peça que está na matriz na posição dada
	 */
	
	public boolean thereIsAPiece(Position position) {
		if (!positionExists(position)) { //exceção para testar se a posição existe antes de testar se há uma peça 
			throw new BoardException("Position not on the board");
		}
		return piece(position) != null;
	}
	
	
	               
}
