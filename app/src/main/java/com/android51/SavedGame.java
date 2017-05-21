package com.android51;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import android.content.Context;
import android.util.Log;

/**
 * Created by Toni on 4/30/2017.
 */

public class SavedGame implements Serializable{

    private String title = "";
    private Date date;
    private ArrayList<String> moves;
    private static final long serialVersionUID = 1L;


    public SavedGame(Date gameDate, String gameTitle, ArrayList<String> gameMoves){
        date = gameDate;
        title = gameTitle;
        moves = gameMoves;
    }

    public String getTitle(){
        return title;
    }

    public Date getDate() {
        return date;
    }

    public ArrayList<String> getMoves() {return moves; }

    // DON'T FORGET IF YOU UNDO A MOVE, YOU REMOVE IT FROM THE LIST AS WELL
    public void addMoves(String fromLoc, String toLoc){
        String oneMove = fromLoc + " " + toLoc;
        moves.add(oneMove);
    }

    public void setTitle(String t){
        title = t;
    }

    public void setMoves(ArrayList<String> m){
        moves = m;
    }

    public void serialize(Context context, File homeDir, String userInput){

        try {
            FileOutputStream fileOut = context.openFileOutput(userInput, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            Log.d("SERIAL PASS", "Serialization Success!");
        } catch(Exception e) {
            Log.d("SERIAL FAIL", "Invalid serialization!");
        }
    }

    /* Comparator for sorting games by DATE */
    public static Comparator<SavedGame> DateComparator = new Comparator<SavedGame>(){
        @Override
        public int compare(SavedGame s1, SavedGame s2){
            Date date1 = s1.getDate();
            Date date2 = s2.getDate();

            return date1.compareTo(date2);
        }
    };

    /* Comparator for sorting games by TITLE */
    public static Comparator<SavedGame> TitleComparator = new Comparator<SavedGame>() {
        @Override
        public int compare(SavedGame s1, SavedGame s2) {
            String title1 = s1.getTitle().toUpperCase();
            String title2 = s2.getTitle().toUpperCase();

            return title1.compareTo(title2);
        }
    };

    /**
     * Write out three data points to a file
     */
    public void writeObject(){}

    /**
     * Read file from internal storage and re-populate
     * 3 internal variables for SavedGame
     */
    public void readObject(){}
}
