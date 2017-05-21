package com.android51;

import android.content.Context;
import android.widget.Toast;

import com.android51.Chess.gameState;

import java.util.ArrayList;


/**
 * An 8x8 chess game board. The columns (i.e. file) are labeled from "a"
 * to "h" starting from left to right. The rows (i.e. rank) are labeled as "8"
 * to "1 starting from top to bottom. The square "a1" which is at the lower left
 * hand corner of the game board is always a black square. Queens always start
 * on the "d" file.
 *
 *
 * @author Toni Au, Sean Wu
 * @version 1.0
 *
 */

public class Board {


    /**
     *  The maximum x-value (i.e. File) of the game board.
     */
    private static final int rowCount = 8;


    /**
     *  The maximum y-value (i.e. Rank) of the game board.
     */
    private static final int columnCount = 8;

    /**
     *  The current location of the black king
     */
    private FileRank blackKingLoc;


    /**
     *  The current location of the white king
     */
    private FileRank whiteKingLoc;


    private EnPassant enPassant = new EnPassant();


    /**
     *   Create the game board itself.
     */
    private Piece[][] board = new Piece[columnCount][rowCount];

    private static Context c;
    /**
     *  Default constructor.
     *
     *  This constructor will initialize a new chess game board and
     *  place all game pieces at their starting position.
     */
    public Board(Context context) {

        // Row 8, black king row
        board[0][7] = new Piece(Piece.Colors.Black, Piece.PieceNames.rook,   "a8");
        board[1][7] = new Piece(Piece.Colors.Black, Piece.PieceNames.knight, "b8");
        board[2][7] = new Piece(Piece.Colors.Black, Piece.PieceNames.bishop, "c8");
        board[3][7] = new Piece(Piece.Colors.Black, Piece.PieceNames.queen,  "d8");
        board[4][7] = new Piece(Piece.Colors.Black, Piece.PieceNames.king,   "e8");
        board[5][7] = new Piece(Piece.Colors.Black, Piece.PieceNames.bishop, "f8");
        board[6][7] = new Piece(Piece.Colors.Black, Piece.PieceNames.knight, "g8");
        board[7][7] = new Piece(Piece.Colors.Black, Piece.PieceNames.rook,   "h8");

        blackKingLoc = new FileRank("e8");

        // Row 7, black pawn row
        board[0][6] = new Piece(Piece.Colors.Black, Piece.PieceNames.pawn, "a7");
        board[1][6] = new Piece(Piece.Colors.Black, Piece.PieceNames.pawn, "b7");
        board[2][6] = new Piece(Piece.Colors.Black, Piece.PieceNames.pawn, "c7");
        board[3][6] = new Piece(Piece.Colors.Black, Piece.PieceNames.pawn, "d7");
        board[4][6] = new Piece(Piece.Colors.Black, Piece.PieceNames.pawn, "e7");
        board[5][6] = new Piece(Piece.Colors.Black, Piece.PieceNames.pawn, "f7");
        board[6][6] = new Piece(Piece.Colors.Black, Piece.PieceNames.pawn, "g7");
        board[7][6] = new Piece(Piece.Colors.Black, Piece.PieceNames.pawn, "h7");

        // row 6 empty spaces
        board[0][5] = new Piece(Piece.Colors.White, Piece.PieceNames.emptySpace, "a6");
        board[1][5] = new Piece(Piece.Colors.Black, Piece.PieceNames.emptySpace, "b6");
        board[2][5] = new Piece(Piece.Colors.White, Piece.PieceNames.emptySpace, "c6");
        board[3][5] = new Piece(Piece.Colors.Black, Piece.PieceNames.emptySpace, "d6");
        board[4][5] = new Piece(Piece.Colors.White, Piece.PieceNames.emptySpace, "e6");
        board[5][5] = new Piece(Piece.Colors.Black, Piece.PieceNames.emptySpace, "f6");
        board[6][5] = new Piece(Piece.Colors.White, Piece.PieceNames.emptySpace, "g6");
        board[7][5] = new Piece(Piece.Colors.Black, Piece.PieceNames.emptySpace, "h6");

        // row 5 empty spaces
        board[0][4] = new Piece(Piece.Colors.Black, Piece.PieceNames.emptySpace, "a5");
        board[1][4] = new Piece(Piece.Colors.White, Piece.PieceNames.emptySpace, "b5");
        board[2][4] = new Piece(Piece.Colors.Black, Piece.PieceNames.emptySpace, "c5");
        board[3][4] = new Piece(Piece.Colors.White, Piece.PieceNames.emptySpace, "d5");
        board[4][4] = new Piece(Piece.Colors.Black, Piece.PieceNames.emptySpace, "e5");
        board[5][4] = new Piece(Piece.Colors.White, Piece.PieceNames.emptySpace, "f5");
        board[6][4] = new Piece(Piece.Colors.Black, Piece.PieceNames.emptySpace, "g5");
        board[7][4] = new Piece(Piece.Colors.White, Piece.PieceNames.emptySpace, "h5");

        // row 4 empty spaces
        board[0][3] = new Piece(Piece.Colors.White, Piece.PieceNames.emptySpace, "a4");
        board[1][3] = new Piece(Piece.Colors.Black, Piece.PieceNames.emptySpace, "b4");
        board[2][3] = new Piece(Piece.Colors.White, Piece.PieceNames.emptySpace, "c4");
        board[3][3] = new Piece(Piece.Colors.Black, Piece.PieceNames.emptySpace, "d4");
        board[4][3] = new Piece(Piece.Colors.White, Piece.PieceNames.emptySpace, "e4");
        board[5][3] = new Piece(Piece.Colors.Black, Piece.PieceNames.emptySpace, "f4");
        board[6][3] = new Piece(Piece.Colors.White, Piece.PieceNames.emptySpace, "g4");
        board[7][3] = new Piece(Piece.Colors.Black, Piece.PieceNames.emptySpace, "h4");

        // row 3 empty spaces
        board[0][2] = new Piece(Piece.Colors.Black, Piece.PieceNames.emptySpace, "a3");
        board[1][2] = new Piece(Piece.Colors.White, Piece.PieceNames.emptySpace, "b3");
        board[2][2] = new Piece(Piece.Colors.Black, Piece.PieceNames.emptySpace, "c3");
        board[3][2] = new Piece(Piece.Colors.White, Piece.PieceNames.emptySpace, "d3");
        board[4][2] = new Piece(Piece.Colors.Black, Piece.PieceNames.emptySpace, "e3");
        board[5][2] = new Piece(Piece.Colors.White, Piece.PieceNames.emptySpace, "f3");
        board[6][2] = new Piece(Piece.Colors.Black, Piece.PieceNames.emptySpace, "g3");
        board[7][2] = new Piece(Piece.Colors.White, Piece.PieceNames.emptySpace, "h3");

        // Row 2, white pawn row
        board[0][1] = new Piece(Piece.Colors.White, Piece.PieceNames.pawn, "a2");
        board[1][1] = new Piece(Piece.Colors.White, Piece.PieceNames.pawn, "b2");
        board[2][1] = new Piece(Piece.Colors.White, Piece.PieceNames.pawn, "c2");
        board[3][1] = new Piece(Piece.Colors.White, Piece.PieceNames.pawn, "d2");
        board[4][1] = new Piece(Piece.Colors.White, Piece.PieceNames.pawn, "e2");
        board[5][1] = new Piece(Piece.Colors.White, Piece.PieceNames.pawn, "f2");
        board[6][1] = new Piece(Piece.Colors.White, Piece.PieceNames.pawn, "g2");
        board[7][1] = new Piece(Piece.Colors.White, Piece.PieceNames.pawn, "h2");

        // Row 1, white king row
        board[0][0] = new Piece(Piece.Colors.White, Piece.PieceNames.rook,   "a1");
        board[1][0] = new Piece(Piece.Colors.White, Piece.PieceNames.knight, "b1");
        board[2][0] = new Piece(Piece.Colors.White, Piece.PieceNames.bishop, "c1");
        board[3][0] = new Piece(Piece.Colors.White, Piece.PieceNames.queen,  "d1");
        board[4][0] = new Piece(Piece.Colors.White, Piece.PieceNames.king,   "e1");
        board[5][0] = new Piece(Piece.Colors.White, Piece.PieceNames.bishop, "f1");
        board[6][0] = new Piece(Piece.Colors.White, Piece.PieceNames.knight, "g1");
        board[7][0] = new Piece(Piece.Colors.White, Piece.PieceNames.rook,   "h1");

        whiteKingLoc = new FileRank("e1");
        c = context;
        evaluateBoard();

    } // End default constructor



