package ch06.logic;

import java.util.ArrayList;
import java.util.List;

public class PositionEditor {

	private List<Piece> pieces = new ArrayList<Piece>();
	
	public PositionEditor() {
	}
	
	public void initializePieces(List<Piece> whitePieces, List<Piece> blackPieces) {
		this.pieces.clear();
		for (Piece piece : whitePieces) {
			if (piece.isCaptured() == false) {
				this.pieces.add(piece);
			}
		}
		for (Piece piece : blackPieces) {
			if (piece.isCaptured() == false) {
				this.pieces.add(piece);
			}
		}	
	}

	public void removePieces() {
		this.pieces.clear();
	}
	
	public void addPiece(Piece piece) {
		this.pieces.add(piece);		
	}
	
	public void removePiece(Piece piece) {
		this.pieces.remove(piece);
	}	
	
	public List<Piece> getPieces() {
		return this.pieces;
	}

	public static Piece createPiece(int color, int type, int row, int column) {
		Piece piece = null;
		switch (type) {
		case Piece.TYPE_BISHOP:
			piece = new Bishop(null, color, type, row, column);break;
		case Piece.TYPE_KING:
			piece = new King(null, color, type, row, column);break;
		case Piece.TYPE_KNIGHT:
			piece = new Knight(null, color, type, row, column);break;
		case Piece.TYPE_PAWN:
			piece = new Pawn(null, color, type, row, column);break;
		case Piece.TYPE_QUEEN:
			piece = new Queen(null, color, type, row, column);break;
		case Piece.TYPE_ROOK:
			piece = new Rook(null, color, type, row, column);break;
		default: break;
		}
		return piece;
	}	    

}
