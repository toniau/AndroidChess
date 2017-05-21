package com.android51;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;


import static com.android51.R.id.chessboard_gridView;
import static com.android51.R.id.turnText;


public class Chess extends AppCompatActivity {

    public Board board;
    private Piece currentPiece;
    private int currentIndex = -1;
    private String fromLoc = "";
    boolean bGameOver = false;
    private SavedGame currentGame;
    private ArrayList<String> movesList;
    private String lastMove;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chess);

        Date date = new Date();
        movesList = new ArrayList<String>();
        currentGame = new SavedGame(date, "", movesList);

        final GridView gridView = (GridView) findViewById(chessboard_gridView);
        final TextView textView = (TextView)findViewById(R.id.turnText);

        board = new Board(getApplicationContext());
        gGameState = gameState.whiteMove;  // White moves first

        gridView.setAdapter(new BoardImageAdapter(getApplicationContext(), board.getBoard(), gGameState));
        gridView.setNumColumns(8);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);

        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // no currently selected piece
                if (currentIndex == -1) {
                    currentIndex = position;
                    fromLoc = coordinatesToString(position);
                }

                // clicked again on currently selected piece
                else if (currentIndex == position) {
                    currentIndex = -1;
                    fromLoc = "";
                }

                /**
                 * There is a square on the piece, and there is a currently selected piece
                 * Initiate move()
                 */
                else {
                    if(board.move(fromLoc,coordinatesToString(position))){
                        //currentGame.addMoves(fromLoc, coordinatesToString(position));
                        movesList.add(fromLoc + " " + coordinatesToString(position));
                        lastMove = fromLoc + " " + coordinatesToString(position);
                        Log.d("CurrentGameMoves", currentGame.getMoves().toString());
                        currentIndex = -1;
                        fromLoc = "";

                        if (gGameState == gameState.whiteMove || gGameState == gameState.blackMove) {
                            //
                            // This game will continue to alternate turns
                            // if it is not ended in "checkmate".
                            //
                            if ( gGameState == gameState.whiteMove ) {
                                gGameState = gameState.blackMove;
                            }
                            else if ( gGameState == gameState.blackMove ) {
                                gGameState = gameState.whiteMove;
                            }
                        }
                        textView.setText(gGameState.toString());
                        alertGameState();
                    }
                    else{
                        AlertDialog alertDialog = new AlertDialog.Builder(Chess.this).create();
                        alertDialog.setMessage("Invalid move!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                        fromLoc="";
                        currentIndex=-1;
                    }
                }

                // refresh board (there's a better way to do this than re-setting the adapter, fix if we have time)
                gridView.setAdapter(new BoardImageAdapter(getApplicationContext(), board.getBoard(), gGameState));

            }
        });
    }


    private String coordinatesToString(int position){
        String location = "";
        int x = position%8;
        int y = 8-position/8;
        switch(x) {
            case 0: location="a";
                break;
            case 1: location="b";
                break;
            case 2: location="c";
                break;
            case 3: location="d";
                break;
            case 4: location="e";
                break;
            case 5: location="f";
                break;
            case 6: location="g";
                break;
            case 7: location="h";
                break;
            default:
                break;
        }
        location = location + y;
        return location;
    }


    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();

    }

    public void onResign(View view){
        if (gGameState == gameState.whiteMove) {
            gGameState = gameState.whiteResign;
        } else {
            gGameState = gameState.blackResign;
        }
        alertGameState();
    }

    public void onDraw(View view){
        String alertText = "";
        final gameState nextGameState;
        if(gGameState == gameState.whiteMove){
            alertText = "White has offered a draw. Do you accept?";
            nextGameState = gameState.blackMove;
        }
        else {
            alertText = "Black has offered a draw. Do you accept?";
            nextGameState = gameState.whiteMove;
        }
        AlertDialog alertDialog = new AlertDialog.Builder(Chess.this).create();
        alertDialog.setMessage(alertText);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        gGameState = gameState.drawAccepted;
                        dialog.dismiss();
                        alertGameState();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        gGameState = nextGameState;
                        dialog.dismiss();
                        alertGameState();
                    }
                });
        alertDialog.show();
    }

    /**
     * Perform a random move for whoever's turn it is
     */
    public void onAI(View view){
        ArrayList<Piece> colorPieces = new ArrayList<Piece>();

        if(gGameState == gameState.whiteMove){
            for(Piece piece[] : board.getBoard()){
                for(Piece piece2 : piece){
                    if(piece2.getColor() == Piece.Colors.White && !piece2.isEmptySpace() && piece2.getTargets().size()>0)
                        colorPieces.add(piece2);
                }
            }
        }
        else{
            for(Piece piece[] : board.getBoard()){
                for(Piece piece2 : piece){
                    if(piece2.getColor() == Piece.Colors.Black && !piece2.isEmptySpace() && piece2.getTargets().size()>0)
                        colorPieces.add(piece2);
                }
            }
        }
        Random randomGenerator = new Random();

        //retrieve random piece
        int index = randomGenerator.nextInt(colorPieces.size());
        Piece piece3 = colorPieces.get(index);
        String tmp = piece3.getLoc();
        Log.d("piece3 getLoc", piece3.getLoc());
        Log.d("piece3 name", piece3.getName().toString());
        Log.d("piece3 x",piece3.getTargets().toString());
        if(piece3.getTargets().size()>0){
            Log.d("entered if","yes");
            index = randomGenerator.nextInt(piece3.getTargets().size());
            FileRank fileRank = piece3.getTargets().get(index);
            Log.d("fileRank getIndex",piece3.getTargets().get(index).toString());
            Log.d("fileRank getName", fileRank.getName());
            board.move(piece3.getLoc(),fileRank.getName());
            movesList.add(piece3.getLoc()+ " " +  fileRank.getName());
            lastMove = tmp + " " +  fileRank.getName();
            Log.d("LAST MOVE", lastMove);
            if (gGameState == gameState.whiteMove || gGameState == gameState.blackMove) {
                //
                // This game will continue to alternate turns
                // if it is not ended in "checkmate".
                //
                if ( gGameState == gameState.whiteMove ) {
                    gGameState = gameState.blackMove;
                }
                else if ( gGameState == gameState.blackMove ) {
                    gGameState = gameState.whiteMove;
                }
            }
            TextView textView = (TextView) findViewById(turnText);
            textView.setText(gGameState.toString());
            alertGameState();
        }
        GridView gridView = (GridView) findViewById(chessboard_gridView);
        gridView.setAdapter(new BoardImageAdapter(getApplicationContext(), board.getBoard(), gGameState));

    }

    public void onUndo(View view){
        // Get move undergoing UNDO action
        if(lastMove.isEmpty()){
            Toast toast = Toast.makeText(this,"Can only undo previous move",Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        Log.d("LAST MOVE", lastMove);
        final String[] moveFragments = lastMove.split(" ");
        Log.d("MOVE FRAGMENTS", moveFragments[0] + " " + moveFragments[1]);

        // Throw alert to confirm undo, cannot be undone afterwards
        String alertText = "Please confirm undo action, cannot be undone afterwards";
        AlertDialog alert = new AlertDialog.Builder(Chess.this).create();
        alert.setTitle("Confirm Undo");
        alert.setMessage(alertText);

        alert.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (gGameState == gameState.whiteMove || gGameState == gameState.blackMove) {
                    if (gGameState == gameState.whiteMove) {
                        gGameState = gameState.blackMove;
                    } else if (gGameState == gameState.blackMove) {
                        gGameState = gameState.whiteMove;
                    }
                }
                board.moveSimple(moveFragments[1], moveFragments[0]);
                TextView textView = (TextView) findViewById(turnText);
                textView.setText(gGameState.toString());
                GridView gridView = (GridView) findViewById(chessboard_gridView);
                gridView.setAdapter(new BoardImageAdapter(getApplicationContext(), board.getBoard(), gGameState));

                // Remove move from moves ArrayList
                int i = movesList.size();
                movesList.remove(i-1);
                lastMove = "";
                Log.d("CurrentGameMoves", currentGame.getMoves().toString());
            }
        });

        alert.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
        });

        alert.show();
    }


    public void saveGame(String s){
        ///Log.d("IN SAVEGAME()", "Got inside of saveGame()");
        final String winText = s;
        final File homeDir = getFilesDir();
        Log.d("CURRENT DIRECTORY", homeDir.toString());

        String alertText = "Please enter a title for the game to save it.";
        AlertDialog alert = new AlertDialog.Builder(Chess.this).create();
        alert.setTitle(s);
        alert.setMessage(alertText);
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setButton(AlertDialog.BUTTON_POSITIVE, "Save",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(input==null || input.toString() == " ") {
                            Toast.makeText(Chess.this, "Invalild Title!", Toast.LENGTH_LONG).show();
                            saveGame(winText);
                        } else {
                            String userInput = input.getText().toString();
                            currentGame.setTitle(userInput);
                            for(int i=0; i < movesList.size(); i++){
                                Log.d("MOVES LIST", movesList.get(i));
                            }
                            currentGame.setMoves(movesList);
                            Log.d("GAME TITLE", userInput);
                            Context c = getApplicationContext();
                            currentGame.serialize(c, homeDir, userInput);
                            Intent myIntent = new Intent(Chess.this, HomeActivity.class);
                            Chess.this.startActivity(myIntent);
                        }
                    }
                });
        alert.setButton(AlertDialog.BUTTON_NEGATIVE, "Discard",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        alert.show();
    }


    /**
     * <p>A non-graphical character-based chess game.
     * The "main" function is defined in this class.
     *
     * <p>
     * To run this chess program:
     * <p>
     * &nbsp;&nbsp;&nbsp; java chess.Chess &nbsp;  &lt;optional playbackFileName&gt;
     * <p>
     *
     *
     * @author Toni Au, Sean Wu
     * @version 1.0
     *
     * @see  java.io.File
     * @see  java.io.BufferedReader
     * @see  java.util.ArrayList
     * @see  java.util.Scanner
     *
     */


    /**
     *
     * Enumerated states of a chess game
     *
     */
    public static enum gameState {
        /**
         *   White's turn to move
         */
        whiteMove,

        /**
         *   Black's turn to move
         */
        blackMove,

        /**
         *   White resigned from the game
         */
        whiteResign,

        /**
         *  Black resigned from the game
         */
        blackResign,

        /**
         *   Accepted a draw proposal offered by opponent during the previous turn
         */
        drawAccepted,

        /**
         *   The current side who is due to make a move is in checkmate.
         *   Therefore, the opposing side has won this chess game.
         */
        checkMate,

        /**
         *   There is no legal move in this turn for the current side.  However,
         *   the current side is not under a check either.  Therefore, this
         *   created a "stalemate" in the chess game.
         */
        staleMate};


    /**
     *  Current state of this chess game.  White always move first.
     */
    public static gameState gGameState = gameState.whiteMove;


    /**
     *  Enumerated pawn promotion choices
     *
     */
    public static enum pawnPromotionChoices {
        /**
         *  Promote a pawn to a rook
         */
        rook,

        /**
         *  Promote a pawn to a knight
         */
        knight,

        /**
         *  Promote a pawn to a bishop
         */
        bishop,

        /**
         *  Promote a pawn to a queen
         */
        queen };


    /**
     *   A pawn is promoted to another piece when it reaches the last rank.
     *   There are four promotion choices: rook, knight, bishop, or queen.
     *   By default, a pawn is promoted to a new queen.
     *
     */
    public static pawnPromotionChoices  pawnPromotionChoice = pawnPromotionChoices.queen;

    /**
     *  Set to "true" if white is in check
     */
    public static boolean bWhiteInCheck = false;

    /**
     *  Set to "true" if black is in check
     */
    public static boolean bBlackInCheck = false;

    /**
     *  Set to "true" if one side offered a "draw" to his opponent
     */
    public static boolean bDrawOffered = false;

    private static ArrayList<String> movements = new ArrayList<String>();



    private void alertGameState() {
        bGameOver = false;
        String alertString = new String();
        switch (gGameState) {
            case whiteMove:
                alertString = "White's move: ";
                break;

            case blackMove:
                alertString = "Black's move: ";
                break;

            case whiteResign:
                saveGame("Black wins!");
                return;

            case blackResign:
                saveGame("White wins!");
                return;

            case drawAccepted:
                saveGame("Draw!");
                return;

            case checkMate:
                if (bWhiteInCheck) {
                    saveGame("Black wins!");
                    return;
                } else {
                    saveGame("White wins!");
                    return;
                }
            case staleMate:
                saveGame("Stalemate!");
                return;

            default:
                break;
        }

        AlertDialog alertDialog = new AlertDialog.Builder(Chess.this).create();
        alertDialog.setMessage(alertString);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if(bGameOver == true){
                            Intent myIntent = new Intent(Chess.this, HomeActivity.class);
                            Chess.this.startActivity(myIntent);
                        }
                    }
                });
        alertDialog.show();
    }


    private static String getNextMove(Scanner keyboardInput) {
        String nextMove = "";

        if (!movements.isEmpty()) {
            nextMove = movements.remove(0);
            System.out.println(nextMove);
        } else {
            nextMove = keyboardInput.nextLine();
        }

        System.out.println();
        return nextMove;
    }


    /*
     * Read each line from the input file and store the line
     * in an ArrayList of type String.
     */
    private static void readInputFile(final String fileName) {
        movements.clear();
        if (fileName.isEmpty()) return;

        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = in.readLine()) != null) {
                if (!line.isEmpty()) movements.add(line);
            }
            in.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}