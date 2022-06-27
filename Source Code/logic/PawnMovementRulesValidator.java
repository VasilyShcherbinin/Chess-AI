package ch06.logic;

public class PawnMovementRulesValidator extends LocationValidator {

	public boolean isValidMove(Piece sourcePiece, Piece targetPiece, 
			int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		
		boolean isValid = false;
		// The pawn may move forward to the unoccupied square immediately in front
		// of it on the same file, or on its first move it may advance two squares
		// along the same file provided both squares are unoccupied
		if( isTargetLocationFree(targetPiece) ) {
			if (sourceColumn == targetColumn) {
				// same column
				if (sourcePiece.getColor() == Piece.COLOR_WHITE) {
					isValid = isValidWhitePawnMove(sourceRow, targetRow, sourceColumn, targetColumn);
				} else {
					isValid = isValidBlackPawnMove(sourceRow, targetRow, sourceColumn, targetColumn);
				}

			} else {
				// not the same column
				isValid = false;

				//				if(sourceRow == Piece.ROW_4 && targetColumn == this.blackPawnEnPassantColumn) {
				//					isValid = true; //(sourceRow-1 == targetRow);
				//				} else {
				//					isValid = false;
				//				}
			}

			// or it may move
			// to a square occupied by an opponent’s piece, which is diagonally in front
			// of it on an adjacent file, capturing that piece. 
			
		} else if( isTargetLocationCaptureable(sourcePiece, targetPiece) ) {

			if( sourceColumn+1 == targetColumn || sourceColumn-1 == targetColumn) {
				// one column to the right or left
				if(  sourcePiece.getColor() == Piece.COLOR_WHITE ) {
					// white
					if( sourceRow+1 == targetRow ) {
						// move one up
						isValid = true;
					} else {
						//System.out.println("not moving one up");
						isValid = false;
					}
				} else {
					// black
					if( sourceRow-1 == targetRow ) {
						// move one down
						isValid = true;
					} else {
						//System.out.println("not moving one down");
						isValid = false;
					}
				}
			} else {
				// note one column to the left or right
				//System.out.println("not moving one column to left or right");
				isValid = false;
			}
		}



		// on its first move it may advance two squares
		// ..

		// The pawn has two special
		// moves, the en passant capture, and pawn promotion.

		// en passant
		if (sourcePiece.getColor() == Piece.COLOR_WHITE) {
			if(sourceRow == Piece.ROW_5 &&
					targetRow == Piece.ROW_6 &&
					targetColumn == sourcePiece.getPlayer().getOpposingPlayer().getPawnEnpassantColumn()) {
				isValid = true;
			} 
		} else {
			if(sourceRow == Piece.ROW_4 &&
					targetRow == Piece.ROW_3 &&
					targetColumn == sourcePiece.getPlayer().getOpposingPlayer().getPawnEnpassantColumn()) {
				isValid = true;
			} 
		}
		
		return isValid;
	}
	
	private boolean isValidWhitePawnMove(int sourceRow, int targetRow, int sourceColumn, int targetColumn) {
		boolean isValid;

		if (sourceRow + 1 == targetRow) {
			isValid = true;
		} else if (sourceRow + 2 == targetRow && sourceRow == Piece.ROW_2) {
			isValid = !MoveController.arePiecesBetweenSourceAndTarget(sourceRow, sourceColumn,
					targetRow, targetColumn, +1, 0);
		} else {
			//System.out.println("not moving one or two up");
			isValid = false;
		}
		return isValid;
	}
	
	private boolean isValidBlackPawnMove(int sourceRow, int targetRow, int sourceColumn, int targetColumn) {
		boolean isValid;

		if (sourceRow - 1 == targetRow) {
			isValid = true;
		} else if (sourceRow - 2 == targetRow && sourceRow == Piece.ROW_7) {
			isValid = !MoveController.arePiecesBetweenSourceAndTarget(sourceRow, sourceColumn,
					targetRow, targetColumn, -1, 0);
		} else {
			//System.out.println("not moving one or two down");
			isValid = false;
		}
		return isValid;
	}	
}
