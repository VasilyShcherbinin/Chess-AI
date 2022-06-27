package ch06.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class GameStatePanel extends JPanel {

    private ChessObserver chessObserver;
    private Chess chess;
    private Observable obsChess;
    private JLabel label;
    
    public GameStatePanel(Chess chess) {

        this.chess = chess;
    	this.chessObserver = new ChessObserver();
    	this.obsChess = chess.getGameStateBoard();
    	this.obsChess.addObserver(chessObserver);

        this.label = new JLabel("New game", JLabel.CENTER);
        add(this.label);
        
    	Dimension boardSize = new Dimension(40, 40);	   
    	setBackground(Color.LIGHT_GRAY);
    	setPreferredSize(boardSize);
    	setBounds(0, 0, boardSize.width, boardSize.height);
    }

    private class ChessObserver implements Observer {

        @Override
        public void update(Observable o, Object arg) {
        	doUpdate((String)arg);
            if (obsChess.countObservers()>0) 
            	obsChess.deleteObservers();
            obsChess = chess.getGameStateBoard();
            obsChess.addObserver(chessObserver);
        }
        
        private void doUpdate(String gameState) {
        	label.setText(gameState);
        }
        
    }
}
