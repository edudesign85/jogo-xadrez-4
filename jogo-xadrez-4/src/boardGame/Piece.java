package boardGame;

public abstract class Piece {
	
	protected Position position;
	private Board board;
	
	/*
	 * passar� apenas o tabuleiro, pois a posi��o inicialmente � nula
	 * n�o era necess�rio declarar, pois Java j� colocaria como nulo, pois n�o estava no construtor
	 * Colocar� o null para destacar
	 * tirar� o set para n�o permitir que o tabuleiro seja alterado facilmente e evite erros de programadores
	 * deixar� protected, pois somente classes no mesmo pacote e subclasses poder�o acessar o tabuleiro de uma pe�a
	 */
	
	public Piece(Board board) { 
		this.board = board;
		position = null;   
	}
	protected Board getBoard() {
		return board;
	}
	
	// IMPORTANTE!!! Bom exemplo abaixo de integra��o entre classe concreta e abstrata
	public abstract boolean[][] possibleMoves();

	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}
	

	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves();
		for (int i=0; i<mat.length; i++) {
			for (int j=0; j<mat.length; j++) {
				if (mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}
