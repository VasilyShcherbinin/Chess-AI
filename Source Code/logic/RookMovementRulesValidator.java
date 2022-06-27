package ch06.logic;

public class RookMovementRulesValidator extends LocationValidator {

	public boolean isValidMove(Piece rook, Piece targetPiece,
			int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		// The rook can move any number of squares along any rank or file, but
		// may not leap over other pieces. Along with the king, the rook is also
		// involved during the king's castling move.

		if( isTargetLocationFree(targetPiece) || isTargetLocationCaptureable(rook, targetPiece)){

			return isRookPathToTarget(
					sourceRow, sourceColumn, targetRow, targetColumn);
		}
		return false;
	}

	private boolean isRookPathToTarget(
			int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		boolean isValid;
		int diffRow = targetRow - sourceRow;
		int diffColumn = targetColumn - sourceColumn;

		if( diffRow == 0 && diffColumn > 0){
			// right
			isValid = !MoveController.arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,0,+1);

		}else if( diffRow == 0 && diffColumn < 0){
			// left
			isValid = !MoveController.arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,0,-1);

		}else if( diffRow > 0 && diffColumn == 0){
			// up
			isValid = !MoveController.arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,+1,0);

		}else if( diffRow < 0 && diffColumn == 0){
			// down
			isValid = !MoveController.arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,-1,0);

		}else{
			// not moving diagonally
			//System.out.println("not moving straight");
			isValid = false;
		}
		return isValid;
	}	
}
