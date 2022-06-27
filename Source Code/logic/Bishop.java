package ch06.logic;

public class Bishop extends Piece {

	public Bishop(Player player, int color, int type, int row, int column) {
		super(player, color, type, row, column);
		
		name = PieceName.Bishop;
		abbreviation = PieceAbbreviation.B;
	}
	
	//	@Override
	protected boolean canMoveTo(int row, int col, ChessGame board) {
		// TODO Auto-generated method stub
		return false;
	}

	//	@Override
	protected boolean canMoveTo(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {

		boolean canMove;
		int diffRow = targetRow - sourceRow;
		int diffColumn = targetColumn - sourceColumn;
		
		if( diffRow==diffColumn && diffColumn > 0){
			// moving diagonally up-right
			canMove = !MoveController.arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,+1,+1);

		}else if( diffRow==-diffColumn && diffColumn > 0){
			// moving diagonally down-right
			canMove = !MoveController.arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,-1,+1);
			
		}else if( diffRow==diffColumn && diffColumn < 0){
			// moving diagonally down-left
			canMove = !MoveController.arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,-1,-1);

		}else if( diffRow==-diffColumn && diffColumn < 0){
			// moving diagonally up-left
			canMove = !MoveController.arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,+1,-1);
			
		}else{
			// not moving diagonally
			canMove = false;
		}
		return canMove;
	}	
}

