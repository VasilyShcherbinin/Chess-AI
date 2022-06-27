package ch06.logic;

public class KnightMovementRulesValidator extends LocationValidator {

	public boolean isValidMove(Piece knight, Piece targetPiece,
			int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		// The knight moves to any of the closest squares which are not on the same rank,
		// file or diagonal, thus the move forms an "L"-shape two squares long and one
		// square wide. The knight is the only piece which can leap over other pieces.
		
		// target location possible?
		if( isTargetLocationFree(targetPiece) || isTargetLocationCaptureable(knight, targetPiece)){

			return isKnightPathToTarget(
					sourceRow, sourceColumn, targetRow, targetColumn);
		}
		return false;
	}

	private boolean isKnightPathToTarget(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {

		if( sourceRow+2 == targetRow && sourceColumn+1 == targetColumn){
			// move up up right
			return true;
		}else if( sourceRow+1 == targetRow && sourceColumn+2 == targetColumn){
			// move up right right
			return true;
		}else if( sourceRow-1 == targetRow && sourceColumn+2 == targetColumn){
			// move down right right
			return true;
		}else if( sourceRow-2 == targetRow && sourceColumn+1 == targetColumn){
			// move down down right
			return true;
		}else if( sourceRow-2 == targetRow && sourceColumn-1 == targetColumn){
			// move down down left
			return true;
		}else if( sourceRow-1 == targetRow && sourceColumn-2 == targetColumn){
			// move down left left
			return true;
		}else if( sourceRow+1 == targetRow && sourceColumn-2 == targetColumn){
			// move up left left
			return true;
		}else if( sourceRow+2 == targetRow && sourceColumn-1 == targetColumn){
			// move up up left
			return true;
		}else{
			return false;
		}
	}	
}
