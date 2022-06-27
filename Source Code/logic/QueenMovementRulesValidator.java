package ch06.logic;

public class QueenMovementRulesValidator  extends LocationValidator {

	public boolean isValidMove(Piece queen, Piece targetPiece,
			int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		// The queen combines the power of the rook and bishop and can move any number
		// of squares along rank, file, or diagonal, but it may not leap over other pieces.
		//
		
		boolean validMove = (new BishopMovementRulesValidator()).isValidMove(
				queen, targetPiece, 
				sourceRow, sourceColumn, 
				targetRow, targetColumn);		

		validMove |=  (new RookMovementRulesValidator()).isValidMove( 
				queen, targetPiece, 
				sourceRow, sourceColumn, 
				targetRow, targetColumn);		

		return validMove;
	}
	
}
