package ch06.logic;

import java.util.ArrayList;
import java.util.List;

import ch06.logic.Player.PlayerColor;
import ch06.logic.Player.PlayerStatus;

//import ch08.logic.ChessGame;
//import ch08.logic.Move;
//import ch08.logic.Piece;

public class ChessGame implements Runnable {

	private int gameState = GAME_STATE_WHITE;
	
	public static final int GAME_STATE_WHITE = 0;
	public static final int GAME_STATE_BLACK = 1;
	public static final int GAME_STATE_END = 2;
	public static final int GAME_STATE_EDIT_POSITION = 3;
	public static final int GAME_STATE_END_BLACK_WON = 4;
	public static final int GAME_STATE_END_WHITE_WON = 5;	

	public static Player playerWhite;
	public static Player playerBlack;
	public static Player playerToPlay;
	
	private MoveHandler moveHandler;
	private UndoMoveHandler undoMoveHandler;
	
	private List<Move> moveHistory = new ArrayList<Move>();
	private List<Move> moveRedoList = new ArrayList<Move>();
	
	private MoveValidator moveValidator;
	
	private IPlayerHandler blackPlayerHandler;
	private IPlayerHandler whitePlayerHandler;
	private IPlayerHandler activePlayerHandler;
	
	/**
	 * initialize game
	 */
	public ChessGame() {
		
//        playerWhite = new PlayerWhite();
//        playerBlack = new PlayerBlack();
//        playerToPlay = playerWhite;
        
        this.moveValidator = new MoveValidator();
        this.moveHandler = new MoveHandler();
        this.undoMoveHandler = new UndoMoveHandler();
	}

	public void setFenPosition(String fenString) {

		Fen fen = new Fen();
		fen.parse(fenString);
		
		setPiecePlacement(fen.getPiecePlacement(), false, false);
		setActivePlayer(fen.getActivePlayerColor());
		setCastlingRights(fen.getCastlingRights());
		setEnPassant(fen.getEnPassant());
	}
	
