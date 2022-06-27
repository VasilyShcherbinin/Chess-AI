package ch06.logic;

import java.util.ArrayList;
import java.util.List;

import ch06.logic.Player.PlayerColor;

public class Fen {

	//FEN - Forsyth-Edwards Notation
	public static final String WHITE_PAWN = "P";
	public static final String WHITE_KNIGHT = "N";	
	public static final String WHITE_BISHOP = "B";
	public static final String WHITE_ROOK = "R";
	public static final String WHITE_QUEEN = "Q";	
	public static final String WHITE_KING = "K";

	public static final String BLACK_PAWN = "p";
	public static final String BLACK_KNIGHT = "n";	
	public static final String BLACK_BISHOP = "b";
	public static final String BLACK_ROOK = "r";
	public static final String BLACK_QUEEN = "q";	
	public static final String BLACK_KING = "k";
	
    private char[] piecePlacement;
	private String activeColor = "w";
	private String castlingRights = "-";
	private String enPassant = "-";
	private String halfMoveClock = "0";
	private String fullMoveNumber = "1";
	
	public final String gameStartPosition = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

	public char[] getPiecePlacement() {
		return piecePlacement;
	}
	
	public PlayerColor getActivePlayerColor() {
		return (this.activeColor.equals("w")) 
			? PlayerColor.WHITE 
			: PlayerColor.BLACK;
	}
	
	public String getCastlingRights() {
		return castlingRights;
	}

	public String getEnPassant() {
		return enPassant;
	}

	public String getHalfMoveClock() {
		return halfMoveClock;
	}

	public String getFullMoveNumber() {
		return fullMoveNumber;
	}
	
	//public void setBoardPosition(String fenString, List<Piece> pieces) {
	public void parse(String fenString) {

        //String strActiveColour = "w";
        //String strCastlingRights = "";
        //String strEnPassant = "-";
        //String strHalfMoveClock = "0";
        //String strFullMoveNumber = "1";

        //pieces.clear();
        
        //Game.FenStartPosition = fenString;

        //Game.CaptureAllPieces();
        //Game.DemoteAllPieces();

        // Break up the string into its various parts
        // rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
        fenString += " ";

        // Piece Placement
        int pos = fenString.indexOf(" ");
        String field1 = fenString.substring(0, pos);
        this.piecePlacement = field1.toCharArray();
        fenString = fenString.substring(pos + 1);

        // Active Colour
        pos = fenString.indexOf(" ");
        if (pos > -1)
        {
            this.activeColor = fenString.substring(0, pos);
            fenString = fenString.substring(pos + 1);
        }

        // Castling Rights
        pos = fenString.indexOf(" ");
        if (pos > -1)
        {
            this.castlingRights = fenString.substring(0, pos);
            fenString = fenString.substring(pos + 1);
        }

        // En passant
        pos = fenString.indexOf(" ");
        if (pos > -1)
        {
            this.enPassant = fenString.substring(0, pos);
            fenString = fenString.substring(pos + 1);
        }

        // Half move clock
        pos = fenString.indexOf(" ");
        if (pos > -1)
        {
            this.halfMoveClock = fenString.substring(0, pos);
            fenString = fenString.substring(pos + 1);
        }

        // Full move number
        pos = fenString.indexOf(" ");
        if (pos > -1)
        {
            this.fullMoveNumber = fenString.substring(0, pos);
        }

        // Match FEN pieces against actual pieces, and move them onto the board

        // Pass 1: Match piece name and location exactly
        //setPiecePlacement(pieces, acharPiecePlacement, false, false);
//        setPiecePlacement(acharPiecePlacement, false, false);
//
//        // Pass 2: Match piece name
//        SetPiecePlacement(ref acharPiecePlacement, true, false);
//
//        // Pass 3: For non-pawns and not the king, allow pawns to be promoted to the named piece
//        SetPiecePlacement(ref acharPiecePlacement, true, true);
//
//        // Set player to play
//        Game.PlayerToPlay = strActiveColour == "b" ? Game.PlayerBlack : Game.PlayerWhite;
//
//        // Set castling rights
//        Piece pieceRook;
//
//        // White King's Rook
//        if ((pieceRook = Board.GetPiece(7, 0)) != null && pieceRook.Name == Piece.PieceNames.Rook
//            && pieceRook.Player.Colour == Player.PlayerColourNames.White)
//        {
//            pieceRook.NoOfMoves = strCastlingRights.LastIndexOf("K") >= 0 ? 0 : 1;
//        }
//
//        // Black King's Rook
//        if ((pieceRook = Board.GetPiece(7, 7)) != null && pieceRook.Name == Piece.PieceNames.Rook
//            && pieceRook.Player.Colour == Player.PlayerColourNames.Black)
//        {
//            pieceRook.NoOfMoves = strCastlingRights.LastIndexOf("k") >= 0 ? 0 : 1;
//        }
//
//        // White Queen's Rook
//        if ((pieceRook = Board.GetPiece(0, 0)) != null && pieceRook.Name == Piece.PieceNames.Rook
//            && pieceRook.Player.Colour == Player.PlayerColourNames.White)
//        {
//            pieceRook.NoOfMoves = strCastlingRights.LastIndexOf("Q") >= 0 ? 0 : 1;
//        }
//
//        // Black Queen's Rook
//        if ((pieceRook = Board.GetPiece(0, 7)) != null && pieceRook.Name == Piece.PieceNames.Rook
//            && pieceRook.Player.Colour == Player.PlayerColourNames.Black)
//        {
//            pieceRook.NoOfMoves = strCastlingRights.LastIndexOf("q") >= 0 ? 0 : 1;
//        }
//
//        // Half move (50 move draw) clock.
//        Game.FiftyMoveDrawBase = int.Parse(strHalfMoveClock);
//
//        // Full move number. Default 1. Must be defined before En Passant.
//        Game.TurnNo = (int.Parse(strFullMoveNumber) - 1) << 1;
//        if (Game.PlayerToPlay.Colour == Player.PlayerColourNames.Black)
//        {
//            Game.TurnNo++; // Always odd for the previous White's move 
//        }
//
//        // En Passant
//        if (strEnPassant[0] != '-')
//        {
//            int indFile = Board.FileFromName(Convert.ToString(strEnPassant[0]));
//            int indRank = int.Parse(Convert.ToString(strEnPassant[1]));
//            if (indRank == 6)
//            {
//                // if fenString = "e6"
//                indRank = 4; // last move was e7-e5 so indRank = 6 - 2 = 4
//            }
//
//            // else if indRank = 3, fenString = "e3" last move was e2-e4 so indRank = 3
//            Piece piecePassed = Board.GetPiece(indFile, indRank);
//            piecePassed.NoOfMoves = 1;
//            piecePassed.LastMoveTurnNo = Game.TurnNo;
//        }
//
//        // Recalculate the hashkey for the current position.
//        Board.EstablishHashKey();
//
//        VerifyPiecePlacement(ref acharPiecePlacement);
		
	}
	
