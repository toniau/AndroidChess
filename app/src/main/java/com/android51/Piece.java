package com.android51;

import java.util.ArrayList;

/**
 * A base class that implements a generic chess Piece.  Chess
 * Pieces such as rook, king, queen, etc. are all derived
 * from this base.  What makes each Piece different is
 * the setting of its properties.  For example, a pawn is
 * only allowed to move up one square whereas a rook can
 * move up to 7 squares.
 *
 * @author Toni Au, Sean Wu
 * @version 1.0
 *
 */

public class Piece {

	/**
	 *  Name definition for all chess pieces
	 */
	public static enum PieceNames { pawn, rook, knight, bishop, queen, king, emptySpace };


	/**
	 *  Color definition for chess pieces (either black or white)
	 */
	public static enum Colors {Black, White};

	// Property of this chess Piece
	private PieceNames name;     // name of this Piece
	private Colors color; 		 // color of this piece
	private FileRank fileRank;	 // current location of this piece
	private boolean bHasMoved = false;	 // remember if this piece has moved

	//
	// When a king moves to any of these two special locations, it can
	// trigger a castling move.
	//
	private FileRank kingShortCastleLoc = null;
	private FileRank kingLongCastleLoc  = null;

	private FileRank rookShortCastleLoc = null;
	private FileRank rookLongCastleLoc  = null;

	// An ArrayList of locations that this piece can go to
	private ArrayList<FileRank> targets = new ArrayList<FileRank>();


	/**
	 * 	Get the name of this piece (e.g. rook, queen, king, etc.)
	 *
	 * @return
	 * 		Return the name of this piece (e.g. rook, queen, king, etc.)
	 */
	public PieceNames getName() {
		return name;
	}




	/**
	 *   Get the color of this piece (black or white)
	 *
	 * @return
	 * 		Return the color of this piece (black or white)
	 */
	public Colors getColor() {
		return color;
	}


	/**
	 *  A constructor to create a new game piece of a color and type and
	 *  place it at a starting location on the game board.
	 *
	 * @param c    Color of a new game piece (e.g. black or white)
	 * @param n    Name of a new game piece (e.g. rook, knight, etc.)
	 * @param squareName   Location (i.e. fileRank) to put this new game piece
	 *
	 */
	public Piece( Colors c, PieceNames n, String squareName) {
		this.name = n;
		this.color = c;
		this.fileRank = new FileRank(squareName);
		this.bHasMoved = false;

		if ( this.name.equals(PieceNames.king)) {
			if ( this.color.equals(Colors.White)) {
				// This is a white king
				this.kingShortCastleLoc = new FileRank("g1");
				this.rookShortCastleLoc = new FileRank("f1");
				this.kingLongCastleLoc = new FileRank("c1");
				this.rookLongCastleLoc  = new FileRank("d1");
			}
			else {
				// This is a black king
				this.kingShortCastleLoc = new FileRank("g8");
				this.rookShortCastleLoc = new FileRank("f8");
				this.kingLongCastleLoc = new FileRank("c8");
				this.rookLongCastleLoc  = new FileRank("d8");
			}

		}
	}



	/*
	 * 	Return the encoded symbolic name of this piece as
	 * 	displayed on the game board.  For example, a white
	 * 	king is "wK".
	 *
	 * @see java.lang.Object#toString()
	 *
	 */
	@Override
	public String toString() {
		String s = new String();
		if ( this.getColor() == Colors.White) {
			s = "w";
		}
		else {
			s = "b";
		}

		if ( this.getColor() == Colors.White) {
			// This is a white piece
			s = "w";
		}
		else {
			// This is a black piece
			s = "b";
		}
		switch (name) {
			case pawn:
				s += "p ";
				break;
			case rook:
				s += "R ";
				break;

			case knight:
				s += "N ";
				break;

			case bishop:
				s += "B ";
				break;

			case queen:
				s += "Q ";
				break;

			case king:
				s += "K ";
				break;

			case emptySpace:
			default:
				if (this.getColor() == Colors.White) {
					s = "   ";
				}
				else {
					s = "## ";
				}
				break;

		}

		return s;
	}




	/**  Get the current x-coordinate (i.e. File) of this piece on the game board
	 *
	 * @return
	 * return the current x-coordinate (i.e. File) of this piece on the game board
	 */
	public int getX() {
		return fileRank.getX();
	}




	/**
	 *   Get the current y-coordinate (i.e. Rank) of this piece on the game board
	 *
	 * @return
	 * return the current y-coordinate (i.e. Rank) of this piece on the game board
	 */
	public int getY() {
		return fileRank.getY();
	}




	/**
	 *   Get FileRank name of the current location (e.g. "e4") for this piece
	 *
	 * @return
	 * 	the FileRank name of the current location (e.g. "e4") for this piece
	 */
	public String getLoc() {
		return fileRank.getName();
	}




	/**
	 *   Move this game piece to a new location
	 *
	 * @param newLoc  A new location (e.g. "e1") specified in FileRank format
	 *
	 * @return boolean  Return "true" if the specified move is successful
	 *
	 */
	public boolean moveTo(String newLoc) {
		bHasMoved = true;
		return  fileRank.moveTo(newLoc);
	}



