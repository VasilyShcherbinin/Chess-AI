package ch06.gui;

import ch06.logic.Move;
import ch06.logic.Piece;

public class UndoMoveHandler {

	private Chess chess;
	
	public UndoMoveHandler(Chess chess) {
		this.chess = chess;
	}
	
	public void undoMove(Move move) {

		switch (move.getName()) {
		case CastleKingSide:
			restoreKingOnPositionBeforeUndo(move);
			restoreKingSideRookOnPositionBeforeUndo(move);
			break;

		case CastleQueenSide:
			restoreKingOnPositionBeforeUndo(move);
			restoreQueenSideRookOnPositionBeforeUndo(move);
			break;

		case PawnPromotionQueen:
		case PawnPromotionRook:
		case PawnPromotionBishop:
		case PawnPromotionKnight:
			restorePiecesOnPositionBeforePromotion(move);
			break;

		default:
			restorePiecesOnPositionBeforeUndo(move);
			break;
		}				
	}
	
	private void restoreKingOnPositionBeforeUndo(Move move) {
		this.chess.removePeaceFromChessBoard(move.targetRow, move.targetColumn);
		this.chess.addPeaceToChessBoard(move.getPiece());        	
	}
	
	private void restoreKingSideRookOnPositionBeforeUndo(Move move) {
		Piece rook;
		if (move.getPiece().getColor() ==  Piece.COLOR_WHITE) {
			rook = this.chess.getPeaceFromChessBoard(Piece.ROW_1, Piece.COLUMN_F);
			this.chess.removePeaceFromChessBoard(Piece.ROW_1, Piece.COLUMN_F);
		} else {
			rook = this.chess.getPeaceFromChessBoard(Piece.ROW_8, Piece.COLUMN_F);
			this.chess.removePeaceFromChessBoard(Piece.ROW_8, Piece.COLUMN_F);
		}
		this.chess.addPeaceToChessBoard(rook);        	
	}
	
	private void restoreQueenSideRookOnPositionBeforeUndo(Move move) {
		Piece rook;
		if (move.getPiece().getColor() ==  Piece.COLOR_WHITE) {
			rook = this.chess.getPeaceFromChessBoard(Piece.ROW_1, Piece.COLUMN_D);
			this.chess.removePeaceFromChessBoard(Piece.ROW_1, Piece.COLUMN_D);
		} else {
			rook = this.chess.getPeaceFromChessBoard(Piece.ROW_8, Piece.COLUMN_D);
			this.chess.removePeaceFromChessBoard(Piece.ROW_8, Piece.COLUMN_D);
		}
		this.chess.addPeaceToChessBoard(rook);        	
	}	
	
	private void restorePiecesOnPositionBeforePromotion(Move move) {
		this.chess.removePeaceFromChessBoard(move.targetRow, move.targetColumn);
		this.chess.addPeaceToChessBoard(move.getPiece());
	}
	
	private void restorePiecesOnPositionBeforeUndo(Move move) {
		
		this.chess.removePeaceFromChessBoard(move.targetRow, move.targetColumn);
		this.chess.addPeaceToChessBoard(move.getPiece());
		
		Piece pieceCaptured = move.getPieceCaptured();
		if(pieceCaptured != null) {
			this.chess.addPeaceToChessBoard(pieceCaptured);			
		}
	}		
}
