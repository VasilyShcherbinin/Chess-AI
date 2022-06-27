package ch06.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

//import ch06.gui.GuiPiece;

import ch06.ai.SimpleAiPlayerHandler;
import ch06.logic.ChessGame;
import ch06.logic.Fen;
import ch06.logic.IPlayerHandler;
import ch06.logic.King;
import ch06.logic.Move;
import ch06.logic.MoveValidator;
import ch06.logic.Pawn;
import ch06.logic.Piece;
import ch06.logic.PositionEditor;
import ch06.logic.PositionValidator;
import ch06.logic.StandardAlgebraicMoveNotation;
import ch06.logic.Player.PlayerColor;
import ch06.logic.Player.PlayerStatus;

/**
 * Chess class actually will be managing the game.
 * This class is responsible for reading mouse input from the screen and handling the game play accordingly.
 * 
 * 
 * All x and y coordinates point to the upper left position of a component
 * 
 */
public class Chess extends JLayeredPane implements ch06.logic.IPlayerHandler{
	
	private static final long serialVersionUID = -8207574964820892354L;

	private ChessGame chessGame; 

	private Color darkColor;
	private Color lightColor;    	
	
	//private PositionEditor positionEditor;
	
	private ChessMouseAdapter mouseAdapter;
	private UndoMoveHandler undoMoveHandler;
	private RedoMoveHandler redoMoveHandler;
	
	//private EditPositionMouseAdapter editPositionMouseAdapter;
	//private ActionListener editPositionActionListener;
	//private GameStatusController gameStatusController;
	//private StandardAlgebraicMoveNotation moveNotation;
		
	private Move lastMove; // the last executed move (used for highlighting)
	private Move currentMove;
	private boolean draggingGamePiecesEnabled;
	
	private int lastGameState;
	//private int lastMoveCount = 0;
	private int lastBoardOrientation;
	
	//private JFrame chessFrame;
	private ChessBoard chessBoard;
	//private boolean showPiecesInfo = false;
	
//	private Piece pawnPromoted;

	//private GameStatusView gameStatusView; 

	private GameStatusBoard gameStatusBoard;	
	private GameStateBoard gameStateBoard;
	private MovesBoard movesBoard;
	private RowNumberBoard rowNumberBoard;
	private ColumnNumberBoard columnNumberBoard;
	private EditPositionBoard editPositionBoard;
	
	public Chess(ChessGame chessGame) {
		
		setLayout(null);
		
		this.chessGame = chessGame;
		
    	this.darkColor = getBackgroundDarkColor();
    	this.lightColor = getBackgroundLightColor();    	
    	this.chessBoard = new ChessBoard(darkColor, lightColor);
    	add(this.chessBoard, JLayeredPane.DEFAULT_LAYER);
    	
		this.gameStatusBoard = new GameStatusBoard();
		this.gameStateBoard = new GameStateBoard();
		this.movesBoard = new MovesBoard();
		this.rowNumberBoard = new RowNumberBoard();
		this.columnNumberBoard = new ColumnNumberBoard();
		this.editPositionBoard = new EditPositionBoard();
		
		this.mouseAdapter = new ChessMouseAdapter(this);
		addMouseAdapter(this.mouseAdapter);
		
		this.undoMoveHandler = new UndoMoveHandler(this);
		this.redoMoveHandler = new RedoMoveHandler(this);
	}
	
    public void addPiecesToChessBoard() {

    	addPiecesToChessBoard(ChessGame.playerWhite.getPieces());
    	addPiecesToChessBoard(ChessGame.playerBlack.getPieces());
		    	
		
		//gameStateBoard.changeGameState(this.chessGame.getGameStateAsText());
    }

	private Color getBackgroundDarkColor() {
		Color bgDark  = new Color(140,162,181);
		//Color bgDark  = new Color(139, 69, 19);
		//Color bgDark  = new Color(44, 107, 224);
		return bgDark;
	}