    /**
     *   This version of a constructor is used to create a clone of a game board.
     *   All pieces and their current locations are copied to a new board.
     *
     * @param copyFromBoard
     *      A game board to copy from.
     *
     */
    public Board(Board copyFromBoard) {
        for (int col=0; col < columnCount; col++) {
            for (int row=0; row < rowCount; row++) {
                FileRank loc = new FileRank(col, row);
                Piece aPiece = copyFromBoard.board[col][row];
                this.board[col][row] = new Piece(aPiece.getColor(), aPiece.getName(), loc.getName());

                if ( aPiece.getName().equals(Piece.PieceNames.king)) {
                    if ( aPiece.getColor().equals(Piece.Colors.White)) {
                        // white king
                        this.whiteKingLoc = loc;
                    }
                    else {
                        // black king
                        this.blackKingLoc = loc;
                    }
                }
            }  // next row
        } // next column

        evaluateBoard();
    }

    public Piece[][] getBoard(){
        return board;
    }

    /**
     *
     * @param position
     * @return TRUE if a piece is in this space, FALSE if is an empty square
     */
    public boolean isPiece(int position){
        if(board[position%8][7-position/8].getName() == Piece.PieceNames.emptySpace)
            return true;
        return false;
    }
    /**
     * Move a chess piece from one location to another location on
     * the game board.
     *
     * @param fromLoc
     * The starting location of a game piece
     *
     * @param toLoc
     * The destination of a game piece
     *
     * @return
     * <p>A boolean return value.  If this is an illegal move,
     * this function will return "false".  Otherwise, a "true"
     * is returned.
     *
     */
    public boolean move(String fromLoc, String toLoc) {

        if ( ! moveAndEvaluate(fromLoc, toLoc) ) {
            return false;
        }

        //
        // The last move was a legal move.  Let's see what is the
        // net effect after making that move.  The game could be
        // in "check", "checkmate", "stalemate", or the opponent's
        // turn to move.
        //
        if ( (Chess.bBlackInCheck) || (Chess.bWhiteInCheck) ) {
            // A legal move that put the opponent's king in "check"
            if ( isCheckMate() ) {
                Chess.gGameState = gameState.checkMate;
            } else {
                Toast toast = Toast.makeText(c,"Check!", Toast.LENGTH_SHORT);
                toast.show();
            };
        }
        else if (Chess.bDrawOffered) {
            // request a draw from the opponent
            //show("draw?");
        }
        else if (isStalemate()) {
            // This is a stalemate.  The opponent does not have
            // any legal move.
            Chess.gGameState = gameState.staleMate;
            //show("Stalemate");
        }
        else {

        }

        //
        // Remove any active En Passant rule.  En Passant rule
        // can only be used one time immediately followed a
        // pawn making a double push move.
        //
/*
		if ((Chess.enPassant.isActive()) &&
				(Chess.enPassant.isEnPassantCompleted()) ) {
				// En Passant capture rule is done.
				Chess.enPassant = new EnPassant();
			}
*/

        if ((enPassant.isActive()) &&
                (enPassant.isEnPassantCompleted()) ) {
            // En Passant capture rule is done.
            enPassant = new EnPassant();
        }

        return true;
    }

    public void moveSimple(String fromLoc, String toLoc){
        FileRank fromHere = new FileRank(fromLoc);
        FileRank toHere = new FileRank(toLoc);

        Piece aPiece = board[fromHere.getX()][fromHere.getY()];
        drawEmptySpace(fromLoc);
        moveRookForCastling(aPiece, toLoc);
        board[toHere.getX()][toHere.getY()] = aPiece;
    }

