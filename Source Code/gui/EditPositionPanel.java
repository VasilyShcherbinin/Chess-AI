package ch06.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.plaf.metal.MetalToggleButtonUI;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractButton;





//import ch06.gui.MovesPanel.ChessObserver;


import ch06.logic.Bishop;
import ch06.logic.ChessGame;
import ch06.logic.King;
import ch06.logic.Knight;
import ch06.logic.Pawn;
import ch06.logic.Piece;
import ch06.logic.PositionEditor;
import ch06.logic.PositionValidator;
import ch06.logic.Queen;
import ch06.logic.Rook;

public class EditPositionPanel extends JPanel {

	private static final int SQUARE_WIDTH = 80;
	private static final int SQUARE_HEIGHT = 60;
	
    final ButtonGroup pieceButtonsGroup = new ButtonGroup();
	final JToggleButton[] pieceButtons = new JToggleButton[12];	
	final ButtonGroup editPositionButtonsGroup = new ButtonGroup();
	final JToggleButton[] editPositionButtons = new JToggleButton[4];
	final GuiLabel[] guiLabels = new GuiLabel[12];	

    private ChessObserver chessObserver;
    private Chess chess;
    private Observable obsChess;
    
	private GuiLabel currentGuiLabel = null;
	
	private PositionEditor positionEditor;
	private EditPositionMouseAdapter mouseAdapter;	
	
	private int boardOrientationBeforeEditPosition;
	private int gameStateBeforeEditPosition;
	
	public EditPositionPanel(Chess chess) {
		this.chess = chess;
    	this.chessObserver = new ChessObserver();
    	this.obsChess = chess.getEditPositionBoard();
    	this.obsChess.addObserver(chessObserver);

		this.positionEditor = new PositionEditor();
		this.mouseAdapter = new EditPositionMouseAdapter(this, chess);
		
		setLayout(new BorderLayout());
		add(createEditPositionPane(), BorderLayout.CENTER);
		add(createButtons(), BorderLayout.SOUTH);
	}
	
