package ch06.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ch06.logic.ChessGame;
import ch06.logic.MoveHandler;

public class PawnPromotionToBishopAdapter implements ActionListener {
	MoveHandler moveHandler;
	PawnPromotionToBishopAdapter(MoveHandler moveHandler) {
		this.moveHandler = moveHandler;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		this.moveHandler.promotePawnToBishop();
	}
}
