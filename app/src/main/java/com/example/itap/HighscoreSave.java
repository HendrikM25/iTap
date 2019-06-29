package com.example.itap;

import android.widget.TextView;

public class HighscoreSave {
    private TextView name;
    private TextView score;

    public HighscoreSave(TextView name, TextView score) {
        this.name = name;
        this.score = score;
    }

    public void displayHighscoreSave(String name, int score) {
        this.name.setText(name);
        this.score.setText(score);
    }
}
