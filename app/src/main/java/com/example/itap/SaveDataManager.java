package com.example.itap;

import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SaveDataManager {
    private ArrayList<ScoreEntry> highscores;

    public void saveHighscoreMap(ArrayList<ScoreEntry> newHighscores) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("highscores.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(newHighscores);
            objectOutputStream.flush();
            objectOutputStream.close();
            //fileOutputStream.close();
            Log.i("SaveDataManager", "highscoreList has been saved in highscores.ser");

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ScoreEntry> loadHighscoreMap() {
        try {
            FileInputStream fileInputStream = new FileInputStream("highscores.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            this.highscores = (ArrayList) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            Log.i("SaveDataManager", "highscoreList has been loaded");

        } catch(IOException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException c) {
            c.printStackTrace();
        }
        if(highscores != null) {
            return highscores;
        } else {
            highscores = new ArrayList<>(10);
            return highscores;
        }
    }
}
