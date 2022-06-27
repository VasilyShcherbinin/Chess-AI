package ch06.logic;

import java.util.List;

public class UndoMoveHandler {

	public UndoMoveHandler() {
		
	}
	
	public Move undoMove(Move move) {
		//      suspendPondering();
		//Move move = undo(move);
		//      saveBackup();
		//      sendBoardPositionChangeEvent();
		//      resumePondering();

		undo(move);		
		return move;
	}

//	private Move undoMoveInternal() {
//      //if (this.moveHistory.size() == 0) {
//      	//return null;
//      //}
//      
//      //Move moveUndo = this.moveHistory.get(this.moveHistory.size()-1);
////      PlayerToPlay.Clock.Revert();
//      //this.moveRedoList.add(moveUndo);
//      undo(moveUndo);
//      return moveUndo;
//      
////      PlayerToPlay = PlayerToPlay.OpposingPlayer;
////      if (MoveHistory.Count > 1)
////      {
////          Move movePenultimate = MoveHistory[MoveHistory.Count - 2];
////          PlayerToPlay.Clock.TimeElapsed = movePenultimate.TimeStamp;
////      }
////      else
////      {
////          PlayerToPlay.Clock.TimeElapsed = new TimeSpan(0);
////      }
////
////      if (!IsPaused)
////      {
////          PlayerToPlay.Clock.Start();
////      }		
//	}
	
  private void undo(Move move)
  {
  	
      //Board.HashCodeA ^= move.To.Piece.HashCodeA; // un_XOR the piece from where it was previously moved to
      //Board.HashCodeB ^= move.To.Piece.HashCodeB; // un_XOR the piece from where it was previously moved to
      //if (move.Piece.Name == Piece.PieceNames.Pawn)
      //{
          //Board.PawnHashCodeA ^= move.To.Piece.HashCodeA;
          //Board.PawnHashCodeB ^= move.To.Piece.HashCodeB;
      //}

      //move.Piece.Square = move.From; // Set piece board location
      //move.From.Piece = move.Piece; // Set piece on board
      //move.Piece.LastMoveTurnNo = move.LastMoveTurnNo;
  	
  	Piece piece = move.getPiece();
		piece.setRow(move.sourceRow);
		piece.setColumn(move.sourceColumn);    	
		piece.decreaseNumberOfMoves();

      //if (move.Name != MoveNames.EnPassent)
      //{
      //   move.To.Piece = move.PieceCaptured; // Return piece taken
      //}
      //else
      //{
      //    move.To.Piece = null; // Blank square where this pawn was
      //    Board.GetSquare(move.To.Ordinal - move.Piece.Player.PawnForwardOffset).Piece = move.PieceCaptured; // Return En Passent pawn taken
      //}

      //if (move.getPieceCaptured() != null)
      //{
          //move.PieceCaptured.Uncapture(move.PieceCapturedOrdinal);
      	//move.getPieceCaptured().isCaptured(false);
          //Board.HashCodeA ^= move.PieceCaptured.HashCodeA; // XOR back into play the piece that was taken
          //Board.HashCodeB ^= move.PieceCaptured.HashCodeB; // XOR back into play the piece that was taken
          //if (move.PieceCaptured.Name == Piece.PieceNames.Pawn)
          //{
          //    Board.PawnHashCodeA ^= move.PieceCaptured.HashCodeA;
          //    Board.PawnHashCodeB ^= move.PieceCaptured.HashCodeB;
          //}
      //}

      Piece rook;
      switch (move.getName()) {
      case Standard:
      case EnPassant:
          if (move.getPieceCaptured() != null) {
          	move.getPieceCaptured().isCaptured(false);
          }
      	break;
      	
      case CastleKingSide:
    	  rook = (move.getPiece().getColor() ==  Piece.COLOR_WHITE)
	    	  ? move.getPiece().getPlayer().getNonCapturedPieceAtLocation(
	    			  Piece.ROW_1, Piece.COLUMN_F) 
			  : move.getPiece().getPlayer().getNonCapturedPieceAtLocation(
					  Piece.ROW_8, Piece.COLUMN_F);

		  if(rook != null) {
			  rook.setColumn(Piece.COLUMN_H);
			  rook.decreaseNumberOfMoves();
			  move.getPiece().getPlayer().hasCastled(false);
		  }
		break;

      case CastleQueenSide:
  		rook = (move.getPiece().getColor() ==  Piece.COLOR_WHITE)
	  		? move.getPiece().getPlayer().getNonCapturedPieceAtLocation(
	  				Piece.ROW_1, Piece.COLUMN_D) 
			: move.getPiece().getPlayer().getNonCapturedPieceAtLocation(
					Piece.ROW_8, Piece.COLUMN_D);
      				
		  if(rook != null) {
			rook.setColumn(Piece.COLUMN_A);
			rook.decreaseNumberOfMoves();
			move.getPiece().getPlayer().hasCastled(false);
		  }
		break;
      			
      case PawnPromotionQueen:
		case PawnPromotionRook:
		case PawnPromotionBishop:
		case PawnPromotionKnight:
			Piece pawn = move.getPiece();
			Player player = pawn.getPlayer();
			List<Piece> pieces = player.getPieces();
			pieces.remove(pieces.size()-1);
			
			player.isPawnPromoted(false);
			pawn.isCaptured(false);
			if(pawn.getColor() ==  Piece.COLOR_WHITE) {
				pawn.setRow(Piece.ROW_7);
			}else {
				pawn.setRow(Piece.ROW_2);
			}
			break;
      			
      default:
      	break;
      }

//      Board.HashCodeA ^= move.From.Piece.HashCodeA; // XOR the piece back into the square it moved back to
//      Board.HashCodeB ^= move.From.Piece.HashCodeB; // XOR the piece back into the square it moved back to
//      if (move.From.Piece.Name == Piece.PieceNames.Pawn)
//      {
//          Board.PawnHashCodeA ^= move.From.Piece.HashCodeA;
//          Board.PawnHashCodeB ^= move.From.Piece.HashCodeB;
//      }
//
//      if (move.IsThreeMoveRepetition)
//      {
//          Board.HashCodeA ^= 31;
//          Board.HashCodeB ^= 29;
//      }

//      Game.TurnNo--;

      //this.moveHistory.remove(this.moveHistory.size()-1);
  }
	
}