	/**
	 *  Verify that this piece is allowed to move to a specified
	 *  location.
	 *
	 * @param newLoc
	 * 		A location name (e.g. "e4")
	 *
	 * @return
	 * 		Return "true" if this piece is allowed to move to the
	 * 		specified location.  Otherwise, return "false".
	 */
	public boolean canMoveTo(String newLoc)
	{
		FileRank targetLoc = new FileRank(newLoc);

		for (FileRank aValidTarget : getTargets()) {
			if ( targetLoc.getName().equals(aValidTarget.getName())) {
				// This is a valid target
				return true;
			}
		}
		return false;
	}



	/**
	 * 	Return all the allowable target destinations that this
	 *  piece is allow to move to.
	 *
	 * @return
	 * 		An ArrayList of FileRank locations
	 */
	public ArrayList<FileRank> getTargets() {
		return targets;
	}



	/**
	 * 	Set an ArrayList of target location for this piece.  This are
	 *  locations on the game board where this piece is allowed to move to.
	 *
	 * @param targets
	 * 		An ArrayList of target locations.
	 */
	public void setTargets(ArrayList<FileRank> targets) {
		targets.clear();
		targets.addAll(targets);
	}


	/**
	 * 	Add one target location for this piece to move to.
	 *
	 * @param aTarget
	 * 		one target location
	 */
	public void addTarget(FileRank aTarget) {
		targets.add(aTarget);
	}


	/**
	 *   Remove all target locations for this piece
	 */
	public void clearTargets() {
		targets.clear();
	}



	/**
	 * 	 Check if this piece has made any move.
	 *
	 * @return
	 * 		Return "true" if this piece has made a move previously.
	 * 		Otherwise, return "false".
	 */
	public boolean hasMoved() {
		if ( getName().equals(PieceNames.pawn) ) {
			if (getColor().equals(Colors.Black) ) {
				// Black pawns all start at rank 7
				return ( ! getFileRank().getRank().equals("7") );
			}
			else {
				// White pawns all start at rank 2
				return ( ! getFileRank().getRank().equals("2") );
			}
		}

		if ( getName().equals(PieceNames.king) ) {
			if (getColor().equals(Colors.Black) ) {
				// Black king starts at "e8"
				if ( ! getFileRank().getName().equals("e8") ) return true;
			}
			else {
				// White king starts at "e1"
				if ( ! getFileRank().getName().equals("e1") ) return true;
			}
		}

		return bHasMoved;
	}



	/**
	 * 	Check if this is a pawn and is making an initial double
	 *  push move.  This check is part of the requirements to
	 *  implement the En Passant capture move.
	 *
	 * @param targetLoc
	 * 		The new location where this pawn moves to
	 *
	 * @return
	 * 		Return "true" if this is an initial double push move
	 * 		by a pawn.
	 */
	public boolean isPawnDoublePush(String targetLoc)
	{
		if ( (! this.getName().equals(PieceNames.pawn)) ||
			 (this.bHasMoved) ) {
			//
			// this is not a pawn or this piece has moved before.
			// A pawn is only allowed to do a double push if it
			// has not moved before (i.e. this is its first move).
			//
			return false;
		}

		// This is a pawn doing a first move
		String rank = targetLoc.trim().substring(1);
		if ( ((this.getColor().equals(Colors.White)) && (this.getY() == 1) && (rank.equals("4"))) ||
			 ((this.getColor().equals(Colors.Black)) && (this.getY() == 6) && (rank.equals("5")))   )
		{
			// This is either a white or a black pawn doing a double push
			return true;
		}
		return false;
	}



	/**
	 * 	Get the current location of this piece in the FileRank format.
	 *
	 * @return
	 * 		Return the current FileRank of this piece
	 */
	public FileRank getFileRank() {
		return fileRank;
	}


	/**
	 * 	Check if this piece is actually an empty space.
	 *
	 * @return
	 * 		Return true is this piece is an empty space
	 */
	public boolean isEmptySpace()
	{
		if ( getName().equals(PieceNames.emptySpace)) return true;
		return false;
	}


	/**  Get the location of the square where the king will land
	 *   when castling on the short side
	 *
	 * @return
	 * 		Location of the square where the king will be when
	 * 		castling on the short side
	 */
	public String getKingShortCastleLoc() {
		return kingShortCastleLoc.getName();
	}




	/**	 Get the location of the square where the king will land
	 *   when castling on the long side
	 *
	 * @return
	 * 		Location of the square where the king will be when
	 * 		castling on the long side
	 */
	public String getKingLongCastleLoc() {
		return kingLongCastleLoc.getName();
	}




	/**  Get the location of the square where the rook will land
	 *   when castling on the short side
	 *
	 * @return
	 * 		Location of the square where the rook will be when
	 * 		castling on the short side
	 */
	public FileRank getRookShortCastleLoc() {
		return rookShortCastleLoc;
	}


	/**	 Get the location of the square where the rook will land
	 *   when castling on the long side
	 *
	 * @return
	 * 		Location of the square where the rook will be when
	 * 		castling on the long side
	 */
	public FileRank getRookLongCastleLoc() {
		return rookLongCastleLoc;
	}


}
