package ch06.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ch06.logic.ChessGame;

public class MovesPanel extends JScrollPane {

    private ChessObserver chessObserver;
    private Chess chess;
    private Observable obsChess;
    private JTable movesTable;
    
    public MovesPanel(Chess chess) {

        this.chess = chess;
    	this.chessObserver = new ChessObserver();
    	this.obsChess = chess.getMovesBoard();
    	this.obsChess.addObserver(chessObserver);

    	this.movesTable = createMovesTable();
    	cleanMovesTable();
    	setViewportView(this.movesTable);
		getViewport().setBackground(Color.LIGHT_GRAY);
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    private JTable createMovesTable() {
    	String data[][] = {};
    	String col[] = {"No", "White", "Black"};

    	DefaultTableModel model = new DefaultTableModel(data, col);
    	JTable table = new JTable(model);
    	table.setOpaque(false);

    	Dimension tableSize =  table.getPreferredSize();
    	table.getColumnModel().getColumn(0).setPreferredWidth(Math.round(tableSize.width*0.2f));
    	table.getColumnModel().getColumn(1).setPreferredWidth(Math.round(tableSize.width*0.4f));
    	table.getColumnModel().getColumn(2).setPreferredWidth(Math.round(tableSize.width*0.4f));

    	Dimension size3 = new Dimension(200,200);
    	table.setPreferredScrollableViewportSize(size3);
    	return table;
    }
    
    private void cleanMovesTable() {
    	DefaultTableModel model = (DefaultTableModel) this.movesTable.getModel();
    	int rowCount = model.getRowCount();
    	//Remove rows one by one from the end of the table
    	for (int i = rowCount - 1; i >= 0; i--) {
    		model.removeRow(i);
    	}		
    	model.addRow(new Object[]{1, "", ""});		
    }
    
    private class ChessObserver implements Observer {

    	@Override
    	public void update(Observable o, Object arg) {
    		String what = (String) arg;
    		switch (what) {
    		case "removeLastMove":
    			removeLastMoveFromMovesTable(); break;
    		case "removeAllMoves":
    			removeAllMovesFromMovesTable(); break;
			default:
    			recordMoveInStandardAlgebraicNotation(what); break; 
    		}
    		
    		if (obsChess.countObservers()>0) 
    			obsChess.deleteObservers();
    		obsChess = chess.getMovesBoard();
    		obsChess.addObserver(chessObserver);
    	}

    	private void recordMoveInStandardAlgebraicNotation (String moveNotation) {

    		DefaultTableModel model = (DefaultTableModel)movesTable.getModel();

    		switch (chess.getChessGame().getGameState()) {
    		case ChessGame.GAME_STATE_WHITE:
    			model.setValueAt(moveNotation, model.getRowCount()-1, 1); break;

    		case ChessGame.GAME_STATE_BLACK:
    			model.setValueAt(moveNotation, model.getRowCount()-1, 2); 
    			model.addRow(new Object[]{model.getRowCount()+1, "", ""}); break;

    			//			case ChessGame.GAME_STATE_END:
    			//				int column = (this.chessGame.getWinColor() == 0) ? 1 : 2; 
    			//				model.setValueAt(moveNotation, model.getRowCount()-1, column); break;

    		default:
    			break;
    		}
    	}
    	
    	private void removeLastMoveFromMovesTable () {
		
    		DefaultTableModel model = (DefaultTableModel)movesTable.getModel();
		
    		int rows = model.getRowCount();
		
    		switch (chess.getChessGame().getGameState()) {
			case ChessGame.GAME_STATE_WHITE:
				model.setValueAt("", rows-1, 1); break;
	
			case ChessGame.GAME_STATE_BLACK:
				model.removeRow(rows-1);
				model.setValueAt("", model.getRowCount()-1, 2); 
			default:
				break;
    		}
    	}    
    	
    	private void  removeAllMovesFromMovesTable() {
    		
    		DefaultTableModel model = (DefaultTableModel)movesTable.getModel();
    		int rows = model.getRowCount();
    		//Remove rows one by one from the end of the table
			for (int i = rows - 1; i >= 0; i--) {
				model.removeRow(i);
			}		
			model.addRow(new Object[]{1, "", ""});		
    	}    	
    }
}
