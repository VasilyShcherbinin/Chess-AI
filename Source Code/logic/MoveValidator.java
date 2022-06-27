package ch06.logic;

public class MoveValidator {

	//private ChessGame chessGame;
	private Piece sourcePiece;
	private Piece targetPiece;

	private String validationMessage;
	
//	public MoveValidator(ChessGame chessGame) {
//		this.chessGame = chessGame;
//	}

	public MoveValidator() {
	}
	
	public String getValidationMessage() {
		return this.validationMessage;
	}	
	
	/**
	 * Checks if the specified move is valid
	 * @param move to validate
	 * @return true if move is valid, false if move is invalid
	 */
	public boolean isMoveValid(Player player, Move move) {
		
		int sourceRow = move.sourceRow;
		int sourceColumn = move.sourceColumn;
		int targetRow = move.targetRow;
		int targetColumn = move.targetColumn;
		
		this.validationMessage = null;
		
		this.sourcePiece = ChessGame.playerWhite.getNonCapturedPieceAtLocation(sourceRow, sourceColumn);
		if (this.sourcePiece == null) {
			this.sourcePiece = ChessGame.playerBlack.getNonCapturedPieceAtLocation(sourceRow, sourceColumn);			
		}
		this.targetPiece = ChessGame.playerWhite.getNonCapturedPieceAtLocation(targetRow, targetColumn);
		if (this.targetPiece == null) {
			this.targetPiece = ChessGame.playerBlack.getNonCapturedPieceAtLocation(targetRow, targetColumn);
		}
		
		// source piece does not exist
		if( this.sourcePiece == null ){
			onNoSource();
			return false;
		}
		
		Piece king = player.getKing();

		if(((King)king).determineCheckStatus()) {

			onCheck(king.getColor());

			//logic:
			//for each opponent's threatening piece:
			//source piece can be placed between the king and the opponent's threatening piece only
			
			if(!(this.sourcePiece instanceof King)) {
				
				int kingRow = king.getRow(); 
				int kingColumn = king.getColumn();
				
				for (Piece piece : player.getOpposingPlayer().getNonCapturedPieces()) {
					if (MoveController.isThreateningPiece(piece, kingRow, kingColumn)) {
						
						boolean isBlocked = MoveController.isAttackerCanBeBlocked(
							this.sourcePiece, 
							this.targetPiece,
							targetRow, targetColumn, 
							piece, king);
						
						boolean isCaptured = MovementRulesValidator.validatePieceMovementRules(
							this.sourcePiece, 
							this.targetPiece,
							sourceRow, sourceColumn, 
							piece.getRow(), piece.getColumn()) &&
							(targetRow == piece.getRow() && targetColumn == piece.getColumn());
						return isBlocked || isCaptured;
					}
				}
				
			} else {
				
				//king in check - valid move must be only out of check
				boolean validKingMove = MovementRulesValidator.validatePieceMovementRules(
					this.sourcePiece,
					this.targetPiece,
					sourceRow, sourceColumn, targetRow, targetColumn);
				if (!validKingMove) {
					return false;
				}

				//to allow leap over
				king.isCaptured(true);
				//boolean isPath = isOpponentPiecesPathToTarget(targetRow, targetColumn, opponentColor);
				boolean isPath = MoveController.playerCanAttackSquare(
						player.getOpposingPlayer(), targetRow, targetColumn);
				//restore
				king.isCaptured(false);
				return !isPath;					
			}
			
		} else {
			// check if source piece preventing check
			if(!(this.sourcePiece instanceof King)) {
				if (isPiecePreventingCheck(this.sourcePiece, king)) {
					
					if(this.sourcePiece instanceof Queen) {
						if (isPiecePreventingCheckOnRow(this.sourcePiece, king, targetRow, targetColumn)) {
							return true;
						}
						if (isPiecePreventingCheckOnColumn(this.sourcePiece, king, targetRow, targetColumn)) {
							return true;
						}
						if (isPiecePreventingCheckOnDiagonal(this.sourcePiece, king, targetRow, targetColumn)) {
							return true;
						}
					}
					
					if(this.sourcePiece instanceof Bishop ) {
						if (isPiecePreventingCheckOnDiagonal(this.sourcePiece, king, targetRow, targetColumn)) {
							return true;
						}
					}
					
					if(this.sourcePiece instanceof Rook) {
						if (isPiecePreventingCheckOnRow(this.sourcePiece, king, targetRow, targetColumn)) {
							return true;
						}
						if (isPiecePreventingCheckOnColumn(this.sourcePiece, king, targetRow, targetColumn)) {
							return true;
						}
					}
					
					if(this.sourcePiece instanceof Pawn ) {
						if (isPiecePreventingCheckOnRow(this.sourcePiece, king, targetRow, targetColumn)) {
							onPreventingCheck();
							return false;
						}
						
						if (isPiecePreventingCheckOnColumn(this.sourcePiece, king, targetRow, targetColumn)) {
							boolean validPawnMove = MovementRulesValidator.validatePieceMovementRules(
									this.sourcePiece, 
									this.targetPiece,
									sourceRow, sourceColumn, targetRow, targetColumn);
							return validPawnMove;
						}
						
						if (isPiecePreventingCheckOnDiagonal(this.sourcePiece, king, targetRow, targetColumn)) {
							boolean validPawnMove = MovementRulesValidator.validatePieceMovementRules(
									this.sourcePiece,
									this.targetPiece,
									sourceRow, sourceColumn, targetRow, targetColumn);
							return validPawnMove;
						}
					}
					
					onPreventingCheck();
					return false;
				}
			}
		}
		
		boolean validPieceMove = MovementRulesValidator.validatePieceMovementRules(
			this.sourcePiece, 
			this.targetPiece,
			sourceRow, sourceColumn,
			targetRow, targetColumn);
		return validPieceMove;
	}

