package ch06.logic;

import java.util.List;

public class PlayerWhite extends Player {

	public PlayerWhite(Player.Intellegence intellegence) {
		setIntellegence(intellegence);
		setColor(Player.PlayerColor.WHITE);
		setPiecesAtStartingPositions();
	}
	
	@Override
	public void setPiecesAtStartingPositions() {
		getPiecesAtStartingPositions(this.pieces);
	}

	@Override
	public void getPiecesAtStartingPositions(List<Piece> pieces) {
		
		pieces.clear();
		int color = this.getColor();
		king = new King(this, color, Piece.TYPE_KING, Piece.ROW_1, Piece.COLUMN_E);		
		
		pieces.add(new Rook(this, color, Piece.TYPE_ROOK, Piece.ROW_1, Piece.COLUMN_A));
		pieces.add(new Knight(this, color, Piece.TYPE_KNIGHT, Piece.ROW_1, Piece.COLUMN_B));
		pieces.add(new Bishop(this, color, Piece.TYPE_BISHOP, Piece.ROW_1, 	Piece.COLUMN_C));
		pieces.add(new Queen(this, color, Piece.TYPE_QUEEN, Piece.ROW_1, Piece.COLUMN_D));
		pieces.add(king);
		pieces.add(new Bishop(this, color, Piece.TYPE_BISHOP, Piece.ROW_1, 	Piece.COLUMN_F));
		pieces.add(new Knight(this, color, Piece.TYPE_KNIGHT, Piece.ROW_1, Piece.COLUMN_G));
		pieces.add(new Rook(this, color, Piece.TYPE_ROOK, Piece.ROW_1, Piece.COLUMN_H));

		// pawns
		int currentColumn = Piece.COLUMN_A;
		for (int i = 0; i < 8; i++) {
			pieces.add(new Pawn(this, color, Piece.TYPE_PAWN, Piece.ROW_2, currentColumn));
			currentColumn++;
		}		
	}

	
//	@Override
//	public void setPiecesAtEditedPositions(List<Piece> pieces) {
//		this.pieces.clear();
//		for (Piece piece : pieces) {
//			if (piece.getColor() == this.getColor() && piece.isCaptured() == false) {
//				piece.setPlayer(this);
//				this.pieces.add(piece);
//				
//				if (piece.getType() == Piece.TYPE_KING) {
//					king = piece;
//				}
//			}
//		}	
//	}
}
