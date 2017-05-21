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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

import static com.android51.R.id.chessboard_gridView;
import static com.android51.R.id.turnText;


public class Replay extends AppCompatActivity {

    public Board board;
    private Piece currentPiece;
    private int currentIndex = -1;
    private String fromLoc = "";
    boolean bGameOver = false;
    private SavedGame currentGame;
    private ArrayList<String> movesList;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int currentMove = 0;
    private Chess.gameState gGameState;
    private GridView gridView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replay);

        Date date = new Date();
        movesList = new ArrayList<String>();
        movesList=getIntent().getExtras().getStringArrayList("moves");
        currentGame = new SavedGame(date, "", movesList);
        gridView = (GridView) findViewById(chessboard_gridView);
        textView = (TextView)findViewById(R.id.turnText);

        board = new Board(getApplicationContext());
        gGameState = Chess.gameState.whiteMove;  // White moves first

        gridView.setAdapter(new BoardImageAdapter(getApplicationContext(), board.getBoard(), gGameState));
        gridView.setNumColumns(8);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
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

    public void onNext(View view){

        if(currentMove<currentGame.getMoves().size()){
            currentMove+=1;
            board = new Board(this);
            for(int i = 0; i<currentMove;i++) {
                StringTokenizer st = new StringTokenizer(currentGame.getMoves().get(i));
                String move1 = st.nextToken();
                String move2 = st.nextToken();
                board.moveSimple(move1, move2);
            }
            gridView.setAdapter(new BoardImageAdapter(getApplicationContext(), board.getBoard(), gGameState));
        }
        else{
            Toast toast = Toast.makeText(this,"End of game",Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onPrev(View view){
        if(currentMove>0) {
            currentMove-=1;
            board = new Board(this);
            for(int i = 0; i<currentMove;i++) {
                StringTokenizer st = new StringTokenizer(currentGame.getMoves().get(i));
                String move1 = st.nextToken();
                String move2 = st.nextToken();
                board.moveSimple(move1, move2);
            }

            gridView.setAdapter(new BoardImageAdapter(getApplicationContext(), board.getBoard(), gGameState));
        }
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
     * @see  File
     * @see  BufferedReader
     * @see  ArrayList
     * @see  Scanner
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
                alertString = "Black wins";
                bGameOver = true;
                break;

            case blackResign:
                alertString = "White wins";
                bGameOver = true;
                break;

            case drawAccepted:
                alertString = "Draw!";
                bGameOver = true;
                break;

            case checkMate:
                if (bWhiteInCheck) {
                    saveGame("Black wins!");
                    //Intent myIntent = new Intent(Chess.this, HomeActivity.class);
                    //Chess.this.startActivity(myIntent);
                    return;
                } else {
                    saveGame("White wins!");
                    //Intent myIntent = new Intent(Chess.this, HomeActivity.class);
                    //Chess.this.startActivity(myIntent);
                    return;
                }
            case staleMate:
                alertString = "Stalemate";
                bGameOver = true;
                break;

            default:
                break;
        }

        AlertDialog alertDialog = new AlertDialog.Builder(Replay.this).create();
        alertDialog.setMessage(alertString);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if(bGameOver == true){
                            Intent myIntent = new Intent(Replay.this, HomeActivity.class);
                            Replay.this.startActivity(myIntent);
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

    public void saveGame(String s){
        ///Log.d("IN SAVEGAME()", "Got inside of saveGame()");
        final String winText = s;
        final File homeDir = getFilesDir();
        Log.d("CURRENT DIRECTORY", homeDir.toString());

        String alertText = "Please enter a title for the game to save it.";
        AlertDialog alert = new AlertDialog.Builder(Replay.this).create();
        alert.setTitle(s);
        alert.setMessage(alertText);
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setButton(AlertDialog.BUTTON_POSITIVE, "Save",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(input==null || input.toString() == " ") {
                            Toast.makeText(Replay.this, "Invalild Title!", Toast.LENGTH_LONG).show();
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
                            Intent myIntent = new Intent(Replay.this, HomeActivity.class);
                            Replay.this.startActivity(myIntent);
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

}