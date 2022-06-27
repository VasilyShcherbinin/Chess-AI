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

public class ColumnNumberPanel extends JPanel {

    private ChessObserver chessObserver;
    private Chess chess;
    private Observable obsChess;
    
    public ColumnNumberPanel(Chess chess) {

        this.chess = chess;
    	this.chessObserver = new ChessObserver();
    	this.obsChess = chess.getColumnNumberBoard();
    	this.obsChess.addObserver(chessObserver);

    	Dimension boardSize = new Dimension(640, 20);	   
    	setBackground(Color.LIGHT_GRAY);
    	setLayout(new GridLayout(1, 8));
    	setPreferredSize(boardSize);
    	setBounds(0, 0, boardSize.width, boardSize.height);
    	
    	add(createColumnNumber("A"));
    	add(createColumnNumber("B"));
    	add(createColumnNumber("C"));
    	add(createColumnNumber("D"));
    	add(createColumnNumber("E"));
    	add(createColumnNumber("F"));
    	add(createColumnNumber("G"));
    	add(createColumnNumber("H"));
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
    		rotateColumnNumbers();
    		if (obsChess.countObservers()>0) 
    			obsChess.deleteObservers();
    		obsChess = chess.getColumnNumberBoard();
    		obsChess.addObserver(chessObserver);
    	}

    	private void rotateColumnNumbers() {
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