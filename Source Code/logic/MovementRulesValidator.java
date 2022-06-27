package ch06.logic;

public class MovementRulesValidator {

	public static boolean validatePieceMovementRules(
			Piece sourcePiece, Piece targetPiece,
			int sourceRow, int sourceColumn, 
			int targetRow, int targetColumn) {

		boolean validMove = false;
		
		switch (sourcePiece.getType()) {
		case Piece.TYPE_BISHOP:
			validMove = (new BishopMovementRulesValidator()).isValidMove(
					sourcePiece, targetPiece, 
					sourceRow, sourceColumn, 
					targetRow, targetColumn);break;
		
		case Piece.TYPE_KING:
			validMove = (new KingMovementRulesValidator()).isValidMove(
					sourcePiece, targetPiece, 
					sourceRow, sourceColumn, 
					targetRow, targetColumn);break;

		case Piece.TYPE_KNIGHT:
			validMove =  (new KnightMovementRulesValidator()).isValidMove(
					sourcePiece, targetPiece, 
					sourceRow, sourceColumn, 
					targetRow, targetColumn);break;

		
		case Piece.TYPE_PAWN:
			validMove =  (new PawnMovementRulesValidator()).isValidMove(
					sourcePiece, targetPiece, 
					sourceRow, sourceColumn, 
					targetRow, targetColumn);break;
		
		case Piece.TYPE_QUEEN:
			validMove =  (new QueenMovementRulesValidator()).isValidMove(
					sourcePiece, targetPiece, 
					sourceRow, sourceColumn, 
					targetRow, targetColumn);break;
			
		
		case Piece.TYPE_ROOK:
			validMove =  (new RookMovementRulesValidator()).isValidMove(
					sourcePiece, targetPiece, 
					sourceRow, sourceColumn, 
					targetRow, targetColumn);break;
		
		default: break;
		}
		return validMove;
	}	
	
}
