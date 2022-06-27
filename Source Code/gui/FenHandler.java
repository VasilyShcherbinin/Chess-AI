package ch06.gui;

import java.awt.Component;

import javax.swing.JOptionPane;

import ch06.logic.Fen;

public class FenHandler {

	public static String getFen(Component chess) {
		boolean isValid = false;
		
		Fen fen = new Fen();
		String s = JOptionPane.showInputDialog(chess, "Enter in FEN:"); 
		do {
			String message = "";
			try {
				isValid = fen.validate(s);
			} catch (Exception ex) {
				message = ex.getMessage();
			}
			
			if (!isValid) {
				s = JOptionPane.showInputDialog(chess, message + "! Enter in FEN:");
				if (s == null) {
					isValid = true;
				}
			}
		} while (!isValid);
		return s;
	}
}
