package com.example.devina.littlereaders;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.barteksc.pdfviewer.PDFView; // Source : https://github.com/barteksc/AndroidPdfViewer



public class Page1 extends AppCompatActivity {

    Button NextPage2;
    PDFView Page1;
    private MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1); // Sets the View to be displayed as the main content view.

        Page1 = (PDFView) findViewById(R.id.BookPage1); // Gives the id of the specific view 'BookPage1' which is defined in the xml file.

        final MediaPlayer Lion = MediaPlayer.create(Page1.this, R.raw.lion);
        final MediaPlayer Rat = MediaPlayer.create(Page1.this, R.raw.rat);


        Lion.start();
        final Handler Rathandler = new Handler();
        Rathandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Rat.start();
            }
        }, 1500);

        Page1.fromAsset("chapter1.pdf").load(); // Opens the first page of pdf from the assets folder




         // Plays a sound when the next page button is clicked.
        NextPage2 = (Button) findViewById(R.id.NextPage2);
        final MediaPlayer pageturn = MediaPlayer.create(Page1.this, R.raw.pageturn);

        //Links to the next page
        NextPage2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Page1.this, Page2.class);
                startActivity(intent);
                pageturn.start();
                pageturn.setVolume(100, 100);

                // The lion and rat sounds stop if still playing once the next button is clicked.
                Lion.stop();
                Rat.stop();
            }
        });


    }

    private void stopPlaying() {
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }
    }

}


