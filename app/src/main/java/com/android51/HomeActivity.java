package com.android51;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        File homeDir = getFilesDir();
        /*Log.d("HOME DIRECTORY", homeDir.toString());

        File file = new File (homeDir, "SavedGames");
        if(!file.exists()){
            file.mkdir();
            Log.d("SAVED GAME DIR", file.toString());
        } else {
            Log.d("File", file.toString());
            Log.d("MKDIR() FAIL", "Failed to create directory or already exists");
        }

        File[] files = homeDir.listFiles();
        for(int i=0; i < files.length; i++){
            Log.d("FILE DIR", files[i].getName());
        } */

    }

    public void toChess(View view){
        Intent myIntent = new Intent(HomeActivity.this, Chess.class);
        HomeActivity.this.startActivity(myIntent);
    }

    public void toRecord(View view){
        Intent myIntent = new Intent(HomeActivity.this, RecordActivity.class);
        HomeActivity.this.startActivity(myIntent);
    }

}
