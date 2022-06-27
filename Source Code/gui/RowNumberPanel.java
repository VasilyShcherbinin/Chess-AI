package ch06.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.MessageFormat;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class RowNumberPanel extends JPanel {

    private ChessObserver chessObserver;
    private Chess chess;
    private Observable obsChess;
    //private JLabel label;
    
    public RowNumberPanel(Chess chess) {

        this.chess = chess;
    	this.chessObserver = new ChessObserver();
    	this.obsChess = chess.getRowNumberBoard();
    	this.obsChess.addObserver(chessObserver);

    	Dimension boardSize = new Dimension(20, 640);	   
    	setBackground(Color.LIGHT_GRAY);
    	setLayout(new GridLayout(8, 1));
    	setPreferredSize(boardSize);
    	setBounds(0, 0, boardSize.width, boardSize.height);
    	
    	add(createRowNumber(8));
    	add(createRowNumber(7));	
    	add(createRowNumber(6));
    	add(createRowNumber(5));
    	add(createRowNumber(4));
    	add(createRowNumber(3));
    	add(createRowNumber(2));
    	add(createRowNumber(1));	
	}

    private JLabel createRowNumber(int i) {
    	return createColumnNumber(Integer.toString(i));    	
    }

    private JLabel createColumnNumber(String s) {
    	Font font = new Font("Arial", Font.PLAIN, 12);
    	JLabel lbl = new JLabel(MessageFormat.format("{0}", s), SwingConstants.CENTER);
    	lbl.setFont(font);
    	lbl.setOpaque(true);
    	lbl.setBackground(Color.LIGHT_GRAY);
    	return lbl;
    } 
    
    private class ChessObserver implements Observer {

    	@Override
    	public void update(Observable o, Object arg) {
    		rotateRowNumbers();
    		if (obsChess.countObservers()>0) 
    			obsChess.deleteObservers();
    		obsChess = chess.getRowNumberBoard();
    		obsChess.addObserver(chessObserver);
    	}

    	private void rotateRowNumbers() {
    		for (int i = 0; i< 4; i++) {
    			JLabel lfrom = (JLabel)getComponent(i);
    			JLabel lto = (JLabel)getComponent(7-i);
    			String textFrom = lfrom.getText();
    			String textTo = lto.getText();

    			lfrom.setText(textTo);
    			lto.setText(textFrom);
    		}
    		validate();
    		repaint();
    	}        
    }
}