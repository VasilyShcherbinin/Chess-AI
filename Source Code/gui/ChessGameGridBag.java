package ch06.gui;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.text.MessageFormat;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.MetalToggleButtonUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import ch06.logic.ChessGame;
import ch06.logic.Piece;

public class ChessGameGridBag extends JPanel {

	private GridBagConstraints constraints = new GridBagConstraints();

	private Chess chess;
	private JPanel rowNumberPane;
	private JPanel columnNumberPane;
	private GameStatePanel statePane;
	private GameStatusPanel statusPane;

	private MovesPanel movesPane;
	private EditPositionPanel editPositionPane;
	
    final ButtonGroup pieceButtonsGroup = new ButtonGroup();
	final JToggleButton[] pieceButtons = new JToggleButton[12];	
	final JToggleButton[] editPositionButtons = new JToggleButton[4];
	
	public ChessGameGridBag(
		Chess chess,
		JPanel rowNumberPane,
		JPanel columnNumberPane,
		GameStatePanel statePane,
		GameStatusPanel statusPane,
		MovesPanel movesPane,
		EditPositionPanel editPositionPane) {

		JLabel emptyPane = createEmptyBar();
		
		this.chess = chess;
		this.rowNumberPane = rowNumberPane;
		this.columnNumberPane = columnNumberPane;
		this.statePane = statePane;
		this.statusPane = statusPane;
		this.movesPane = movesPane;
		this.editPositionPane = editPositionPane;

		setBackground(Color.LIGHT_GRAY);
		setLayout(new GridBagLayout());
		
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.fill = GridBagConstraints.BOTH;
		
		int x,y;
		constraints.gridheight = 1;
		constraints.gridwidth = 3;
		//addGB(new JButton("1"), x=0, y=0);
		addGB(statusPane, x=0, y=0);

		constraints.gridheight = 8;
		constraints.gridwidth = 1;
		//addGB(new JButton("2"), x=0, y=1);
		addGB(rowNumberPane, x=0, y=1);

		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		addGB(emptyPane, x=0, y=10);
		
//		constraints.gridheight = 1;
//		constraints.gridwidth = 1;
//		addGB(new JButton("2.1"), x=0, y=2);
//		addGB(new JButton("2.2"), x=0, y=3);
//		addGB(new JButton("2.3"), x=0, y=4);
//		addGB(new JButton("2.4"), x=0, y=5);
//		addGB(new JButton("2.5"), x=0, y=6);
//		addGB(new JButton("2.6"), x=0, y=7);
//		addGB(new JButton("2.7"), x=0, y=8);
		
		constraints.gridheight = 8;
		constraints.gridwidth = 1;
		//addGB(new JButton("3"), x=1, y=1);
		addGB(this.chess, x=1, y=1);
		
//		addGB(new JButton("3.1"), x=1, y=2);
//		addGB(new JButton("3.2"), x=1, y=3);
//		addGB(new JButton("3.3"), x=1, y=4);
//		addGB(new JButton("3.4"), x=1, y=5);
//		addGB(new JButton("3.5"), x=1, y=6);
//		addGB(new JButton("3.6"), x=1, y=7);
//		addGB(new JButton("3.7"), x=1, y=8);
		
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		//addGB(new JButton("4"), x=1, y=10);
		addGB(this.columnNumberPane, x=1, y=10);
		
//		constraints.gridheight = 1;		
//		constraints.gridwidth = 1;
//		//addGB(new JButton("5"), x=2, y=1);
//		addGB(stateBar, x=2, y=10);
		
		constraints.gridheight = 8;		
		constraints.gridwidth = 1;
		constraints.insets.left = 3;
		constraints.insets.top = 0;
		constraints.insets.right = 3;
		constraints.insets.bottom = 0;
		//addGB(new JButton("5"), x=2, y=1);
		addGB(this.movesPane, x=2, y=1);
		//addGB(editPositionPane, x=2, y=1);
		
		
		//constraints.ipadx = 0;
		//constraints.ipady = 0;
		
//		addGB(new JButton("6.1"), x=2, y=3);
//		addGB(new JButton("6.2"), x=2, y=4);
//		addGB(new JButton("6.3"), x=2, y=5);
//		addGB(new JButton("6.4"), x=2, y=6);
//		addGB(new JButton("6.5"), x=2, y=7);
//		addGB(new JButton("6.6"), x=2, y=8);
//		addGB(new JButton("6.7"), x=2, y=9);

		constraints.gridheight = 1;		
		constraints.gridwidth = 1;
		constraints.insets.left = 3;
		constraints.insets.top = 3;
		constraints.insets.right = 3;
		constraints.insets.bottom = 3;
		//addGB(new JButton("6"), x=2, y=10);
		addGB(this.statePane, x=2, y=10);
	}
	
	private void addGB(Component component, int x, int y) {
		constraints.gridx = x;
		constraints.gridy = y;
		add(component, constraints);
	}

    private JLabel createEmptyBar() {
    	String labelText = "";
    	JLabel lbl = new JLabel(labelText, SwingConstants.CENTER);
    	lbl.setOpaque(true);
    	lbl.setBackground(Color.LIGHT_GRAY);
    	lbl.setPreferredSize(new Dimension(40, 40));
    	return lbl;
    }
    
//	public void setStateBar(String txt) {
//		this.stateBar.setText(txt);
//	}
//
//    public JLabel getStateBar() {
//    	return this.stateBar;
//    }

//	public void setStatusBar(String txt) {
//		this.statusBar.setText(txt);
//	}
//    
//	public JLabel getStatusBar() {
//		return this.statusBar;
//	}

//	public JTable getMovesTable() {
//		return this.movesTable;
//	}
//	
	public JScrollPane getMovesPane() {
		return this.movesPane;
	}

	public EditPositionPanel getEditPositionPane() {
		return this.editPositionPane;
	}
	
//    public Chess getChessGui() {
//    	return this.chess;
//    }

//    public JPanel getRowNumberPane() {
//    	return this.rowNumberPane;
//    }    
//
//    public JPanel getColumnNumberPane() {
//    	return this.columnNumberPane;
//    }    
}
