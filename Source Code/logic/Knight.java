package ch06.logic;

public class Knight extends Piece {

	public Knight(Player player, int color, int type, int row, int column) {
		super(player, color, type, row, column);
		
		name = PieceName.Knight;
		abbreviation = PieceAbbreviation.N;
	}
	
	//	@Override
	protected boolean canMoveTo(int row, int col, ChessGame board) {
		// TODO Auto-generated method stub
		return false;
	}

	protected boolean canMoveTo(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {

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