package ch06.ai;

import java.util.ArrayList;
import java.util.List;

import ch06.gui.Chess;
import ch06.logic.ChessGame;
import ch06.logic.IPlayerHandler;
import ch06.logic.Move;
import ch06.logic.Piece;
import ch06.logic.Player;

public class NegaMaxAiPlayerHandler implements IPlayerHandler {

	private ChessGame chessGame;
	private Chess chess;
	
	/**
	 * number of moves to look into the future
	 */
	public int maxDepth = 2;


	public NegaMaxAiPlayerHandler(ChessGame chessGame, Chess chess) {
		this.chessGame = chessGame;
		this.chess = chess;
	}

	@Override
	public Move getMove() {
		return getBestMove();
	}

	/**
	 * get best move for current game situation
	 * @return a valid Move instance
	 */
	private Move getBestMove() {
		System.out.println("getting best move");
		//ChessConsole.printCurrentGameState(this.chessGame);
		printCurrentGameState(this.chessGame);
		System.out.println("thinking...");
		
		//List<Move> validMoves = generateMoves(false);
		List<Move> validMoves = generateMoves();
		int bestResult = Integer.MIN_VALUE;
		Move bestMove = null;
		
		for (Move move : validMoves) {
			executeMove(move);
			//System.out.println("evaluate move: "+move+" =========================================");
			int evaluationResult = -1 * negaMax(this.maxDepth,"");
			//System.out.println("result: "+evaluationResult);
			undoMove(move);
			if( evaluationResult > bestResult){
				bestResult = evaluationResult;
				bestMove = move;
			}
		}
		System.out.println("done thinking! best move is: "+bestMove);
		return bestMove;
	}

	@Override
	public void moveSuccessfullyExecuted(Move move) {
		// we are using the same chessGame instance, so no need to do anything here.
		//System.out.println("executed: "+move);
		
		this.chess.moveSuccessfullyExecuted(move);
	}

	@Override
	public void activePlayerSwapped() {
		this.chess.activePlayerSwapped();
	}
	
	/**
	 * evaluate current game state according to nega max algorithm
	 *
	 * @param depth - current depth level (number of counter moves that still need to be evaluated)
	 * @param indent - debug string, that is placed in front of each log message
	 * @return integer score of game state after looking at "depth" counter moves
	 */
	private int negaMax(int depth, String indent) {
		//do static evaluation at terminal states
		if (depth <= 0
			|| this.chessGame.getGameState() == ChessGame.GAME_STATE_END_WHITE_WON
			|| this.chessGame.getGameState() == ChessGame.GAME_STATE_END_BLACK_WON){
			
			return evaluateState();
		}
		
		//start from worst possible score i.e a loss = Integer.MIN_VALUE
		int max = Integer.MIN_VALUE;
		int score = Integer.MIN_VALUE;

		
		// generate list of legal moves
		List<Move> moves = generateMoves();
		for(Move m : moves){
			//make the move AND switch sides i.e player != player
			executeMove(m);
			
			printCurrentGameState(this.chessGame);
			
			//recursive call with a reduced depth of 1
			score = -1 * negaMax(depth - 1, indent+" ");
			//System.out.println(indent+"handling move: "+currentMove+" : "+score);
			
			if( score > max){
				max = score;
			}
			
			undoMove(m);
		}
		//System.out.println(indent+"max: "+currentMax);
		return max;
	}

	/**
	 * undo specified move
	 */
	private void undoMove(Move move) {
		//System.out.println("undoing move");
		this.chessGame.undoMove0(move);
		//this.chessGame.undoMove();
		//state.changeGameState();
	}

	/**
	 * Execute specified move. This will also change the game state after the
	 * move has been executed.
	 */
	private void executeMove(Move move) {
		//System.out.println("executing move");
		this.chessGame.movePiece0(move);
		this.chessGame.changeGameState();
	}