	private JPanel createButtons() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 1));
		panel.add(createClearBoardButton());
		panel.add(createInitialPositionButton());
		panel.add(createFinishedButton());
		panel.add(createCancelButton());
		return panel;
	}

	private JButton createClearBoardButton() {
		JButton btn = new JButton("Clear Board");
		btn.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	clearBoard();
	        }
	    });		
		return btn;
	}

	private JButton createInitialPositionButton() {
		JButton btn = new JButton("Initial Position");
		btn.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	initialPosition();
	        }
	    });		
		return btn;
	}
	
	private JButton createFinishedButton() {
		JButton btn = new JButton("Finished");
		btn.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	finishEditPosition();
	        }
	    });		
		return btn;
	}

	private JButton createCancelButton() {
		JButton btn = new JButton("Cancel");
		btn.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	cancelEditPosition();
	        }
	    });		
		return btn;
	}
	
	private void clearBoard() {
		this.positionEditor.removePieces();
		this.chess.getChessBoard().removePieces();
		this.chess.getChessBoard().unselectAll();
		this.chess.validate();
		this.chess.repaint();
	}

	private void initialPosition() {
		List<Piece> whitePieces = new ArrayList<Piece>();
		List<Piece> blackPieces = new ArrayList<Piece>();
		
		ChessGame.playerWhite.getPiecesAtStartingPositions(whitePieces);
		ChessGame.playerBlack.getPiecesAtStartingPositions(blackPieces);
		
		this.positionEditor.initializePieces(whitePieces, blackPieces);
		this.chess.getChessBoard().removePieces();

		this.chess.addPiecesToChessBoard(this.positionEditor.getPieces());
		this.chess.validate();
		this.chess.repaint();		
	}
	
	private void finishEditPosition() {
		
		int activeColor = getActiveColor();
		if (activeColor == -1) {
			this.chess.changeStatusBoard("Who is moving next not specified");
			return;
		}		
		
		PositionValidator validator = new PositionValidator(this.positionEditor.getPieces());
		if(!validator.isValidPosition()) {
			//at the moment only first error is used
			this.chess.changeStatusBoard(validator.getFirstValidationError());
			return;
		}
		
		if(!validator.isValidKingsPosition()) {
			this.chess.changeStatusBoard(validator.getFirstValidationError());
			return;
		}
		
		//check if initial position and drop some flags 
		//todo: with FEN ?
		//..........
		
		this.chess.getChessGame().setGameState(activeColor);		
		
		this.chess.removeMouseAdapter(this.mouseAdapter);
		this.chess.addMouseAdapter(this.chess.getMouseAdapter());		
		
		this.chess.clearChessBoard();
		this.chess.orientChessBoard(this.boardOrientationBeforeEditPosition);
		this.chess.changeStateAndStatusBoards();		
		
		ChessGame.playerWhite.setPiecesAtEditedPositions(this.positionEditor.getPieces());
		ChessGame.playerBlack.setPiecesAtEditedPositions(this.positionEditor.getPieces());

		this.chess.addPiecesToChessBoard();
		
		if(validator.isDrawByInsufficientMaterial()) {
			this.chess.changeStatusBoard(validator.getFirstValidationError());
		}		
		
		//boolean isEnPassant = editPositionButtons[3].isSelected();
		
		this.chess.cleanMovesTable();
		closeEditPositionPanel();
	}

	private int getActiveColor() {
		int activeColor = -1;
		if(editPositionButtons[0].isSelected()) {
			activeColor = Piece.COLOR_WHITE;
		} else if(editPositionButtons[1].isSelected()) {
			activeColor = Piece.COLOR_BLACK;
		}
		return activeColor;
	}
	
	private void openEditPosition() {
		
		this.boardOrientationBeforeEditPosition = 
			this.chess.getChessBoard().getBoardOrientation();
		
		this.gameStateBeforeEditPosition = 
			this.chess.getChessGame().getGameState();
		
		this.chess.getChessGame().setGameState(
			ChessGame.GAME_STATE_EDIT_POSITION);		
		
		this.positionEditor.initializePieces(
			ChessGame.playerWhite.getPieces(),
			ChessGame.playerBlack.getPieces());

		this.chess.removeMouseAdapter(this.chess.getMouseAdapter());
		this.chess.addMouseAdapter(this.mouseAdapter);
		
		this.chess.clearChessBoard();
		this.chess.orientChessBoard(ChessBoard.BOARD_ORIENTATION_WHITE);
		this.chess.changeStateAndStatusBoards();
		this.chess.addPiecesToChessBoard(this.positionEditor.getPieces());
		
		openEditPositionPanel();
	}
	
	private void cancelEditPosition() {
		
		this.chess.getChessGame().setGameState(
			this.gameStateBeforeEditPosition);		
		
		this.chess.removeMouseAdapter(this.mouseAdapter);
		this.chess.addMouseAdapter(this.chess.getMouseAdapter());

		this.chess.clearChessBoard();
		this.chess.orientChessBoard(this.boardOrientationBeforeEditPosition);
		this.chess.changeStateAndStatusBoards();
		
		this.chess.addPiecesToChessBoard(ChessGame.playerWhite.getPieces());
		this.chess.addPiecesToChessBoard(ChessGame.playerBlack.getPieces());
		
		clearSelection();
		closeEditPositionPanel();
	}

	private void openEditPositionPanel() {
		ChessGameGridBag gb = (ChessGameGridBag)chess.getParent();
		GridBagLayout layout = (GridBagLayout)gb.getLayout();
		GridBagConstraints gbc = layout.getConstraints(gb.getMovesPane());
		gb.remove(gb.getMovesPane());
		gb.add(gb.getEditPositionPane(), gbc, 0);
		gb.validate();
		gb.repaint();    		
	}
	
	private void closeEditPositionPanel() {
		ChessGameGridBag gb = (ChessGameGridBag)chess.getParent();
		GridBagLayout layout = (GridBagLayout)gb.getLayout();
		GridBagConstraints gbc = layout.getConstraints(gb.getEditPositionPane());
		gb.remove(gb.getEditPositionPane());
		gb.add(gb.getMovesPane(), gbc, 0);
		gb.validate();
		gb.repaint();    		
	}	
	
	private void clearSelection() {
		pieceButtonsGroup.clearSelection();
    	editPositionButtons[0].setSelected(false);
    	editPositionButtons[1].setSelected(false);
    	editPositionButtons[2].setSelected(false);
    	editPositionButtons[3].setSelected(false);				
	}
	
	private JPanel createEditPositionPane() {
		JPanel pane = new JPanel();
		
		pane.setBackground(Color.LIGHT_GRAY);
		pane.setLayout(new GridLayout(8, 2));
		pane.setBorder(BorderFactory.createLineBorder(Color.GRAY));

    	pieceButtons[0] = createSquare(0, new Pawn(null, Piece.COLOR_WHITE, Piece.TYPE_PAWN, -1, -1));
    	pieceButtons[1] = createSquare(1, new Pawn(null, Piece.COLOR_BLACK, Piece.TYPE_PAWN, -1, -1));
    	pieceButtons[2] = createSquare(2, new Knight(null, Piece.COLOR_WHITE, Piece.TYPE_KNIGHT, -1, -1));
    	pieceButtons[3] = createSquare(3, new Knight(null, Piece.COLOR_BLACK, Piece.TYPE_KNIGHT, -1, -1));
    	pieceButtons[4] = createSquare(4, new Bishop(null, Piece.COLOR_WHITE, Piece.TYPE_BISHOP, -1, -1));
    	pieceButtons[5] = createSquare(5, new Bishop(null, Piece.COLOR_BLACK, Piece.TYPE_BISHOP, -1, -1));
    	pieceButtons[6] = createSquare(6, new Rook(null, Piece.COLOR_WHITE, Piece.TYPE_ROOK, -1, -1));
    	pieceButtons[7] = createSquare(7, new Rook(null, Piece.COLOR_BLACK, Piece.TYPE_ROOK, -1, -1));
    	pieceButtons[8] = createSquare(8, new Queen(null, Piece.COLOR_WHITE, Piece.TYPE_QUEEN, -1, -1));
    	pieceButtons[9] = createSquare(9, new Queen(null, Piece.COLOR_BLACK, Piece.TYPE_QUEEN, -1, -1));
    	pieceButtons[10] = createSquare(10, new King(null, Piece.COLOR_WHITE, Piece.TYPE_KING, -1, -1));
    	pieceButtons[11] = createSquare(11, new King(null, Piece.COLOR_BLACK, Piece.TYPE_KING, -1, -1));
    	
    	 //Group buttons.
        pieceButtonsGroup.add(pieceButtons[0]);
        pieceButtonsGroup.add(pieceButtons[1]);
        pieceButtonsGroup.add(pieceButtons[2]);
        pieceButtonsGroup.add(pieceButtons[3]);
        pieceButtonsGroup.add(pieceButtons[4]);
        pieceButtonsGroup.add(pieceButtons[5]);
        pieceButtonsGroup.add(pieceButtons[6]);
        pieceButtonsGroup.add(pieceButtons[7]);
        pieceButtonsGroup.add(pieceButtons[8]);
        pieceButtonsGroup.add(pieceButtons[9]);
        pieceButtonsGroup.add(pieceButtons[10]);
        pieceButtonsGroup.add(pieceButtons[11]);
    	
        //Add buttons to pane        
        pane.add(pieceButtons[0]);
        pane.add(pieceButtons[1]);
        pane.add(pieceButtons[2]);
        pane.add(pieceButtons[3]);
        pane.add(pieceButtons[4]);
        pane.add(pieceButtons[5]);
        pane.add(pieceButtons[6]);
        pane.add(pieceButtons[7]);
        pane.add(pieceButtons[8]);
        pane.add(pieceButtons[9]);
        pane.add(pieceButtons[10]);
        pane.add(pieceButtons[11]);
    	
    	editPositionButtons[0] = createWhiteToMoveButton();
    	editPositionButtons[1] = createBlackToMoveButton();    	
    	editPositionButtons[2] = createEraseButton();
    	editPositionButtons[3] = createEnPassantButton();
    	
    	ButtonGroup group2 = new ButtonGroup();
    	group2.add(editPositionButtons[0]);
    	group2.add(editPositionButtons[1]);

    	//ButtonGroup group3 = new ButtonGroup();
    	editPositionButtonsGroup.add(editPositionButtons[2]);
    	editPositionButtonsGroup.add(editPositionButtons[3]);
    	
    	pane.add(editPositionButtons[0]);
    	pane.add(editPositionButtons[1]);
    	pane.add(editPositionButtons[2]);
    	pane.add(editPositionButtons[3]);
		
		return pane;
	}
	
    private JToggleButton createSquare(int i, Piece piece) {
    	
		//ImageIcon imic = getImageIconForPiece(piece.getColor(), piece.getType());
		//GuiLabel guiLabel = new GuiLabel(imic, piece);    	
    	//this.guiLabels[i] = guiLabel;
    	this.guiLabels[i] = this.chess.createGuiPiece(piece);
		
		Dimension squareSize = new Dimension(SQUARE_WIDTH, SQUARE_HEIGHT);
    	
		JToggleButton btn = new JToggleButton(this.guiLabels[i].getIcon());
		btn.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	setCurrentGuiLabel();
        		editPositionButtonsGroup.clearSelection();
	        }
	    });		
		
		btn.setPreferredSize(squareSize);
		
		btn.setUI(new MetalToggleButtonUI() {
		    @Override
		    protected Color getSelectColor() {
		        return Color.GREEN;
		    }
		});		
		
		return btn;
    }        
    
    private JToggleButton createWhiteToMoveButton() {
		Dimension squareSize = new Dimension(SQUARE_WIDTH, SQUARE_HEIGHT);
    	
		JToggleButton btn = new JToggleButton();
		btn.setPreferredSize(squareSize);
		btn.setLayout(new GridLayout(3,1));
		JLabel label1 = new JLabel("White", SwingConstants.CENTER);
		JLabel label2 = new JLabel("to", SwingConstants.CENTER);
		JLabel label3 = new JLabel("move", SwingConstants.CENTER);
		btn.add(label1);
		btn.add(label2);
		btn.add(label3);
		   
		btn.setUI(new MetalToggleButtonUI() {
		    @Override
		    protected Color getSelectColor() {
		        return Color.GREEN;
		    }
		});			
		return btn;
    }
    
    private JToggleButton createEraseButton() {
		Dimension squareSize = new Dimension(SQUARE_WIDTH, SQUARE_HEIGHT);
    	
		JToggleButton btn = new JToggleButton( "Erase" );
		btn.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	pieceButtonsGroup.clearSelection();
	        	dropCurrentGuiLabel();
	        }
	    });		
		
		btn.setPreferredSize(squareSize);
		btn.setUI(new MetalToggleButtonUI() {
		    @Override
		    protected Color getSelectColor() {
		        return Color.GREEN;
		    }
		});			
		return btn;
    }    

    private JToggleButton createBlackToMoveButton() {
		Dimension squareSize = new Dimension(SQUARE_WIDTH, SQUARE_HEIGHT);
    	
		JToggleButton btn = new JToggleButton();
		btn.setPreferredSize(squareSize);
		btn.setLayout(new GridLayout(3,1));
		JLabel label1 = new JLabel("Black", SwingConstants.CENTER);
		JLabel label2 = new JLabel("to", SwingConstants.CENTER);
		JLabel label3 = new JLabel("move", SwingConstants.CENTER);
		btn.add(label1);
		btn.add(label2);
		btn.add(label3);

		btn.setUI(new MetalToggleButtonUI() {
		    @Override
		    protected Color getSelectColor() {
		        return Color.GREEN;
		    }
		});			
		return btn;
    }
    
    private JToggleButton createEnPassantButton() {
		Dimension squareSize = new Dimension(SQUARE_WIDTH, SQUARE_HEIGHT);
    	
		JToggleButton btn = new JToggleButton();
		btn.setPreferredSize(squareSize);
		btn.setLayout(new GridLayout(3,1));
		JLabel label1 = new JLabel("En", SwingConstants.CENTER);
		JLabel label2 = new JLabel("passant", SwingConstants.CENTER);
		btn.add(label1);
		btn.add(label2);
		
		btn.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	pieceButtonsGroup.clearSelection();
	        }
	    });		
	
		btn.setPreferredSize(squareSize);
		btn.setUI(new MetalToggleButtonUI() {
		    @Override
		    protected Color getSelectColor() {
		        return Color.GREEN;
		    }
		});			
		return btn;
    }    
    