    /// <summary>
    /// The set piece placement.
    /// </summary>
    /// <param name="acharPiecePlacement">
    /// The achar piece placement.
    /// </param>
    /// <param name="blnAnyLocation">
    /// The bln any location.
    /// </param>
    /// <param name="blnAllowPromotion">
    /// The bln allow promotion.
    /// </param>
    /// <exception cref="ValidationException">
    /// Unknow character in FEN string.
    /// </exception>
    private void setPiecePlacement(//List<Piece> pieces,
        char[] acharPiecePlacement, boolean blnAnyLocation, boolean blnAllowPromotion)
    {
        // Setup piece placement
    	List<Piece> whitePieces = playerWhite.getPieces();
    	List<Piece> blackPieces = playerBlack.getPieces();

    	whitePieces.clear();
    	blackPieces.clear();
    	
        int rank = Piece.ROW_8;
        int file = Piece.COLUMN_A;
        for (int i = 0; i < acharPiecePlacement.length; i++)
        {
            switch (Character.toString(acharPiecePlacement[i]))
            {
                case ".":

                    // Indicates a processed piece, so move on
                    file++;
                    break;

                case "/":
                    file = 0;
                    rank--;
                    break;

                case Fen.WHITE_KING:
                	//pieces.add(PieceMaker.createPiece(Piece.COLOR_WHITE, Piece.TYPE_KING, rank, file));
                	whitePieces.add(new King(playerWhite, Piece.COLOR_WHITE, Piece.TYPE_KING, rank, file));
                    file++;
                    break;

                case Fen.WHITE_QUEEN:
                	//pieces.add(PieceMaker.createPiece(Piece.COLOR_WHITE, Piece.TYPE_QUEEN, rank, file));
                	whitePieces.add(new Queen(playerWhite, Piece.COLOR_WHITE, Piece.TYPE_QUEEN, rank, file));
                    file++;
                    break;
                	
                case Fen.WHITE_ROOK:
                	//pieces.add(PieceMaker.createPiece(Piece.COLOR_WHITE, Piece.TYPE_ROOK, rank, file));
                	whitePieces.add(new Rook(playerWhite, Piece.COLOR_WHITE, Piece.TYPE_ROOK, rank, file));                	
                    file++;
                    break;
                	
                case Fen.WHITE_BISHOP:
                	//pieces.add(PieceMaker.createPiece(Piece.COLOR_WHITE, Piece.TYPE_BISHOP, rank, file));
                	whitePieces.add(new Bishop(playerWhite, Piece.COLOR_WHITE, Piece.TYPE_BISHOP, rank, file));                	
                    file++;
                    break;
                	
                case Fen.WHITE_KNIGHT:
                	//pieces.add(PieceMaker.createPiece(Piece.COLOR_WHITE, Piece.TYPE_KNIGHT, rank, file));
                	whitePieces.add(new Knight(playerWhite, Piece.COLOR_WHITE, Piece.TYPE_KNIGHT, rank, file));                	
                    file++;
                    break;
                	
                case Fen.WHITE_PAWN:
                	//pieces.add(PieceMaker.createPiece(Piece.COLOR_WHITE, Piece.TYPE_PAWN, rank, file));
                	whitePieces.add(new Pawn(playerWhite, Piece.COLOR_WHITE, Piece.TYPE_PAWN, rank, file));
                    file++;
                    break;
                	
                case Fen.BLACK_KING:
                	//pieces.add(PieceMaker.createPiece(Piece.COLOR_BLACK, Piece.TYPE_KING, rank, file));
                	blackPieces.add(new King(playerBlack, Piece.COLOR_BLACK, Piece.TYPE_KING, rank, file));                	
                    file++;
                    break;
                	
                case Fen.BLACK_QUEEN:
                	//pieces.add(PieceMaker.createPiece(Piece.COLOR_BLACK, Piece.TYPE_QUEEN, rank, file));
                	blackPieces.add(new Queen(playerBlack, Piece.COLOR_BLACK, Piece.TYPE_QUEEN, rank, file));                	
                    file++;
                    break;
                    
                case Fen.BLACK_ROOK:
                	//pieces.add(PieceMaker.createPiece(Piece.COLOR_BLACK, Piece.TYPE_ROOK, rank, file));
                	blackPieces.add(new Rook(playerBlack, Piece.COLOR_BLACK, Piece.TYPE_ROOK, rank, file));
                    file++;
                    break;
                	
                case Fen.BLACK_BISHOP:
                	//pieces.add(PieceMaker.createPiece(Piece.COLOR_BLACK, Piece.TYPE_BISHOP, rank, file));
                	blackPieces.add(new Bishop(playerBlack, Piece.COLOR_BLACK, Piece.TYPE_BISHOP, rank, file));
                    file++;
                    break;
                	
                case Fen.BLACK_KNIGHT:
                	//pieces.add(PieceMaker.createPiece(Piece.COLOR_BLACK, Piece.TYPE_KNIGHT, rank, file));
                	blackPieces.add(new Knight(playerBlack, Piece.COLOR_BLACK, Piece.TYPE_KNIGHT, rank, file));
                    file++;
                    break;
                	
                case Fen.BLACK_PAWN:
                	//pieces.add(PieceMaker.createPiece(Piece.COLOR_BLACK, Piece.TYPE_PAWN, rank, file));
                	blackPieces.add(new Pawn(playerBlack, Piece.COLOR_BLACK, Piece.TYPE_PAWN, rank, file));                	
                    file++;
                    break;
                	
//                    movePieceToFenPosition(
//                        acharPiecePlacement[intIndex], intFile, intRank, blnAnyLocation, blnAllowPromotion);
//                    intFile++;
//                    break;

                default:
                	if (Character.isDigit(acharPiecePlacement[i])) {
                		file += Integer.parseInt(Character.toString(acharPiecePlacement[i]));
                	}
//					todo properly                	
//                	else {
//                		throw new ValidationException(
//                				"Unknow character in FEN string:" + acharPiecePlacement[intIndex]);
//                	}
                    break;
            }
        }
    }
	
    private void setCastlingRights(String castlingRights) {
    	Piece piece;

    	if (castlingRights.equals("-")) {
    		return;
    	}
		// White King's Rook
    	piece = playerWhite.getNonCapturedPieceAtLocation(Piece.ROW_1, Piece.COLUMN_H);
    	if (piece != null && piece instanceof Rook) {
    		Rook rook = (Rook)piece;
    		if (rook.getColor() == Piece.COLOR_WHITE) {
    			rook.setNumberOfMoves(castlingRights.lastIndexOf("K") >= 0 ? 0 : 1);
    		}
    	}

    	// Black King's Rook
		piece = playerBlack.getNonCapturedPieceAtLocation(Piece.ROW_8, Piece.COLUMN_H);
		if (piece != null && piece instanceof Rook) {
			Rook rook = (Rook)piece;
			if (rook.getColor() == Piece.COLOR_BLACK) {
				rook.setNumberOfMoves(castlingRights.lastIndexOf("k") >= 0 ? 0 : 1);
			}
		}

		// White Queen's Rook
		piece = playerWhite.getNonCapturedPieceAtLocation(Piece.ROW_1, Piece.COLUMN_A);
		if (piece != null && piece instanceof Rook) {
			Rook rook = (Rook)piece;
			if (rook.getColor() == Piece.COLOR_WHITE) {
				rook.setNumberOfMoves(castlingRights.lastIndexOf("Q") >= 0 ? 0 : 1);
			}
		}

		// Black Queen's Rook
		piece = playerBlack.getNonCapturedPieceAtLocation(Piece.ROW_8, Piece.COLUMN_A);
		if (piece != null && piece instanceof Rook) {
			Rook rook = (Rook)piece;
			if (rook.getColor() == Piece.COLOR_BLACK) {
				rook.setNumberOfMoves(castlingRights.lastIndexOf("q") >= 0 ? 0 : 1);
			}
		}
    }
    