	private void onCheck(int color) {
		//String s = (chessGame.getGameState() == ChessGame.GAME_STATE_BLACK) ?
		String s = (color == Piece.COLOR_BLACK) ?
			"Black in check!" : "White in check!";
		this.validationMessage = (s + " A player may not make any move which places or leaves his king in check. Must move out of check or block check or capture the threatening piece!");
	}

	private void onNoSource() {
		this.validationMessage = "no source piece";
	}
	
	private void onPreventingCheck() {
		this.validationMessage = this.sourcePiece.getName() + " is preventing check.";		
	}
	
	private boolean isPiecePreventingCheck(Piece piece, Piece king) {
		//to allow leap over
		piece.isCaptured(true);
		
		//int opponentColor = this.chessGame.getOpponentColor(king);
		//boolean isPath = isOpponentPiecesPathToTarget(king.getRow(), king.getColumn(), opponentColor);
		boolean isPath = MoveController.playerCanAttackSquare(
			king.getPlayer().getOpposingPlayer(),
			king.getRow(), king.getColumn());
		
		//restore
		piece.isCaptured(false);
		return isPath;
	}
	
	private boolean isPiecePreventingCheckOnRow(Piece blocker, Piece king, 
		int checkRow, int validatingColumn) {

		if(blocker.getRow() != checkRow) {
			return false;
		}
		
		//to allow leap over
		blocker.isCaptured(true);
		
		boolean isPath = isOpponentPiecesPathToKingOnRow(
				blocker,
				king,
//				blocker.getRow(), blocker.getColumn(),
//				king.getRow(), king.getColumn(), 
//				opponentColor, 
				checkRow, validatingColumn);
		
		//restore
		blocker.isCaptured(false);
		return isPath;
	}
	
	private boolean isPiecePreventingCheckOnColumn(Piece blocker, Piece king, 
		int validatingRow, int checkColumn) {
		
		if(blocker.getColumn() != checkColumn) {
			return false;
		}
		
		//to allow leap over
		blocker.isCaptured(true);
		
		//int opponentColor = this.chessGame.getOpponentColor(king);
		boolean isPath = isOpponentPiecesPathToKingOnColumn(
				blocker,
				king,
//				blocker.getRow(), blocker.getColumn(),
//				king.getRow(), king.getColumn(), 
//				opponentColor, 
				validatingRow, checkColumn);
		
		//restore
		blocker.isCaptured(false);
		return isPath;
	}
	
	private boolean isPiecePreventingCheckOnDiagonal(Piece blocker, Piece king, 
		int validatingRow, int validatingRowColumn) {
		//to allow leap over
		blocker.isCaptured(true);
		
		//int opponentColor = this.chessGame.getOpponentColor(king);
		boolean isPath = isOpponentPiecesPathToKingOnDiagonal(
				blocker,
				king,
//				blocker.getRow(), blocker.getColumn(),
//				king.getRow(), king.getColumn(), 
//				opponentColor, 
				validatingRow, validatingRowColumn);
		
		//restore
		blocker.isCaptured(false);
		return isPath;
	}
	