	private Color getBackgroundLightColor() {
		Color bgLight = new Color(206,214,239); 
		//Color bgLight = new Color(222, 184, 135);
		//Color bgLight = new Color(130, 192, 255);
		return bgLight;
	}
	
//	/**
//	 * load image for given color and type. This method translates the color and
//	 * type information into a filename and loads that particular file.
//	 * 
//	 * @param color color constant
//	 * @param type type constant
//	 * @return image
//	 */
//	private Image getImageForPiece(int color, int type) {
//
//		String filename = "";
//
//		filename += (color == Piece.COLOR_WHITE ? "w" : "b");
//		switch (type) {
//			case Piece.TYPE_BISHOP:
//				filename += "b";
//				break;
//			case Piece.TYPE_KING:
//				filename += "k";
//				break;
//			case Piece.TYPE_KNIGHT:
//				filename += "n";
//				break;
//			case Piece.TYPE_PAWN:
//				filename += "p";
//				break;
//			case Piece.TYPE_QUEEN:
//				filename += "q";
//				break;
//			case Piece.TYPE_ROOK:
//				filename += "r";
//				break;
//		}
//		filename += ".png";
//
//		URL urlPieceImg = getClass().getResource("/ch06/gui/img/" + filename);
//		return new ImageIcon(urlPieceImg).getImage();
//	}

	private ImageIcon getImageIconForPiece(int color, int type) {

		String filename = "";

		filename += (color == Piece.COLOR_WHITE ? "w" : "b");
		switch (type) {
			case Piece.TYPE_BISHOP:
				filename += "b";
				break;
			case Piece.TYPE_KING:
				filename += "k";
				break;
			case Piece.TYPE_KNIGHT:
				filename += "n";
				break;
			case Piece.TYPE_PAWN:
				filename += "p";
				break;
			case Piece.TYPE_QUEEN:
				filename += "q";
				break;
			case Piece.TYPE_ROOK:
				filename += "r";
				break;
		}
		filename += ".png";

		URL urlPieceImg = getClass().getResource("/ch06/gui/img/" + filename);
		return new ImageIcon(urlPieceImg);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// draw valid target locations, if user is dragging a game piece
		if(!isUserDraggingPiece() ) {
			return;
		}

		// iterate the complete board to check if target locations are valid
		for (int column = Piece.COLUMN_A; column <= Piece.COLUMN_H; column++) {
			for (int row = Piece.ROW_1; row <= Piece.ROW_8; row++) {
				
				Piece piece = ((GuiLabel)this.getComponentsInLayer(JLayeredPane.DRAG_LAYER)[0]).getPiece();
				Move move =  new Move(piece, piece.getRow(), piece.getColumn(), row, column);
				
				// check if target location is valid
				if(this.chessGame.isItYourTurn(piece.getColor())) { 
					if( this.chessGame.getMoveValidator().isMoveValid(piece.getPlayer(), move) ) {
						this.chessBoard.selectSquare(column, row);
					} else {
						gameStatusBoard.changeGameStatus(
							this.chessGame.getMoveValidator().getValidationMessage());
					}
				} else {
					gameStatusBoard.changeGameStatus(notYourTurn());
				}
			}
		}
	}

	private String notYourTurn() {
		return (this.chessGame.getGameState() == ChessGame.GAME_STATE_WHITE) 
				? "It's white's turn." 
				: "It's black's turn.";
	}


	
	/** 
	 * @return true if user is currently dragging a game piece
	 */
	private boolean isUserDraggingPiece() {
		int count = this.getComponentsInLayer(JLayeredPane.DRAG_LAYER).length;
		return count > 0; 
	}

