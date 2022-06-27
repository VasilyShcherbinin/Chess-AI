package ch06.logic;

import java.awt.Component;

import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.List;

import ch06.gui.GuiLabel;

public class PositionValidator {
	
	List<Piece> pieces;
	
	int whiteKings   = 0;
	int blackKings   = 0;
	int whiteQueens  = 0;
	int blackQueens  = 0;
	int whiteRooks   = 0;
	int blackRooks   = 0;
	int whiteBishops = 0;
	int blackBishops = 0;
	int whiteKnights = 0;
	int blackKnights = 0;
	int whitePawns   = 0;
	int blackPawns   = 0;
	int pawnsOnRank1 = 0;
	int pawnsOnRank8 = 0;
	
	List<String> validationResults = new ArrayList<>();
	
	public PositionValidator(List<Piece> pieces) {
		this.pieces = pieces;
		calculatePosition();
	}
	
	private void calculatePosition() {

		for (Piece piece : this.pieces) {
			if(piece.isCaptured()) {
				continue;
			}
			
			if(piece.getType() == Piece.TYPE_KING) {
				if(piece.getColor() == Piece.COLOR_WHITE) {
					whiteKings++;
				} else {
					blackKings++;
				}
				continue;
			}

			if(piece.getType() == Piece.TYPE_QUEEN) {
				if(piece.getColor() == Piece.COLOR_WHITE) {
					whiteQueens++;
				} else {
					blackQueens++;
				}
				continue;
			}

			if(piece.getType() == Piece.TYPE_ROOK) {
				if(piece.getColor() == Piece.COLOR_WHITE) {
					whiteRooks++;
				} else {
					blackRooks++;
				}
				continue;
			}

			if(piece.getType() == Piece.TYPE_BISHOP) {
				if(piece.getColor() == Piece.COLOR_WHITE) {
					whiteBishops++;
				} else {
					blackBishops++;
				}
				continue;
			}

			if(piece.getType() == Piece.TYPE_KNIGHT) {
				if(piece.getColor() == Piece.COLOR_WHITE) {
					whiteKnights++;
				} else {
					blackKnights++;
				}
				continue;
			}		

			if(piece.getType() == Piece.TYPE_PAWN) {
				if (piece.getRow() == 0) {
					pawnsOnRank1++;
				}

				if (piece.getRow() == 7) {
					pawnsOnRank8++;
				}

				if(piece.getColor() == Piece.COLOR_WHITE) {
					whitePawns++;
				} else {
					blackPawns++;
				}
				continue;
			}		
		}
		
	}
	
	public boolean isValidPosition() {
		boolean valid = true;

		if (whiteKings == 0) {
			valid = false;
			addValidationResult("White King is missing");
		} else if (whiteKings > 1) {
			valid = false;
			addValidationResult("Too many white Kings");
		}

		if (blackKings == 0) {
			valid = false;
			addValidationResult("Black King is missing");
		} else if (blackKings > 1) {
			valid = false;
			addValidationResult("Too many black Kings");
		}

		if (whiteQueens > 9) {
			valid = false;
			addValidationResult("Too many white Queens");
		}

		if (blackQueens > 9) {
			valid = false;
			addValidationResult("Too many black Queens");
		}

		if (whiteRooks > 10) {
			valid = false;
			addValidationResult("Too many white Rooks");
		}

		if (blackRooks > 10) {
			valid = false;
			addValidationResult("Too many black Rooks");
		}

		if (whiteBishops > 10) {
			valid = false;
			addValidationResult("Too many white Bishops");
		}

		if (blackBishops > 10) {
			valid = false;
			addValidationResult("Too many black Bishops");
		}

		if (whiteKnights > 10) {
			valid = false;
			addValidationResult("Too many white Knights");
		}

		if (blackKnights > 10) {
			valid = false;
			addValidationResult("Too many black Knights");
		}

		if (whitePawns > 8) {
			valid = false;
			addValidationResult("Too many white Pawns");
		}

		if (blackPawns > 8) {
			valid = false;
			addValidationResult("Too many black Pawns");
		}

		if ((whiteKings + whiteQueens + whiteRooks + whiteBishops + whiteKnights + whitePawns) > 16) {
			valid = false;
			addValidationResult("Too many white pieces on board");
		}

		if ((blackKings + blackQueens + blackRooks + blackBishops + blackKnights + blackPawns) > 16) {
			valid = false;
			addValidationResult("Too many black pieces on board");
		}

		if (pawnsOnRank8 > 0) {
			valid = false;
			addValidationResult("No Pawns on 8th rank allowed");
		}

		if (pawnsOnRank1 > 0) {	
			valid = false;
			addValidationResult("No Pawns on 1st rank allowed");
		}
		return valid;
	}	

