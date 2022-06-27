package ch06.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ch06.logic.ChessGame;
import ch06.logic.MoveHandler;

public class PawnPromotionToQueenAdapter implements ActionListener {
	MoveHandler moveHandler;
	PawnPromotionToQueenAdapter(MoveHandler moveHandler) {
		this.moveHandler = moveHandler;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		this.moveHandler.promotePawnToQueen();
	}
}
