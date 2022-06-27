package ch06.gui;
//package ch06.gui;
//
//import java.awt.*;
//import java.awt.event.*;
//import java.text.MessageFormat;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.JTableHeader;
//import javax.swing.table.TableModel;
//
//import ch06.ai.SimpleAiPlayerHandler;
//import ch06.logic.ChessGame;
//import ch06.logic.Piece;
//
//public class ChessGameMain implements ActionListener {
//	
//	//private JFrame frame;
//	private ChessGameGridBag chessGameGridBag;
//    
//	public ChessGameMain() {
//		//this.frame = frame;
//	}
//	
//    public JMenuBar createMenuBar() {
//    	JMenuBar menuBar = new JMenuBar();
//    	addFileMenuTo(menuBar);
//    	addGameMenuTo(menuBar);
//    	addDisplayMenuTo(menuBar);
//    	return menuBar;
//    }
// 
//    private void addFileMenuTo(JMenuBar menuBar) {
//    	JMenu fileMenu = new JMenu("File");
//    	fileMenu.setMnemonic(KeyEvent.VK_F);
//    	menuBar.add(fileMenu);
//    	
//    	JMenuItem newGameMenuItem;
//    	JMenuItem loadGameMenuItem;
//    	JMenuItem saveGameMenuItem;
//    	//JMenuItem rotateGameMenuItem;
//    	JMenuItem exitGameMenuItem;
//
//    	//a group of JMenuItems
//    	newGameMenuItem = new JMenuItem("New Game Window", KeyEvent.VK_N);
//    	newGameMenuItem.addActionListener(this);
//    	fileMenu.add(newGameMenuItem);
//    	
//    	fileMenu.addSeparator();
//
//    	ButtonGroup group = new ButtonGroup();    	
//    	loadGameMenuItem = new JMenuItem("Load Game", KeyEvent.VK_L);
//    	loadGameMenuItem.addActionListener(this);
//    	group.add(loadGameMenuItem);
//    	fileMenu.add(loadGameMenuItem);
//
//    	fileMenu.addSeparator();
//
//    	saveGameMenuItem = new JMenuItem("Save Game", KeyEvent.VK_S);
//    	saveGameMenuItem.addActionListener(this);
//    	group.add(saveGameMenuItem);
//    	fileMenu.add(saveGameMenuItem);
//
//    	fileMenu.addSeparator();
//    	
//    	exitGameMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
//    	exitGameMenuItem.addActionListener(this);
//    	fileMenu.add(exitGameMenuItem);
//    }
//
//    private void addGameMenuTo(JMenuBar menuBar) {
//    	JMenu gameMenu = new JMenu("Game");
//    	gameMenu.setMnemonic(KeyEvent.VK_G);
//    	menuBar.add(gameMenu);    	
//    	
//    	JMenuItem newGameMenuItem;
//    	JMenuItem newGameFromFenMenuItem;
//    	JMenuItem editPositionMenuItem;
//    	JMenuItem undoMoveMenuItem;
//    	JMenuItem redoMoveMenuItem;
//
//    	newGameMenuItem = new JMenuItem("New Game", KeyEvent.VK_N);
//    	gameMenu.add(newGameMenuItem);
//    	newGameMenuItem.addActionListener(this);
//
//    	newGameFromFenMenuItem = new JMenuItem("New Game from FEN", KeyEvent.VK_F);
//    	gameMenu.add(newGameFromFenMenuItem);
//    	newGameFromFenMenuItem.addActionListener(this);
//    	
//    	gameMenu.addSeparator();
//
//    	editPositionMenuItem = new JMenuItem("Edit Position", KeyEvent.VK_E);
//    	gameMenu.add(editPositionMenuItem);
//    	editPositionMenuItem.addActionListener(this);
//
//    	gameMenu.addSeparator();
//    	
//    	undoMoveMenuItem = new JMenuItem("Undo Move", KeyEvent.VK_U);
//    	gameMenu.add(undoMoveMenuItem);
//    	undoMoveMenuItem.addActionListener(this);
//
//    	redoMoveMenuItem = new JMenuItem("Redo Move", KeyEvent.VK_R);
//    	gameMenu.add(redoMoveMenuItem);
//    	redoMoveMenuItem.addActionListener(this);
//    }
//
//    private void addDisplayMenuTo(JMenuBar menuBar) {
//    	JMenu displayMenu = new JMenu("Display");
//    	displayMenu.setMnemonic(KeyEvent.VK_D);
//    	menuBar.add(displayMenu);
//    	
//    	JMenuItem showPiecesInfoMenuItem;
//    	JMenuItem hidePiecesInfoMenuItem;
//    	JMenuItem rotateBoardMenuItem;
//    	JMenuItem notationMenuItem;
//    	JMenuItem colorSchemeMenuItem;
//
//    	showPiecesInfoMenuItem = new JMenuItem("Show Pieces Info", KeyEvent.VK_W);
//    	displayMenu.add(showPiecesInfoMenuItem);
//    	showPiecesInfoMenuItem.addActionListener(this);
//    	
//    	hidePiecesInfoMenuItem = new JMenuItem("Hide Pieces Info", KeyEvent.VK_H);
//    	displayMenu.add(hidePiecesInfoMenuItem);
//    	hidePiecesInfoMenuItem.addActionListener(this);
//
//    	displayMenu.addSeparator();
//
//    	rotateBoardMenuItem = new JMenuItem("Rotate ChessBoard", KeyEvent.VK_R);
//    	displayMenu.add(rotateBoardMenuItem);
//    	rotateBoardMenuItem.addActionListener(this);
//
//    	displayMenu.addSeparator();
//    	
//    	notationMenuItem = new JMenuItem("Notation", KeyEvent.VK_N);
//    	displayMenu.add(notationMenuItem);
//    	notationMenuItem.addActionListener(this);
//
//    	displayMenu.addSeparator();    	
//
//    	colorSchemeMenuItem = new JMenuItem("Colour Scheme", KeyEvent.VK_N);
//    	displayMenu.add(colorSchemeMenuItem);
//    	colorSchemeMenuItem.addActionListener(this);
//    	
//    }
//    
//    public Container createContentPane() {
//    	this.chessGameGridBag = new ChessGameGridBag();
//    	//this.chessGameGridBag.getChessGui().setChessFrame(this.frame);
//    	return chessGameGridBag;
//    }
//    
//    /**
//     * Create the GUI and show it.  For thread safety,
//     * this method should be invoked from the
//     * event-dispatching thread.
//     */
//    private static void createAndShowGUI() {
//    	
//        JFrame frame = new JFrame("Vasily Chess");
//        ChessGameMain gui = new ChessGameMain();    	
//
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setContentPane(gui.createContentPane());
//        frame.setJMenuBar(gui.createMenuBar());
//        //frame.setResizable(false);
//        frame.pack();
//
//    	centerFrame(frame);
//
//        frame.setVisible(true);
//    }
//
//	private static void centerFrame(JFrame frame) {
//    	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//		int xPos = (dim.width-frame.getWidth())/2;
//		int yPos = (dim.height-frame.getHeight())/2;
//        frame.setLocation(xPos, yPos);
//	}
//
//    public void actionPerformed(ActionEvent e) {
//    	//String newline = "\n";
//        JMenuItem source = (JMenuItem)(e.getSource());
////        String s = "Action event detected."
////                   + newline
////                   + "    Event source: " + source.getText()
////                   + " (an instance of " + getClassName(source) + ")";
////        output.append(s + newline);
////        output.setCaretPosition(output.getDocument().getLength());
//
//    	Chess chessGui = this.chessGameGridBag.getChessGui();
//        
//        if(source.getText() == "New Game") {
//        	chessGui.newGame();
//        	chessGui.repaint();
//        }
//
//        if(source.getText() == "New Game from FEN") {
//        	chessGui.newGameFromFen();
//        	chessGui.repaint();
//        }
//        
//        if(source.getText() == "Edit Position") {
//        	chessGui.editPosition();
//        	chessGui.repaint();
//        }
//
//        if(source.getText() == "Undo Move") {
//        	chessGui.undoMove();
//        	chessGui.repaint();
//        }
//
//        if(source.getText() == "Redo Move") {
//        	chessGui.redoMove();
//        	chessGui.repaint();
//        }
//        
//        if(source.getText() == "Load Game") {
//        	chessGui.loadGame();
//        	chessGui.repaint();
//        }
//        
//        if(source.getText() == "Hide Pieces Info") {
//        	chessGui.getChessBoard().hidePiecesInfo();
//        	chessGui.repaint();
//        }
//        
//        if(source.getText() == "Show Pieces Info") {
//        	chessGui.getChessBoard().showPiecesInfo();
//        	chessGui.repaint();
//        }
//        
//        if(source.getText() == "Rotate ChessBoard") {
//        	chessGui.rotateChessBoard();
//        	chessGui.repaint();
//        }
//        
//        if(source.getText() == "Exit") {
//        	System.exit(0);
//
//        }
//    }
//    
//    public static void main(String[] args) {
//        //Schedule a job for the event-dispatching thread:
//        //creating and showing this application's GUI.
//        //javax.swing.SwingUtilities.invokeLater(new Runnable() {
//        //    public void run() {
//        //        createAndShowGUI();
//        //    }
//        //});
//        
//		ChessGame chessGame = new ChessGame();
//		SimpleAiPlayerHandler ai1 = new SimpleAiPlayerHandler(chessGame);
//		ai1.maxDepth = 2;
//		SimpleAiPlayerHandler ai2 = new SimpleAiPlayerHandler(chessGame);
//		ai1.maxDepth = 2;
//		
//		chessGame.setPlayer(Piece.COLOR_WHITE, ai1);
//		chessGame.setPlayer(Piece.COLOR_BLACK, ai2);
//		
//		// in the end we start the game
//		new Thread(chessGame).start();		
//    }
//}
//