	/**
	 * change location of given piece, if the location is valid.
	 * If the location is not valid, move the piece back to its original
	 * position.
	 * @param dragPiece
	 * @param x
	 * @param y
	 */
	public boolean setNewPieceLocation(GuiLabel dragPiece, int x, int y) {
		
		int targetRow = this.chessBoard.convertYToRow(y);
		int targetColumn = this.chessBoard.convertXToColumn(x);

		//change model and update gui piece afterwards
		Piece piece = dragPiece.getPiece();

		Move move = new Move(piece, piece.getRow(), piece.getColumn(), targetRow, targetColumn);

		//MoveValidator moveValidator = this.chessGame.getMoveValidator();
		if(!this.chessGame.isItYourTurn(piece.getColor())) {
			//this.gameStatusController.setStatusBar(notYourTurn());
			gameStatusBoard.changeGameStatus(notYourTurn());
			return false;
		}

		if (!this.chessGame.getMoveValidator().isMoveValid(piece.getPlayer(), move) ) {
			gameStatusBoard.changeGameStatus("move invalid");
			return false;
		}
		this.currentMove = move;

//todo do not forget about this code
//		
//		this.chessGame.movePiece(move);
//
//		//int opponentColor = this.chessGame.getOpponentColor(piece);		
//		
//		//remember move for highlighting it in the user interface
//		this.lastMove = move;
//
//		//increase move count 
////		if (this.chessGame.getGameState() == ChessGame.GAME_STATE_WHITE) {
////			this.lastMoveCount ++;
////		}
//		
//		if (piece instanceof King) {
//			checkIfCastlingAndPlaceRookOnTheOtherSideOfKing(piece, move);
//		}
//		
//		if (piece instanceof Pawn) {
//			if (piece.getPlayer().getOpposingPlayer().hasPawnEnpassant()) {
//				removeEnpassantPawnFromChessBoard(piece, move);
//			}
//			
//			if(piece.getPlayer().isPawnPromoted()) {
//				onPawnPromoted(piece, move);
//				return true;
//			}
//		}
//		
//		if (this.chessBoard.isShowPiecesInfo()) {
//			dragPiece.setText(piece.getInfo());
//		}
//		
//		PlayerStatusName opponentStatus = piece.getPlayer().getOpposingPlayer().getStatus();
//		
//		String moveNotation = this.moveNotation.getMoveNotation(
//				piece, move, opponentStatus);			
//		
//		//todo restore recordMoveInStandardAlgebraicNotation(moveNotation);
//		
//		if(opponentStatus == PlayerStatusName.InCheckMate) {
//			setStatusBarOnGameEnd(piece.getColor());
//		}
//		
//		this.chessGame.swapActivePlayer();

//		gameStateBoard.changeGameState(this.chessGame.getGameStateAsText());
		return true;
	}

	public void onPawnPromoted(Piece piece, Move move) {

		piece.getPlayer().isPawnPromoted(false);
		
		removePromotedPawnFromChessBoard(move);
		
		List<Piece> pieces = piece.getPlayer().getPieces();
		Piece piecePromoted = pieces.get(pieces.size()-1);
		GuiLabel guiPiece = addPeaceToChessBoard(piecePromoted);
		
		if (this.chessBoard.isShowPiecesInfo()) {
			guiPiece.setText(piecePromoted.getInfo());
		}
		
		PlayerStatus opponentStatus = piece.getPlayer().getOpposingPlayer().getStatus();
		
		String moveNotation = StandardAlgebraicMoveNotation.getMoveNotation(
				piecePromoted, move, opponentStatus);
		this.movesBoard.recordMove(moveNotation);		
		
		if(opponentStatus == PlayerStatus.InCheckMate) {
			//setStatusBarOnGameEnd(piece.getColor());
			gameStatusBoard.onGameEnd(piece.getColor());
		}
		
		//this.chessGame.swapActivePlayer();
		//gameStateBoard.changeGameState(this.chessGame.getGameStateAsText());
	}	
	
	public void removeEnpassantPawnFromChessBoard(Piece piece, Move move) {
		
		if (piece.getColor() == Piece.COLOR_WHITE) {
			if(move.sourceRow == Piece.ROW_5 &&
				move.targetRow == Piece.ROW_6 &&
				move.targetColumn == piece.getPlayer().getOpposingPlayer().getPawnEnpassantColumn()) {
				removePeaceFromChessBoard(Piece.ROW_5, move.targetColumn);
			}
		} else {
			if(move.sourceRow == Piece.ROW_4 &&
				move.targetRow == Piece.ROW_3 &&
				move.targetColumn == piece.getPlayer().getOpposingPlayer().getPawnEnpassantColumn()) {				
				removePeaceFromChessBoard(Piece.ROW_4, move.targetColumn);
			}
		}
	}
	