    private boolean moveAndEvaluate(String fromLoc, String toLoc) {
        // Verify that you have selected a valid piece to move from
        if ( ! canMoveFrom(fromLoc) ) return false;

        // Take the piece at the "fromLoc"
        FileRank fromHere = new FileRank(fromLoc);
        FileRank toHere = new FileRank(toLoc);

        Piece aPiece = board[fromHere.getX()][fromHere.getY()];

        // Verify that this piece is allowed to move to the
        // specified destination.
        if ( ! aPiece.canMoveTo(toLoc) ) return false;

        // Remember the original pieces at the "from" and
        // "to" location.  Also remember the original locations
        // of the black and white king.  These information are
        // needed if we have to restore the original game board
        // conditions to undo an illegal move.
        //
        Piece origPieceAtFromLoc = board[fromHere.getX()][fromHere.getY()];
        Piece origPieceAtToLoc = board[toHere.getX()][toHere.getY()];
        FileRank origWhiteKingLoc = whiteKingLoc;
        FileRank origBlackKingLoc = blackKingLoc;

        //
        // Execute castling if it is one of the special castling
        // moves made by a king.  This function only moves the
        // corresponding rook for the castling.  The actual
        // movement of the king is done by the following "moveTo"
        // statement.
        //
        moveRookForCastling(aPiece, toLoc);

        boolean bCreateEnPassant = aPiece.isPawnDoublePush(toLoc);

        // move the piece at the "fromLoc" to the new "toLoc"
        if ( aPiece.moveTo(toLoc) ) {
            board[toHere.getX()][toHere.getY()] = aPiece;
/*
				System.out.println("Board.move(): after move, board[" + toHere.getX() + "][" +
									toHere.getY() + "]= " +
									board[toHere.getX()][toHere.getY()].toString() + "@" +
									board[toHere.getX()][toHere.getY()].getFileRank().getName() );
*/


            if (bCreateEnPassant) {
                // This pawn just made an initial double step push.
                // It may trigger a setup for the
                // opponent to execute an "En Passant" move.  This pawn is
                // now a potential target for an "En Passant" capture by
                // the opponent.
                //
                enPassant = new EnPassant(aPiece);
            }


            // Remember the new location of the two kings if either
            // one of them has moved.
            if ( aPiece.getName().equals(Piece.PieceNames.king) ) {
                if ( aPiece.getColor().equals(Piece.Colors.White)) {
                    whiteKingLoc = new FileRank(aPiece.getLoc());
                }
                else {
                    blackKingLoc = new FileRank(aPiece.getLoc());
                }
            }
        }

        //
        // back fill the vacated square with an empty space
        //
        drawEmptySpace(fromHere);


        //
        // All special moves are artifacts or consequences of a move that
        // satisfies a set of conditions.   If all the conditions are met,
        // this move will cause movements to one or more other pieces.
        //
        Piece aPromotedPawn = isPawnPromotion(aPiece, toLoc);
        if ( aPromotedPawn != null ) {
            // This is a newly promoted pawn
            board[toHere.getX()][toHere.getY()] = aPromotedPawn;
        }


        //
        // Check for execution of an En Passant capture move
        //
        if ((enPassant.isActive()) &&
                (aPiece.getColor().equals(enPassant.getEnPassantColor())) &&
                (aPiece.getName().equals(Piece.PieceNames.pawn)) ) {

            if ((toHere.equals(enPassant.getTriggerLoc())) ) {
                //
                // Just executed an En Passant capture move.
                // Remove the double pushed pawn from the game board.
                //
                drawEmptySpace(enPassant.getDoublePushedPawnLoc());
            }
            else {
                // En Passant is active but the opponent choose to move
                // a different piece instead.  Therefore, he will give
                // up this one-time only En Passant move.
            }

            enPassant.setEnPassantCompleted(true);
        }

        // Evaluate the result from the last move.
        evaluateBoard();

        AddCastlingMoves();


        //
        // Verify my own king is not in check after I have made this move.
        // Otherwise, this will be an illegal move.
        // If I am in check, undo the last move and return false to report
        // this as an invalid move.
        //
        if ( ((Chess.gGameState == Chess.gameState.whiteMove) && (Chess.bWhiteInCheck)) ||
                ((Chess.gGameState == Chess.gameState.blackMove) && (Chess.bBlackInCheck)) )	{

            //
            //   **********  Debug use only  **********
            //
            // This function, explainWhoCheckedMe, is only needed
            // for debugging purpose only.  Remove it before final
            // submission.
            //
            //explainWhoCheckedMe(fromLoc, toLoc);


            //
            // Either white or black made an illegal move and exposed his own king.
            // Must undo this illegal move and restore the original game board conditions.
            //
            board[fromHere.getX()][fromHere.getY()] = origPieceAtFromLoc;
            board[toHere.getX()][toHere.getY()] = origPieceAtToLoc;
            whiteKingLoc = origWhiteKingLoc;
            blackKingLoc = origBlackKingLoc;

            // Undo En Passant move
            if ( enPassant.isActive()) {
                // Restore any pawn captured by En Passant capture
                Piece aRestoredPawn = enPassant.getDoublePushedPawn();
                int x = aRestoredPawn.getX();
                int y = aRestoredPawn.getY();
                board[x][y] = aRestoredPawn;
            }

            evaluateBoard();
            return false;
        }

        return true;
    }



    /**
     * 		Calculate all legal moves for every chess piece
     * 		on the board.   This is where each piece can legally
     * 		move to from where it is now.  The result of this
     * 		calculation is an ArrayList of "FileRank".   This
     * 		ArrayList is stored with each chess piece.   This
     * 		ArrayList is also used to determine if either the
     * 		white king or the black king is in "check".  If there
     * 		is no legal move for a chess piece, the ArrayList
     *      will be empty.   The board should only be evaluated
     *      after a legal move has been made.
     */
    private void evaluateBoard()
    {
        Chess.bBlackInCheck = false;
        Chess.bWhiteInCheck = false;

        for (int row=0; row < rowCount; row++) {
            for (int col=0; col < columnCount; col++) {
                Piece aPiece = board[row][col];

                // Calculate all the legal moves for this piece
                switch 	(aPiece.getName()) {
                    case rook:
                        setRookMovements(aPiece);
                        break;

                    case knight:
                        setKnightMovements(aPiece);
                        break;

                    case bishop:
                        setBishopMovements(aPiece);
                        break;

                    case queen:
                        setQueenMovements(aPiece);
                        break;

                    case king:
                        setKingMovements(aPiece);
                        break;

                    case pawn:
                        setPawnMovements(aPiece);
                        break;

                    case emptySpace:
                    default:
                        // Nothing to calculate for empty square
                }
            }
        }
    }



    /**
     * @param aKing  A King game piece
     * <p>
     *   Calculate all the valid movements for a "king"
     */
    private void setKingMovements(Piece aKing)
    {
        aKing.clearTargets();

        int x = aKing.getX();
        int y = aKing.getY();

        // Move north
        if ((y+1 < rowCount) && ( ! isOccupiedByMyColor(x, y+1, aKing))) addTarget(aKing, new FileRank(x, y+1));

        // Move north east
        if ((x+1 < columnCount) && (y+1 < rowCount) &&
                ( ! isOccupiedByMyColor(x+1, y+1, aKing))) addTarget(aKing, new FileRank(x+1, y+1));

        // Move east
        if ((x+1 < columnCount) && ( ! isOccupiedByMyColor(x+1, y, aKing))) addTarget(aKing, new FileRank(x+1, y));

        // Move south east
        if ((x+1 < columnCount) && (y-1 >= 0) &&
                ( ! isOccupiedByMyColor(x+1, y-1, aKing))) addTarget(aKing, new FileRank(x+1, y-1));

        // Move south
        if ((y-1 >= 0) && ( ! isOccupiedByMyColor(x, y-1, aKing))) addTarget(aKing, new FileRank(x, y-1));

        // Move south west
        if ((x-1 >= 0) && (y-1 >= 0) &&
                ( ! isOccupiedByMyColor(x-1, y-1, aKing))) addTarget(aKing, new FileRank(x-1, y-1));

        // Move west
        if ((x-1 >= 0) && ( ! isOccupiedByMyColor(x-1, y, aKing))) addTarget(aKing, new FileRank(x-1, y));

        // Move north west
        if ((x-1 >= 0) && (y+1 < rowCount) &&
                ( ! isOccupiedByMyColor(x-1, y+1, aKing))) addTarget(aKing, new FileRank(x-1, y+1));

    }


