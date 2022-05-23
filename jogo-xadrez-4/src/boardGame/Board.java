package boardGame;

public class Board {
	
	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int rows, int columns) {
		if (rows < 1 || columns < 1) { //lan�ou a exce��o criada para sempre ter pelo menos 1 linha e 1 coluna
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
		if (!positionExists(row, column)) { //exce��o para se a posi��o n�o existir
			throw new BoardException("Position not on the board");
		}
		return pieces[row][column];
	}
	
	public Piece piece(Position position) {
		if (!positionExists(position)) { //exce��o para se a posi��o n�o existir
			throw new BoardException("Position not on the board");
		}	
		return pieces[position.getRow()][position.getColumn()];
	}
	
	/*
	 * Far� o m�todo respons�vel por atribuir (colocar) uma pe�a na posi��o do tabuleiro na matriz de pe�a
	 * pegar� a matriz pieces na posi��o dada, linha e coluna, e atribuiu a ela a pe�a piece informada
	 * Por �ltimo, falara que a pe�a n�o estar� mais na posi��o nula, mas sim receber� a nova posi��o position informada
	 */
	 
	public void placePiece(Piece piece, Position position) { 
		if (thereIsAPiece(position)) { //exce��o para se j� tiver uma pe�a no lugar quando for colocar outra
			throw new BoardException("There is already a piece on position" + position);
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	
	// far� o m�todo para remover as pe�as e informar que removeu, lan�ando exce��o caso posi��o n�o exista
	
	public Piece removePiece(Position position) { // � um m�todo public com o nome removePiece, retornando uma pe�a que receber� uma posi��o como argumento
		if (!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		if (piece(position) == null) { // se a pe�a do tabuleiro nessa posi��o � igual a nulo
			return null;
		}
		Piece aux = piece(position);
		aux.position = null; // falou que a posi��o da pe�a aux � nula, ou seja, foi retirada do tabuleiro
		pieces[position.getRow()][position.getColumn()] = null; // falou que a matriz na posi��o de onde revomeu a pe�a � nula
		return aux; // retornou a vari�vel que contem a pe�a removida
	}
	
	/*
	 * Far� m�todo para confirmar que a posi��o existe, se est� dentro do tabuleiro
	 * A linha tem que ser maior ou igual a 0 e menor que rows, que � a quantidade de linhas do tabuleiro
	 * Al�m disso a coluna tem que ser maior ou igual a 0 e menor que columns, que � a quantidade de colunas
	 */
	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}

	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	/*
	 * Far� m�todo para confirmar se existe uma pe�a em uma posi��o
	 * A pe�a que estar� na matriz em uma posi��o tem que ser diferente de nulo
	 * Usar� o m�todo piece(position) feito acima, que retornar� a pe�a que est� na matriz na posi��o dada
	 */
	
	public boolean thereIsAPiece(Position position) {
		if (!positionExists(position)) { //exce��o para testar se a posi��o existe antes de testar se h� uma pe�a 
			throw new BoardException("Position not on the board");
		}
		return piece(position) != null;
	}
	
	
	               
}
