package ch06.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ch06.logic.ChessGame;
import ch06.logic.MoveHandler;

public class PawnPromotionToRookAdapter implements ActionListener {
	MoveHandler moveHandler;
	PawnPromotionToRookAdapter(MoveHandler moveHandler) {
		this.moveHandler = moveHandler;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		this.moveHandler.promotePawnToRook();
	}
}