	public boolean isValidKingsPosition()
	{
		boolean valid = true;
		int whiteKingRow = 0;
		int whiteKingColumn = 0;
		int blackKingRow = 0;
		int blackKingColumn = 0;
		
		if (whiteKings == 1 && blackKings == 1) {
			for (Piece piece : this.pieces) {
				if(piece.isCaptured()) {
					continue;
				}
				
				if(piece.getType() == Piece.TYPE_KING) {
					if(piece.getColor() == Piece.COLOR_WHITE) {
						whiteKingRow = piece.getRow();
						whiteKingColumn = piece.getColumn();
					} else {
						blackKingRow = piece.getRow();
						blackKingColumn = piece.getColumn();
					}
				}
			}
			
			int diffRow = whiteKingRow - blackKingRow;
			int diffColumn = whiteKingColumn - blackKingColumn;
			
			if (diffRow == 1  && (diffColumn == -1 || diffColumn == 0 || diffColumn == 1) ||
				diffRow == 0  && (diffColumn == -1 || diffColumn == 1) ||	
				diffRow == -1 && (diffColumn == -1 || diffColumn == 0 || diffColumn == 1)) {
				valid = false;
				addValidationResult("Kings cannot stand next to each other");
			}
		}
		return valid;		
	}
	
	public boolean isDrawByInsufficientMaterial()
	{
		boolean isDrawn = false;
		int piecesTotal = 
				whiteKings +
				blackKings +
				whiteQueens +
				blackQueens +
				whiteRooks +
				blackRooks +
				whiteBishops +
				blackBishops +
				whiteKnights +
				blackKnights +
				whitePawns +
				blackPawns;				
				
		//King against King
		if (piecesTotal == 2 && whiteKings == 1 && blackKings == 1) {
			isDrawn = true;
		}

		//King and Knight against King
		if(piecesTotal == 3 && (  
			(whiteKings == 1 && whiteKnights == 1 && blackKings == 1) ||
			(blackKings == 1 && blackKnights == 1 && whiteKings == 1))) {
			isDrawn = true;
		}
		
		//King and Bishop against King
		if(piecesTotal == 3 && (
			(whiteKings == 1 && whiteBishops == 1 && blackKings == 1) ||
			(blackKings == 1 && blackBishops == 1 && whiteKings == 1))) {
			isDrawn = true;
		}
		
		//King and two Knights against King
		if(piecesTotal == 4 && (
			(whiteKings == 1 && whiteKnights == 2 && blackKings == 1) ||
			(blackKings == 1 && blackKnights == 2 && whiteKings == 1))) {
			isDrawn = true;
		}
		
		//King and Knight against King and Knight
		if(piecesTotal == 4 && (
			(whiteKings == 1 && whiteKnights == 1 && blackKings == 1 && blackKnights == 1) ||
			(blackKings == 1 && blackKnights == 1 && whiteKings == 1 && whiteKnights == 1))) {
			isDrawn = true;
		}
		
		//King and Bishop against King and Bishop
		if(piecesTotal == 4 && (
			(whiteKings == 1 && whiteBishops == 1 && blackKings == 1 && blackBishops == 1) ||
			(blackKings == 1 && blackBishops == 1 && whiteKings == 1 && whiteBishops == 1))) {
			isDrawn = true;
		}
		
		if(isDrawn) {
			addValidationResult("Draw. (Insufficient material)");
		}
		return isDrawn;
	}
	
	private void addValidationResult(String e) {
		this.validationResults.add(e);
	}
	
	public String getFirstValidationError() {
		return this.validationResults.get(0); 
	}
}