	/**
	* generate all possible/valid moves for the specified game
	* @param state - game state for which the moves should be generated
	* @return list of all possible/valid moves
	*/
	//private List<Move> generateMoves(boolean debug) {
	private List<Move> generateMoves() {

		//List<Piece> pieces = this.chessGame.getPieces();
		List<Move> validMoves = new ArrayList<Move>();
		Move testMove = new Move(0,0,0,0);
		
//			int pieceColor = (this.chessGame.getGameState()==ChessGame.GAME_STATE_WHITE
//				?Piece.COLOR_WHITE
//				:Piece.COLOR_BLACK);

		Player player = (this.chessGame.getGameState()==ChessGame.GAME_STATE_WHITE)
			? ChessGame.playerWhite
			: ChessGame.playerBlack;

		List<Piece> pieces = player.getNonCapturedPieces();
		
		// iterate over all non-captured pieces
		for (Piece piece : pieces) {

			testMove.setPiece(piece);
			testMove.sourceRow = piece.getRow();
			testMove.sourceColumn = piece.getColumn();

			// iterate over all board rows and columns
			for (int targetRow = Piece.ROW_1; targetRow <= Piece.ROW_8; targetRow++) {
				for (int targetColumn = Piece.COLUMN_A; targetColumn <= Piece.COLUMN_H; targetColumn++) {

					// finish generating move
					testMove.targetRow = targetRow;
					testMove.targetColumn = targetColumn;

					//if(debug) System.out.println("testing move: "+testMove);
					
					// check if generated move is valid
					if (this.chessGame.getMoveValidator().isMoveValid(
						player, testMove)) {
						// valid move
						validMoves.add(testMove.clone());
					} else {
						// generated move is invalid, so we skip it
					}
				}
			}
		}
		return validMoves;
	}

	/**
	 * evaluate the current game state from the view of the
	 * current player. High numbers indicate a better situation for
	 * the current player.
	 *
	 * @return integer score of current game state
	 */
	private int evaluateState() {

		// add up score
		//
		int scoreWhite = 0;
		int scoreBlack = 0;

//		for (Piece piece : ChessGame.playerToPlay.getNonCapturedPieces()) {
//			if(piece.getColor() == Piece.COLOR_BLACK){
//				scoreBlack +=
//					getScoreForPieceType(piece.getType());
//				scoreBlack +=
//					getScoreForPiecePosition(piece.getRow(),piece.getColumn());
//			}else if( piece.getColor() == Piece.COLOR_WHITE){
//				scoreWhite +=
//					getScoreForPieceType(piece.getType());
//				scoreWhite +=
//					getScoreForPiecePosition(piece.getRow(),piece.getColumn());
//			}else{
//				throw new IllegalStateException(
//						"unknown piece color found: "+piece.getColor());
//			}
//		}

		for (Piece piece : ChessGame.playerWhite.getNonCapturedPieces()) {
			scoreWhite +=
				getScoreForPieceType(piece.getType());
			scoreWhite +=
				getScoreForPiecePosition(piece.getRow(),piece.getColumn());
		}

		for (Piece piece : ChessGame.playerBlack.getNonCapturedPieces()) {
			scoreBlack +=
				getScoreForPieceType(piece.getType());
			scoreBlack +=
				getScoreForPiecePosition(piece.getRow(),piece.getColumn());
		}
		
		// return evaluation result depending on who's turn it is
		int gameState = this.chessGame.getGameState();
		
		if( gameState == ChessGame.GAME_STATE_BLACK){
			return scoreBlack - scoreWhite;
		
		}else if(gameState == ChessGame.GAME_STATE_WHITE){
			return scoreWhite - scoreBlack;
		
		}else if(gameState == ChessGame.GAME_STATE_END_WHITE_WON
				|| gameState == ChessGame.GAME_STATE_END_BLACK_WON){
			return Integer.MIN_VALUE + 1;
		
		}else{
			throw new IllegalStateException("unknown game state: "+gameState);
		}
	}
	
	/**
	 * get the evaluation bonus for the specified position
	 * @param row - one of Piece.ROW_..
	 * @param column - one of Piece.COLUMN_..
	 * @return integer score
	 */
	private int getScoreForPiecePosition(int row, int column) {
		byte[][] positionWeight =
		{ {1,1,1,1,1,1,1,1}
		 ,{2,2,2,2,2,2,2,2}
		 ,{2,2,3,3,3,3,2,2}
		 ,{2,2,3,4,4,3,2,2}
		 ,{2,2,3,4,4,3,2,2}
		 ,{2,2,3,3,3,3,2,2}
		 ,{2,2,2,2,2,2,2,2}
		 ,{1,1,1,1,1,1,1,1}
		 };
		return positionWeight[row][column];
	}

