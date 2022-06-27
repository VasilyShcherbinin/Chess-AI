package ch06.logic;

public class MoveController {

	public static boolean arePiecesBetweenSourceAndTarget(
		int sourceRow, int sourceColumn,
		int targetRow, int targetColumn, 
		int rowIncrementPerStep, int columnIncrementPerStep) {

		int currentRow = sourceRow + rowIncrementPerStep;
		int currentColumn = sourceColumn + columnIncrementPerStep;
		while(true){
			if(currentRow == targetRow && currentColumn == targetColumn){
				break;
			}
			if( currentRow < Piece.ROW_1 || currentRow > Piece.ROW_8
					|| currentColumn < Piece.COLUMN_A || currentColumn > Piece.COLUMN_H){
				break;
			}

			//if( this.chessGame.isNonCapturedPieceAtLocation(currentRow, currentColumn)){
			if(ChessGame.playerWhite.isNonCapturedPieceAtLocation(currentRow, currentColumn) ||
					ChessGame.playerBlack.isNonCapturedPieceAtLocation(currentRow, currentColumn)) {
				//System.out.println("pieces in between source and target");
				return true;
			}

			currentRow += rowIncrementPerStep;
			currentColumn += columnIncrementPerStep;
		}
		return false;
	}
	
	public static boolean playerCanAttackSquare(Player player, int row, int column) {
		for (Piece piece : player.getNonCapturedPieces()) {
			if (isThreateningPiece(piece, row, column)) {
				return true;
			}
		}
		return false;		
	}
	
	public static boolean isThreateningPiece(Piece piece, int targetRow, int targetColumn) {
		boolean canAttack = false;
		int sourceRow = piece.getRow(); 
		int sourceColumn = piece.getColumn();

		switch (piece.getType()) {
		case Piece.TYPE_BISHOP:
			canAttack = ((Bishop)piece).canMoveTo(sourceRow, sourceColumn, targetRow, targetColumn); 
			break;
		case Piece.TYPE_KING:
			canAttack = ((King)piece).canMoveTo(sourceRow, sourceColumn, targetRow, targetColumn);
			break;
		case Piece.TYPE_KNIGHT:
			canAttack = ((Knight)piece).canMoveTo(sourceRow, sourceColumn, targetRow, targetColumn); 
			break;
		case Piece.TYPE_PAWN:
			canAttack = ((Pawn)piece).canMoveTo(sourceRow, sourceColumn, targetRow, targetColumn);
			break;
		case Piece.TYPE_QUEEN:
			canAttack = ((Queen)piece).canMoveTo(sourceRow, sourceColumn, targetRow, targetColumn);
			break;
		case Piece.TYPE_ROOK:
			canAttack = ((Rook)piece).canMoveTo(sourceRow, sourceColumn, targetRow, targetColumn);
			break;
		default: break;
		}
		return canAttack;
	}	
	
	public static boolean isAttackerCanBeBlocked(
			Piece sourcePiece, 
			Piece targetPiece, 
			int targetRow, int targetColumn, 
			Piece attackerPiece, Piece king) {
			
			boolean isBlocked = false;
			//int sourceRow = sourcePiece.getRow(); 
			//int sourceColumn = sourcePiece.getColumn();

			switch (attackerPiece.getType()) {
			case Piece.TYPE_BISHOP:
				isBlocked = isBishopBlocked(targetRow, targetColumn, 
						sourcePiece, targetPiece, 
						attackerPiece, king);   
				break;
			case Piece.TYPE_QUEEN:
				isBlocked = isQueenBlocked(targetRow, targetColumn, 
						sourcePiece, targetPiece, 
						attackerPiece, king);
				break;
			case Piece.TYPE_ROOK:
				isBlocked = isRookBlocked(targetRow, targetColumn, 
						sourcePiece, targetPiece, 
						attackerPiece, king);
				break;
			default: break;
			}
			return isBlocked;
		}	
	
