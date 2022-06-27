package ch06.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.plaf.metal.MetalToggleButtonUI;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.AbstractButton;

import ch06.logic.ChessGame;
import ch06.logic.MoveHandler;
import ch06.logic.Piece;

public class PawnPromotionPanel extends JPanel {

	private MoveHandler moveHandler = null;
	
	public PawnPromotionPanel(MoveHandler moveHandler) {
		this.moveHandler = moveHandler;
		add(createButtons(), BorderLayout.CENTER);
	}
	
	private JPanel createButtons() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 1));
		panel.add(new JLabel("Promote to:"));
		panel.add(createPromoteToQueenButton());
		panel.add(createPromoteToRookButton());
		panel.add(createPromoteToBishopButton());
		panel.add(createPromoteToKnightButton());
		return panel;
	}

	private JButton createPromoteToQueenButton() {
		JButton btn = new JButton("Queen");
		btn.addActionListener(new PawnPromotionToQueenAdapter(this.moveHandler));
		return btn;
	}

	private JButton createPromoteToRookButton() {
		JButton btn = new JButton("Rook");
		btn.addActionListener(new PawnPromotionToRookAdapter(this.moveHandler));		
		return btn;
	}

	private JButton createPromoteToBishopButton() {
		JButton btn = new JButton("Bishop");
		btn.addActionListener(new PawnPromotionToBishopAdapter(this.moveHandler));		
		return btn;
	}

	private JButton createPromoteToKnightButton() {
		JButton btn = new JButton("Knight");
		btn.addActionListener(new PawnPromotionToKnightAdapter(this.moveHandler));		
		return btn;
	}
}
