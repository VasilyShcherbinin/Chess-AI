package ch06.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;


public class PawnPromotionDialog extends JDialog implements ActionListener {

    /** Flag that indicates whether or not the dialog was cancelled. */
    private boolean cancelled;

    //private final ChessGui chessGui;
    
    /**
     * Standard constructor - builds a dialog...
     *
     * @param owner  the owner.
     * @param title  the title.
     * @param modal  modal?
     */
    public PawnPromotionDialog(final Frame owner, final String title, final boolean modal,
    		PawnPromotionPanel pane) {
        super(owner, title, modal);
        this.setSize(200,200);
        this.cancelled = false;
        //this.chessGui = chessGui;
        
		Component[] components = pane.getComponents();
		if (components.length > 0) {
			components = ((JPanel)components[0]).getComponents();
			//((JButton)components[0]).addActionListener(this);        
			((JButton)components[1]).addActionListener(this);
			((JButton)components[2]).addActionListener(this);
			((JButton)components[3]).addActionListener(this);
			((JButton)components[4]).addActionListener(this);
		}
		
        this.getContentPane().add(pane);
        this.setLocationRelativeTo(owner);
        this.setVisible(true);
    }

//    /**
//     * Standard constructor - builds a dialog...
//     *
//     * @param owner  the owner.
//     * @param title  the title.
//     * @param modal  modal?
//     */
//    public PawnPromotionDialog(final Dialog owner, final String title, final boolean modal) {
//        super(owner, title, modal);
//        this.cancelled = false;
//        //this.chessGui = chessGui;    
//        createButtonPanel();
//    }

    /**
     * Returns a flag that indicates whether or not the dialog has been
     * cancelled.
     *
     * @return boolean.
     */
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * Handles clicks on the standard buttons.
     *
     * @param event  the event.
     */
    public void actionPerformed(final ActionEvent event) {
        setVisible(false);
//        final String command = event.getActionCommand();
//        if (command.equals("helpButton")) {
//            // display help information
//        }
//        else if (command.equals("okButton")) {
//            this.cancelled = false;
//            setVisible(false);
//        }
//        else if (command.equals("cancelButton")) {
//            this.cancelled = true;
//            setVisible(false);
//        }
    }
//
//    /**
//     * Builds and returns the user interface for the dialog.  This method is
//     * shared among the constructors.
//     *
//     * @return the button panel.
//     */
//    protected JPanel createButtonPanel() { //ChessGui chessGui) {
//		return new PawnPromotionPanel();
//        final L1R2ButtonPanel buttons = new L1R2ButtonPanel(
//                "Help",
//                "OK",
//                "Cancel");
//
//        final JButton helpButton = buttons.getLeftButton();
//        helpButton.setActionCommand("helpButton");
//        helpButton.addActionListener(this);
//
//        final JButton okButton = buttons.getRightButton1();
//        okButton.setActionCommand("okButton");
//        okButton.addActionListener(this);
//
//        final JButton cancelButton = buttons.getRightButton2();
//        cancelButton.setActionCommand("cancelButton");
//        cancelButton.addActionListener(this);
//
//        buttons.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
//        return buttons;
//    }

}
