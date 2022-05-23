package boardGame;

public class BoardException extends RuntimeException {


	private static final long serialVersionUID = 1L;
	
	/*
	 * fará mátodo apenas para passar mensagem para a superclasse RuntimeException
	 */
	
	public BoardException(String msg) {
		super(msg);
	}

}
