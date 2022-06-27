package ch06.gui;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import ch06.logic.Piece;
import ch06.logic.PositionEditor;

public class EditPositionMouseAdapter implements MouseListener, MouseMotionListener {
	private Chess chess;
	private EditPositionPanel editPositionPane;
	
	public EditPositionMouseAdapter(EditPositionPanel editPositionPane, Chess chess) {
		this.chess = chess;
		this.editPositionPane = editPositionPane;
	}
	
    @Override
    public void mousePressed(MouseEvent e) 
    {
    	GuiLabel currentGuiLabel = this.editPositionPane.getCurrentGuiLabel();
    	boolean isErase = this.editPositionPane.isErase();
    	if (currentGuiLabel == null && !isErase)
    		return;

		ChessBoard cb = this.chess.getChessBoard();
    	Component c = cb.findComponentAt(e.getX(), e.getY());
    	int row = cb.convertYToRow(c.getY());
    	int column = cb.convertXToColumn(c.getX());
    	
    	if (currentGuiLabel != null) {
	    	int color = currentGuiLabel.getPiece().getColor();
	    	int type = currentGuiLabel.getPiece().getType();
	    	
	    	if (c instanceof JPanel) {
	    		JPanel jp = (JPanel)c; 
	
	    		Piece piece = PositionEditor.createPiece(color, type, row, column);
	
	    		//add piece to chessBoard (and to editPositionPieces collection)
	    		this.editPositionPane.getPositionEditor().addPiece(piece);
	    		GuiLabel guiLabel = new GuiLabel(currentGuiLabel.getIcon(), piece);
	    		jp.add(guiLabel);
	    		jp.validate();
	    		jp.repaint();
	
	    	} else {
	    		//delete piece from chessBoard (and not from editPositionPieces collection)
	    		GuiLabel gl = (GuiLabel)c;
	    		Piece removedPiece = gl.getPiece();
	    		
	    		row = removedPiece.getRow();
	    		column = removedPiece.getColumn();
	    		
	    		this.editPositionPane.getPositionEditor().removePiece(removedPiece);
	    		
	    		JPanel jp = (JPanel)c.getParent();
	    		jp.removeAll();
	    		jp.repaint();
	
	    		if(gl.getIcon() != currentGuiLabel.getIcon() || removedPiece.getColor() != color) {
	
	    			Piece piece = PositionEditor.createPiece(color, type, row, column);
	    			
	    			//add piece to chessBoard (and not yet to editPositionPieces collection)
	    			this.editPositionPane.getPositionEditor().addPiece(piece);
	    			GuiLabel guiLabel = new GuiLabel(currentGuiLabel.getIcon(), piece);
	    			jp.add(guiLabel);
	    			jp.validate();
	    			jp.repaint();
	    		}
	    	}
    	} else {
    		//erase only
    		if (c instanceof GuiLabel) {
    			GuiLabel gl = (GuiLabel)c;
    			Piece removedPiece = gl.getPiece();
    			this.editPositionPane.getPositionEditor().removePiece(removedPiece);
    			JPanel jp = (JPanel)c.getParent();
    			jp.removeAll();
    			jp.repaint();
    		}
    	}
    }

    @Override
    public void mouseDragged(MouseEvent me) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}
	
	@Override
	public void mouseMoved(MouseEvent arg0) {}
	
}