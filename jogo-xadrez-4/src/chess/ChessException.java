package chess;

import boardGame.BoardException;

public class ChessException extends BoardException {

	private static final long serialVersionUID = 1L;
	
	/*
	 * far� m�todo apenas para passar mensagem para a superclasse RuntimeException
	 */
	
	public ChessException(String msg) {
		super(msg);
	}
}


