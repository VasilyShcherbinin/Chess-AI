package ch06.logic;

import java.util.List;

import ch06.logic.Player.PlayerColor;


public abstract class Piece {

	private int color;
	public static final int COLOR_WHITE = 0;
	public static final int COLOR_BLACK = 1;
	
	private int type;
	public static final int TYPE_ROOK = 1;
	public static final int TYPE_KNIGHT = 2;
	public static final int TYPE_BISHOP = 3;
	public static final int TYPE_QUEEN = 4;
	public static final int TYPE_KING = 5;
	public static final int TYPE_PAWN = 6;
	
	//Chess is played on a square board of
	//eight rows (called ranks and denoted with numbers 1 to 8)
	//and eight columns (called files and denoted with letters a to h) of squares.
	private int row;
	private int column;
	
	public static final int ROW_1 = 0;
	public static final int ROW_2 = 1;
	public static final int ROW_3 = 2;
	public static final int ROW_4 = 3;
	public static final int ROW_5 = 4;
	public static final int ROW_6 = 5;
	public static final int ROW_7 = 6;
	public static final int ROW_8 = 7;
	
	public static final int COLUMN_A = 0;
	public static final int COLUMN_B = 1;
	public static final int COLUMN_C = 2;
	public static final int COLUMN_D = 3;
	public static final int COLUMN_E = 4;
	public static final int COLUMN_F = 5;
	public static final int COLUMN_G = 6;
	public static final int COLUMN_H = 7;

	private boolean hasBeenPromoted = false;
	private boolean isCaptured = false;
	private int numberOfMoves = 0;

	protected PieceAbbreviation abbreviation;
	protected PieceName name;
	protected Player player;
	
	public Piece(Player player, int color, int type, int row, int column) {
		this.player = player;
		this.color = color;
		this.type = type;
		this.row = row;
		this.column = column;
	}

    public enum PieceName
    {
        Pawn, 
        Bishop, 
        Knight, 
        Rook, 
        Queen, 
        King
    }

    public enum PieceAbbreviation
    {
        P, 
        B, 
        N, 
        R, 
        Q, 
        K
    }

    public String getAbbreviation() {
    	return this.abbreviation.toString();
    }
    
	public int getNumberOfMoves() {
		return this.numberOfMoves;
	}

	public void setNumberOfMoves(int numberOfMoves) {
		this.numberOfMoves = numberOfMoves;
	}
	
	public void increaseNumberOfMoves() {
		this.numberOfMoves++; 
		getPlayer().revokeEnpassantCaptureRight();
	}
    
	public void decreaseNumberOfMoves() {
		this.numberOfMoves--; 
	}
	
	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(Player player) {
		this.player = player; 
	}
	
	public int getRow() {
		return this.row;
	}

	public int getColumn() {
		return this.column;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getColor() {
		return this.color;
	}
	
	@Override
	public String toString() {
		PlayerColor color = (this.color == COLOR_WHITE) ?
				PlayerColor.WHITE : PlayerColor.BLACK;
		
		PieceAbbreviation pa = null;
		switch (this.type) {
			case TYPE_BISHOP: pa = PieceAbbreviation.B; break;
			case TYPE_KING: pa = PieceAbbreviation.K; break;
			case TYPE_KNIGHT: pa = PieceAbbreviation.N; break;
			case TYPE_PAWN: pa = PieceAbbreviation.P; break;
			case TYPE_QUEEN: pa = PieceAbbreviation.Q; break;
			case TYPE_ROOK: pa = PieceAbbreviation.R; break;
		}
		
		String strRow = getRowString(this.row);
		String strColumn = getColumnString(this.column);
		return color.toString() + " " + pa.toString() + " " + strColumn + "/" + strRow;
	}

	public String getInfo() {
		String strRow = getRowString(this.row);
		String strColumn = getColumnString(this.column);
		return strColumn + strRow + ((isCaptured) ? "(c)" : "");
	}

	public String getName() {
		PieceName name = null;
		switch (this.type) {
			case TYPE_BISHOP: name = PieceName.Bishop; break;
			case TYPE_KING: name = PieceName.King; break;
			case TYPE_KNIGHT: name = PieceName.Knight; break;
			case TYPE_PAWN: name = PieceName.Pawn; break;
			case TYPE_QUEEN: name = PieceName.Queen; break;
			case TYPE_ROOK: name = PieceName.Rook; break;
		}
		
		String strRow = getRowString(this.row);
		String strColumn = getColumnString(this.column);
		
		return "The " + name.toString() + " on " + strColumn + strRow;
	}
	
	public int getType() {
		return this.type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public static String getRowString(int row){
		String strRow = "unknown";
		switch (row) {
			case ROW_1: strRow = "1";break;
			case ROW_2: strRow = "2";break;
			case ROW_3: strRow = "3";break;
			case ROW_4: strRow = "4";break;
			case ROW_5: strRow = "5";break;
			case ROW_6: strRow = "6";break;
			case ROW_7: strRow = "7";break;
			case ROW_8: strRow = "8";break;
		}
		return strRow;
	}
	
	public static String getColumnString(int column){
		String strColumn = "unknown";
		switch (column) {
			case COLUMN_A: strColumn = "A";break;
			case COLUMN_B: strColumn = "B";break;
			case COLUMN_C: strColumn = "C";break;
			case COLUMN_D: strColumn = "D";break;
			case COLUMN_E: strColumn = "E";break;
			case COLUMN_F: strColumn = "F";break;
			case COLUMN_G: strColumn = "G";break;
			case COLUMN_H: strColumn = "H";break;
		}
		return strColumn;
	}
	
	public void isCaptured(boolean isCaptured) {
		this.isCaptured = isCaptured;
	}

	public boolean isCaptured() {
		return this.isCaptured;
	}

	public void hasBeenPromoted(boolean promoted) {
		this.hasBeenPromoted = promoted;
	}

	public boolean hasBeenPromoted() {
		return this.hasBeenPromoted;
	}
	
    public void generateLegalMoves(List<Move> moves)
    {
//        this.GenerateLazyMoves(moves, Moves.MoveListNames.All);
//        for (int intIndex = moves.Count - 1; intIndex >= 0; intIndex--)
//        {
//            Move move = moves[intIndex];
//            Move moveUndo = move.Piece.Move(move.Name, move.To);
//            if (move.Piece.Player.IsInCheck)
//            {
//                moves.Remove(move);
//            }
//
//            Model.Move.Undo(moveUndo);
//        }
    }		
	
	protected abstract boolean canMoveTo(int row, int col, ChessGame board);
	protected abstract boolean canMoveTo(int sourceRow, int sourceColumn, int targetRow, int targetColumn);

}