	private void removePromotedPawnFromChessBoard(Move move) {
		removePeaceFromChessBoard(move.targetRow, move.targetColumn);
	}
	
//	private void refreshPeaceOnChessBoard(int x, int y) {
//		int pieceSquare = 63 - (x*8 + 7 - y);
//		JPanel square = (JPanel)chessBoard.getComponent(pieceSquare);
//		square.validate();
//		square.repaint();
//	}
	
	public void removePeaceFromChessBoard(int x, int y) {
		int pieceSquare = this.chessBoard.getSquareIndex(y,x);
		JPanel square = (JPanel)this.chessBoard.getComponent(pieceSquare);
		if(square.getComponentCount()>0) {
			square.remove(0);
			square.validate();
			square.repaint();
		}
	}
	
	public Piece getPeaceFromChessBoard(int x, int y) {
		int pieceSquare = this.chessBoard.getSquareIndex(y,x);
		JPanel square = (JPanel)chessBoard.getComponent(pieceSquare);
		GuiLabel guiPiece = (GuiLabel)square.getComponent(0);
		return guiPiece.getPiece();
	}
	
	
	public void checkIfCastlingAndPlaceRookOnTheOtherSideOfKing(Piece piece, Move move) {
		
		if(!(move.sourceRow == Piece.ROW_1 || move.sourceRow == Piece.ROW_8)) {
			return;
		}

		if(move.sourceColumn == Piece.COLUMN_E && move.targetColumn == Piece.COLUMN_G) {
			if (piece.getColor() == Piece.COLOR_WHITE) {
				placeRookOnTheOtherSideOfKing(Piece.COLUMN_H, Piece.COLUMN_F, Piece.ROW_1);
			} else {
				placeRookOnTheOtherSideOfKing(Piece.COLUMN_H, Piece.COLUMN_F, Piece.ROW_8);
			}
		}
		else {
			if(move.sourceColumn == Piece.COLUMN_E && move.targetColumn == Piece.COLUMN_C) {
				if (piece.getColor() == Piece.COLOR_WHITE) {
					placeRookOnTheOtherSideOfKing(Piece.COLUMN_A, Piece.COLUMN_D, Piece.ROW_1);
				} else {
					placeRookOnTheOtherSideOfKing(Piece.COLUMN_A, Piece.COLUMN_D, Piece.ROW_8);
				}
			}
		}
	}

	private void placeRookOnTheOtherSideOfKing(int fromColumn, int toColumn, int row) {
		
		int squareIndexFrom = this.chessBoard.getSquareIndex(fromColumn, row);
		int squareIndexTo = this.chessBoard.getSquareIndex(toColumn, row);
		
		JPanel squareFrom =  (JPanel)this.chessBoard.getComponent(squareIndexFrom);
		JPanel squareTo =  (JPanel)this.chessBoard.getComponent(squareIndexTo);
		
		if (squareFrom != null)
		{
			GuiLabel rook = (GuiLabel)squareFrom.getComponent(0);
			
		    squareFrom.remove(0);
		    squareFrom.validate();
		    squareFrom.repaint();

			Piece piece = rook.getPiece();
			
			if (this.chessBoard.isShowPiecesInfo()) {
				rook.setText(piece.getInfo());		
			}
		    
			squareTo.add(rook);
			squareTo.validate();
			squareTo.repaint();
		}
	}
	
	public Move getLastMove() {
		return this.lastMove;
	}

	public void newGame() {
		
		ChessGame.playerWhite.setPiecesAtStartingPositions();
    	ChessGame.playerBlack.setPiecesAtStartingPositions();

		this.movesBoard.removeAllMoves();    	

		clearChessBoard();
		addPiecesToChessBoard();
		this.chessGame.setActivePlayer(PlayerColor.WHITE);
		
		this.gameStateBoard.changeGameState(this.chessGame.getGameStateAsText());
		this.gameStatusBoard.changeGameStatus("New Game");
		validate();
		repaint();    	
	}

//	private String getFen() {
//		boolean isValid = false;
//		
//		Fen fen = new Fen();
//		String s = JOptionPane.showInputDialog(this ,"Enter in FEN:"); 
//		do {
//			String message = "";
//			try {
//				isValid = fen.validate(s);
//			} catch (Exception ex) {
//				message = ex.getMessage();
//			}
//			
//			if (!isValid) {
//				s = JOptionPane.showInputDialog(this , message + "! Enter in FEN:");
//				if (s == null) {
//					isValid = true;
//				}
//			}
//		} while (!isValid);
//		return s;
//	}
	