    /**
     * @param aBishop  A bishop game piece
     * <p>
     *   Calculate all the valid movements for a "bishop"
     */
    private void setBishopMovements(Piece aBishop)
    {
        aBishop.clearTargets();

        // Move north east
        int x = aBishop.getX();
        int y = aBishop.getY();
        while ((++x < columnCount) && (++y < rowCount)) {
            if ( isEmpty(x,y) ) {
                addTarget(aBishop, new FileRank(x, y));
            }
            else if ( isOccupiedByOpponent(x, y, aBishop)) {
                addTarget(aBishop, new FileRank(x, y));
                break;
            }
            else {
                // This space is occupied by my own side
                break;
            }
        }

        // Move south east
        x = aBishop.getX();
        y = aBishop.getY();
        while ((++x < columnCount) && (--y >= 0)) {
            if ( isEmpty(x,y) ) {
                addTarget(aBishop, new FileRank(x, y));
            }
            else if ( isOccupiedByOpponent(x, y, aBishop)) {
                addTarget(aBishop, new FileRank(x, y));
                break;
            }
            else {
                // This space is occupied by my own side
                break;
            }
        }

        // Move south west
        x = aBishop.getX();
        y = aBishop.getY();
        while ((--x >= 0) && (--y >= 0)) {
            if ( isEmpty(x,y) ) {
                addTarget(aBishop, new FileRank(x, y));
            }
            else if ( isOccupiedByOpponent(x, y, aBishop)) {
                addTarget(aBishop, new FileRank(x, y));
                break;
            }
            else {
                // This space is occupied by my own side
                break;
            }
        }

        // Move north west
        x = aBishop.getX();
        y = aBishop.getY();
        while ((--x >= 0) && (++y < rowCount)) {
            if ( isEmpty(x,y) ) {
                addTarget(aBishop, new FileRank(x, y));
            }
            else if ( isOccupiedByOpponent(x, y, aBishop)) {
                addTarget(aBishop, new FileRank(x, y));
                break;
            }
            else {
                // This space is occupied by my own side
                break;
            }
        }

    }


    /**
     * @param aQueen  A queen game piece
     * <p>
     *   Calculate all the valid movements for a "queen"
     */
    private void setQueenMovements(Piece aQueen)
    {
        aQueen.clearTargets();

        // First calculate movements going east.
        int x = aQueen.getX();
        int y = aQueen.getY();
        while (++x < columnCount) {
            if ( isEmpty(x,y) ) {
                addTarget(aQueen, new FileRank(x, y));
            }
            else if ( isOccupiedByOpponent(x, y, aQueen)) {
                addTarget(aQueen, new FileRank(x, y));
                break;
            }
            else {
                // This space is occupied by my own side
                break;
            }
        }


        // calculate movements going west.
        x = aQueen.getX();
        y = aQueen.getY();
        while (--x >= 0) {
            if ( isEmpty(x,y) ) {
                addTarget(aQueen, new FileRank(x, y));
            }
            else if ( isOccupiedByOpponent(x, y, aQueen)) {
                addTarget(aQueen, new FileRank(x, y));
                break;
            }
            else {
                // This space is occupied by my own side
                break;
            }
        }


        // calculate movements going north.
        x = aQueen.getX();
        y = aQueen.getY();
        while (++y < rowCount) {
            if ( isEmpty(x,y) ) {
                addTarget(aQueen, new FileRank(x, y));
            }
            else if ( isOccupiedByOpponent(x, y, aQueen)) {
                addTarget(aQueen, new FileRank(x, y));
                break;
            }
            else {
                // This space is occupied by my own side
                break;
            }
        }


        // calculate movements going south.
        x = aQueen.getX();
        y = aQueen.getY();
        while (--y >= 0) {
            if ( isEmpty(x,y) ) {
                addTarget(aQueen, new FileRank(x, y));
            }
            else if ( isOccupiedByOpponent(x, y, aQueen)) {
                addTarget(aQueen, new FileRank(x, y));
                break;
            }
            else {
                // This space is occupied by my own side
                break;
            }
        }

        // calculate movements going north east
        x = aQueen.getX();
        y = aQueen.getY();
        while ((++x < columnCount) && (++y < rowCount)) {
            if ( isEmpty(x,y) ) {
                addTarget(aQueen, new FileRank(x, y));
            }
            else if ( isOccupiedByOpponent(x, y, aQueen)) {
                addTarget(aQueen, new FileRank(x, y));
                break;
            }
            else {
                // This space is occupied by my own side
                break;
            }
        }

        // calculate movements going south east
        x = aQueen.getX();
        y = aQueen.getY();
        while ((++x < columnCount) && (--y >= 0)) {
            if ( isEmpty(x,y) ) {
                addTarget(aQueen, new FileRank(x, y));
            }
            else if ( isOccupiedByOpponent(x, y, aQueen)) {
                addTarget(aQueen, new FileRank(x, y));
                break;
            }
            else {
                // This space is occupied by my own side
                break;
            }
        }

        // calculate movements going south west
        x = aQueen.getX();
        y = aQueen.getY();
        while ((--x >= 0) && (--y >= 0)) {
            if ( isEmpty(x,y) ) {
                addTarget(aQueen, new FileRank(x, y));
            }
            else if ( isOccupiedByOpponent(x, y, aQueen)) {
                addTarget(aQueen, new FileRank(x, y));
                break;
            }
            else {
                // This space is occupied by my own side
                break;
            }
        }

        // calculate movements going north west
        x = aQueen.getX();
        y = aQueen.getY();
        while ((--x >= 0) && (++y < rowCount)) {
            if ( isEmpty(x,y) ) {
                addTarget(aQueen, new FileRank(x, y));
            }
            else if ( isOccupiedByOpponent(x, y, aQueen)) {
                addTarget(aQueen, new FileRank(x, y));
                break;
            }
            else {
                // This space is occupied by my own side
                break;
            }
        }

    }




