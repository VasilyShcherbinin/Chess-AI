package ch06.logic;

public class Move {
	
	public int sourceRow;
	public int sourceColumn;
	public int targetRow;
	public int targetColumn;
	
	private Piece piece;
	private Piece pieceCaptured;
	
	private MoveName moveName;
	
	public Move(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		this.sourceRow = sourceRow;
		this.sourceColumn = sourceColumn;
		this.targetRow = targetRow;
		this.targetColumn = targetColumn;
	}

	public Move(Piece piece, int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		this(sourceRow, sourceColumn, targetRow, targetColumn);
		this.piece = piece;
	}	

	@Override
	public String toString() {
		return Piece.getColumnString(sourceColumn)+"/"+Piece.getRowString(sourceRow)
		+" -> "+Piece.getColumnString(targetColumn)+"/"+Piece.getRowString(targetRow);
		//return sourceRow+"/"+sourceColumn+" -> "+targetRow+"/"+targetColumn;
	}
	
	public Move clone(){
		return new Move(this.piece, this.sourceRow,this.sourceColumn,this.targetRow,this.targetColumn);
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	public void setPieceCaptured(Piece pieceCaptured) {
		this.pieceCaptured = pieceCaptured;
	}
	
	public Piece getPieceCaptured() {
		return this.pieceCaptured;
	}

	public Piece getPiece() {
		return this.piece;
	}

	public MoveName getName() {
		return this.moveName;
	}

	public void setName(MoveName moveName) {
		this.moveName = moveName;
	}
	
    public enum MoveName {
        Standard, 
        CastleQueenSide, 
        CastleKingSide, 
        PawnPromotionQueen, 
        PawnPromotionRook, 
        PawnPromotionKnight, 
        PawnPromotionBishop, 
        EnPassant, 
        NullMove
    }

	
}