	public void newGameFromFen() {
		//todo: ask about FEN
		//...
		//String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
		//String fen = "r1bqkbnr/pppppppp/2n5/8/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 0 1";
		//String fen = "r1bq1r1k/p1pnbpp1/1p2p3/6p1/3PB3/5N2/PPPQ1PPP/2KR3R w - -";
		//newGameFromFen(fen);
		
		//Input dialog with a text field
//		boolean isValid = false;
//		String fen = JOptionPane.showInputDialog(this ,"Enter in FEN:"); 
//		do {
//			isValid = Fen.validate(fen);
//			if (!isValid) {
//				fen = JOptionPane.showInputDialog(this ,"Invalid FEN! Enter in FEN:");
//				if (fen == null) {
//					isValid = true;
//				}
//			}
//		} while (!isValid);
		String fenString = FenHandler.getFen(this);
		if (fenString == null) {
			return;
		}		
		
		this.chessGame.setFenPosition(fenString);
		
		//removeMouseListener(this.mouseAdapter);
		//removeMouseMotionListener(this.mouseAdapter);	
		
		this.movesBoard.removeAllMoves();    	

		clearChessBoard();
		
		//this.chessBoard.removePieces();
		//this.chessBoard.unselectAll();
		//todo restore		cleanMovesTable();
		
		//this.lastMove = null;
		//this.lastMoveCount = 0;
		
		//this.chessGame = new ChessGame(fen);
		
		
		
		
    	addPiecesToChessBoard(ChessGame.playerWhite.getPieces());
    	addPiecesToChessBoard(ChessGame.playerBlack.getPieces());			
		//addPiecesToChessBoard(this.chessGame.getPieces());
		
		//this.moveValidator =  new MoveValidator(this.chessGame);	
		//this.moveNotation =  new StandardAlgebraicMoveNotation();
		
		//this.chessGame.setMoveValidator(this.moveValidator);
		
		//this.mouseAdapter = new ChessMouseAdapter(this);
		//this.editPositionMouseAdapter = new EditPositionMouseAdapter(this);
		
		//this.addMouseListener(this.mouseAdapter);
		//this.addMouseMotionListener(this.mouseAdapter);
		
		gameStateBoard.changeGameState(this.chessGame.getGameStateAsText());
		gameStatusBoard.changeGameStatus("New Game");
		validate();
		repaint();
	}
	
//    private void newGameFromFen(String fenString)
//    {
//        Fen.validate(fenString);
//
//        //HashTable.Clear();
//        //HashTablePawn.Clear();
//        //HashTableCheck.Clear();
//        //KillerMoves.Clear();
//        //HistoryHeuristic.Clear();
//
//        //UndoAllMovesInternal();
//        //MoveRedoList.Clear();
//        //saveGameFileName = string.Empty;
//        Fen.setBoardPosition(fenString, this.chessGame.getPieces());
//        
//        //PlayerWhite.Clock.Reset();
//        //PlayerBlack.Clock.Reset();
//    }	
	
	public void editPosition() {
		this.editPositionBoard.openEditPosition();
	}
	
	public void orientChessBoard(int newBoardOrientation) {
		int boardOrientation = this.chessBoard.getBoardOrientation();
		if (boardOrientation != newBoardOrientation) {
			this.rowNumberBoard.rotate(newBoardOrientation);
			this.columnNumberBoard.rotate(newBoardOrientation);
			this.chessBoard.setBoardOrientation(newBoardOrientation);
		}		
	}
	