	/**
	 * get the evaluation score for the specified piece type
	 * @param type - one of Piece.TYPE_..
	 * @return integer score
	 */
	private int getScoreForPieceType(int type){
		switch (type) {
			case Piece.TYPE_BISHOP: return 30;
			case Piece.TYPE_KING: return 99999;
			case Piece.TYPE_KNIGHT: return 30;
			case Piece.TYPE_PAWN: return 10;
			case Piece.TYPE_QUEEN: return 90;
			case Piece.TYPE_ROOK: return 50;
			default: throw new IllegalArgumentException("unknown piece type: "+type);
		}
	}

	public static void printCurrentGameState(ChessGame game) {

//		System.out.println("  a  b  c  d  e  f  g  h  ");
//		for (int row = Piece.ROW_8; row >= Piece.ROW_1; row--) {
//
//			System.out.println(" +--+--+--+--+--+--+--+--+");
//			String strRow = (row + 1) + "|";
//			for (int column = Piece.COLUMN_A; column <= Piece.COLUMN_H; column++) {
//				Piece piece = game.getNonCapturedPieceAtLocation(row, column);
//				String pieceStr = getNameOfPiece(piece);
//				strRow += pieceStr + "|";
//			}
//			System.out.println(strRow + (row + 1));
//		}
//		System.out.println(" +--+--+--+--+--+--+--+--+");
//		System.out.println("  a  b  c  d  e  f  g  h  ");

		String gameStateStr = "unknown";
		switch (game.getGameState()) {
			case ChessGame.GAME_STATE_BLACK: gameStateStr="black";break;
			case ChessGame.GAME_STATE_END_WHITE_WON: gameStateStr="white won";break;
			case ChessGame.GAME_STATE_END_BLACK_WON: gameStateStr="black won";break;
			case ChessGame.GAME_STATE_WHITE: gameStateStr="white";break;
		}
		System.out.println("state: " + gameStateStr);

	}
	
