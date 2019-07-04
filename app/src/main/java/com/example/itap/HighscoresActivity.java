package com.example.itap;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class HighscoresActivity extends AppCompatActivity {

    TextView nameRank1, nameRank2, nameRank3, nameRank4, nameRank5;
    TextView nameRank6, nameRank7, nameRank8, nameRank9, nameRank10;

    TextView scoreRank1, scoreRank2, scoreRank3, scoreRank4, scoreRank5;
    TextView scoreRank6, scoreRank7, scoreRank8, scoreRank9, scoreRank10;

    ArrayList<TextView> nameViews = new ArrayList<>(10);
    ArrayList<TextView> scoreViews = new ArrayList<>(10);

    ArrayList<ScoreEntry> highscores;

    //SaveDataManager saveDataManager = new SaveDataManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        //load highscore list from internal storage to highscores
        //highscores = saveDataManager.loadHighscoreMap();
        loadData();

        //initialize textviews
        nameRank1 = findViewById(R.id.rank1Name);
        nameRank2 = findViewById(R.id.rank2Name);
        nameRank3 = findViewById(R.id.rank3Name);
        nameRank4 = findViewById(R.id.rank4Name);
        nameRank5 = findViewById(R.id.rank5Name);
        nameRank6 = findViewById(R.id.rank6Name);
        nameRank7 = findViewById(R.id.rank7Name);
        nameRank8 = findViewById(R.id.rank8Name);
        nameRank9 = findViewById(R.id.rank9Name);
        nameRank10 = findViewById(R.id.rank10Name);

        scoreRank1 = findViewById(R.id.rank1Score);
        scoreRank2 = findViewById(R.id.rank2Score);
        scoreRank3 = findViewById(R.id.rank3Score);
        scoreRank4 = findViewById(R.id.rank4Score);
        scoreRank5 = findViewById(R.id.rank5Score);
        scoreRank6 = findViewById(R.id.rank6Score);
        scoreRank7 = findViewById(R.id.rank7Score);
        scoreRank8 = findViewById(R.id.rank8Score);
        scoreRank9 = findViewById(R.id.rank9Score);
        scoreRank10 = findViewById(R.id.rank10Score);

        nameViews.add(0, nameRank1);
        nameViews.add(1, nameRank2);
        nameViews.add(2, nameRank3);
        nameViews.add(3, nameRank4);
        nameViews.add(4, nameRank5);
        nameViews.add(5, nameRank6);
        nameViews.add(6, nameRank7);
        nameViews.add(7, nameRank8);
        nameViews.add(8, nameRank9);
        nameViews.add(9, nameRank10);

        scoreViews.add(0, scoreRank1);
        scoreViews.add(1, scoreRank2);
        scoreViews.add(2, scoreRank3);
        scoreViews.add(3, scoreRank4);
        scoreViews.add(4, scoreRank5);
        scoreViews.add(5, scoreRank6);
        scoreViews.add(6, scoreRank7);
        scoreViews.add(7, scoreRank8);
        scoreViews.add(8, scoreRank9);
        scoreViews.add(9, scoreRank10);


        //insert highscores in textviews
        for(int i = 0; i < highscores.size(); i++) {
            nameViews.get(i).setText(highscores.get(i).getName());
            String temp = String.valueOf(highscores.get(i).getScore());
            scoreViews.get(i).setText(temp);
        }
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("highscores", null);
        Type type = new TypeToken<ArrayList<ScoreEntry>>() {}.getType();
        highscores = gson.fromJson(json, type);

        if (highscores == null) {
            highscores = new ArrayList<>(10);
        }
    }
}