	public void clearChessBoard() {
		this.chessBoard.unselectAll();
		this.chessBoard.removePieces();		
	}
	
	public void removeMouseAdapter(Object adapter) {
		removeMouseListener((MouseListener)adapter);
		removeMouseMotionListener((MouseMotionListener)adapter);		
	}
	
	public void addMouseAdapter(Object adapter) {
		addMouseListener((MouseListener)adapter);
		addMouseMotionListener((MouseMotionListener)adapter);
	}
	
	public void changeStateAndStatusBoards() {
		gameStateBoard.changeGameState(this.chessGame.getGameStateAsText());
		gameStatusBoard.changeGameStatus("");		
	}

	public void changeStatusBoard(String message) {
		gameStatusBoard.changeGameStatus(message);		
	}
	
	public void cleanMovesTable() {
		movesBoard.removeAllMoves();
	}
	
	public void loadGame() {
		//this.guiPieces.clear();
	}
	
	public void rotateChessBoard() {
		this.chessBoard.flip();
		this.chessBoard.unselectAll();
		this.chessBoard.rotatePieces();
		this.rowNumberBoard.rotate(this.chessBoard.getBoardOrientation());
		this.columnNumberBoard.rotate(this.chessBoard.getBoardOrientation());
	}

	
    public void addPiecesToChessBoard(List<Piece> pieces) {
		for (Piece piece : pieces) {
			addPeaceToChessBoard(piece);
		}
    }

    public GuiLabel addPeaceToChessBoard(Piece piece) {
		GuiLabel guiLabel = createGuiPiece(piece);
		this.chessBoard.addPiece(guiLabel);

		if (this.chessBoard.isShowPiecesInfo()) {
			guiLabel.setText(piece.getInfo());			
		}
		return guiLabel;
    }
    
    public GuiLabel createGuiPiece(Piece piece) {
		ImageIcon imic = this.getImageIconForPiece(piece.getColor(), piece.getType());
		GuiLabel guiLabel = new GuiLabel(imic, piece);
		
//		if (this.chessBoard.isShowPiecesInfo()) {
//			guiLabel.setText(piece.getInfo());			
//		}
		return guiLabel;
	}

    public ChessBoard getChessBoard() {
    	return this.chessBoard;
    }

    public Object getMouseAdapter() {
    	return this.mouseAdapter;    	
    }
//    new ChessBoardPanel(boardSize, darkColor, lightColor)    
//    public void setChessBoard(JPanel chessBoard) {
//    	this.chessBoard = chessBoard;
//		this.add(chessBoard, JLayeredPane.DEFAULT_LAYER);    	
//		addPiecesToChessBoard(this.chessGame.getPieces());
//    }
    
//    public ChessGameGridBag getChessGameGridBag() {
//    	return this.chessGameGridBag;
//    }
    
    public ChessGame getChessGame() {
    	return this.chessGame;
    }
    
//    public PositionEditor getPositionEditor() {
//    	return this.positionEditor;
//    }
    
//    public void setChessFrame (JFrame frame) {
//    	this.chessFrame = frame;
//    }
    
	public void undoMove() {

    	if (this.chessGame.getMoveHistory().size() == 0) {
    		return;
    	}		
    	
    	this.chessGame.swapActivePlayer0();
    	
		this.chessBoard.unselectAll();
		
		Move move = this.chessGame.undoMove();
		
		//this.chessGame.swapActivePlayer();
		//this.chessGame.swapActivePlayer0();
		//this.gameStateBoard.changeGameState(this.chessGame.getGameStateAsText());

		this.undoMoveHandler.undoMove(move);
		
		this.movesBoard.removeLastMove();
		
		//this.chessGame.swapActivePlayer0();
		this.gameStateBoard.changeGameState(this.chessGame.getGameStateAsText());
		
		validate();
		repaint();
	}

