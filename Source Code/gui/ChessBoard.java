package ch06.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import ch06.logic.Piece;

public class ChessBoard extends JPanel {

	public static final int BOARD_ORIENTATION_WHITE = 0;
	public static final int BOARD_ORIENTATION_BLACK = 1;
	
	private static final int BOARD_WIDTH = 640;
	private static final int BOARD_HEIGHT = 640;

	private static final int BOARD_START_X = 0;
	private static final int BOARD_START_Y = 0;
	
	private static final int SQUARE_WIDTH = 80;
	private static final int SQUARE_HEIGHT = 80;

	private static final int PIECE_WIDTH = 80;
	private static final int PIECE_HEIGHT = 80;
	
	//private static final int PIECES_START_X = BOARD_START_X + (int)(SQUARE_WIDTH/2.0 - PIECE_WIDTH/2.0);
	//private static final int PIECES_START_Y = BOARD_START_Y + (int)(SQUARE_HEIGHT/2.0 - PIECE_HEIGHT/2.0);
	
	private static final int DRAG_TARGET_SQUARE_START_X = BOARD_START_X - (int)(PIECE_WIDTH/2.0);
	private static final int DRAG_TARGET_SQUARE_START_Y = BOARD_START_Y - (int)(PIECE_HEIGHT/2.0);
	
	private Color darkColor;
	private Color lightColor;
	private int boardOrientation;
	
	private boolean showPiecesInfo = false;
	
	public ChessBoard(Color darkColor, Color lightColor) {
		Dimension boardSize = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
		
		this.boardOrientation = BOARD_ORIENTATION_WHITE;
		this.darkColor = darkColor;
		this.lightColor = lightColor;
		
		setBackground(Color.LIGHT_GRAY);
    	setLayout(new GridLayout(8, 8));
    	setPreferredSize(boardSize);
    	setBounds(0, 0, boardSize.width, boardSize.height);	
    	setBorder(BorderFactory.createLineBorder(Color.GRAY));
    	
		createOddRow("8");		
		createEvenRow("7");
		createOddRow("6");		
		createEvenRow("5");
		createOddRow("4");		
		createEvenRow("3");
		createOddRow("2");		
		createEvenRow("1");    	
	}

	public void addPiece(GuiLabel guiLabel) {
		Piece piece = guiLabel.getPiece();
		//int square = 63 - ((guiLabel.getPiece().getRow())*8 + 7 - guiLabel.getPiece().getColumn());
		int squareIndex = getSquareIndex(piece.getColumn(), piece.getRow());
		JPanel jp = (JPanel)getComponent(squareIndex); 		
		jp.add(guiLabel);
	}
	
	public void flip() {
		this.boardOrientation = this.boardOrientation == BOARD_ORIENTATION_WHITE ? 
			BOARD_ORIENTATION_BLACK : BOARD_ORIENTATION_WHITE;
	}
	
	public void setBoardOrientation(int boardOrientation) {
		this.boardOrientation = boardOrientation;
	}	

	public void setWhiteOrientation() {
		this.boardOrientation = BOARD_ORIENTATION_WHITE;
	}

	public void setBlackOrientation() {
		this.boardOrientation = BOARD_ORIENTATION_BLACK;
	}
	
	public int getBoardOrientation() {
		return this.boardOrientation;
	}
	
	public boolean isOrientationWhite() {
		return (this.boardOrientation == BOARD_ORIENTATION_WHITE);
	}
	
	public boolean isOrientationBlack() {
		return (this.boardOrientation == BOARD_ORIENTATION_BLACK);
	}
	
	public boolean isShowPiecesInfo() {
		return this.showPiecesInfo;
	}
	
	/**
	 * convert x coordinate into logical column
	 * @param x
	 * @return logical column for x coordinate
	 */
	public int convertXToColumn(int x){
		if(this.boardOrientation == BOARD_ORIENTATION_WHITE) {
			return (x - DRAG_TARGET_SQUARE_START_X)/SQUARE_WIDTH;
		} else {
			return Piece.ROW_8 -(x - DRAG_TARGET_SQUARE_START_X)/SQUARE_WIDTH;
		}
	}
	