	public String getBoardPosition() {
		return "todo";
	}
	
	public boolean validate(String fenString) throws ValidationException {
		 if (fenString == null) {
			 return false;
		 }

        String[] arrStrFen = fenString.split(" ");
        int fieldCount = arrStrFen.length;

        if (fieldCount < 1 || fieldCount > 6) {
            throw new ValidationException(
                "1000: A FEN string must 1 to 6 fields separated by spaces\n e.g. rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        }

        if (fieldCount >= 2) {
            fenCheck2Color(arrStrFen[1]);
        }

        if (fieldCount >= 3) {
            fenCheck3Castle(arrStrFen[2]);
        }

        if (fieldCount >= 4) {
            fenCheck4EnPassant(arrStrFen[3]);
        }

        if (fieldCount >= 5) {
            fenCheck5Counter50MoveDraw(arrStrFen[4]);
        }

        if (fieldCount >= 6) {
            fenCheck6NbrMove(arrStrFen[5]);
        }
        return true;
	}
	
    private void fenCheck2Color(String fenString) throws ValidationException
    {
        if (!(fenString.compareTo("w") == 0 || fenString.compareTo("b") == 0)) {
            throw new ValidationException(
            		"1200: " + fenHlpMsg(2) + "The active color must be 'w' or 'b'");
        }
    }
    
    private void fenCheck3Castle(String fenString) throws ValidationException
    {
        int dash = 0, whiteKing = 0, whiteQueen = 0, blackKing = 0, blackQueen = 0;
        char[] achar = fenString.toCharArray();

        for (int i = 0; i < achar.length; i++) {
            switch (Character.toString(achar[i]))
            {
                case WHITE_KING:
                    whiteKing++;
                    break;
                case BLACK_KING:
                    blackKing++;
                    break;
                case WHITE_QUEEN:
                    whiteQueen++;
                    break;
                case BLACK_QUEEN:
                    blackQueen++;
                    break;
                case "-":
                    dash++;
                    break;
                default:
                    throw new ValidationException(
                		"1300: " + fenHlpMsg(3) + "Expected character 'KQkq-'");
            }
        }

        if ((whiteKing > 1) || (whiteQueen > 1) || (blackKing > 1) || (blackQueen > 1) || (dash > 1)) {
            throw new ValidationException("1310: " + fenHlpMsg(3) + "At least one occurrence of 'KQkq-'");
        }

        if ((dash == 1) && ((whiteKing == 1) || (whiteQueen == 1) || (blackKing == 1) || (blackQueen == 1))) {
            throw new ValidationException("1320: " + fenHlpMsg(3) + "'KQkq' or exclusive '-'");
        }
    }
    
    private void fenCheck4EnPassant(String fenString) throws ValidationException {
    	
    	char[] achar = fenString.toCharArray();
    	
        if (((achar[0] < 'a') || (achar[0] > 'h')) && (achar[0] != '-'))
        {
            throw new ValidationException("1400: " + fenHlpMsg(4) + "Expected character 'abcdefgh-'");
        }

        if (achar[0] == '-')
        {
            if (fenString.length() > 1)
            {
                throw new ValidationException("1410: " + fenHlpMsg(4) + "No expected character after '-'");
            }
        }
        else if (((achar[0] >= 'a') && (achar[0] <= 'h'))
                 &&
                 (((fenString.length() == 2) && (achar[1] != '3') && (achar[1] != '6'))
                  || (fenString.length() > 2)))
        {
            throw new ValidationException(
                "1420: " + fenHlpMsg(4) + "After the pawn file, expect the rank '3' or '6'");
        }
    }

    private void fenCheck5Counter50MoveDraw(String fenString) throws ValidationException {
        if (fenString.length() > 2) {
            throw new ValidationException(
                "1500: " + fenHlpMsg(5) + "1 or 2 digits for the nbr of ply for rule of 50 moves");
        }

        int halfMove;
        try {
            halfMove = Integer.parseInt(fenString);
        }
        catch (Exception ex) {
            throw new ValidationException(
                "1510: " + fenHlpMsg(5) + "Expect a half move number for the rule of 50 moves");
        }

        if ((halfMove < 0) || (halfMove > 100)) {
            throw new ValidationException(
        		"1520: " + fenHlpMsg(5) + "Expect a non negative half move number <= 100");
        }
    }
    
    private void fenCheck6NbrMove(String fenString) throws ValidationException {
        int fullMove;
        try {
            fullMove = Integer.parseInt(fenString);
        }
        catch (Exception ex) {
            throw new ValidationException(
        		"1600: " + fenHlpMsg(6) + "Expect a full move number");
        }

        if ((fullMove < 1) || (fullMove > 200)) {
            throw new ValidationException(
        		"1610: " + fenHlpMsg(6) + "Expect a positive full move number <= 200");
        }
    }
    
    private String fenHlpMsg(int fieldNumber) {
        switch (fieldNumber) {
            case 1:
                return "FEN field 1: Piece placement data.\n";
            case 2:
                return "FEN field 2: Active color.\n";
            case 3:
                return "FEN field 3: Castling availability.\n";
            case 4:
                return "FEN field 4: En passant target square coordonates.\n";
            case 5:
                return "FEN field 5: Nbr of half move without capture or pawn move.\n";
            case 6:
                return "FEN field 6: Full move number.\n";
        }
        return "";
    }
    
//    /// <summary>
//    /// The set piece placement.
//    /// </summary>
//    /// <param name="acharPiecePlacement">
//    /// The achar piece placement.
//    /// </param>
//    /// <param name="blnAnyLocation">
//    /// The bln any location.
//    /// </param>
//    /// <param name="blnAllowPromotion">
//    /// The bln allow promotion.
//    /// </param>
//    /// <exception cref="ValidationException">
//    /// Unknow character in FEN string.
//    /// </exception>
//    private void setPiecePlacement(//List<Piece> pieces,
//        char[] acharPiecePlacement, boolean blnAnyLocation, boolean blnAllowPromotion)
//    {
//        // Setup piece placement
//    	//List<Piece> whitePieces = ChessGame.playerWhite.getPieces();
//    	//List<Piece> blackPieces = ChessGame.playerBlack.getPieces();
//
//    	whitePieces.clear();
//    	blackPieces.clear();
//    	
//        int rank = Piece.ROW_8;
//        int file = Piece.COLUMN_A;
//        for (int i = 0; i < acharPiecePlacement.length; i++)
//        {
//            switch (Character.toString(acharPiecePlacement[i]))
//            {
//                case ".":
//
//                    // Indicates a processed piece, so move on
//                    file++;
//                    break;
//
//                case "/":
//                    file = 0;
//                    rank--;
//                    break;
//
//                case WHITE_KING:
//                	//pieces.add(PieceMaker.createPiece(Piece.COLOR_WHITE, Piece.TYPE_KING, rank, file));
//                	whitePieces.add(new King(ChessGame.playerWhite, Piece.COLOR_WHITE, Piece.TYPE_KING, rank, file));
//                    file++;
//                    break;
//
//                case WHITE_QUEEN:
//                	//pieces.add(PieceMaker.createPiece(Piece.COLOR_WHITE, Piece.TYPE_QUEEN, rank, file));
//                	whitePieces.add(new Queen(ChessGame.playerWhite, Piece.COLOR_WHITE, Piece.TYPE_QUEEN, rank, file));
//                    file++;
//                    break;
//                	
//                case WHITE_ROOK:
//                	//pieces.add(PieceMaker.createPiece(Piece.COLOR_WHITE, Piece.TYPE_ROOK, rank, file));
//                	whitePieces.add(new Rook(ChessGame.playerWhite, Piece.COLOR_WHITE, Piece.TYPE_ROOK, rank, file));                	
//                    file++;
//                    break;
//                	
//                case WHITE_BISHOP:
//                	//pieces.add(PieceMaker.createPiece(Piece.COLOR_WHITE, Piece.TYPE_BISHOP, rank, file));
//                	whitePieces.add(new Bishop(ChessGame.playerWhite, Piece.COLOR_WHITE, Piece.TYPE_BISHOP, rank, file));                	
//                    file++;
//                    break;
//                	
//                case WHITE_KNIGHT:
//                	//pieces.add(PieceMaker.createPiece(Piece.COLOR_WHITE, Piece.TYPE_KNIGHT, rank, file));
//                	whitePieces.add(new Knight(ChessGame.playerWhite, Piece.COLOR_WHITE, Piece.TYPE_KNIGHT, rank, file));                	
//                    file++;
//                    break;
//                	
//                case WHITE_PAWN:
//                	//pieces.add(PieceMaker.createPiece(Piece.COLOR_WHITE, Piece.TYPE_PAWN, rank, file));
//                	whitePieces.add(new Pawn(ChessGame.playerWhite, Piece.COLOR_WHITE, Piece.TYPE_PAWN, rank, file));
//                    file++;
//                    break;
//                	
//                case BLACK_KING:
//                	//pieces.add(PieceMaker.createPiece(Piece.COLOR_BLACK, Piece.TYPE_KING, rank, file));
//                	blackPieces.add(new King(ChessGame.playerBlack, Piece.COLOR_BLACK, Piece.TYPE_KING, rank, file));                	
//                    file++;
//                    break;
//                	
//                case BLACK_QUEEN:
//                	//pieces.add(PieceMaker.createPiece(Piece.COLOR_BLACK, Piece.TYPE_QUEEN, rank, file));
//                	blackPieces.add(new Queen(ChessGame.playerBlack, Piece.COLOR_BLACK, Piece.TYPE_QUEEN, rank, file));                	
//                    file++;
//                    break;
//                    
//                case BLACK_ROOK:
//                	//pieces.add(PieceMaker.createPiece(Piece.COLOR_BLACK, Piece.TYPE_ROOK, rank, file));
//                	blackPieces.add(new Rook(ChessGame.playerBlack, Piece.COLOR_BLACK, Piece.TYPE_ROOK, rank, file));
//                    file++;
//                    break;
//                	
//                case BLACK_BISHOP:
//                	//pieces.add(PieceMaker.createPiece(Piece.COLOR_BLACK, Piece.TYPE_BISHOP, rank, file));
//                	blackPieces.add(new Bishop(ChessGame.playerBlack, Piece.COLOR_BLACK, Piece.TYPE_BISHOP, rank, file));
//                    file++;
//                    break;
//                	
//                case BLACK_KNIGHT:
//                	//pieces.add(PieceMaker.createPiece(Piece.COLOR_BLACK, Piece.TYPE_KNIGHT, rank, file));
//                	blackPieces.add(new Knight(ChessGame.playerBlack, Piece.COLOR_BLACK, Piece.TYPE_KNIGHT, rank, file));
//                    file++;
//                    break;
//                	
//                case BLACK_PAWN:
//                	//pieces.add(PieceMaker.createPiece(Piece.COLOR_BLACK, Piece.TYPE_PAWN, rank, file));
//                	blackPieces.add(new Pawn(ChessGame.playerBlack, Piece.COLOR_BLACK, Piece.TYPE_PAWN, rank, file));                	
//                    file++;
//                    break;
//                	
////                    movePieceToFenPosition(
////                        acharPiecePlacement[intIndex], intFile, intRank, blnAnyLocation, blnAllowPromotion);
////                    intFile++;
////                    break;
//
//                default:
//                	if (Character.isDigit(acharPiecePlacement[i])) {
//                		file += Integer.parseInt(Character.toString(acharPiecePlacement[i]));
//                	}
////					todo properly                	
////                	else {
////                		throw new ValidationException(
////                				"Unknow character in FEN string:" + acharPiecePlacement[intIndex]);
////                	}
//                    break;
//            }
//        }
//    }
	
    /// <summary>
    /// The move piece to fen position.
    /// </summary>
    /// <param name="charToken">
    /// The char token.
    /// </param>
    /// <param name="intFile">
    /// The int file.
    /// </param>
    /// <param name="intRank">
    /// The int rank.
    /// </param>
    /// <param name="blnAnyLocation">
    /// The bln any location.
    /// </param>
    /// <param name="blnAllowPromotion">
    /// The bln allow promotion.
    /// </param>
//    private static void movePieceToFenPosition(
//        char charToken, int intFile, int intRank, boolean blnAnyLocation, boolean blnAllowPromotion)
//    {
//        Piece.PieceNames piecename = Piece.PieceNames.King;
//        Player player = charToken.ToString() == charToken.ToString().ToUpper() ? 
//        		Game.PlayerWhite : Game.PlayerBlack;
//
//        switch (charToken.ToString().ToUpper())
//        {
//            case "K":
//                piecename = Piece.PieceNames.King;
//                break;
//            case "Q":
//                piecename = Piece.PieceNames.Queen;
//                break;
//            case "R":
//                piecename = Piece.PieceNames.Rook;
//                break;
//            case "B":
//                piecename = Piece.PieceNames.Bishop;
//                break;
//            case "N":
//                piecename = Piece.PieceNames.Knight;
//                break;
//            case "P":
//                piecename = Piece.PieceNames.Pawn;
//                break;
//        }
//
//        // Try to find the required piece in from the available pool of captured 
//        // pieces that haven't been placed on the board yet.
//        Piece pieceToUse = null;
//        foreach (Piece pieceCaptured in player.OpposingPlayer.CapturedEnemyPieces)
//        {
//            if ((pieceCaptured.Name == piecename || (blnAllowPromotion && pieceCaptured.Name == Piece.PieceNames.Pawn))
//                && (pieceCaptured.StartLocation == Board.GetSquare(intFile, intRank) || blnAnyLocation))
//            {
//                pieceToUse = pieceCaptured;
//                break;
//            }
//        }
//
//        if (pieceToUse != null)
//        {
//            Square square = Board.GetSquare(intFile, intRank);
//            pieceToUse.Uncapture(0);
//            square.Piece = pieceToUse;
//            pieceToUse.Square = square;
//            pieceToUse.NoOfMoves = blnAnyLocation ? 1 : 0;
//            if (pieceToUse.Name != piecename)
//            {
//                pieceToUse.Promote(piecename);
//            }
//
//            // Mark the token in the original FEN string with a * to indicate that the piece has been processed
//            charToken = '.';
//        }
//    }
    
    class ValidationException extends ApplicationException
    {
    	private String fenMessage = "";

    	public ValidationException(String message) {
    		super(message);
    		this.fenMessage = message;
    	}

    	public String getFenMessage() {
    		return this.fenMessage;
    	}
    }    
}
