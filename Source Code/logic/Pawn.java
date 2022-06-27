package ch06.logic;

import ch06.logic.Move.MoveName;

public class Pawn extends Piece {

	public Pawn(Player player, int color, int type, int row, int column) {
		super(player, color, type, row, column);
		
		name = PieceName.Pawn;
		abbreviation = PieceAbbreviation.P;
	}
	
	//	@Override
	protected boolean canMoveTo(int row, int col, ChessGame board) {
		// TODO Auto-generated method stub
		return false;
	}

	protected boolean canMoveTo(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {	

		if (getColor() == Piece.COLOR_WHITE) {
			if( sourceRow+1 == targetRow && sourceColumn+1 == targetColumn){
				//up right
				return true;
			}

			if( sourceRow+1 == targetRow && sourceColumn-1 == targetColumn){
				//up left
				return true;
			}
		} else {		
			if( sourceRow-1 == targetRow && sourceColumn+1 == targetColumn){
				//down right
				return true;
			}

			if( sourceRow-1 == targetRow && sourceColumn-1 == targetColumn){
				//down left
				return true;
			}
		}
		return false;
	}	
	
	protected boolean isPawnPromotion(Move move) {

		if (getColor() == Piece.COLOR_WHITE) {
			if(	move.targetRow == Piece.ROW_8) {
				return true;
			}
		} else {
			if(	move.targetRow == Piece.ROW_1) {
				return true;
			}
		}
		return false;
	}
	
	protected void checkIfPawnMovesForwardTwoSquares(Move move) {
		if (getColor() == Piece.COLOR_WHITE) {
			if(move.sourceRow + 2 == move.targetRow) {
				checkIfBlackPawnOnFourthRowOnAdjacentColumn(
						getPlayer(), move.targetColumn);
			}
		} else {
			if(move.sourceRow - 2 == move.targetRow) {
				checkIfWhitePawnOnFifthRowOnAdjacentColumn(
						getPlayer(), move.targetColumn);
			}
		}
	}
	
	private void checkIfBlackPawnOnFourthRowOnAdjacentColumn(Player player, int targetColumn) {
		for (Piece piece : player.getOpposingPlayer().getNonCapturedPieces()) {
			if(piece.getType() == Piece.TYPE_PAWN && piece.getRow() == Piece.ROW_4) {
				setPawnEnpassantColumn(player, targetColumn, piece);
			}
		}		
	}
	
	private void checkIfWhitePawnOnFifthRowOnAdjacentColumn(Player oppositePlayer, int targetColumn) {
		for (Piece piece : player.getOpposingPlayer().getNonCapturedPieces()) {
			if(piece.getType() == Piece.TYPE_PAWN && piece.getRow() == Piece.ROW_5) {
				setPawnEnpassantColumn(player, targetColumn, piece);
			}
		}		
	}
	
	private void setPawnEnpassantColumn(Player player, int targetColumn, Piece piece) {
		int adjacentColumn;
		int pieceColumn = piece.getColumn();
		if(pieceColumn == Piece.COLUMN_A) {
			adjacentColumn = pieceColumn + 1;
			if (adjacentColumn == targetColumn) {
				player.setPawnEnpassantColumn(targetColumn);
			}
		} else if (pieceColumn == Piece.COLUMN_H ) {
			adjacentColumn = pieceColumn - 1;
			if (adjacentColumn == targetColumn) {
				player.setPawnEnpassantColumn(targetColumn);
			}
		} else {
			adjacentColumn = pieceColumn + 1;
			if (adjacentColumn == targetColumn) {
				player.setPawnEnpassantColumn(targetColumn);
			}
			adjacentColumn = pieceColumn - 1;
			if (adjacentColumn == targetColumn) {
				player.setPawnEnpassantColumn(targetColumn);
			}
		}
	}

	protected void checkIfEnpassantAndCaptureOpponentPawn(Move move) {
		
		if (getColor() == Piece.COLOR_WHITE) {
			if(move.sourceRow == Piece.ROW_5 &&
				move.targetRow == Piece.ROW_6 &&
				move.targetColumn == getPlayer().getOpposingPlayer().getPawnEnpassantColumn()) {				

				Piece opponentPiece = getPlayer().getOpposingPlayer().getNonCapturedPieceAtLocation(
						move.targetRow-1, move.targetColumn);
				opponentPiece.isCaptured(true);
				move.setPieceCaptured(opponentPiece);
				//getPlayer().isOpponentEnpassantPawnCaptured(true);
				move.setName(MoveName.EnPassant);
			}				
		} else {
			if(move.sourceRow == Piece.ROW_4 &&
					move.targetRow == Piece.ROW_3 &&
					move.targetColumn == getPlayer().getOpposingPlayer().getPawnEnpassantColumn()) {	

				Piece opponentPiece = getPlayer().getOpposingPlayer().getNonCapturedPieceAtLocation(
						move.targetRow+1, move.targetColumn);
				opponentPiece.isCaptured(true);
				move.setPieceCaptured(opponentPiece);
				//getPlayer().isOpponentEnpassantPawnCaptured(true);
				move.setName(MoveName.EnPassant);
			}
		}
	}			
}

