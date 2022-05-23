package boardGame;

public abstract class Piece {
	
	protected Position position;
	private Board board;
	
	/*
	 * passará apenas o tabuleiro, pois a posição inicialmente é nula
	 * não era necessário declarar, pois Java já colocaria como nulo, pois não estava no construtor
	 * Colocará o null para destacar
	 * tirará o set para não permitir que o tabuleiro seja alterado facilmente e evite erros de programadores
	 * deixará protected, pois somente classes no mesmo pacote e subclasses poderão acessar o tabuleiro de uma peça
	 */
	
	public Piece(Board board) { 
		this.board = board;
		position = null;   
	}
	protected Board getBoard() {
		return board;
	}
	
	// IMPORTANTE!!! Bom exemplo abaixo de integração entre classe concreta e abstrata
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
