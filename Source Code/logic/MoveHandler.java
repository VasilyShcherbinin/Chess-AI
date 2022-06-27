package ch06.logic;

import java.util.List;

import ch06.gui.PawnPromotionDialog;
import ch06.gui.PawnPromotionPanel;
import ch06.logic.Move.MoveName;

public class MoveHandler {

	private Piece pawnPromoted;
	private Player player;
	
	public MoveHandler () {
		
	}
	
	/**
	 * Move piece to the specified location. If the target location is occupied
	 * by an opponent piece, that piece is marked as 'captured'.
	 * 
	 * @param move to execute
	 */
	public void movePiece(Move move) {

		player = move.getPiece().getPlayer();
		
		Piece piece = player.getNonCapturedPieceAtLocation(move.sourceRow, move.sourceColumn);

		if (player.getOpposingPlayer().isNonCapturedPieceAtLocation(
				move.targetRow, move.targetColumn)) {
			Piece opponentPiece = player.getOpposingPlayer().getNonCapturedPieceAtLocation(
				move.targetRow, move.targetColumn);
			opponentPiece.isCaptured(true);
			move.setPieceCaptured(opponentPiece);
		}

		piece.setRow(move.targetRow);
		piece.setColumn(move.targetColumn);
		piece.increaseNumberOfMoves();
		move.setName(MoveName.Standard);

		if (piece instanceof King) {
			((King)piece).checkIfCastlingAndPlaceRookOnTheOtherSideOfKing(move);
		}
		
		if (piece instanceof Pawn) {
			Pawn pawn = (Pawn)piece;
			pawn.checkIfPawnMovesForwardTwoSquares (move);
			pawn.checkIfEnpassantAndCaptureOpponentPawn(move);
			
			if (pawn.isPawnPromotion(move)) {
				this.pawnPromoted = pawn;
				this.pawnPromoted.isCaptured(true);
				
				PawnPromotionPanel pane = new PawnPromotionPanel(this);
				new PawnPromotionDialog(null, "Pawn Promotion", true, pane);
				
				player.isPawnPromoted(true);
				
				Piece piecePromoted = player.getPieces().get(player.getPieces().size()-1);
				if(piecePromoted instanceof Queen) {
					move.setName(MoveName.PawnPromotionQueen);
				}else if(piecePromoted instanceof Rook) {
					move.setName(MoveName.PawnPromotionRook);
				}else if(piecePromoted instanceof Bishop) {
					move.setName(MoveName.PawnPromotionBishop);
				}else if(piecePromoted instanceof Knight) {
					move.setName(MoveName.PawnPromotionKnight);
				}
			}
		}
		
		//todo: At the end of each turn, you should also check to see 
		//if the move resulted in either 
		//- player being in check, 
		//-if there is a checkmate,
		//-if there is a stalemate.
		//..
		//boolean isInCheckMate(ChessBoard board, int Player)
		//boolean isInCheck(ChessBoard board, int Player)
		//boolean isInStalemate(ChessBoard board, int Player).
		
	}

	public void movePiece0(Move move) {

		player = move.getPiece().getPlayer();
		
		Piece piece = player.getNonCapturedPieceAtLocation(move.sourceRow, move.sourceColumn);

		if (player.getOpposingPlayer().isNonCapturedPieceAtLocation(
				move.targetRow, move.targetColumn)) {
			Piece opponentPiece = player.getOpposingPlayer().getNonCapturedPieceAtLocation(
				move.targetRow, move.targetColumn);
			opponentPiece.isCaptured(true);
			move.setPieceCaptured(opponentPiece);
		}

		piece.setRow(move.targetRow);
		piece.setColumn(move.targetColumn);
		piece.increaseNumberOfMoves();
		move.setName(MoveName.Standard);

		if (piece instanceof King) {
			((King)piece).checkIfCastlingAndPlaceRookOnTheOtherSideOfKing(move);
		}
		
		if (piece instanceof Pawn) {
			Pawn pawn = (Pawn)piece;
			pawn.checkIfPawnMovesForwardTwoSquares (move);
			pawn.checkIfEnpassantAndCaptureOpponentPawn(move);
			
			if (pawn.isPawnPromotion(move)) {
				this.pawnPromoted = pawn;
				this.pawnPromoted.isCaptured(true);

				//dialog cannot be used when calculate best move 				
//				PawnPromotionPanel pane = new PawnPromotionPanel(this);
//				new PawnPromotionDialog(null, "Pawn Promotion", true, pane);


				//I am doing queening only 
				promotePawnToQueen();
				
				player.isPawnPromoted(true);
				move.setName(MoveName.PawnPromotionQueen);
				
//				Piece piecePromoted = player.getPieces().get(player.getPieces().size()-1);
//				if(piecePromoted instanceof Queen) {
//					move.setName(MoveName.PawnPromotionQueen);
//				}else if(piecePromoted instanceof Rook) {
//					move.setName(MoveName.PawnPromotionRook);
//				}else if(piecePromoted instanceof Bishop) {
//					move.setName(MoveName.PawnPromotionBishop);
//				}else if(piecePromoted instanceof Knight) {
//					move.setName(MoveName.PawnPromotionKnight);
//				}
			}
		}
		
		//todo: At the end of each turn, you should also check to see 
		//if the move resulted in either 
		//- player being in check, 
		//-if there is a checkmate,
		//-if there is a stalemate.
		//..
		//boolean isInCheckMate(ChessBoard board, int Player)
		//boolean isInCheck(ChessBoard board, int Player)
		//boolean isInStalemate(ChessBoard board, int Player).
		
	}
	
	public void promotePawnToQueen() {

		Queen piece = new Queen(player, 
				this.pawnPromoted.getColor(), 
				Piece.TYPE_QUEEN, 
				this.pawnPromoted.getRow(), 
				this.pawnPromoted.getColumn());
		piece.hasBeenPromoted(true);

		List<Piece> pieces = player.getPieces();
		pieces.add(piece);
	}
	
	public void promotePawnToRook() {

		Rook piece = new Rook(player, 
				this.pawnPromoted.getColor(), 
				Piece.TYPE_ROOK, 
				this.pawnPromoted.getRow(), 
				this.pawnPromoted.getColumn());
		piece.hasBeenPromoted(true);

		List<Piece> pieces = player.getPieces();
		pieces.add(piece);
	}	
	
	public void promotePawnToBishop() {

		Bishop piece = new Bishop(player, 
				this.pawnPromoted.getColor(), 
				Piece.TYPE_BISHOP, 
				this.pawnPromoted.getRow(), 
				this.pawnPromoted.getColumn());
		piece.hasBeenPromoted(true);

		List<Piece> pieces = player.getPieces();
		pieces.add(piece);
	}	
	
	public void promotePawnToKnight() {

		Knight piece = new Knight(player, 
				this.pawnPromoted.getColor(), 
				Piece.TYPE_KNIGHT, 
				this.pawnPromoted.getRow(), 
				this.pawnPromoted.getColumn());
		piece.hasBeenPromoted(true);

		List<Piece> pieces = player.getPieces();
		pieces.add(piece);
	}		
}
