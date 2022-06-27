package ch06.logic;

public class BishopMovementRulesValidator extends LocationValidator {

	public boolean isValidMove(Piece bishop, Piece targetPiece,
			int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		//The bishop can move any number of squares diagonally, but may not leap
		//over other pieces.
		
		// target location possible?
		if( isTargetLocationFree(targetPiece) || isTargetLocationCaptureable(bishop, targetPiece)){

			// first lets check if the path to the target is diagonally at all
			return isBishopPathToTarget(
					sourceRow, sourceColumn, targetRow, targetColumn);
		}
		return false;
	}

	private boolean isBishopPathToTarget(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {

		boolean isValid;
		int diffRow = targetRow - sourceRow;
		int diffColumn = targetColumn - sourceColumn;
		
		if( diffRow==diffColumn && diffColumn > 0){
			// moving diagonally up-right
			isValid = !MoveController.arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,+1,+1);

		}else if( diffRow==-diffColumn && diffColumn > 0){
			// moving diagonally down-right
			isValid = !MoveController.arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,-1,+1);
			
		}else if( diffRow==diffColumn && diffColumn < 0){
			// moving diagonally down-left
			isValid = !MoveController.arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,-1,-1);

		}else if( diffRow==-diffColumn && diffColumn < 0){
			// moving diagonally up-left
			isValid = !MoveController.arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,+1,-1);
			
		}else{
			// not moving diagonally
			//System.out.println(diffRow);
			//System.out.println(diffColumn);
			//System.out.println("not moving diagonally");
			isValid = false;
		}
		return isValid;
	}	
}