	private static boolean isBishopBlocked(int targetRow, int targetColumn, 
			Piece sourcePiece, Piece targetPiece, 
			Piece threateningPiece, Piece king) {

		int threateningRow = threateningPiece.getRow();
		int threateningColumn = threateningPiece.getColumn();
		int kingRow = king.getRow();
		int kingColumn = king.getColumn();
		
		boolean isBlocked;
		int diffRow = kingRow - threateningRow;
		int diffColumn = kingColumn - threateningColumn;

		if( diffRow==diffColumn && diffColumn > 0){
			// moving diagonally up-right
			isBlocked = isThreateningPieceBlocked(targetRow, targetColumn,
					sourcePiece, targetPiece, 
					threateningRow,threateningColumn,kingRow,kingColumn,+1,+1);

		}else if( diffRow==-diffColumn && diffColumn > 0){
			// moving diagnoally down-right
			isBlocked = isThreateningPieceBlocked(targetRow, targetColumn,
					sourcePiece, targetPiece, 
					threateningRow,threateningColumn,kingRow,kingColumn,-1,+1);

		}else if( diffRow==diffColumn && diffColumn < 0){
			// moving diagnoally down-left
			isBlocked = isThreateningPieceBlocked(targetRow, targetColumn,
					sourcePiece, targetPiece, 
					threateningRow,threateningColumn,kingRow,kingColumn,-1,-1);

		}else if( diffRow==-diffColumn && diffColumn < 0){
			// moving diagnoally up-left
			isBlocked = isThreateningPieceBlocked(targetRow, targetColumn,
					sourcePiece, targetPiece, 
					threateningRow,threateningColumn,kingRow,kingColumn,+1,-1);

		}else{
			isBlocked = false;
		}
		return isBlocked;
	}
	
	private static boolean isRookBlocked(int targetRow, int targetColumn, 
			Piece sourcePiece, Piece targetPiece, 
			Piece threateningPiece, Piece king) {

		int threateningRow = threateningPiece.getRow();
		int threateningColumn = threateningPiece.getColumn();
		int kingRow = king.getRow();
		int kingColumn = king.getColumn();
		
		boolean isBlocked;
		int diffRow = kingRow - threateningRow;
		int diffColumn = kingColumn - threateningColumn;
		
		if( diffRow == 0 && diffColumn > 0){
			// right
			isBlocked =  isThreateningPieceBlocked(targetRow, targetColumn,
					sourcePiece, targetPiece, threateningRow,threateningColumn,kingRow,kingColumn,0,+1);

		}else if( diffRow == 0 && diffColumn < 0){
			// left
			isBlocked =  isThreateningPieceBlocked(targetRow, targetColumn,
					sourcePiece, targetPiece, 
					threateningRow,threateningColumn,kingRow,kingColumn,0,-1);
			
		}else if( diffRow > 0 && diffColumn == 0){
			// up
			isBlocked =  isThreateningPieceBlocked(targetRow, targetColumn,
					sourcePiece, targetPiece, 
					threateningRow,threateningColumn,kingRow,kingColumn,+1,0);

		}else if( diffRow < 0 && diffColumn == 0){
			// down
			isBlocked =  isThreateningPieceBlocked(targetRow, targetColumn,
					sourcePiece, targetPiece, 
					threateningRow,threateningColumn,kingRow,kingColumn,-1,0);
			
		}else{
			isBlocked = false;
		}
		return isBlocked;
	}
	
	private static boolean isQueenBlocked(int targetRow, int targetColumn, 
			Piece sourcePiece, Piece targetPiece, 
			Piece threateningPiece, Piece king) {
		boolean result = isBishopBlocked(targetRow, targetColumn, 
				sourcePiece, targetPiece, 
				threateningPiece, king);
		result |= isRookBlocked(targetRow, targetColumn, 
				sourcePiece, targetPiece, 
				threateningPiece, king);
		return result;
	}
	
	private static boolean isThreateningPieceBlocked(int targetRow, int targetColumn, 
			Piece sourcePiece,
			Piece targetPiece,
			int threateningRow, int threateningColumn,
			int kingRow, int kingColumn, int rowIncrementPerStep, int columnIncrementPerStep) {

		int currentRow = threateningRow + rowIncrementPerStep;
		int currentColumn = threateningColumn + columnIncrementPerStep;
		while(true) {
			if(currentRow == kingRow && currentColumn == kingColumn){
				break;
			}
			
			boolean validPieceMove = MovementRulesValidator.validatePieceMovementRules(
					sourcePiece, targetPiece, sourcePiece.getRow(), sourcePiece.getColumn(), 
					targetRow, targetColumn);
			
			boolean same = (targetRow == currentRow && targetColumn == currentColumn);
			
			if ( validPieceMove && same) {
				return true;
			}

//			if(blockingRow == currentRow && blockingColumn == currentColumn) {
//				return true;
//			}
			
			currentRow += rowIncrementPerStep;
			currentColumn += columnIncrementPerStep;
		}
		return false;
	}	
}
