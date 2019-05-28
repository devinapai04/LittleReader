package com.example.devina.littlereaders;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.barteksc.pdfviewer.PDFView;

public class BookSelection extends AppCompatActivity {

    Button Bookbutton;
    PDFView FirstPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) { // Initialize the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookselection); // Sets the View to be displayed as the main content view.

        Bookbutton = (Button) findViewById(R.id.Buttontoselectbook); // Gives the id of the specific view 'Buttontoselectbook' which is defined in the xml file.


        Bookbutton.setOnClickListener(new View.OnClickListener() {// Assigned onclicklistener to Bookbutton
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BookSelection.this, Page1.class); //
                startActivity(intent); // This method sends the intent to the Android system, which launches the Page1 class.


                final Handler Lionhandler = new Handler(); // Creating handler LionHandler
                Lionhandler.postDelayed(new Runnable() { // Executes a Runnable task on the UIThread after a delay

                    @Override
                    public void run() {
                        MediaPlayer Lion = MediaPlayer.create(BookSelection.this, R.raw.lion);// Play sound 'lion' which is stored in the raw folder
                        //Lion.start();
                    }

                }, 1500); // setting the delay time


                final Handler Rathandler = new Handler(); // Creating a new handler RatHandler
                Rathandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MediaPlayer Rat = MediaPlayer.create(BookSelection.this, R.raw.rat);// Play the rat sound from the raw folder
                        //Rat.start();
                    }
                }, 3000); // setting the delay time
            }
        });
    }
}