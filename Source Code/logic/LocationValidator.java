package ch06.logic;

public class LocationValidator {

	public boolean isTargetLocationFree(Piece targetPiece) {
		return targetPiece == null;
	}
	
	public boolean isTargetLocationCaptureable(Piece sourcePiece, Piece targetPiece) {
		if(targetPiece == null ){
			return false;
		}else { 
			return !(targetPiece.getColor() == sourcePiece.getColor());
		}
	}	
}
