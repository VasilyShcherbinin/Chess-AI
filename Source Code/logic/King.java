package ch06.logic;

import ch06.logic.Move.MoveName;

public class King extends Piece {

	boolean kingNotInCheck = true;	
	
	public King(Player player, int color, int type, int row, int column) {
		super(player, color, type, row, column);
		
		name = PieceName.King;
		abbreviation = PieceAbbreviation.K;
	}

	public boolean isKingNotMovedYet() {
		return getNumberOfMoves() == 0; 
	}
	
	public boolean isKingRookNotMovedYet() {
        Piece rook = getColor() == Piece.COLOR_WHITE
                ? getPlayer().getNonCapturedPieceAtLocation(ROW_1, COLUMN_H)
                : getPlayer().getNonCapturedPieceAtLocation(ROW_8, COLUMN_H);
        return (rook != null) 
        		? (rook.getNumberOfMoves() == 0)
				: false;
	}
	
	public boolean isQueenRookNotMovedYet() {
        Piece rook = getColor() == Piece.COLOR_WHITE
                ? getPlayer().getNonCapturedPieceAtLocation(ROW_1, COLUMN_A)
                : getPlayer().getNonCapturedPieceAtLocation(ROW_8, COLUMN_A);
        return (rook != null) 
        		? (rook.getNumberOfMoves() == 0)
				: false;
	}

	public boolean isKingNotInCheck() {
		return this.kingNotInCheck;
	}
	
	//	@Override
	protected boolean canMoveTo(int row, int col, ChessGame board) {
		// TODO Auto-generated method stub
		return false;
	}

	protected boolean canMoveTo(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {

		if( sourceRow+1 == targetRow && sourceColumn == targetColumn){
			//up
			return true;
		}

		if( sourceRow+1 == targetRow && sourceColumn+1 == targetColumn){
			//up right
			return true;
		}
		
		if( sourceRow == targetRow && sourceColumn+1 == targetColumn){
			//right
			return true;
		}
		
		if( sourceRow-1 == targetRow && sourceColumn+1 == targetColumn){
			//down right
			return true;
		}
		
		if( sourceRow-1 == targetRow && sourceColumn == targetColumn){
			//down
			return true;
		}
		
		if( sourceRow-1 == targetRow && sourceColumn-1 == targetColumn){
			//down left
			return true;
		}
		
		if( sourceRow == targetRow && sourceColumn-1 == targetColumn){
			//left
			return true;
		}
		
		if( sourceRow+1 == targetRow && sourceColumn-1 == targetColumn){
			//up left
			return true;
		}

		return false;
	}
	
	protected boolean determineCheckStatus() {
		int row = getRow();
		int column = getColumn();
		return MoveController.playerCanAttackSquare(this.player.getOpposingPlayer(), row, column);
	}
	
	protected void checkIfCastlingAndPlaceRookOnTheOtherSideOfKing(Move move) {

		if(!(move.sourceRow == Piece.ROW_1 || move.sourceRow == Piece.ROW_8)) {
			return;
		}

		Piece rook;
		if(move.sourceColumn == Piece.COLUMN_E && move.targetColumn == Piece.COLUMN_G) {
			rook = getPlayer().getNonCapturedPieceAtLocation(move.sourceRow, Piece.COLUMN_H);
			//rook.setRow(move.targetRow);
			rook.setColumn(Piece.COLUMN_F);
			rook.increaseNumberOfMoves();
			move.setName(MoveName.CastleKingSide);
		}
		else {
			if(move.sourceColumn == Piece.COLUMN_E && move.targetColumn == Piece.COLUMN_C) {
				rook = getPlayer().getNonCapturedPieceAtLocation(move.sourceRow, Piece.COLUMN_A);
				//rook.setRow(move.targetRow);
				rook.setColumn(Piece.COLUMN_D);
				rook.increaseNumberOfMoves();
				move.setName(MoveName.CastleQueenSide);
			}
		}
	}
	
	protected boolean canMoveOutOfCheck() {
		int kingRow = getRow();
		int kingColumn = getColumn();
		
		for (int column = Piece.COLUMN_A; column <= Piece.COLUMN_H; column++) {
			for (int row = Piece.ROW_1; row <= Piece.ROW_8; row++) {
		
				Piece targetPiece = ChessGame.playerWhite.getNonCapturedPieceAtLocation(row, column);
				if (targetPiece == null) {
					targetPiece = ChessGame.playerBlack.getNonCapturedPieceAtLocation(row, column);
				}
				
				if(MovementRulesValidator.validatePieceMovementRules(
						this,
						targetPiece,
						kingRow, kingColumn, row, column)) {
					return true;
				}				
			}
		}
		return false;
	}

//	boolean isGetOutofCheckByKingMove(Piece king) {
//		// iterate the complete board to check if target locations are valid
//		for (int column = Piece.COLUMN_A; column <= Piece.COLUMN_H; column++) {
//			for (int row = Piece.ROW_1; row <= Piece.ROW_8; row++) {
//				Move move =  new Move(king.getRow(), king.getColumn(), row, column);
//				// check if target location is valid
//				if(ChessGame.this.moveValidator.isMoveValid(king.getPlayer(), move) ) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}	
}
