package ch06.logic;

public class KingMovementRulesValidator extends LocationValidator {

	public boolean isValidMove(Piece king, Piece targetPiece, 
			int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
			
			if( isTargetLocationFree(targetPiece) || isTargetLocationCaptureable(king, targetPiece)){

				return isKingPathToTarget(king, 
					sourceRow, sourceColumn, targetRow, targetColumn);
			}
			return false;
		}
	
	private boolean isKingPathToTarget(Piece king, 
			int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		
		if (isKingMovingOneSquare(sourceRow, sourceColumn, targetRow, targetColumn)){
			return willKingNotMoveIntoCheck(king, targetRow, targetColumn);
		}
		
		if(isCastlingKingSide(king, sourceRow, sourceColumn, targetRow, targetColumn)) {
			return true;
		}
		
		if(isCastlingQueenSide(king, sourceRow, sourceColumn, targetRow, targetColumn)) {
			return true;
		}

		return false;
	}

	private boolean isKingMovingOneSquare(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {

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

	private boolean willKingNotMoveIntoCheck(Piece king, int targetRow, int targetColumn) {
		return !isOpponentPiecesPathToTarget(king, targetRow, targetColumn);
	}
	
	private boolean isOpponentPiecesPathToTarget(Piece king, int targetRow, int targetColumn) {
		for (Piece piece : king.player.getOpposingPlayer().getNonCapturedPieces()) {
			if (MoveController.isThreateningPiece(piece, targetRow, targetColumn)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isCastlingKingSide(Piece king, int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		King k = (King)king;

		return (sourceRow == targetRow && sourceColumn+2 == targetColumn) &&
				k.isKingNotMovedYet() &&
				k.isKingRookNotMovedYet() &&
				k.isKingNotInCheck() &&
				isKingNotPassThroughAttackedSquare(king, sourceRow, sourceColumn+1) &&
				willKingNotMoveIntoCheck(king, sourceRow, sourceColumn+2) &&
				!MoveController.arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,Piece.COLUMN_H,0,+1);
	}
	
	private boolean isCastlingQueenSide(Piece king, int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		King k = (King)king;

		return (sourceRow == targetRow && sourceColumn-2 == targetColumn) &&
				k.isKingNotMovedYet() &&
				k.isQueenRookNotMovedYet() && 
				k.isKingNotInCheck() &&
				isKingNotPassThroughAttackedSquare(king, sourceRow, sourceColumn-1) &&
				willKingNotMoveIntoCheck(king, sourceRow, sourceColumn-2) &&
				!MoveController.arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,Piece.COLUMN_A,0,-1);
	}
	
	private boolean isKingNotPassThroughAttackedSquare(Piece king, int attackedRow, int attackedColumn)
	{
		return willKingNotMoveIntoCheck(king, attackedRow, attackedColumn);
	}	
}
