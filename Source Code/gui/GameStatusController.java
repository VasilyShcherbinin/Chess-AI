package ch06.gui;

import javax.swing.JLabel;

public class GameStatusController {

	private JLabel lblGameStatus;
	
	public GameStatusController(JLabel lblGameStatus) {
		this.lblGameStatus = lblGameStatus;
	}
	
	public void setStatusBar(String txt) {
		this.lblGameStatus.setText(txt);
	}	
}