    /**
     * @param aPawn  A pawn game piece
     * <p>
     *   Calculate all the valid movements for a "pawn"
     */
    private void setPawnMovements(Piece aPawn)
    {
        aPawn.clearTargets();

        int x = aPawn.getX();
        int y = aPawn.getY();

        // Get the color of this pawn
        if ( aPawn.getColor().equals(Piece.Colors.Black) ) {
            // This is a black pawn
            if ((! aPawn.hasMoved()) && (isEmpty(x, y-2)) ) {
                // This pawn has not moved before
                addTarget(aPawn, new FileRank(x, y-2));
            }
            if ( (y-1 >= 0) && (isEmpty(x, y-1)) ) {
                addTarget(aPawn, new FileRank(x, y-1));
            }

            // Calculate diagonal capture
            if ((x-1 >= 0) && (isOccupiedByWhite(x-1, y-1)) ){
                // Occupied by an opponent
                addTarget(aPawn, new FileRank(x-1, y-1));
            }

            if ((x+1 < columnCount) && (isOccupiedByWhite(x+1, y-1)) ) {
                // Occupied by an opponent
                addTarget(aPawn, new FileRank(x+1, y-1));
            }
        }
        else {
            // This is a white pawn
            if ((! aPawn.hasMoved()) && (isEmpty(x, y+2)) ) {
                // This pawn has not moved before
                addTarget(aPawn, new FileRank(x, y+2));
            }

            if ((y+1 < rowCount) && (isEmpty(x, y+1)) ) {
                addTarget(aPawn, new FileRank(x, y+1));
            }

            // Calculate diagonal capture
            if ((x-1 >= 0) && (isOccupiedByBlack(x-1, y+1)) ) {
                // Occupied by an opponent
                addTarget(aPawn, new FileRank(x-1, y+1));
            }

            if ((x+1 < columnCount) && (isOccupiedByBlack(x+1, y+1)) ) {
                // Occupied by an opponent
                addTarget(aPawn, new FileRank(x+1, y+1));
            }

        }

        // Implement En Passant move for a pawn.  En Passant conditions are:
        //
        //    1. the capturing pawn must be on its fifth rank;
        //    2. the captured pawn must be on an adjacent file and must have
        //       just moved two squares in a single move (i.e. a double-step move);
        //    3. the capture can only be made on the move immediately after the
        //       opposing pawn makes the double-step move; otherwise the right to
        //       capture it en passant is lost.
        //
        //if ( Chess.enPassant.isParticipant(aPawn) ) {
        if ( enPassant.isParticipant(aPawn) ) {
            // This pawn can participate in an En Passant capture move
            //addTarget(aPawn, Chess.enPassant.getTriggerLoc());
            addTarget(aPawn, enPassant.getTriggerLoc());
        }

    }



    /**
     * @param aRook  A rook game piece
     * <p>
     *   Calculate all the valid movements for a "rook"
     */
    private void setRookMovements(Piece aRook)
    {
        aRook.clearTargets();

        int x = aRook.getX();
        int y = aRook.getY();

        // First calculate movements going east.
        while (++x < columnCount) {
            if ( isEmpty(x,y) ) {
                addTarget(aRook, new FileRank(x, y));
            }
            else if ( isOccupiedByOpponent(x, y, aRook)) {
                addTarget(aRook, new FileRank(x, y));
                break;
            }
            else {
                // This space is occupied by my own side
                break;
            }
        }


        // calculate movements going west.
        x = aRook.getX();
        y = aRook.getY();
        while (--x >= 0) {
            if ( isEmpty(x,y) ) {
                addTarget(aRook, new FileRank(x, y));
            }
            else if ( isOccupiedByOpponent(x, y, aRook)) {
                addTarget(aRook, new FileRank(x, y));
                break;
            }
            else {
                // This space is occupied by my own side
                break;
            }
        }


        // calculate movements going north.
        x = aRook.getX();
        y = aRook.getY();
        while (++y < rowCount) {
            if ( isEmpty(x,y) ) {
                addTarget(aRook, new FileRank(x, y));
            }
            else if ( isOccupiedByOpponent(x, y, aRook)) {
                addTarget(aRook, new FileRank(x, y));
                break;
            }
            else {
                // This space is occupied by my own side
                break;
            }
        }


        // calculate movements going south.
        x = aRook.getX();
        y = aRook.getY();
        while (--y >= 0) {
            if ( isEmpty(x,y) ) {
                addTarget(aRook, new FileRank(x, y));
            }
            else if ( isOccupiedByOpponent(x, y, aRook)) {
                addTarget(aRook, new FileRank(x, y));
                break;
            }
            else {
                // This space is occupied by my own side
                break;
            }
        }

    }



    /**
     * @param aKnight   A Knight game piece
     * <p>
     *   Calculate all the valid movements for a "knight"
     */
    private void setKnightMovements(Piece aKnight)
    {
        aKnight.clearTargets();

        int x;
        int y;

        // First calculate movements going east.
        x = aKnight.getX() + 2;
        y = aKnight.getY();
        if ( (x < columnCount) && ( y+1 < rowCount) &&
                ( ! isOccupiedByMyColor(x, y+1, aKnight)) ) {
            addTarget(aKnight, new FileRank(x, y+1));
        }

        if ( (x < columnCount) && ( y-1 >= 0) &&
                ( ! isOccupiedByMyColor(x, y-1, aKnight)) ) {
            addTarget(aKnight, new FileRank(x, y-1));
        }


        // calculate movements going west.
        x = aKnight.getX() - 2;
        y = aKnight.getY();
        if ( (x >= 0) && ( y+1 < rowCount) &&
                ( ! isOccupiedByMyColor(x, y+1, aKnight)) ) {
            addTarget(aKnight, new FileRank(x, y+1));
        }

        if ( (x >= 0) && ( y-1 >= 0) &&
                ( ! isOccupiedByMyColor(x, y-1, aKnight)) ) {
            addTarget(aKnight, new FileRank(x, y-1));
        }


        // calculate movements going north.
        x = aKnight.getX();
        y = aKnight.getY() + 2;
        if ( (x+1 < columnCount) && ( y < rowCount) &&
                ( ! isOccupiedByMyColor(x+1, y, aKnight)) ) {
            addTarget(aKnight, new FileRank(x+1, y));
        }

        if ( (x-1 >= 0) && ( y < rowCount) &&
                ( ! isOccupiedByMyColor(x-1, y, aKnight)) ) {
            addTarget(aKnight, new FileRank(x-1, y));
        }


        // calculate movements going south.
        x = aKnight.getX();
        y = aKnight.getY() - 2;
        if ( (x+1 < columnCount) && ( y >= 0) &&
                ( ! isOccupiedByMyColor(x+1, y, aKnight)) ) {
            addTarget(aKnight, new FileRank(x+1, y));
        }

        if ((x-1 >= 0) && ( y >= 0) &&
                ( ! isOccupiedByMyColor(x-1, y, aKnight)) ) {
            addTarget(aKnight, new FileRank(x-1, y));
        }

    }



