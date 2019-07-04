package com.example.itap;

import java.io.Serializable;

public class ScoreEntry implements Serializable {

    private String name;
    private int score;

    ScoreEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }
}