	private boolean isBishopPathToTarget(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		boolean isValid;
		int diffRow = targetRow - sourceRow;
		int diffColumn = targetColumn - sourceColumn;
		
		if( diffRow==diffColumn && diffColumn > 0){
			// moving diagonally up-right
			isValid = !MoveController.arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,+1,+1);

		}else if( diffRow==-diffColumn && diffColumn > 0){
			// moving diagonally down-right
			isValid = !MoveController.arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,-1,+1);
			
		}else if( diffRow==diffColumn && diffColumn < 0){
			// moving diagonally down-left
			isValid = !MoveController.arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,-1,-1);

		}else if( diffRow==-diffColumn && diffColumn < 0){
			// moving diagonally up-left
			isValid = !MoveController.arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,+1,-1);
			
		}else{
			// not moving diagonally
			//System.out.println(diffRow);
			//System.out.println(diffColumn);
			//System.out.println("not moving diagonally");
			isValid = false;
		}
		return isValid;
	}

	private boolean isOpponentPiecesPathToKingOnRow(
		Piece blocker,
		Piece king,
		int checkRow, int validatingColumn) {
		
//		int blockerRow = blocker.getRow(); 
		int blockerColumn = blocker.getColumn();
		int kingRow = king.getRow(); 
		int kingColumn = king.getColumn();
		
		//for (Piece piece : this.chessGame.getNonCapturedPieces(opponentColor)) {
		for (Piece piece : king.getPlayer().getOpposingPlayer().getNonCapturedPieces()) {
			if (MoveController.isThreateningPiece(piece, kingRow, kingColumn) &&
					piece.getRow() == checkRow) {
				if (piece.getColumn() < kingColumn) {
					if((validatingColumn >= piece.getColumn() && validatingColumn < blockerColumn) ||
						(validatingColumn > blockerColumn && validatingColumn < kingColumn)) {
						return true;
					}
				} else {
					if((validatingColumn > kingColumn && validatingColumn < blockerColumn) ||
						(validatingColumn > blockerColumn && validatingColumn <= piece.getColumn())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean isOpponentPiecesPathToKingOnColumn(
		Piece blocker,
		Piece king,
		int validatingRow, int checkColumn) {

		int blockerRow = blocker.getRow(); 
//		int blockerColumn = blocker.getColumn();
		int kingRow = king.getRow(); 
		int kingColumn = king.getColumn();
		
//		for (Piece piece : this.chessGame.getNonCapturedPieces(opponentColor)) {
		for (Piece piece : king.getPlayer().getOpposingPlayer().getNonCapturedPieces())	 {	
			if (MoveController.isThreateningPiece(piece, kingRow, kingColumn) &&
					piece.getColumn() == checkColumn) {
				if (piece.getRow() < kingRow) {
					if((validatingRow >= piece.getRow() && validatingRow < blockerRow) ||
							(validatingRow > blockerRow && validatingRow < kingRow)) {
						return true;
					}
				} else {
					if((validatingRow > kingRow && validatingRow < blockerRow) ||
							(validatingRow > blockerRow && validatingRow <= piece.getRow())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean isOpponentPiecesPathToKingOnDiagonal(
			Piece blocker,
			Piece king,
			int validatingRow, int validatingColumn) {

//		int blockerRow = blocker.getRow(); 
//		int blockerColumn = blocker.getColumn();
		int kingRow = king.getRow(); 
		int kingColumn = king.getColumn();
		
		//for (Piece piece : this.chessGame.getNonCapturedPieces(opponentColor)) {
		for (Piece piece : king.getPlayer().getOpposingPlayer().getNonCapturedPieces())	 {		
			if (MoveController.isThreateningPiece(piece, kingRow, kingColumn)) {

				int attackerRow = piece.getRow();
				int attackerColumn = piece.getColumn();
				
				if(validatingRow == attackerRow && validatingColumn == attackerColumn) {
					return isBishopPathToTarget(attackerRow, attackerColumn, kingRow, kingColumn);
				}
			}
		}
		return false;
	}
}