    private boolean isEmpty(int x, int y)
    {
        if (board[x][y].getName().equals(Piece.PieceNames.emptySpace) ) return true;
        return false;
    }


    private boolean isEmpty(String loc)
    {
        FileRank myLoc = new FileRank(loc);
        return isEmpty(myLoc.getX(), myLoc.getY());
    }




    private boolean isOccupiedByBlack(int x, int y)
    {
        if ( isEmpty(x, y) ) return false;
        if ( board[x][y].getColor().equals(Piece.Colors.Black) ) return true;
        return false;
    }

    private boolean isOccupiedByBlack(String loc)
    {
        if ( isEmpty(loc) ) return false;
        FileRank myLoc = new FileRank(loc);
        int x = myLoc.getX();
        int y = myLoc.getY();
        if ( board[x][y].getColor().equals(Piece.Colors.Black) ) return true;
        return false;
    }


    private boolean isOccupiedByWhite(int x, int y)
    {
        if ( isEmpty(x, y) ) return false;
        if ( board[x][y].getColor().equals(Piece.Colors.White) ) return true;
        return false;
    }

    private boolean isOccupiedByWhite(String loc)
    {
        if ( isEmpty(loc) ) return false;
        FileRank myLoc = new FileRank(loc);
        int x = myLoc.getX();
        int y = myLoc.getY();
        if ( board[x][y].getColor().equals(Piece.Colors.White) ) return true;
        return false;
    }



    private boolean isOccupiedByOpponent(int x, int y, Piece myPiece)
    {
        if ( isEmpty(x, y) ) return false;
        if ( ! board[x][y].getColor().equals(myPiece.getColor()) ) return true;
        return false;
    }


    private boolean isOccupiedByMyColor(int x, int y, Piece myPiece)
    {
        if ( isEmpty(x, y) ) return false;
        if ( board[x][y].getColor().equals(myPiece.getColor()) ) return true;
        return false;
    }


    /**
     * @param fromLoc
     * The "from" location of a game piece given in the FileRank format.
     *
     * @return
     * <p>
     * Return "true" if there is a game piece from my side (i.e. same
     * color) located at the specified location which allows movement.
     * <p>
     */
    private boolean canMoveFrom(String fromLoc)
    {
        // Verify the format of the from location
        if ( (! FileRank.isValid(fromLoc)) ) return false;

        // Verify that you did not select an empty square to move
        if ( isEmpty(fromLoc) ) return false;

        // Verify the color of the selected game piece
        if ( Chess.gGameState == Chess.gameState.whiteMove ) {
            // This is white's move but you selected a black piece
            if ( isOccupiedByBlack(fromLoc) )  return false;
        }
        else {
            // This is black's move but you selected a white piece
            if ( isOccupiedByWhite(fromLoc) )  return false;
        }

        // Determine if this game piece can make any moves
        FileRank myLoc = new FileRank(fromLoc);
        Piece aPiece = board[myLoc.getX()][myLoc.getY()];
        if ( aPiece.getTargets().isEmpty() ) {
            // The piece at this location cannot make any moves
            //
            //System.out.println("canMoveFrom: " + aPiece.getName() +
            //		" @" + aPiece.getLoc() + " cannot move anywhere");

            return false;
        }
        return true;
    }


    /**
     * @param aPiece
     * <p>
     * A game piece that just executed a move.   If this is a "pawn",
     * there is a chance that it can be promoted.
     * <p>
     * @param toLoc
     * <p>
     * The new location of this game piece after the move.
     * <p>
     *
     * @return
     * <p>
     * Return a new game piece if this "pawn" has been promoted.
     * Otherwise, return a "null" to indicate no pawn promotion.
     * <p>
     */
    private Piece isPawnPromotion(Piece aPiece, String toLoc)
    {
        if ( ! aPiece.getName().equals(Piece.PieceNames.pawn)) return null;

        FileRank myLoc = new FileRank(toLoc);
        if ( aPiece.getColor().equals(Piece.Colors.White) ) {
            // This is a white pawn.  White pawn must reach
            // row 7 to get promoted.
            if ( myLoc.getY() == rowCount -1) {
                // Promote this white pawn now.
                switch (Chess.pawnPromotionChoice) {
                    case rook:
                        return new Piece(Piece.Colors.White, Piece.PieceNames.rook, toLoc);

                    case knight:
                        return new Piece(Piece.Colors.White, Piece.PieceNames.knight, toLoc);

                    case bishop:
                        return new Piece(Piece.Colors.White, Piece.PieceNames.bishop, toLoc);

                    case  queen:
                    default:
                        return new Piece(Piece.Colors.White, Piece.PieceNames.queen, toLoc);
                }
            }
        }
        else {
            // This is a black pawn.  Black pawn must reach
            // row 0 to get promoted.
            if ( myLoc.getY() == 0) {
                // Promote this black pawn now.
                switch (Chess.pawnPromotionChoice) {
                    case rook:
                        return new Piece(Piece.Colors.Black, Piece.PieceNames.rook, toLoc);

                    case knight:
                        return new Piece(Piece.Colors.Black, Piece.PieceNames.knight, toLoc);

                    case bishop:
                        return new Piece(Piece.Colors.Black, Piece.PieceNames.bishop, toLoc);

                    case  queen:
                    default:
                        return new Piece(Piece.Colors.Black, Piece.PieceNames.queen, toLoc);
                }
            }
        }
        return null;
    }



    private void drawEmptySpace(FileRank loc)
    {

        if ( (loc.getX() % 2) == 0 ) {
            // Even column
            if( (loc.getY() % 2) == 0 ) {
                // Even row.  This is now an empty black square
                board[loc.getX()][loc.getY()] = new Piece(Piece.Colors.Black,
                        Piece.PieceNames.emptySpace,
                        loc.getName());
            }
            else {
                // Odd row.  This is now an empty white square
                board[loc.getX()][loc.getY()] = new Piece(Piece.Colors.White,
                        Piece.PieceNames.emptySpace,
                        loc.getName());
            }
        }
        else {
            // Odd column
            if( (loc.getY() % 2) == 0 ) {
                // Even row.  This is now an empty white square.
                board[loc.getX()][loc.getY()] = new Piece(Piece.Colors.White,
                        Piece.PieceNames.emptySpace,
                        loc.getName());
            }
            else {
                // Odd row.  This is now an empty black square.
                board[loc.getX()][loc.getY()] = new Piece(Piece.Colors.Black,
                        Piece.PieceNames.emptySpace,
                        loc.getName());
            }
        }
    }


