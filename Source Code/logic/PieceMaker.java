package ch06.logic;

import java.util.List;

public class PieceMaker {
//
//	public static void initializePieces(List<Piece> pieces) {
//		// create and place pieces
//		// rook, knight, bishop, queen, king, bishop, knight, and rook
//
//		pieces.add(createPiece(Piece.COLOR_BLACK, Piece.TYPE_ROOK, Piece.ROW_8, Piece.COLUMN_A));
//		pieces.add(createPiece(Piece.COLOR_BLACK, Piece.TYPE_KNIGHT, Piece.ROW_8, Piece.COLUMN_B));
//		pieces.add(createPiece(Piece.COLOR_BLACK, Piece.TYPE_BISHOP, Piece.ROW_8, Piece.COLUMN_C));
//		pieces.add(createPiece(Piece.COLOR_BLACK, Piece.TYPE_QUEEN, Piece.ROW_8, Piece.COLUMN_D));
//		pieces.add(createPiece(Piece.COLOR_BLACK, Piece.TYPE_KING, Piece.ROW_8, Piece.COLUMN_E));
//		pieces.add(createPiece(Piece.COLOR_BLACK, Piece.TYPE_BISHOP, Piece.ROW_8, Piece.COLUMN_F));
//		pieces.add(createPiece(Piece.COLOR_BLACK, Piece.TYPE_KNIGHT, Piece.ROW_8, Piece.COLUMN_G));
//		pieces.add(createPiece(Piece.COLOR_BLACK, Piece.TYPE_ROOK, Piece.ROW_8, Piece.COLUMN_H));
//
//		// pawns
//		int currentColumn = Piece.COLUMN_A;
//		for (int i = 0; i < 8; i++) {
//			pieces.add(createPiece(Piece.COLOR_BLACK, Piece.TYPE_PAWN, Piece.ROW_7, currentColumn));
//			currentColumn++;
//		}
//		
//		pieces.add(createPiece(Piece.COLOR_WHITE, Piece.TYPE_ROOK, Piece.ROW_1, Piece.COLUMN_A));
//		pieces.add(createPiece(Piece.COLOR_WHITE, Piece.TYPE_KNIGHT, Piece.ROW_1, Piece.COLUMN_B));
//		pieces.add(createPiece(Piece.COLOR_WHITE, Piece.TYPE_BISHOP, Piece.ROW_1, 	Piece.COLUMN_C));
//		pieces.add(createPiece(Piece.COLOR_WHITE, Piece.TYPE_QUEEN, Piece.ROW_1, Piece.COLUMN_D));
//		pieces.add(createPiece(Piece.COLOR_WHITE, Piece.TYPE_KING, Piece.ROW_1, Piece.COLUMN_E));
//		pieces.add(createPiece(Piece.COLOR_WHITE, Piece.TYPE_BISHOP, Piece.ROW_1, 	Piece.COLUMN_F));
//		pieces.add(createPiece(Piece.COLOR_WHITE, Piece.TYPE_KNIGHT, Piece.ROW_1, Piece.COLUMN_G));
//		pieces.add(createPiece(Piece.COLOR_WHITE, Piece.TYPE_ROOK, Piece.ROW_1, Piece.COLUMN_H));
//
//		// pawns
//		currentColumn = Piece.COLUMN_A;
//		for (int i = 0; i < 8; i++) {
//			pieces.add(createPiece(Piece.COLOR_WHITE, Piece.TYPE_PAWN, Piece.ROW_2, currentColumn));
//			currentColumn++;
//		}
//	}
//	
//	public static Piece createPiece(int color, int type, int row, int column) {
//		return new King(color, type, row, column);
//	}	
}
