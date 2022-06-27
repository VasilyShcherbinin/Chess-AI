package ch06.logic;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {

    private PlayerColor playerColor;
    private Intellegence intellegence;
    
    protected List<Piece> pieces = new ArrayList<Piece>();
    protected Piece king;
    protected Player opposingPlayer;
    
    //protected boolean isInCheck = false;
    protected boolean canMove = true;
    //protected boolean isInCheckMate = false;
    
    private boolean hasCastled;    
    private boolean isPawnPromoted;
    
    private boolean isPawnEnpassant;
    private boolean isOpponentEnpassantPawnCaptured;
    private int	 pawnEnpassantColumn;
    
    
	// if a constructor is declared protected then 
	//only classes in the same package, or subclasses
	//of that class can call that constructor
    protected Player()
    {
    	//todo
        //this.Clock = new PlayerClock();
        //this.MaterialCount = 7;
        //this.PawnCountInPlay = 8;
        //this.Pieces = new Pieces();
        //this.CapturedEnemyPieces = new Pieces();
        //this.Brain = new Brain(this);
    }
    
    public enum PlayerColor
    {
    	WHITE(0),	//calls constructor with value 0
    	BLACK(1)	//calls constructor with value 1
    	; // semicolon needed when fields / methods follow
    	
        private final int color;

        PlayerColor(int color) {
            this.color = color;
        }
        
        public int getColor() {
        	return this.color;
        }
    }

    public enum Intellegence
    {
        Human(0),	//calls constructor with value 0,
        Computer(1)	//calls constructor with value 1
        ; // semicolon needed when fields / methods follow
    	
        private final int intellegence;

        Intellegence(int intellegence) {
            this.intellegence = intellegence;
        }
        
        public int getIntellegence() {
        	return this.intellegence;
        }        
    }    
    
    public enum PlayerStatus
    {
        Normal(0),
        InCheck(1),
        InStalemate(2),
        InCheckMate(3),
        InsufficientMaterial(4),
        RepetitionOfPosition(5),
        FiftyMoveRule(6)
        ;
        
        private final int status;

        PlayerStatus(int status) {
            this.status = status;
        }
        
        public int getStatus() {
        	return this.status;
        }
    }

    public PlayerStatus getStatus()
    {
        if (isInCheckMate()) {
            return PlayerStatus.InCheckMate;
        }
        if (!this.canMove) {
            return PlayerStatus.InStalemate;
        }
        if (isInCheck()) {
            return PlayerStatus.InCheck;
        }
        return PlayerStatus.Normal;
    }    
    
    public List<Piece> getPieces() {
		return this.pieces;
	}

    public Piece getKing() {
    	return this.king;
    }

	public void hasCastled(boolean castled) {
		this.hasCastled = castled;
	}

	public void isPawnPromoted(boolean pawnPromoted) {
		this.isPawnPromoted = pawnPromoted;
	}
	
	public boolean isPawnPromoted() {
		return this.isPawnPromoted;
	}
	
	public boolean isInCheck() {
		return ((King)this.king).determineCheckStatus();
	}    

	public boolean canMove() {
		//todo:
		return true;
		//return this.kingsAndRooksController.blackKingNotInCheck;
	}    
	
	public boolean isInCheckMate() {
        if (!this.isInCheck()) {
            return false;
        }

        if(((King)this.king).canMoveOutOfCheck()) {
        	return false;
        }
        
        //List<Move> moves = new ArrayList<Move>();
        //generateMoves(moves);
        
        List<Move> moves = generateMoves(); 
        return moves.size() == 0;
        
        //return true;
	}    
	
	public Player getOpposingPlayer() {
    	return this.playerColor == PlayerColor.WHITE 
			? ChessGame.playerBlack 
			: ChessGame.playerWhite;
    }
    
    protected void setColor(PlayerColor playerColor) {
    	this.playerColor = playerColor;
    }

//    protected PlayerColorName getPlayerColor() {
//    	return this.playerColor;
//    }

    protected int getColor() {
    	return this.playerColor.getColor();
    }

    protected void setIntellegence(Intellegence intellegence) {
    	this.intellegence = intellegence;
    }

    protected int getIntellegence() {
    	return this.intellegence.getIntellegence();
    }
    
	public boolean hasPawnEnpassant() {
		return this.isPawnEnpassant;
	}

    public int getPawnEnpassantColumn() {
		return this.pawnEnpassantColumn;
	}

    protected void setPawnEnpassantColumn(int pawnEnpassantColumn) {
		this.pawnEnpassantColumn = pawnEnpassantColumn;
		this.isPawnEnpassant = true;
	}
    
    protected void revokeEnpassantCaptureRight() {
		this.pawnEnpassantColumn = -1;
		this.isPawnEnpassant = false;
    }
    
	/**
	 * returns the first piece at the specified location that is not marked as
	 * 'captured'.
	 * 
	 * @param row one of Piece.ROW_..
	 * @param column one of Piece.COLUMN_..
	 * @return the first not captured piece at the specified location
	 */
	public Piece getNonCapturedPieceAtLocation(int row, int column) {
		for (Piece piece : this.pieces) {
			if (piece.getRow() == row && piece.getColumn() == column &&
					piece.isCaptured() == false) {
				return piece;
			}
		}
		return null;
	}
	
	public List<Piece> getNonCapturedPieces() {
		List<Piece> pieces = new ArrayList<Piece>();
		for (Piece piece : this.pieces) {
			if (piece.isCaptured() == false) {
				pieces.add(piece);
			}
		}
		return pieces;
	}
	
	/**
	 * Checks whether there is a non-captured piece at the specified location
	 * 
	 * @param row one of Piece.ROW_..
	 * @param column on of Piece.COLUMN_..
	 * @return true, if the location contains a piece
	 */
	public boolean isNonCapturedPieceAtLocation(int row, int column) {
		for (Piece piece : this.pieces) {
			if (piece.getRow() == row && piece.getColumn() == column &&
					piece.isCaptured() == false) {
				return true;
			}
		}
		return false;
	}
	
//	public void setPiecesAtEditedPositions(List<Piece> pieces) {
//		this.pieces.clear();
//		for (Piece piece : pieces) {
//			if (piece.getColor() == this.getColor() && piece.isCaptured() == false) {
//				this.pieces.add(piece);
//			}
//			if (piece.getType() == Piece.TYPE_KING) {
//				king = piece;
//			}
//		}	
//	}
	
	/**
	* generate all possible/valid moves for the specified game
	* @param state - game state for which the moves should be generated
	* @return list of all possible/valid moves
	*/
	private List<Move> generateMoves() {

		MoveValidator moveValidator = new MoveValidator();
		
		List<Move> validMoves = new ArrayList<Move>();
		Move testMove = new Move(0,0,0,0);
		
		List<Piece> pieces = getNonCapturedPieces();
		
		// iterate over all non-captured pieces
		for (Piece piece : pieces) {

			testMove.setPiece(piece);
			testMove.sourceRow = piece.getRow();
			testMove.sourceColumn = piece.getColumn();

			// iterate over all board rows and columns
			for (int targetRow = Piece.ROW_1; targetRow <= Piece.ROW_8; targetRow++) {
				for (int targetColumn = Piece.COLUMN_A; targetColumn <= Piece.COLUMN_H; targetColumn++) {

					// finish generating move
					testMove.targetRow = targetRow;
					testMove.targetColumn = targetColumn;

					//if(debug) System.out.println("testing move: "+testMove);
					
					// check if generated move is valid
					if (moveValidator.isMoveValid(
						this, testMove)) {
						// valid move
						validMoves.add(testMove.clone());
					} else {
						// generated move is invalid, so we skip it
					}
				}
			}
		}
		return validMoves;
	}	

	public void setPiecesAtEditedPositions(List<Piece> pieces) {
		this.pieces.clear();
		for (Piece piece : pieces) {
			if (piece.getColor() == this.getColor() && piece.isCaptured() == false) {
				piece.setPlayer(this);
				this.pieces.add(piece);
				
				if (piece.getType() == Piece.TYPE_KING) {
					king = piece;
				}
			}
		}	
	}
	
	//public abstract void generateLegalMoves(List<Move> moves);
	public abstract void setPiecesAtStartingPositions();
    public abstract void getPiecesAtStartingPositions(List<Piece> pieces);
    //public abstract void setPiecesAtEditedPositions(List<Piece> pieces);
    //public abstract void setPiecesAtFenPositions();
}
