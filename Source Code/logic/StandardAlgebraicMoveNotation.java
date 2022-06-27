package ch06.logic;

import ch06.logic.Move.MoveName;
import ch06.logic.Player.PlayerStatus;

public class StandardAlgebraicMoveNotation {

	public static String getMoveNotation(Piece piece, Move move, PlayerStatus opponentStatus) {

		Player player = piece.getPlayer();
		String moveNotation = getForMove(piece); 
		String castlingNotation = getForCastling(piece, move);

		if (castlingNotation != "") {
			moveNotation = castlingNotation;
		} else {

			if(move.getPieceCaptured() != null) {
				moveNotation = getForCapture(piece, move);
			}

			if (move.getName() == MoveName.EnPassant && move.getPieceCaptured() != null) {
				moveNotation = moveNotation + "e.p.";
			}
			
			if (piece.hasBeenPromoted())
			{
				moveNotation = moveNotation.substring(1, 3) + "=";
				moveNotation = moveNotation + piece.getAbbreviation();
			}

			switch (opponentStatus) {
			case InCheckMate:
				moveNotation = moveNotation + (player.getColor() == Piece.COLOR_WHITE
					? "# 1-0" 
					: "# 0-1"); break;
			case InStalemate:
			case InsufficientMaterial:
			case RepetitionOfPosition:
			case FiftyMoveRule:
				moveNotation = moveNotation + (" 1/2-1/2"); break;
			case InCheck:
				moveNotation = moveNotation + "+"; break;
			default:
				break;
			}

			//todo
			//Disambiguating moves
		}
		return moveNotation;
	}

	private static String getPieceType(int pieceType) {
		String strType = "unknown";
		switch (pieceType) {
		case Piece.TYPE_BISHOP: strType = "B";break;
		case Piece.TYPE_KING: strType = "K";break;
		case Piece.TYPE_KNIGHT: strType = "N";break;
		case Piece.TYPE_PAWN: strType = "";break;
		case Piece.TYPE_QUEEN: strType = "Q";break;
		case Piece.TYPE_ROOK: strType = "R";break;
		}
		return strType;
	}

	private static String getForMove(Piece piece) {
		String strType = getPieceType(piece.getType());
		String strRow = Piece.getRowString(piece.getRow());
		String strColumn = Piece.getColumnString(piece.getColumn()).toLowerCase();
		return strType+strColumn+strRow;
	}

	private static String getForCastling(Piece piece, Move move) {
		String s = "";
		if(piece.getType() != Piece.TYPE_KING) {
			return s;
		}

		if(move.sourceRow == Piece.ROW_1 || move.sourceRow == Piece.ROW_8) {
			if(move.sourceColumn == Piece.COLUMN_E && move.targetColumn == Piece.COLUMN_G) {
				s="0-0";
			}
			else {
				if(move.sourceColumn == Piece.COLUMN_E && move.targetColumn == Piece.COLUMN_C) {
					s="0-0-0";
				}
			}
		}
		return s;
	}

	private static String getForCapture(Piece piece, Move move) {
		String type = getPieceType(piece.getType());		
		String sourceColumn = Piece.getColumnString(move.sourceColumn).toLowerCase();
		String row = Piece.getRowString(piece.getRow());
		String column = Piece.getColumnString(piece.getColumn()).toLowerCase();
		String s = (type=="") 
				? sourceColumn+"x"+column+row 
				: type+"x"+column+row;
		return s;
	}	

	//todo
	//getForDisambiguatingMoves
	//getForPawnPromotion
	//getForCheckAndCheckmate
	//getForEndOfGame	
}