	public void redoMove() {
		
    	if (this.chessGame.getMoveRedoList().size() == 0) {
    		return;
    	}
    	
    	//this.chessGame.swapActivePlayer0();
    	
    	this.chessBoard.unselectAll();
    	
		Move move = this.chessGame.redoMove();
		
		this.redoMoveHandler.redoMove(move);
		
		Piece piece = move.getPiece();

		//if (this.chessBoard.isShowPiecesInfo()) {
		//	dragPiece.setText(piece.getInfo());
		//}
		
		PlayerStatus opponentStatus = piece.getPlayer().getOpposingPlayer().getStatus();
		
		String moveNotation = StandardAlgebraicMoveNotation.getMoveNotation(
				piece, move, opponentStatus);			
		this.movesBoard.recordMove(moveNotation);
		
		if(opponentStatus == PlayerStatus.InCheckMate) {
			gameStatusBoard.onGameEnd(piece.getColor());
		}
		
		this.chessGame.swapActivePlayer0();
		this.gameStateBoard.changeGameState(this.chessGame.getGameStateAsText());
		
		validate();
		repaint();		
	}
	
//    public EditPositionPanel getEditPositionPane() {
//    	return editPositionPane;
//    }
    
    
//    @Override
//    public void doLayout() {
//        super.doLayout();
//        // Synchronizing on getTreeLock, because I see other layouts doing that.
//        // see BorderLayout::layoutContainer(Container)
//        synchronized (getTreeLock()) {
//            int w = getWidth();
//            int h = getHeight();
//            
//    		for (Component c : getComponents()) {
//    			if (c instanceof JPanel) {
//    				c.setBounds(0, 0, w, h);
//    			}
////            for (Component c : getComponents()) {
////                if (getLayer(c) == JLayeredPane.PALETTE_LAYER) {
////                    c.setFont(new Font("Serif", Font.PLAIN, (int) (getHeight() * 0.8)));
////                }
////                c.setBounds(0, 0, w, h);
//            }
//        }
//    }

	@Override
	public Move getMove() {
		this.draggingGamePiecesEnabled = true; 
		Move moveForExecution = this.currentMove;
		this.currentMove = null;
		return moveForExecution;
	}

	@Override
	public void activePlayerSwapped() {
		String gameState = this.chessGame.getGameStateAsText();
		this.gameStateBoard.changeGameState(gameState);
	}
	