	public static void main(String[] args) {
//			ChessGame ch = new ChessGame();
//			SimpleAiPlayerHandler ai = new SimpleAiPlayerHandler(ch);
//			/*
//			ch.pieces = new ArrayList<Piece>();
//			ch.createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_KING, Piece.ROW_3, Piece.COLUMN_C);
//			ch.createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_KING, Piece.ROW_4, Piece.COLUMN_C);
//			ch.createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_ROOK, Piece.ROW_5, Piece.COLUMN_C);
//			ch.createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_ROOK, Piece.ROW_4, Piece.COLUMN_B);
//			ChessConsole.printCurrentGameState(ch);
//			System.out.println("score: "+ai.evaluateState());
//			System.out.println("move: "+ai.getBestMove()); //c4 b4
//			*/
//			
//			/*
//			  a  b  c  d  e  f  g  h  
//			  +--+--+--+--+--+--+--+--+
//			 8|BR|  |  |  |  |  |  |BR|8
//			  +--+--+--+--+--+--+--+--+
//			 7|BP|  |WR|  |BK|  |BP|BP|7
//			  +--+--+--+--+--+--+--+--+
//			 6|  |  |  |  |BP|BP|  |  |6
//			  +--+--+--+--+--+--+--+--+
//			 5|  |  |  |  |  |  |  |  |5
//			  +--+--+--+--+--+--+--+--+
//			 4|  |  |  |  |BB|  |  |  |4
//			  +--+--+--+--+--+--+--+--+
//			 3|  |  |  |  |WB|WP|  |  |3
//			  +--+--+--+--+--+--+--+--+
//			 2|WP|  |  |WQ|  |  |  |WP|2
//			  +--+--+--+--+--+--+--+--+
//			 1|  |  |  |  |WK|  |  |WR|1
//			  +--+--+--+--+--+--+--+--+
//			   a  b  c  d  e  f  g  h
//			*/
//			
//			/*
//			ch.pieces = new ArrayList<Piece>();
//			ch.createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_ROOK, Piece.ROW_8, Piece.COLUMN_A);
//			ch.createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_ROOK, Piece.ROW_8, Piece.COLUMN_H);
//			ch.createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_PAWN, Piece.ROW_7, Piece.COLUMN_A);
//			ch.createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_KING, Piece.ROW_7, Piece.COLUMN_E);
//			ch.createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_PAWN, Piece.ROW_7, Piece.COLUMN_G);
//			ch.createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_PAWN, Piece.ROW_7, Piece.COLUMN_H);
//			ch.createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_ROOK, Piece.ROW_7, Piece.COLUMN_C);
//			ch.createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_PAWN, Piece.ROW_6, Piece.COLUMN_E);
//			ch.createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_PAWN, Piece.ROW_6, Piece.COLUMN_F);
//			ch.createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_BISHOP, Piece.ROW_4, Piece.COLUMN_E);
//			ch.createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_BISHOP, Piece.ROW_3, Piece.COLUMN_E);
//			ch.createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_PAWN, Piece.ROW_3, Piece.COLUMN_F);
//			ch.createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_PAWN, Piece.ROW_2, Piece.COLUMN_A);
//			ch.createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_QUEEN, Piece.ROW_2, Piece.COLUMN_D);
//			ch.createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_PAWN, Piece.ROW_2, Piece.COLUMN_H);
//			ch.createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_KING, Piece.ROW_1, Piece.COLUMN_E);
//			ch.createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_ROOK, Piece.ROW_1, Piece.COLUMN_H);
//			ChessConsole.printCurrentGameState(ch);
//			ai = new SimpleAiPlayerHandler(ch);
//			System.out.println("score: "+ai.evaluateState());
//			System.out.println("move: "+ai.getBestMove()); //c4 b4
//			*/
//			
//			/*
//			 *   a  b  c  d  e  f  g  h  
// +--+--+--+--+--+--+--+--+
//8|BR|  |  |  |  |  |  |BR|8
// +--+--+--+--+--+--+--+--+
//7|BP|BB|WR|  |BK|  |BP|BP|7
// +--+--+--+--+--+--+--+--+
//6|  |  |  |  |BP|BP|  |  |6
// +--+--+--+--+--+--+--+--+
//5|  |  |  |  |  |  |  |  |5
// +--+--+--+--+--+--+--+--+
//4|  |  |  |  |WP|  |  |  |4
// +--+--+--+--+--+--+--+--+
//3|  |  |  |  |WB|WP|  |  |3
// +--+--+--+--+--+--+--+--+
//2|WP|  |  |WQ|  |  |  |WP|2
// +--+--+--+--+--+--+--+--+
//1|  |  |  |  |WK|  |  |WR|1
// +--+--+--+--+--+--+--+--+
//  a  b  c  d  e  f  g  h  
//			 */
//			
//			ch.pieces = new ArrayList<Piece>();
//			ch.createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_ROOK, Piece.ROW_8, Piece.COLUMN_A);
//			ch.createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_ROOK, Piece.ROW_8, Piece.COLUMN_H);
//			ch.createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_PAWN, Piece.ROW_7, Piece.COLUMN_A);
//			ch.createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_BISHOP, Piece.ROW_7, Piece.COLUMN_B);
//			ch.createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_ROOK, Piece.ROW_7, Piece.COLUMN_C);
//			ch.createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_KING, Piece.ROW_7, Piece.COLUMN_E);
//			ch.createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_PAWN, Piece.ROW_7, Piece.COLUMN_G);
//			ch.createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_PAWN, Piece.ROW_7, Piece.COLUMN_H);
//			ch.createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_PAWN, Piece.ROW_6, Piece.COLUMN_E);
//			ch.createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_PAWN, Piece.ROW_6, Piece.COLUMN_F);
//			ch.createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_PAWN, Piece.ROW_4, Piece.COLUMN_E);
//			ch.createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_BISHOP, Piece.ROW_3, Piece.COLUMN_E);
//			ch.createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_PAWN, Piece.ROW_3, Piece.COLUMN_F);
//			ch.createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_PAWN, Piece.ROW_2, Piece.COLUMN_A);
//			ch.createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_QUEEN, Piece.ROW_2, Piece.COLUMN_D);
//			ch.createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_PAWN, Piece.ROW_2, Piece.COLUMN_H);
//			ch.createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_KING, Piece.ROW_1, Piece.COLUMN_E);
//			ch.createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_ROOK, Piece.ROW_1, Piece.COLUMN_H);
//			ch.gameState = ChessGame.GAME_STATE_BLACK;
//			ChessConsole.printCurrentGameState(ch);
//			System.out.println("score: "+ai.evaluateState());
//			System.out.println("move: "+ai.getBestMove()); //c4 b4
	}
}