    private void setEnPassant(String enPassant) {
    	//Piece piece;

    	if (enPassant.equals("-")) {
    		return;
    	}
    	
    	//todo
    	//...
    }
    
//	public ChessGame(String fenString) {
//		this();
//
//		Fen fen = new Fen();
//		fen.setBoardPosition(fenString);
//	}

	public boolean isItYourTurn(int color) {
		if( color == Piece.COLOR_WHITE
			&& this.gameState == ChessGame.GAME_STATE_WHITE){
			return true;
		}else if( color == Piece.COLOR_BLACK
			&& this.gameState == ChessGame.GAME_STATE_BLACK){
			return true;
		}else{
			return false;
		}
	}
	
	public MoveValidator getMoveValidator(){
		return this.moveValidator;
	}
	
    public List<Move> getMoveHistory() {
		return this.moveHistory;
	}	

    public List<Move> getMoveRedoList() {
		return this.moveRedoList;
	}	
    
	public void movePiece(Move move) {
		this.moveHandler.movePiece(move);
		this.moveHistory.add(move);		
	}

	public void movePiece0(Move move) {
		this.moveHandler.movePiece0(move);
	}
	
    public Move undoMove() {
    	
		//swapActivePlayer0();
    	
    	Move move = this.moveHistory.get(this.moveHistory.size()-1);
    	this.moveRedoList.add(move);
    	
    	this.undoMoveHandler.undoMove(move);
    	this.moveHistory.remove(this.moveHistory.size()-1);
    	
    	//swapActivePlayer0();
    	//changeGameState();
    	return move;
    }

	public void undoMove0(Move move){
		//Piece piece = getNonCapturedPieceAtLocation(move.targetRow, move.targetColumn);
		
		//piece.setRow(move.sourceRow);
		//piece.setColumn(move.sourceColumn);
		
//		if(move.capturedPiece != null){
//			move.capturedPiece.setRow(move.targetRow);
//			move.capturedPiece.setColumn(move.targetColumn);
//			move.capturedPiece.isCaptured(false);
//			this.capturedPieces.remove(move.capturedPiece);
//			this.pieces.add(move.capturedPiece);
//		}
		
    	//Move moveToUndo = this.moveHistory.get(this.moveHistory.size()-1);		
    	//this.undoMoveHandler.undoMove(moveToUndo);
		this.undoMoveHandler.undoMove(move);
    	//this.moveHistory.remove(this.moveHistory.size()-1);
		
		if(move.getPiece().getColor() == Piece.COLOR_BLACK){
			this.gameState = ChessGame.GAME_STATE_BLACK;
		}else{
			this.gameState = ChessGame.GAME_STATE_WHITE;
		}
	}
	
	public Move redoMove() {
    	
    	Move moveToRedo = this.moveRedoList.get(this.moveRedoList.size()-1);
    	movePiece(moveToRedo);
    	
    	this.moveRedoList.remove(this.moveRedoList.size()-1);
    	//swapActivePlayer0();
    	//changeGameState();
    	return moveToRedo;
	}    
	
	/**
	 * @return current game state (one of ChessGame.GAME_STATE_..)
	 */
	public int getGameState() {
		return this.gameState;
	}

	public void setGameState(int gameState) {
		this.gameState = gameState;
		setActivePlayer();
	}

	private void setActivePlayer() {
		playerToPlay = (this.gameState == GAME_STATE_WHITE)
			? playerWhite 
			: playerBlack;
	}
	
//	public void swapActivePlayer() {
//
//		playerToPlay = playerToPlay.getOpposingPlayer();
//		changeGameState();
//	}

	/**
	 * swap active player and update game state
	 */
	public void swapActivePlayer0() {
		this.activePlayerHandler = this.activePlayerHandler == this.whitePlayerHandler
			? this.blackPlayerHandler
			: this.whitePlayerHandler;
		
		//playerToPlay = playerToPlay.getOpposingPlayer();		
		this.changeGameState();
		
		this.blackPlayerHandler.activePlayerSwapped();
		this.whitePlayerHandler.activePlayerSwapped();
	}
	
