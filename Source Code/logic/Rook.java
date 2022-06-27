package ch06.logic;

public class Rook extends Piece {

	public Rook(Player player, int color, int type, int row, int column) {
		super(player, color, type, row, column);
		
		name = PieceName.Rook;
		abbreviation = PieceAbbreviation.R;
	}
	
	//	@Override
	protected boolean canMoveTo(int row, int col, ChessGame board) {
		// TODO Auto-generated method stub
		return false;
	}

	protected boolean canMoveTo(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		boolean canMove;
		int diffRow = targetRow - sourceRow;
		int diffColumn = targetColumn - sourceColumn;
		
		if( diffRow == 0 && diffColumn > 0){
			// right
			canMove = !MoveController.arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,0,+1);

		}else if( diffRow == 0 && diffColumn < 0){
			// left
			canMove = !MoveController.arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,0,-1);
			
		}else if( diffRow > 0 && diffColumn == 0){
			// up
			canMove = !MoveController.arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,+1,0);

		}else if( diffRow < 0 && diffColumn == 0){
			// down
			canMove = !MoveController.arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,-1,0);
			
		}else{
			// not moving diagonally
			//System.out.println("not moving straight");
			canMove = false;
		}
		return canMove;
	}	
}