    private void drawEmptySpace(String loc)
    {
        FileRank fr = new FileRank(loc);
        drawEmptySpace(fr);
    }



    /**
     * @param aPiece    A chess piece
     * @param targetLoc  A location on the board where this chess piece can move to
     *
     */
    private void addTarget(Piece aPiece, FileRank targetLoc)
    {
        aPiece.addTarget(targetLoc);
        if ((aPiece.getColor().equals(Piece.Colors.White)) &&
                (blackKingLoc.getName().equals(targetLoc.getName())) ) {
            // black king is now in check
            Chess.bBlackInCheck = true;
        }

        if ((aPiece.getColor().equals(Piece.Colors.Black)) &&
                (whiteKingLoc.getName().equals(targetLoc.getName())) ) {
            // white king is now in check
            Chess.bWhiteInCheck = true;
        }
    }



    /**
     * @return  Return a boolean value indicating a "checkmate" condition
     */
    private boolean isCheckMate()
    {
        // Find a move by any piece that can remove the "check" condition.
        // This piece must come from the side that is put in "check".
        // If such a move is available, then this is just a simple "check"
        // but not a "checkmate".
        //

        Chess.gameState currentState = Chess.gGameState;

        if ( Chess.gGameState == gameState.whiteMove ) {
            Chess.gGameState = gameState.blackMove;
        }
        else if ( Chess.gGameState == gameState.blackMove ) {
            Chess.gGameState = gameState.whiteMove;
        }
        //System.out.println("isCheckMate: changed gameState to " + Chess.gGameState);


        for (int col=0; col < columnCount; col++) {
            for (int row=0; row < rowCount; row++) {
                Piece aPiece = board[col][row];
                if (aPiece.getName().equals(Piece.PieceNames.emptySpace)) continue;

                if ((aPiece.getColor().equals(Piece.Colors.Black)) &&
                        (Chess.bBlackInCheck) ) {
                    // Try to find a move by this black piece that
                    // can remove the black "in check" condition.
                    if (canRemoveCheck(aPiece)) {
                        Chess.gGameState = currentState;
                        return false;  // Not in checkmate
                    }
                }

                if ((aPiece.getColor().equals(Piece.Colors.White)) &&
                        (Chess.bWhiteInCheck) ) {
                    // Try to find a move by this white piece that
                    // can remove the white "in check" condition.
                    if (canRemoveCheck(aPiece))  {
                        Chess.gGameState = currentState;
                        return false;  // Not in checkmate
                    }
                }
            }  //end of row
        }  //end of column

        // This is a "checkmate".   There is no legal move that the
        // "in check" side can make that will remove the condition.
        Chess.gGameState = currentState;
        return true;
    }


    private boolean canRemoveCheck(Piece aPiece)
    {
        FileRank fromLoc = aPiece.getFileRank();
        ArrayList<FileRank> toLocations = aPiece.getTargets();

        for (FileRank toLoc : toLocations) {
            Board aTestBoard = new Board(this);

            //System.out.println("canRemoveCheck(): moving " + aPiece.toString() + " @" + aPiece.getLoc() +
            //					" to " + toLoc.getName() );
            aTestBoard.moveAndEvaluate(fromLoc.getName(), toLoc.getName());

            //System.out.println("canRemoveCheck(): moved " + aPiece.toString() + " @" + aPiece.getLoc() +
            //		" to " + toLoc.getName() );

            //aTestBoard.evaluateBoard();
            if ((! Chess.bBlackInCheck) && (! Chess.bWhiteInCheck) )
                return true;
        }

        return false;
    }



    /**
     *
     *
     *  The purpose of this function is to calculate the location(s) for
     *  a king to execute a "castling".   If all of the required conditions
     *  are met, one or two special "castling" location(s) will be added
     *  to the list of allowable movements for the king.
     *
     * Castling Requirements:
     * ---------------------
     *   1. the king cannot currently in check
     *   2. the king has not previously moved
     *   3. the rook has not previously moved
     *   4. Spaces between the king and the rook must be empty
     *   5. the king can pass through to the castle location
     *      without being checked
     *   6. the king at the castle location is not exposed
     *
     */
    private void AddCastlingMoves()
    {
        // Check white king castling moves
        Piece whiteKing = board[whiteKingLoc.getX()][whiteKingLoc.getY()];

        if ((! Chess.bWhiteInCheck) && (! whiteKing.hasMoved()) ) {

            // White king has two castling locations.  One on the right
            // side at "g1" and is called short castling.   Another one
            // on the left side at "c1" called queen-side castling or
            // long castling.
            //
            // Check for the possibility of a white king short castling.
            if ((anUnmovedRook("h1")) &&
                    (isEmptyAndNotUnderAttackBy(Piece.Colors.Black, "f1", "g1")) ) {
                // White short castling is allowed
                addTarget(whiteKing, new FileRank("g1"));
                //System.out.println("AddCastlingMoves: white king can castle at \"g1\"");
            }

            // Check for the possibility of a white king long castling
            if ((isEmpty("b1")) && (anUnmovedRook("a1")) &&
                    (isEmptyAndNotUnderAttackBy(Piece.Colors.Black, "c1", "d1")) ) {
                // White long castling is allowed
                addTarget(whiteKing, new FileRank("c1"));
                //System.out.println("AddCastlingMoves: white king can castle at \"c1\"");
            }
        } //End of white castling check


        // Check black king castling moves
        Piece blackKing = board[blackKingLoc.getX()][blackKingLoc.getY()];

        if ((! Chess.bBlackInCheck) && (! blackKing.hasMoved()) ) {

            // Black king has two castling locations.  One on the right
            // side at "g8" and is called short castling.   Another one
            // on the left side at "c8" called queen-side castling or
            // long castling.
            //
            // Check for the possibility of a black king short castling.
            if ((anUnmovedRook("h8")) &&
                    (isEmptyAndNotUnderAttackBy(Piece.Colors.White, "f8", "g8")) ) {
                // Black short castling is allowed
                addTarget(blackKing, new FileRank("g8"));
                //System.out.println("AddCastlingMoves: black king can castle at \"g8\"");
            }

            // Check for the possibility of a black king long castling
            if ((isEmpty("b8")) && (anUnmovedRook("a8")) &&
                    (isEmptyAndNotUnderAttackBy(Piece.Colors.White, "c8", "d8")) ) {
                // Black long castling is allowed
                addTarget(blackKing, new FileRank("c8"));
                //System.out.println("AddCastlingMoves: black king can castle at \"c8\"");
            }
        } //End of black castling check
    }


