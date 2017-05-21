package com.android51;

/**
 *
 *   An utility class that helps manage the location of a game
 *   piece on the board.  The game board is an 8x8 square.  Each
 *   location has a name specified in "FileRank" format (e.g. "a1").
 *   This utility helps to translate this location name into an
 *   "(x,y)" coordinate format.
 *
 * @author Toni Au, Sean Wu
 * @version 1.0
 */

public class FileRank {

	private String name;
	private int x;
	private int y;



	/**
	 * 	 Constructor of a FileRank object.   Each square on the 8x8 chess game board is
	 *   represented with a FileRank coordinate.  The "File" ranges from "a" to "h".
	 *   The "Rank" ranges from "1" to "8".
	 *
	 * @param fileRankName
	 * 		 A location specified in the FileRank format (e.g "e4")
	 */
	public FileRank(String fileRankName) {

		String sqName = fileRankName.trim();
		if ( sqName.length() != 2 ) return;

		// Valid File letter is from "a" to "h"
		int xValue;  // Column number on the board
		String fileLetter = sqName.toLowerCase().substring(0, 1);

		switch (fileLetter) {
			case "a":
				xValue = 0;
				break;

			case "b":
				xValue = 1;
				break;

			case "c":
				xValue = 2;
				break;

			case "d":
				xValue = 3;
				break;

			case "e":
				xValue = 4;
				break;

			case "f":
				xValue = 5;
				break;

			case "g":
				xValue = 6;
				break;

			case "h":
				xValue = 7;
				break;

			default:
				return;

		}

		// Valid Rank number is from "1" to "8"
		int yValue;   // Row number on the board
		yValue = Integer.parseInt( sqName.toLowerCase().substring(1, 2) ) - 1;
		if ( (yValue < 0) || (yValue > 7)) {
			// illegal input
			return;
		}

		this.name = sqName.toLowerCase();
		this.x = xValue;
		this.y = yValue;
	}


	/**
	 * A constructor to create a new FileRank object mapped to
	 * location (x,y) on the game board.
	 *
	 * @param xValue  "X" value (i.e. File) of the game board location
	 * @param yValue  "Y" value (i.e. Rank) of the game board location
	 *
	 */
	public FileRank(int xValue, int yValue) {
		this.x = xValue;
		this.y = yValue;

		switch (xValue) {
			case 0:
				this.name = "a";
				break;

			case 1:
				this.name = "b";
				break;

			case 2:
				this.name = "c";
				break;

			case 3:
				this.name = "d";
				break;

			case 4:
				this.name = "e";
				break;

			case 5:
				this.name = "f";
				break;

			case 6:
				this.name = "g";
				break;

			case 7:
				this.name = "h";
				break;

			default:

		}

		this.name += String.valueOf(yValue+1);

	}



	/**
	 *   Return the location name in coordinate format (e.g. "e4")
	 *
	 * @return
	 * 		Return the location name
	 */
	public String getName() {
		return name;
	}



	/**
	 *   Get the X-coordinate from this FileRank object.
	 *
	 * @return
	 * 		Return the x-coordinate as an integer
	 */
	public int getX() {
		return this.x;
	}


	/**
	 *   Get the x-coordinate of a given FileRank.  For example, the
	 *   x-coordinate for FileRank "a1" is "0".
	 *
	 * @param FileRankString
	 * 		A location specified as a FileRank such as "e4"
	 *
	 * @return
	 * 		Return the x-coordinate of the given fileRank
	 */
	public int getX(String FileRankString) {
		if ( ! FileRank.isValid(FileRankString))  return -1;

		// Valid File letter is from "a" to "h"
		String fileLetter = FileRankString.toLowerCase().substring(0, 1);

		switch (fileLetter) {
			case "a":
				return 0;

			case "b":
				return 1;

			case "c":
				return 2;

			case "d":
				return 3;

			case "e":
				return 4;

			case "f":
				return 5;

			case "g":
				return 6;

			case "h":
				return 7;
		}

		return -1;
	}

