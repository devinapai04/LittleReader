package com.example.devina.littlereaders;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Welcome extends AppCompatActivity {
    MediaPlayer welcomeaudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        /*This method returns an instance of MediaPlayer class with
         'welcomesound' as the name of the song in the rew folder. */
        welcomeaudio = MediaPlayer.create(this, R.raw.welcomesound);
        welcomeaudio.start(); // Start playing the welcome sound
    }

    //Shows a book selection page once clicked.
    public void showbookselection(View view) {
        Intent intent = new Intent(Welcome.this, BookSelection.class);
        startActivity(intent);
    }

}

