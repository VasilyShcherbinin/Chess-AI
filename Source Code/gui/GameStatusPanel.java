package ch06.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import ch06.logic.Piece;

public class GameStatusPanel extends JPanel {

    private ChessObserver chessObserver;
    private Chess chess;
    private Observable obsChess;
    private JLabel label;
    
    public GameStatusPanel(Chess chess) {

        this.chess = chess;
    	this.chessObserver = new ChessObserver();
    	this.obsChess = chess.getGameStatusBoard();
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
        	if (arg instanceof Integer) {
        		onGameEnd((int)arg);
        	} else {
        		onChangeStatus((String)arg);
        	}
        	
            if (obsChess.countObservers()>0) 
            	obsChess.deleteObservers();
            obsChess = chess.getGameStatusBoard();
            obsChess.addObserver(chessObserver);
        }
        
        private void onChangeStatus(String gameStatus) {
        	Font font = UIManager.getFont("Label.font");
        	label.setFont(font);
        	label.setForeground(Color.BLACK);
        	label.setText(gameStatus);
        }
        
        private void onGameEnd(int color) {
        	Font font = new Font("Arial", Font.PLAIN, 24);
        	label.setFont(font);			
        	label.setForeground(Color.RED);
        	label.setText((color == Piece.COLOR_WHITE) 
    			? "Game over! White won!" 
				: "Game over! Black won!");
        }        
        
    }
    
//	private void setStatusBarOnNewGame(String text) {
//	Font font = UIManager.getFont("Label.font");
//	////		this.chessGameGridBag.getStatusBar().setFont(font);			
//	////		this.chessGameGridBag.getStatusBar().setForeground(Color.BLACK);
//	////		this.chessGameGridBag.setStatusBar(text);
//}

    
}