	public void setActivePlayer(PlayerColor activeColor) {
		if (activeColor == PlayerColor.WHITE) {
			this.activePlayerHandler = this.whitePlayerHandler;
			playerToPlay = playerWhite;
		
			this.gameState = GAME_STATE_WHITE;
			//this.whitePlayerHandler.activePlayerSwapped();
		} else {
			this.activePlayerHandler = this.blackPlayerHandler;
			playerToPlay = playerBlack;
		
			this.gameState = GAME_STATE_BLACK;
			//this.blackPlayerHandler.activePlayerSwapped();
		}
	}
	
	public void changeGameState() {
		
		if (isGameEndConditionReached()) {

			if (this.gameState == ChessGame.GAME_STATE_BLACK) {
				this.gameState = ChessGame.GAME_STATE_END_BLACK_WON;
			} else if(this.gameState == ChessGame.GAME_STATE_WHITE){
				this.gameState = ChessGame.GAME_STATE_END_WHITE_WON;
			}else{
				// leave game state as it is
			}
			return;
		}
		
		switch (this.gameState) {
		case GAME_STATE_BLACK:
			this.gameState = GAME_STATE_WHITE;
			playerToPlay = playerWhite;
			break;
		case GAME_STATE_WHITE:
			this.gameState = GAME_STATE_BLACK;
			playerToPlay = playerBlack;
			break;
		case GAME_STATE_END:
		case GAME_STATE_END_WHITE_WON:
		case GAME_STATE_END_BLACK_WON:// don't change anymore
			// don't change anymore
			break;
		default:
			throw new IllegalStateException("unknown game state:" + this.gameState);
		}
	}

	private boolean isGameEndConditionReached() {
		
//		PlayerStatus opponentStatus = playerToPlay.getStatus();
//		if(opponentStatus == PlayerStatus.InCheckMate) {
//			return true;
//		}
		
//todo: find better logic...		
//		for (Piece piece : this.capturedPieces) {
//			if (piece.getType() == Piece.TYPE_KING ) {
//				return true;
//			} else {
//				// continue iterating
//			}
//		}

		return false;
	}
	
	/**
	 * @return textual description of current game state
	 */
	public String getGameStateAsText() {
		String state = "unknown";
		switch (this.gameState) {
			case ChessGame.GAME_STATE_BLACK: state = "Black";break;
			case ChessGame.GAME_STATE_END: state = "Checkmate!";break;
			case ChessGame.GAME_STATE_WHITE: state = "White";break;
			case ChessGame.GAME_STATE_EDIT_POSITION: state = "Edit Position";break;
		}
		return state;
	}
	
	/**
	 * set the client/player for the specified piece color
	 * @param pieceColor - color the client/player represents 
	 * @param playerHandler - the client/player
	 */
	public void setPlayer(int pieceColor, Player.Intellegence intellegence,	IPlayerHandler playerHandler){
		switch (pieceColor) {
		case Piece.COLOR_WHITE:
			this.whitePlayerHandler = playerHandler;
	        playerWhite = new PlayerWhite(intellegence);
			break;
		case Piece.COLOR_BLACK:
			this.blackPlayerHandler = playerHandler;
	        playerBlack = new PlayerBlack(intellegence);
			break;
		default:
			throw new IllegalArgumentException("Invalid pieceColor: "+pieceColor);
		}
	}	
	

	
	/**
	 * start main game flow
	 */
	public void startGame(){
		// check if all players are ready
		//System.out.println("ChessGame: waiting for players");
		while (this.blackPlayerHandler == null || this.whitePlayerHandler == null){
			// players are still missing
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
		}
		
		setActivePlayer(PlayerColor.WHITE);
		
		// start game flow
		//System.out.println("ChessGame: starting game flow");
		while(!isGameEndConditionReached()){
			waitForMove();
			swapActivePlayer0();
		}
		
		//System.out.println("ChessGame: game ended");
	}
	
	/**
	 * wait for a valid player move and execute it
	 */
	private void waitForMove() {
		Move move = null;
		// wait for a valid move
		do{
			move = this.activePlayerHandler.getMove();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}while(move == null || !this.moveValidator.isMoveValid(playerToPlay, move));
		
		//execute move
		//boolean success = movePiece(move);
		//if(success){
		movePiece(move);
		this.activePlayerHandler.moveSuccessfullyExecuted(move);
		
		//this.blackPlayerHandler.moveSuccessfullyExecuted(move);
		//this.whitePlayerHandler.moveSuccessfullyExecuted(move);
		//}else{
		//	throw new IllegalStateException("move was valid, but failed to execute it");
		//}
	}
	
	@Override
	public void run() {
		startGame();
	}	
}
