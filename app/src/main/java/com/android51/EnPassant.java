package com.android51;


/**
 *  A class that implements the special En Passant
 *  capturing move by a pawn.
 *
 * @author Toni Au, Sean Wu
 * @version 1.0
 *
 */
public class EnPassant {

	private Piece doublePushedPawn;
	private boolean bEnPassantCompleted = false;


	/**
	 *   A default constructor.
	 */
	public EnPassant() {
		// This is the pawn that just did a double step move
		this.doublePushedPawn = null;
		this.bEnPassantCompleted = false;
	}


	/**
	 * A constructor to create a new EnPassant object associated
	 * with a pawn that just made a double step move.
	 *
	 * @param aPawn  A pawn who just made a double step move
	 */
	public EnPassant(Piece aPawn) {
		// This is the pawn that just did a double step move
		this.doublePushedPawn = aPawn;
		this.bEnPassantCompleted = false;
	}



	/**
	 *
	 *  Get the FileRank location of the pawn
	 *  that just made a double push movement.
	 *  This is the pawn that is subject to an
	 *  En Passant capture move by the opponent.
	 *
	 * @return
	 * 		Return a FileRank location
	 */
	public FileRank getDoublePushedPawnLoc()
	{
		return doublePushedPawn.getFileRank();
	}



	/**
	 *  Get the En Passant trigger location.
	 *  This is the empty space behind the pawn
	 *  that just made a double push move.   If
	 *  the opponent has a pawn right next to the
	 *  double-pushed pawn, he can move to this
	 *  empty trigger location and capture the
	 *  double-pushed pawn.
	 *
	 * @return
	 * 		Return the En Passant trigger location.
	 */
	public FileRank getTriggerLoc()
	{
		FileRank triggerLoc = getDoublePushedPawnLoc();
		int x = triggerLoc.getX();
		int y = triggerLoc.getY();

		if ( getEnPassantColor().equals(Piece.Colors.White)) {
			// White can make En Passant capture move
			y++;
		}
		else {
			// Black can make En Passant capture move
			y--;
		}
		return (new FileRank(x,y));
	}


	/**
	 *  Get the color of the side that can
	 *  execute an En Passant capture move.  For example,
	 *  if a white pawn made a double push move, it is the
	 *  black side that may have an opportunity to execute
	 *  an En Passant move
	 *
	 * @return
	 * 		Return a color
	 */
	public Piece.Colors getEnPassantColor()
	{
		if ( doublePushedPawn.getColor().equals(Piece.Colors.White)) {
			// It is black who can execute En Passant capture
			return Piece.Colors.Black;
		}

		// It is white who can execute En Passant capture
		return Piece.Colors.White;
	}


	/**
	 *  Check if En Passant rule is in effect.
	 *
	 * @return
	 * 		Return a boolean flag indicating if En Passant rule
	 *      is in effect.
	 */
	public boolean isActive() {
		if ( doublePushedPawn == null) return false;
		return true;
	}



	/**
	 *   Check if a given pawn can participate in an En Passant move.
	 *
	 * @param aPawn
	 * 		A pawn.
	 *
	 * @return
	 * 		Return true if the specified pawn can participate in
	 *      an En Passant capture move.
	 *
	 */
	public boolean isParticipant(Piece aPawn)
	{
		if ( (! isActive()) || ( ! aPawn.getColor().equals(getEnPassantColor())) ||
			 ( ! aPawn.getName().equals(Piece.PieceNames.pawn)) ) return false;


		// Implement En Passant rule.  En Passant conditions are:
		//
		//    1. the capturing pawn must be on its fifth rank;
		//    2. the captured pawn must be on an adjacent file and must have
		//       just moved two squares in a single move (i.e. a double-step move);
		//    3. the capture can only be made on the move immediately after the
		//       opposing pawn makes the double-step move; otherwise the right to
		//       capture it en passant is lost.
		//

		int x = aPawn.getX();
		int y = aPawn.getY();

		if ( aPawn.getColor().equals(Piece.Colors.White) ) {
			// A white pawn must be on rank "5" to do En Passant capture
			if ( y != 4 ) return false;
		}
		else {
			// A black pawn must be on rank "4" to do En Passant capture
			if ( y != 3 ) return false;
		}

		//
		// Determine if this opposing pawn is to the left or right
		// of the pawn that just did a double push.
		//
		if( y != doublePushedPawn.getY()) return false;

		int x_DoublePushedPawn = doublePushedPawn.getX();

		// Is this an opposing pawn to the left of the
		// double pushed pawn?
		if ((x_DoublePushedPawn > 0) &&
			(x == x_DoublePushedPawn - 1) ) {
			// I can participate in the En Passant capture move.
			return true;
		}

		// Is this an opposing pawn to the right of the
		// double pushed pawn?
		if ((x_DoublePushedPawn < 7 ) &&
			(x == x_DoublePushedPawn + 1) )	{
			// I can participate in the En Passant capture move.
			return true;
		}

		return false;
	}


	/**
	 *   Get the pawn that just took a double push move
	 *
	 * @return
	 * 		A pawn that just made a double push move
	 */
	public Piece getDoublePushedPawn() {
		return doublePushedPawn;
	}


	/**
	 * 	Check if the En Passant Rule is over.  En Passant is a
	 *  one-time only move and must be taken immediately after
	 *  a pawn made a double push move.
	 *
	 * @return
	 * 		Return "true" if En Passanst is over.  Otherwise,
	 * 		return "false".
	 */
	public boolean isEnPassantCompleted() {
		return bEnPassantCompleted;
	}


	/**
	 * All En Passant capture move is only valid for one turn.
	 * Set a flag to indicate that this En Passant move has completed.
	 * This move is no longer available at the start of next turn.
	 *
	 * @param bFlag
	 * 		Set a boolean flag
	 */
	public void setEnPassantCompleted(Boolean bFlag) {
		this.bEnPassantCompleted = bFlag;
	}



}
