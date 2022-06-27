package ch06.logic;

public interface IPlayerHandler {

	public Move getMove();

	public void moveSuccessfullyExecuted(Move move);

	public void activePlayerSwapped();
}
