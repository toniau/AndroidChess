package com.android51;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.nio.channels.FileLockInterruptionException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static java.lang.Integer.parseInt;

/**
 * Created by Toni on 4/29/2017.
 */

public class RecordActivity extends AppCompatActivity implements Serializable{

    public ArrayList<SavedGame> gameList;
    GameAdapter adapter;
    private ListView gameView;
    private File homeDir;
    private static final long serialVersionUID = 1L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        homeDir = getFilesDir();
        gameList = new ArrayList<SavedGame>();
        gameList = getSavedGames();
        adapter = new GameAdapter(this, gameList);
        gameView = (ListView) findViewById(R.id.game_list);
        gameView.setAdapter(adapter);
        gameView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(RecordActivity.this, Replay.class);
                myIntent.putStringArrayListExtra("moves", gameList.get(position).getMoves());
                // PUT IN EXTRA HERE <----------
                RecordActivity.this.startActivity(myIntent);
            }
        });
        Log.d("GAMES ARRAY SIZE", Integer.toString(gameList.size()));
        /*ArrayList<String> tmpArray = new ArrayList<String>();
        tmpArray.add("e2 e4");
        SavedGame tmp = new SavedGame("03/11/2013 07:22:44", "MySecondGame", tmpArray);
        adapter.add(tmp);
        Log.d("GAMES ARRAY", gameList.toString());*/
    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            // Sort list by Date
            case R.id.radio_date:
                if (checked){
                    Collections.sort(gameList, SavedGame.DateComparator);
                    adapter = new GameAdapter(this, gameList);
                    gameView = (ListView) findViewById(R.id.game_list);
                    gameView.setAdapter(adapter);
                    break;
                }
                // Sort list by Title
            case R.id.radio_title:
                if (checked) {
                    Collections.sort(gameList, SavedGame.TitleComparator);
                    adapter = new GameAdapter(this, gameList);
                    gameView = (ListView) findViewById(R.id.game_list);
                    gameView.setAdapter(adapter);
                    break;
                }
        }
    }

    /*public ArrayList<SavedGame> sortByDate(ArrayList<SavedGame> gameList) throws ParseException{
        ArrayList<SavedGame> sortedDates = new ArrayList<SavedGame>();
        Date tmp;
        for(int i=0; i < gameList.size(); i++){
            String date = gameList.get(i).getDate();
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            for(int j=i; j > 0; j--){
                String dateA = gameList.get(j).getDate();
                Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(dateA);
                String dateB = gameList.get(j-1).getDate();
                Date date3 = new SimpleDateFormat("yyyy-MM-dd").parse(dateB);
                if(date2.compareTo(date3) > 0){
                    tmp = date2;
                    gameList.get(j) = gameList.get(j-1);
                    gameList.get(j-1) = tmp;

                }
            }
        }
        return sortedDates;
    }*/

    /**
     * Load saved games to ListView
     */
    public ArrayList<SavedGame> getSavedGames(){
        File[] files = homeDir.listFiles();
        Context c = getApplicationContext();
        ArrayList<SavedGame> myGames = new ArrayList<SavedGame>();

        for(int i=0; i < files.length; i++){
            Log.d("MY FILE DIR", files[i].getName());
            SavedGame gameObj = deserialize(c, files[i].getName());
            myGames.add(gameObj);
        }

        return myGames;
    }

    public SavedGame deserialize(Context context, String file) {
        SavedGame gameObj = null;
        try {
            FileInputStream fileIn = context.openFileInput(file);
            Log.d("FILE IN STREAM", "fileIn Passed Through");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            //Log.d("OBJ IN STREAM", "OIS Passed");
            gameObj = (SavedGame) in.readObject();
            Log.d("READ OBJ", "Read In gameObj");
            in.close();
            fileIn.close();
            Log.d("DE-SERIAL", "Read Serialization Success!");
        } catch (Exception e) {
            Log.d("DE-SERIAL FAIL", "Deserialization Fail!");
            e.printStackTrace();
        }
        return gameObj;
    }

    /**
     * Play back this specific game
     * Switch back to Chess Activity
     * Send back extra containing ArrayList with all the moves
     * @param view
     */
    public void playGame(ArrayList<String> moves){

    }


}