	/**
	 * convert y coordinate into logical row
	 * @param y
	 * @return logical row for y coordinate
	 */
	public int convertYToRow(int y){
		if(this.boardOrientation == BOARD_ORIENTATION_WHITE) {
			return Piece.ROW_8 - (y - DRAG_TARGET_SQUARE_START_Y)/SQUARE_HEIGHT;
		}
		else {
			return (y - DRAG_TARGET_SQUARE_START_Y)/SQUARE_HEIGHT;			
		}
			
	}
	
	public void removePieces() {
		for (Component c : getComponents()) {
			if (c instanceof JPanel) {
				((JPanel)c).removeAll();
			}
		}
	}
	
	public void unselectAll() {
		for (Component c : getComponents()) {
			if (c instanceof JPanel) {
				((JPanel)c).setBorder(BorderFactory.createEmptyBorder());	
			}
		}
	}

	public int getSquareIndex(int column, int row) {
		int squareIndex;
		if(this.isOrientationWhite()) {
			squareIndex = 63 - (row*8 + 7 - column);
		}
		else {
			squareIndex = (row*8 + 7 - column);
		}
		return squareIndex; 
	}
	
	public void selectSquare(int column, int row) {
		int squareIndex = getSquareIndex(column, row);
		JPanel jp = (JPanel)getComponent(squareIndex); 		
		jp.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
	}
	
	public void hidePiecesInfo() {
		this.showPiecesInfo = false;
		for (Component c : getComponents()) {
			if (c instanceof JPanel) {
				Component[] components = ((JPanel)c).getComponents();
				if (components.length == 1) {				
					if (components[0] instanceof GuiLabel) {
						((GuiLabel)components[0]).setText("");
					}
				}
			}
		}
	}
	
	public void showPiecesInfo() {
		this.showPiecesInfo = true;
		for (Component c : getComponents()) {
			if (c instanceof JPanel) {
				Component[] components = ((JPanel)c).getComponents();
				if (components.length == 1) {				
					if (components[0] instanceof GuiLabel) {
						GuiLabel lbl = (GuiLabel)components[0];
						Piece piece = lbl.getPiece();
						lbl.setText(piece.getInfo());
					}
				}
			}
		}
	}
	
	public void rotatePieces() {
		for (int i = 0; i< 32; i++) {
			JPanel pfrom = (JPanel)getComponents()[i];
			JPanel pto = (JPanel)getComponents()[63-i];

			Component cFrom = null;
			if(pfrom.getComponents().length > 0) {
				cFrom = ((JPanel)pfrom).getComponents()[0];
			}

			Component cTo = null;
			if(pto.getComponents().length > 0) {
				cTo = ((JPanel)pto).getComponents()[0];
			}

			if(cFrom != null) {
				pfrom.removeAll();

				if (cTo != null) {
					pto.removeAll();
					pfrom.add(cTo);
				}
				pto.add(cFrom);	
			} else {

				if (cTo != null) {
					pto.removeAll();
					pfrom.add(cTo);
				}
			}
		}
		validate();
		repaint();
	}	
	
	private void createEvenRow(String row) {
		
		add(createSquare(darkColor, row, "A"));		
		add(createSquare(lightColor, row, "B"));
		add(createSquare(darkColor, row, "C"));	
		add(createSquare(lightColor, row, "D"));
		add(createSquare(darkColor, row, "E"));		
		add(createSquare(lightColor, row, "F"));
		add(createSquare(darkColor, row, "G"));	
		add(createSquare(lightColor, row, "H"));
	}

	private void createOddRow(String row) {
		add(createSquare(lightColor, row, "A"));
		add(createSquare(darkColor, row, "B"));
		add(createSquare(lightColor, row, "C"));
		add(createSquare(darkColor, row, "D"));		
		add(createSquare(lightColor, row, "E"));
		add(createSquare(darkColor, row, "F"));		
		add(createSquare(lightColor, row, "G"));
		add(createSquare(darkColor, row, "H"));
	}
	
    private JPanel createSquare(Color color, String row, String column) {
		Dimension squareSize = new Dimension(SQUARE_WIDTH, SQUARE_HEIGHT);
    	JPanel square = new JPanel();
    	square.setName(column+row);
    	square.setLayout(new BorderLayout());
		square.setBackground(color);
		square.setPreferredSize(squareSize);
		square.setBorder(BorderFactory.createEmptyBorder());	
    	return square;
    }	
}
