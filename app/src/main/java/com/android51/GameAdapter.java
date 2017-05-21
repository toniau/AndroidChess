package com.android51;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Toni on 5/2/2017.
 */

public class GameAdapter extends ArrayAdapter<SavedGame> implements Serializable{

    public static int gameIndex;
    private Activity activity;
    private static final long serialVersionUID = 1L;

    public GameAdapter(Context context, ArrayList<SavedGame> gameList){
        super(context, 0, gameList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Get data item for this position
        SavedGame game = getItem(position);

        // Check if existing view is being reused, otherwise inflate the view
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_game, parent, false);
        }

        // Look up view for data population of list view
        TextView game_title = (TextView) convertView.findViewById(R.id.game_title);
        TextView game_date = (TextView) convertView.findViewById(R.id.game_date);

        /*btn_play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int position = (Integer) view.getTag();
                SavedGame tmp = getItem(position);
                // this grabs the position of which button was clicked
                // use this to determine which game to play
                // unsure where to grab gameList.getMoves();
                // either here or RecordActivity, should be RecordActivity I thinnk doe

                getActivity().playGame(view);
            }

        });*/

        // Populate data into template view using data object
        game_title.setText(game.getTitle());

        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = formatter.format(game.getDate());
        game_date.setText(s);

        return convertView;
    }
}
