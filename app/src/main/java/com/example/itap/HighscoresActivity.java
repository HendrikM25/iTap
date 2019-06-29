package com.example.itap;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class HighscoresActivity extends AppCompatActivity {

    ArrayList<ScoreEntry> highscores = new ArrayList<>(10);

    TextView nameRank1, nameRank2, nameRank3, nameRank4, nameRank5;
    TextView nameRank6, nameRank7, nameRank8, nameRank9, nameRank10;

    TextView scoreRank1, scoreRank2, scoreRank3, scoreRank4, scoreRank5;
    TextView scoreRank6, scoreRank7, scoreRank8, scoreRank9, scoreRank10;

    ArrayList<HighscoreSave> highscoreSaves = new ArrayList<>(10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        //get current highscores from MainActivity
        highscores = (ArrayList<ScoreEntry>) getIntent().getSerializableExtra("highscores");

        //log highscoreList
        Log.i("highscoreList", highscores.toString());

        //initialize textViews
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

        //save textViews in suitable highscoreSaves
        highscoreSaves.add(new HighscoreSave(nameRank1, scoreRank1));
        highscoreSaves.add(new HighscoreSave(nameRank2, scoreRank2));
        highscoreSaves.add(new HighscoreSave(nameRank3, scoreRank3));
        highscoreSaves.add(new HighscoreSave(nameRank4, scoreRank4));
        highscoreSaves.add(new HighscoreSave(nameRank5, scoreRank5));
        highscoreSaves.add(new HighscoreSave(nameRank6, scoreRank6));
        highscoreSaves.add(new HighscoreSave(nameRank7, scoreRank7));
        highscoreSaves.add(new HighscoreSave(nameRank8, scoreRank8));
        highscoreSaves.add(new HighscoreSave(nameRank9, scoreRank9));
        highscoreSaves.add(new HighscoreSave(nameRank10, scoreRank10));

        //display highscores
        for(int i = 0; i < highscores.size(); i++) {
            highscoreSaves.get(i).displayHighscoreSave(highscores.get(i).getName(), highscores.get(i).getScore());
        }


    }




}