//	private ImageIcon getImageIconForPiece(int color, int type) {
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
//		return new ImageIcon(urlPieceImg);
//	}    
	
//	private ButtonModel getSelectedPieceButton() {
//		return pieceButtonsGroup.getSelection();
//	}
//			
//	private Icon getSelectedPieceButtonIcon() {
//        for (Enumeration<AbstractButton> buttons = pieceButtonsGroup.getElements(); buttons.hasMoreElements();) {
//            AbstractButton button = buttons.nextElement();
//
//            if (button.isSelected()) {
//                return button.getIcon();
//            }
//        }
//        return null;
//    }	

	private int getSelectedPieceButtonIndex() {
    	int i = 0;
        for (Enumeration<AbstractButton> buttons = pieceButtonsGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return i;
            }
            i++;
        }
        return -1;
    }	

	private void dropCurrentGuiLabel() {
		this.currentGuiLabel = null;
	}
	
	private void setCurrentGuiLabel() {
		int i = getSelectedPieceButtonIndex();
		this.currentGuiLabel = this.guiLabels[i];
	}
	
	public GuiLabel getCurrentGuiLabel() {
		return this.currentGuiLabel;
	}	
    
	public PositionEditor getPositionEditor() {
		return this.positionEditor;
	}
	
	public boolean isErase() {
		return editPositionButtons[2].isSelected();
	}
	
    private class ChessObserver implements Observer {

    	@Override
    	public void update(Observable o, Object arg) {
    		switch ((String) arg) {
    		case "openEditPosition":
    			openEditPosition(); break;
			default:
				break;
    		}
    		
    		if (obsChess.countObservers()>0) 
    			obsChess.deleteObservers();
    		obsChess = chess.getEditPositionBoard();
    		obsChess.addObserver(chessObserver);
    	}
    }
}
