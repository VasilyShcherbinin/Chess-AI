package ch06.gui;

import ch06.logic.King;
import ch06.logic.Move;
import ch06.logic.Pawn;
import ch06.logic.Piece;

public class RedoMoveHandler {

	private Chess chess;
	
	public RedoMoveHandler(Chess chess) {
		this.chess = chess;
	}
	
	public void redoMove(Move move) {
		
		Piece piece = move.getPiece();

		Piece pieceCaptured = move.getPieceCaptured();
		if(pieceCaptured != null) {
			this.chess.removePeaceFromChessBoard(move.targetRow, move.targetColumn);
			//addPeaceToChessBoard(pieceCaptured);			
		}		
		
		this.chess.removePeaceFromChessBoard(move.sourceRow, move.sourceColumn);
		this.chess.addPeaceToChessBoard(piece);
		
		
		//this code is taken from setNewPieceLocation and should be refactored
		if (piece instanceof King) {
			this.chess.checkIfCastlingAndPlaceRookOnTheOtherSideOfKing(piece, move);
		}
		
		if (piece instanceof Pawn) {
			if (piece.getPlayer().getOpposingPlayer().hasPawnEnpassant()) {
				this.chess.removeEnpassantPawnFromChessBoard(piece, move);
			}
			
			if(piece.getPlayer().isPawnPromoted()) {
				this.chess.onPawnPromoted(piece, move);
				return; // true;
			}
		}
		
	}
}
