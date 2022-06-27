package ch06.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import ch06.logic.Piece;

public class GuiLabel extends JLabel
{
	private Piece piece;

    public GuiLabel(ImageIcon imic, Piece piece)
    {
    	this.piece = piece;
    	super.setIcon(imic);
    	//super.setText(piece.getRow()+"/"+piece.getColumn()); 
    	super.setHorizontalTextPosition(JLabel.CENTER);
    	super.setVerticalTextPosition(JLabel.BOTTOM);    	
    	super.setHorizontalAlignment(JLabel.CENTER);
    }

    public GuiLabel(Icon imic, Piece piece)
    {
    	this.piece = piece;
    	super.setIcon(imic);
    	//super.setText(piece.getRow()+"/"+piece.getColumn()); 
    	super.setHorizontalTextPosition(JLabel.CENTER);
    	super.setVerticalTextPosition(JLabel.BOTTOM);    	
    	super.setHorizontalAlignment(JLabel.CENTER);
    }
    
	public Piece getPiece() {
		return piece;
	}

	public Icon getIcon() {
		return super.getIcon();
	}
	
	public void setIcon(ImageIcon imic) {
		super.setIcon(imic);
	}
	
	public boolean isCaptured() {
		return this.piece.isCaptured();
	}
	
//	public void paintComponent(Graphics g)
//	{	
//	   
////	    Graphics2D g2d = (Graphics2D) g;
////	    int x = (this.getWidth() - img.getWidth(null)) / 2;
////	    int y = (this.getHeight() - img.getHeight(null)) / 2;
////	    g2d.drawImage(img, x, y, null);
//		
//		g.drawString(piece.getRow()+"/"+piece.getColumn(),	0, 0);
//		 super.paintComponent(g);
//		 
////		super.setText("xxx"); 
////    	//super.setIcon(imic);
////    	setText(piece.getRow()+"/"+piece.getColumn()); 
////    	setHorizontalTextPosition(JLabel.CENTER);
////    	setVerticalTextPosition(JLabel.BOTTOM);    	
////    	setHorizontalAlignment(JLabel.CENTER);	
//	}
	
//	public void paintComponent(Graphics g)
//	{
//
//		Graphics2D g2d = (Graphics2D) g;
//		Color color1 = new Color(226, 218, 145);
//		Color color2 = color1.brighter();
//		int w = getWidth();
//		int h = getHeight();
//		GradientPaint gp = new GradientPaint(
//				0, 0, color1, 0, h, color2);
//		g2d.setPaint(gp);
//		g2d.fillRect(0, 0, w, h);
//		super.paintComponent(g);
//	}

}