    private boolean anUnmovedRook(String loc)
    {
        FileRank fr = new FileRank(loc);
        Piece aPiece = board[fr.getX()][fr.getY()];
        if((! aPiece.hasMoved()) &&
                (aPiece.getName().equals(Piece.PieceNames.rook)) ) {
            return true;
        }
        return false;
    }


    /**
     * @param attackByThisColor  The color of the side that can attack either locations
     *
     * @param loc1  A square location to check.
     *
     * @param loc2  A square location to check.
     *
     * @return  Verify the given locations are empty spaces and
     *          no opponent pieces can get there.  That means both
     *          locations are not under attack by the opponent.
     */
    private boolean isEmptyAndNotUnderAttackBy(Piece.Colors attackByThisColor,
                                               String loc1, String loc2)
    {
        if ((! isEmpty(loc1)) || (! isEmpty(loc2)) ) return false;

        // Looking for any opponent pieces that can attack either
        // one of these two locations
        for (int col=0; col < columnCount; col++) {
            for (int row=0; row < rowCount; row++) {
                Piece aPiece = board[col][row];
                if ((! aPiece.isEmptySpace()) &&
                        (aPiece.getColor().equals(attackByThisColor)) ) {

                    for (FileRank fr : aPiece.getTargets()) {
                        if ((fr.equals(loc1)) || (fr.equals(loc2)) ) {
                            // The specified location is under attack
                            // by this opponent piece
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }



    private Piece getPiece(String loc)
    {
        // Return the game piece at this location
        FileRank fr = new FileRank(loc);
        return board[fr.getX()][fr.getY()];
    }


    private void moveRookForCastling(Piece aPiece, String toLoc)
    {
        if (! aPiece.getName().equals(Piece.PieceNames.king)) return;
        if ( aPiece.hasMoved()) return;
        //
        // This is the first move from this king.   Now check he
        // wants to do castling on which side.
        //
        if ((aPiece.getKingShortCastleLoc() != null) &&
                (aPiece.getKingShortCastleLoc().equals(toLoc)) ) {
            // This king is doing a short castling
            //
            String oldRookLoc;
            if ( aPiece.getColor().equals(Piece.Colors.White)) {
                oldRookLoc = "h1";
            }
            else {
                oldRookLoc = "h8";
            }

            // move the rook to the new castling location
            Piece aRook = getPiece(oldRookLoc);
            FileRank newRookLoc = aPiece.getRookShortCastleLoc();
            if ( aRook.moveTo(newRookLoc.getName()) ) {
                board[newRookLoc.getX()][newRookLoc.getY()] = aRook;
                drawEmptySpace(oldRookLoc);
            }
        }


        if ((aPiece.getKingLongCastleLoc() != null) &&
                (aPiece.getKingLongCastleLoc().equals(toLoc)) ) {
            // This king is doing a long castling
            //
            String oldRookLoc;
            if ( aPiece.getColor().equals(Piece.Colors.White)) {
                oldRookLoc = "a1";
            }
            else {
                oldRookLoc = "a8";
            }

            // move the rook to the new castling location
            Piece aRook = getPiece(oldRookLoc);
            FileRank newRookLoc = aPiece.getRookLongCastleLoc();
            if ( aRook.moveTo(newRookLoc.getName()) ) {
                board[newRookLoc.getX()][newRookLoc.getY()] = aRook;
                drawEmptySpace(oldRookLoc);
            }
        }
    }



    private boolean isStalemate()
    {
        Chess.gameState currentState = Chess.gGameState;

        if ( Chess.gGameState == gameState.whiteMove ) {
            Chess.gGameState = gameState.blackMove;
        }
        else if ( Chess.gGameState == gameState.blackMove ) {
            Chess.gGameState = gameState.whiteMove;
        }

        for (int col=0; col < columnCount; col++) {
            for (int row=0; row < rowCount; row++) {
                Piece aPiece = board[col][row];
                if (aPiece.getName().equals(Piece.PieceNames.emptySpace)) continue;

                if ( Chess.gGameState == gameState.whiteMove ) {
                    if (aPiece.getColor().equals(Piece.Colors.White)) {
                        if (hasLegalMove(aPiece)) {
                            // White still has at least one legal move.
                            Chess.gGameState = currentState;
                            return false;  // not in stalemate
                        }
                    }
                }
                else {
                    if (aPiece.getColor().equals(Piece.Colors.Black)) {
                        if (hasLegalMove(aPiece)) {
                            // Black still has at least one legal move.
                            Chess.gGameState = currentState;
                            return false;  // not in stalemate
                        }
                    }
                }
            }  //end of row
        }  //end of column

        Chess.gGameState = currentState;
        return true;
    }



    private boolean hasLegalMove(Piece aPiece)
    {
        FileRank fromLoc = aPiece.getFileRank();
        ArrayList<FileRank> toLocations = aPiece.getTargets();

        for (FileRank toLoc : toLocations) {
            Board aTestBoard = new Board(this);

            //System.out.println("hasLegalMove(): moving " + aPiece.toString() + " @" + aPiece.getLoc() +
            //					" to " + toLoc.getName() );
            if( aTestBoard.moveAndEvaluate(fromLoc.getName(), toLoc.getName()) ) {
                // This is a legal move
                return true;
            }
        }

        return false;
    }



/*
	private void explainWhoCheckedMe(String fromLoc, String toLoc)
	{
		String lookForThisKingLoc = whiteKingLoc.getName();
		Piece.Colors c = Piece.Colors.White;

		if ((Chess.gGameState == Chess.gameState.whiteMove) && (Chess.bWhiteInCheck)) {
			System.out.println("White king @" + whiteKingLoc.getName() +
					" is exposed after an illegal white move from " +
					fromLoc + " to " + toLoc);

			lookForThisKingLoc = whiteKingLoc.getName();
			c = Piece.Colors.Black;
		}

		if ((Chess.gGameState == Chess.gameState.blackMove) && (Chess.bBlackInCheck)) {
			System.out.println("Black king @" + blackKingLoc.getName() +
					" is exposed after an illegal black move from " +
					fromLoc + " to " + toLoc);

			lookForThisKingLoc = blackKingLoc.getName();
			c = Piece.Colors.White;
		}

		for (int row=0; row < rowCount; row++) {
			for (int col=0; col < columnCount; col++) {
				Piece aPiece = board[row][col];

				if (aPiece.getName().equals(Piece.PieceNames.emptySpace)) continue;
				if ( ! aPiece.getColor().equals(c)) continue;

				for (FileRank aLoc : aPiece.getTargets()) {
					if ( aLoc.getName().equals(lookForThisKingLoc)) {
						System.out.println("   king is checked by " + c + " " +
										aPiece.getName() + " @" + aPiece.getLoc());
					}
				}
			}
		}
		System.out.println();
	}
*/


}
