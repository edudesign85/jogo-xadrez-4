package boardGame;

public class BoardException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/*
	 * far� m�todo apenas para passar mensagem para a superclasse RuntimeException
	 */
	
	public BoardException(String msg) {
		super(msg);
	}

}
