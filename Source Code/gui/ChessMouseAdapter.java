package ch06.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import ch06.logic.ChessGame;
import ch06.logic.Piece;

public class ChessMouseAdapter implements MouseListener, MouseMotionListener {
	private Chess chess;
	private JPanel clickedPanel;
	private GuiLabel chessPieceLabel;
	
	private int xCoordinate;
	private int yCoordinate;	
	
	public ChessMouseAdapter(Chess chess) {
		this.chess = chess;
	}
	
    @Override
    public void mousePressed(MouseEvent e) 
    {
		if( !this.chess.isDraggingGamePiecesEnabled()){
			return;
		}
		
    	int gameState = chess.getChessGame().getGameState();
    	if (gameState == ChessGame.GAME_STATE_END) {
    		return;
    	}
    	
		clickedPanel = (JPanel) chess.getChessBoard().getComponentAt(e.getPoint());
		Component[] components = clickedPanel.getComponents();
		if (components.length == 0) {
			clickedPanel = null;
			return;
		}

		chessPieceLabel = null;
		Component c = chess.getChessBoard().findComponentAt(e.getX(), e.getY());

		if (c instanceof JPanel)
			return;

		Point parentLocation = c.getParent().getLocation();
		xCoordinate = parentLocation.x - e.getX();
		yCoordinate = parentLocation.y - e.getY();
		chessPieceLabel = (GuiLabel) c;
		chessPieceLabel.setLocation(e.getX() + xCoordinate, e.getY() + yCoordinate);
		chessPieceLabel.setSize(chessPieceLabel.getWidth(), chessPieceLabel.getHeight());
		chess.add(chessPieceLabel, JLayeredPane.DRAG_LAYER);
		chess.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
    }

    @Override
    public void mouseDragged(MouseEvent me)
    {
        if (chessPieceLabel == null) return;

        //  The drag location should be within the bounds of the chess board

        int x = me.getX() + xCoordinate;
        int xMax = chess.getWidth() - chessPieceLabel.getWidth();
        x = Math.min(x, xMax);
        x = Math.max(x, 0);

        int y = me.getY() + yCoordinate;
        int yMax = chess.getHeight() - chessPieceLabel.getHeight();
        y = Math.min(y, yMax);
        y = Math.max(y, 0);

        chessPieceLabel.setLocation(x, y);
     }

    @Override
    public void mouseReleased(MouseEvent e)
    {
    	chess.setCursor(null);

    	if (clickedPanel == null) return;
        if (chessPieceLabel == null) return;

        //  Make sure the chess piece is no longer painted on the layered pane
        Piece p = chessPieceLabel.getPiece();
        
        chessPieceLabel.setVisible(false);
        chess.remove(chessPieceLabel);
        //chessPieceLabel.setVisible(true);

        //  The drop location should be within the bounds of the chess board
        int pieceWidth = chessPieceLabel.getWidth();
        int pieceHeight = chessPieceLabel.getHeight();
        
        int xMax = chess.getWidth() - pieceWidth;
        int x = Math.min(e.getX(), xMax);
        x = Math.max(x, 0);

        int yMax = chess.getHeight() - pieceHeight;
        int y = Math.min(e.getY(), yMax);
        y = Math.max(y, 0);

        Component c =  chess.getChessBoard().findComponentAt(x, y);
        if (c instanceof GuiLabel) {
            mouseReleasedOnPiece(c);            		
        }
        else  {
        	mouseReleasedOnEmptySquare(c);
        }

        chessPieceLabel.setVisible(true);
        chessPieceLabel.validate();
		clickedPanel = null;
        
//		if (chessGui.getChessGame().getGameState() == ChessGame.GAME_STATE_END) {
//			chessGui.setStatusBarOnGameEnd(chessPieceLabel.getPiece().getColor());
//		} else {
//			chessGui.getChessGameGridBag().setStatusBar("");
//		}
//		
//		chessGui.getChessGameGridBag().setStateBar(chessGui.getGameStateAsText());
		this.chess.changeStatusBoard("");
    }

	private void mouseReleasedOnPiece(Component c) {
		Container parent;
		parent = c.getParent();
		
		//only valid move is allowed - into square with green border
		Insets insets = ((JPanel)parent).getInsets();
		if (insets.left > 0) {
			parent.remove(0);
			
			chess.getChessBoard().unselectAll();		

			parent.add(chessPieceLabel);
			((JPanel)parent).setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
			clickedPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
			parent.validate();				
			
			if (this.chess.setNewPieceLocation(chessPieceLabel,
					parent.getX(), parent.getY())) {
				return;
			}
		}
		movePeaceBack();			
	}

	private void mouseReleasedOnEmptySquare(Component c) {
		Container parent;
		parent = (Container)c;

		//same square - no move
		if (parent.getName() != clickedPanel.getName()) {
			//only valid move is allowed - into square with green border
			Insets insets = ((JPanel)parent).getInsets();
			if (insets.left > 0) {
				
				chess.getChessBoard().unselectAll();		

				parent.add(chessPieceLabel);
				((JPanel)parent).setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
				clickedPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
				parent.validate();				
				
				if (this.chess.setNewPieceLocation(chessPieceLabel,
						parent.getX(), parent.getY())) {
					return;
				}
			}
		}
		movePeaceBack();
	}
    
	private void movePeaceBack() {
		chess.getChessBoard().unselectAll();
		clickedPanel.add(chessPieceLabel);
		clickedPanel.validate();
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}
	
	@Override
	public void mouseMoved(MouseEvent arg0) {}
	
}