	/**
	 *   Get the y-coordinate from this FileRank object.
	 *
	 * @return
	 * 		Return the y-coordinate as an integer
	 */
	public int getY() {
		return this.y;
	}


	/**
	 *   Get the y-coordinate of a given FileRank.  For example, the
	 *   y-coordinate for FileRank "a1" is "0".
	 *
	 * @param FileRankString
	 * 		A location specified as a FileRank such as "e4"
	 *
	 * @return
	 * 		Return the y-coordinate of the given fileRank
	 */
	public int getY(String FileRankString) {
		if ( ! FileRank.isValid(FileRankString))  return -1;

		// Valid Rank number is from "1" to "8"
		int yValue;
		yValue = Integer.parseInt( FileRankString.toLowerCase().substring(1, 2) ) - 1;
		if ( (yValue < 0) || (yValue > 7)) {
			// illegal input
			return -1;
		}

		return yValue;
	}



	/**
	 *   Check if a given FileRank is valid for a chess game board.
	 *
	 * @param fileRank
	 * 		A fileRank location such as "e4"
	 *
	 * @return
	 * 		Return "true" if the given fileRank is valid.  Otherwise,
	 * 		return "false".
	 */
	public static boolean isValid(String fileRank) {
		String checkName = fileRank.trim();
		if ( checkName.length() != 2 ) return false;

		// Valid File letter is from "a" to "h"
		String fileLetter = checkName.toLowerCase().substring(0, 1);

		switch (fileLetter) {
			case "a":
			case "b":
			case "c":
			case "d":
			case "e":
			case "f":
			case "g":
			case "h":
				// valid letter
				break;

			default:
				// invalid
				return false;
		}

		// Valid Rank number is from "1" to "8"
		int yValue;
		yValue = Integer.parseInt( checkName.toLowerCase().substring(1, 2) );
		if ( (yValue < 1) || (yValue > 8)) {
			// illegal input
			return false;
		}
		return true;
	}


	/**
	 *   Get the "File" from this fileRank object.  The "File" is the
	 *   first letter in the name of this fileRank object.
	 *
	 * @return
	 * 		Return the "File" from this fileRank object
	 */
	public String getFile() {
		switch ( getX() ) {
			case 0:
				return "a";

			case 1:
				return "b";

			case 2:
				return "c";

			case 3:
				return "d";

			case 4:
				return "e";

			case 5:
				return "f";

			case 6:
				return "g";

			case 7:
				return "g";

			default:
		}
		return "";
	}


	/**
	 *   Get the "Rank" from this fileRank object.  The "Rank" is the
	 *   second letter in the name of this fileRank object.
	 *
	 * @return
	 * 		Return the "Rank" from this fileRank object
	 */
	public String getRank()
	{
		return  String.valueOf( getY()+1 );
	}



	/**
	 *   Move the coordinate stored in this fileRank object
	 *   to a new location.
	 *
	 * @param newLoc
	 * 		A new location for this FileRank object.
	 *
	 * @return
	 * 		Return "true" if the given location is valid and
	 * 		has been stored.
	 */
	public boolean moveTo( String newLoc) {

		if ( ! FileRank.isValid(newLoc) ) return false;

		this.x = getX(newLoc);
		this.y = getY(newLoc);
		this.name = newLoc;
		return true;
	}


	/**
	 *   Check if a given FileRank object has the same location
	 *   with this FileRank object.
	 *
	 * @param aLoc
	 * 		A location specified as a FileRank object
	 *
	 * @return
	 * 		Return "true" if the locations between the two FileRank
	 * 		objects are the same.  Otherwise, return "false".
	 */
	public boolean equals(FileRank aLoc) {
		if ((aLoc.getX() == this.getX()) &&
			(aLoc.getY() == this.getY()) ) return true;
		return false;
	}


}
