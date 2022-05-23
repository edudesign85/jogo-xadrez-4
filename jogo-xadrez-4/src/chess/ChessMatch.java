package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardGame.Board;
import boardGame.Piece;
import boardGame.Position;
import chess.pieces.Peao;
import chess.pieces.Rei;
import chess.pieces.Torre;


/*
 * Classe cora��o do jogo de xadrez
 * Aqui estar�o todas as regras e dados do jogo, como a dimens�o do tabuleiro do Board
 */

public class ChessMatch {
	
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;

	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	public ChessMatch () {
		board = new Board(8, 8); // especificou a dimens�o do tabuleiro
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}

	/* far� um m�todo para mostrar as camadas de xadrez, com as pe�as e tabuleiro, e n�o somente as partes do tabuleiro
	* far� um downcasting da classe board com o (ChessPiece) para indicar que � pe�a de xadrez e n�o comum
	* percorrer� a matriz de pe�as de board e far� donwcasting para a matriz mat
	* pegar� as informa��es do board para essa camada final de ChessPiece
	* retornar� mat, a matriz de pe�as da partida de xadrez
	*/
	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()]; // aqui pegou dados de board
		for (int i=0; i<board.getRows(); i++) {
			for (int j=0; j<board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j); // aqui fez o downcasting com (ChessPiece)
			}
		}
		return mat;
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
	// implementar� m�todo para mover pe�a de um ponto a outro, de um ponto de origem para um ponto de destino
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition(); // convertendo as duas posi��es para posi��o de matriz
		Position target = targetPosition.toPosition();
		validateSourcePosition(source); // validar� se existe a posi��o de origem, com m�todo criado mais abaixo
		validateTargetPosition(source, target); // validar� a posi��o de destino, com m�todo criado mais abaixo
		Piece capturedPiece = makeMove(source, target); // vari�vel receber� resultado do makeMove, m�todo abaixo para mover as pe�as j� na posi��o do formato de matriz
		
		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check");
		}

		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		if (testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		}
		else {
			nextTurn();
		}
		
		nextTurn();
		return (ChessPiece)capturedPiece; // fez downcast da vari�vel, pois era do tipo Piece, superclasse de ChessPiece
	}
	
	/*
	 * Quando mover uma pe�a vai verificar se esse movimento causou uma captura de pe�as
	 * Se capturou, retira do tabuleiro e coloca na lista de pe�as capturadas
	 */
	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece)board.removePiece(source); //retirou a pe�a da posi��o de origem
		p.increaseMoveCount(); // aumentar a contagem dos movimentos
		Piece capturedPiece = board.removePiece(target); // removeu a poss�vel pe�a da posi��o de destino e ser� a pe�a capturada
		board.placePiece(p, target); // colocou a pe�a p retirada na posi��o de destino
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece); // retirou a pe�a da lista de pe�as do tabuleiro
			capturedPieces.add(capturedPiece); // colocou na lista de pe�as capturadas
		}
		
		return capturedPiece;
	}
	
	// opera��o para desfazer movimento caso pessoa se ponha em cheque, risco de cheque mate
	private void undoMove(Position source, Position target, Piece capturedPiece) { // recebe tamb�m poss�vel pe�a capturada
		ChessPiece p = (ChessPiece)board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);

		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
	}
	
	
	/*
	 * O segundo if do m�todo acessar� o tabuleiro. A partir dele acessar� a pe�a na posi��o de origem
	 * A partir dessa pe�a chamar� a opera��o isThereAnyPossibleMove()
	 * E testar� se n�o existe nenhum movimento poss�vel para essa pe�ar e lan�ar� exce��o
	 */
	
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
			throw new ChessException("The chosen piece is not yours");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	

	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move to target position");
		}
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private ChessPiece rei(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof Rei) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board");
	}

	private boolean testCheck(Color color) {
		Position reiPosition = rei(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[reiPosition.getRow()][reiPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) {
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			boolean[][] mat = p.possibleMoves();
			for (int i=0; i<board.getRows(); i++) {
				for (int j=0; j<board.getColumns(); j++) {
					if (mat[i][j]) {
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	/*
	 * Far� m�todo respons�vel por iniciar a partida de xadrez colocando as pe�as no tabuleiro e na lista de pe�as do tabuleiro
	 * Usar� m�todo para converter da posi��o de matriz para o sistema de coordenadas do xadrez
	 */
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}
	
	private void initialSetup() {
		placeNewPiece('a', 1, new Torre(board, Color.WHITE));
        placeNewPiece('e', 1, new Rei(board, Color.WHITE));
        placeNewPiece('h', 1, new Torre(board, Color.WHITE));
        placeNewPiece('a', 2, new Peao(board, Color.WHITE));
        placeNewPiece('b', 2, new Peao(board, Color.WHITE));
        placeNewPiece('c', 2, new Peao(board, Color.WHITE));
        placeNewPiece('d', 2, new Peao(board, Color.WHITE));
        placeNewPiece('e', 2, new Peao(board, Color.WHITE));
        placeNewPiece('f', 2, new Peao(board, Color.WHITE));
        placeNewPiece('g', 2, new Peao(board, Color.WHITE));
        placeNewPiece('h', 2, new Peao(board, Color.WHITE));
        
        placeNewPiece('a', 8, new Torre(board, Color.BLACK));
        placeNewPiece('e', 8, new Rei(board, Color.BLACK));
        placeNewPiece('h', 8, new Torre(board, Color.BLACK));
        placeNewPiece('a', 7, new Peao(board, Color.BLACK));
        placeNewPiece('b', 7, new Peao(board, Color.BLACK));
        placeNewPiece('c', 7, new Peao(board, Color.BLACK));
        placeNewPiece('d', 7, new Peao(board, Color.BLACK));
        placeNewPiece('e', 7, new Peao(board, Color.BLACK));
        placeNewPiece('f', 7, new Peao(board, Color.BLACK));
        placeNewPiece('g', 7, new Peao(board, Color.BLACK));
        placeNewPiece('h', 7, new Peao(board, Color.BLACK));

      
	}
}