	@Override
	public void moveSuccessfullyExecuted(Move move) {
		
		String gameState = this.chessGame.getGameStateAsText();
		this.gameStateBoard.changeGameState(gameState);
		
		// adjust GUI piece
		GuiLabel guiPiece;
		
		Piece piece = move.getPiece();

		int squareIndexFrom = this.chessBoard.getSquareIndex(move.sourceColumn, move.sourceRow);
		JPanel jpFrom =  (JPanel)this.chessBoard.getComponent(squareIndexFrom);		
		
		//move can be done by computer -> guiPiece should be moved manually
		if (jpFrom.getComponentCount()>0) {

			this.chessBoard.unselectAll();

			Piece pieceCaptured = move.getPieceCaptured();
			if(pieceCaptured != null) {
				removePeaceFromChessBoard(pieceCaptured.getRow(), pieceCaptured.getColumn());
			}			
			
			int squareIndexTo = this.chessBoard.getSquareIndex(move.targetColumn, move.targetRow);
			JPanel jpTo =  (JPanel)this.chessBoard.getComponent(squareIndexTo);		
			
			jpFrom.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
			jpTo.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
			
			removePeaceFromChessBoard(move.sourceRow, move.sourceColumn);
			guiPiece = addPeaceToChessBoard(move.getPiece());
			
			if (this.chessBoard.isShowPiecesInfo()) {
				guiPiece.setText(piece.getInfo());
			}
		}
		
//		if(!this.draggingGamePiecesEnabled) {
//			
//			this.chessBoard.unselectAll();
//
//			Piece pieceCaptured = move.getPieceCaptured();
//			
//			if(pieceCaptured != null) {
//				removePeaceFromChessBoard(pieceCaptured.getRow(), pieceCaptured.getColumn());
//			}			
//			
//			int squareIndexFrom = this.chessBoard.getSquareIndex(move.sourceColumn, move.sourceRow);
//			JPanel jpFrom =  (JPanel)this.chessBoard.getComponent(squareIndexFrom);		
//			
//			int squareIndexTo = this.chessBoard.getSquareIndex(move.targetColumn, move.targetRow);
//			JPanel jpTo =  (JPanel)this.chessBoard.getComponent(squareIndexTo);		
//			
//			jpFrom.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
//			jpTo.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
//			
//			removePeaceFromChessBoard(move.sourceRow, move.sourceColumn);
//			guiPiece = addPeaceToChessBoard(move.getPiece());
//			
//			if (this.chessBoard.isShowPiecesInfo()) {
//				guiPiece.setText(piece.getInfo());
//			}
//		}


		if (piece instanceof King) {
			checkIfCastlingAndPlaceRookOnTheOtherSideOfKing(piece, move);
		}

		if (piece instanceof Pawn) {
			if (piece.getPlayer().getOpposingPlayer().hasPawnEnpassant()) {
				removeEnpassantPawnFromChessBoard(piece, move);
			}

			if(piece.getPlayer().isPawnPromoted()) {
				onPawnPromoted(piece, move);
				return; //true;
			}
		}

		PlayerStatus opponentStatus = piece.getPlayer().getOpposingPlayer().getStatus();

		String moveNotation = StandardAlgebraicMoveNotation.getMoveNotation(
				piece, move, opponentStatus);			

		this.movesBoard.recordMove(moveNotation);

		if(opponentStatus == PlayerStatus.InCheckMate) {
			this.gameStatusBoard.onGameEnd(piece.getColor());
		}		

		//int squareIndex = this.chessBoard.getSquareIndex(move.targetColumn, move.targetRow);
		//JPanel jp =  (JPanel)this.chessBoard.getComponent(squareIndex);		

		//		GuiPiece guiPiece = this.getGuiPieceAt(move.targetRow, move.targetColumn);
		//		if( guiPiece == null){
		//			throw new IllegalStateException("no guiPiece at "+move.targetRow+"/"+move.targetColumn);
		//		}
		//		guiPiece.resetToUnderlyingPiecePosition();

		//		// remember last move
		//		this.lastMove = move;

		// disable dragging until asked by ChessGame for the next move
		this.draggingGamePiecesEnabled = false;

		// repaint the new state
		validate();
		repaint();
	}	
	
	public boolean isDraggingGamePiecesEnabled(){
		return this.draggingGamePiecesEnabled;
	}	

    public Observable getGameStatusBoard() {
        return this.gameStatusBoard;
    }
	
    private class GameStatusBoard extends Observable {
    	public void changeGameStatus(String message) {
            setChanged();
            notifyObservers(message);
        }
    	
    	public void onGameEnd(int winColor) {
            setChanged();
            notifyObservers(winColor);
    	}
    }
    
    public Observable getGameStateBoard() {
        return this.gameStateBoard;
    }
	
    private class GameStateBoard extends Observable {

    	public void changeGameState(String message) {
            setChanged();
            notifyObservers(message);
        }
    }
    
    public Observable getMovesBoard() {
        return this.movesBoard;
    }
	
    private class MovesBoard extends Observable {

    	public void recordMove(String message) {
            setChanged();
            notifyObservers(message);
        }

    	public void removeLastMove() {
            setChanged();
            notifyObservers("removeLastMove");
        }

    	public void removeAllMoves() {
            setChanged();
            notifyObservers("removeAllMoves");
        }
    }

    public Observable getRowNumberBoard() {
        return this.rowNumberBoard;
    }
	
    private class RowNumberBoard extends Observable {

    	public void rotate(int boardOrientation) {
            setChanged();
            notifyObservers(boardOrientation);
        }
    }

    public Observable getColumnNumberBoard() {
        return this.columnNumberBoard;
    }
	
    private class ColumnNumberBoard extends Observable {

    	public void rotate(int boardOrientation) {
            setChanged();
            notifyObservers(boardOrientation);
        }
    }

    public Observable getEditPositionBoard() {
        return this.editPositionBoard;
    }
	
    private class EditPositionBoard extends Observable {

    	public void openEditPosition() {
            setChanged();
            notifyObservers("openEditPosition");
        }
    }
   
    
}
