package com.example.devina.littlereaders;


import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.util.ArrayList;
import java.util.Locale;

public class Page11 extends AppCompatActivity  implements TextToSpeech.OnInitListener {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    Button Previouspage10, NextPage12, Read11;
    PDFView Page11;

    private MediaPlayer mp;
    static int Sentence = 0;
    String statement = "";
    String text;
    TextToSpeech tts;

    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;

    MediaPlayer pageturn;
    MediaPlayer Cartoonwalk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page11);

        Page11 = (PDFView) findViewById(R.id.BookPage11);
        Page11.fromAsset("chapter11.pdf").load();

        tts = new TextToSpeech(this, this);


        NextPage12 = (Button) findViewById(R.id.NextPage12);
        Read11 = (Button) findViewById(R.id.read11);

        pageturn = MediaPlayer.create(this, R.raw.pageturn);
        Cartoonwalk = MediaPlayer.create(this, R.raw.cartoonwalking);

        pageturn.start();
        Cartoonwalk.start();

        NextPage12.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Page11.this, Page12.class);
                startActivity(intent);

            }
        });

        Read11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startVoiceInput();
            }
        });

        Previouspage10 = (Button) findViewById(R.id.Previouspage10);

        Previouspage10.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Page11.this, Page10.class);
                startActivity(intent);
                pageturn.start();
                Cartoonwalk.stop();
            }
        });


    }

    private void startVoiceInput() { // copy
        if (Sentence == 0) {
        } else if (Sentence == 1) {
           // Sentence = 2;
            //Log.d("TEST", "Flow 1");
        }
        tts.setLanguage(Locale.US);
        tts.speak(statement, TextToSpeech.QUEUE_FLUSH, null);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please say " + statement);
                startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
            }
        }, 5000);



    }

    @Override //copy
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);


                    Log.d("statement", result.get(0));
                    Log.d("statement", statement);
                    Log.d("sentence", String.valueOf(Sentence));

                    if ((result.get(0)).equals(statement)) {
                       /* Toast.makeText(Page11.this,
                                "Its A MATCH" + (result.get(0)), Toast.LENGTH_LONG).show(); */
                        AlertDialog alertDialog = new AlertDialog.Builder(Page11.this).create();
                        alertDialog.setTitle("Congratulations");
                        alertDialog.setMessage(" Very Goood ");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        if (Sentence == 0) {
                                            Sentence = 1;
                                            startVoiceInput();
                                        } else if (Sentence == 1) {
                                            // Sentence = 0;
                                            Intent intent = new Intent(Page11.this, Page12.class);
                                            startActivity(intent);
                                            pageturn.start();
                                            Cartoonwalk.start();

                                        }
                                    }
                                });
                        alertDialog.show();


                    } else {
                        Toast.makeText(Page11.this,
                                "Other Message " + (result.get(0)), Toast.LENGTH_LONG).show();
                        AlertDialog alertDialog = new AlertDialog.Builder(Page11.this).create();
                        alertDialog.setTitle("Ooops !!");
                        alertDialog.setMessage("Lets Try again !!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        startVoiceInput();
                                    }
                                });
                        alertDialog.show();
                    }
                }
            }
        }
    }
    private void stopPlaying() {
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }
    }


    @Override
    public void onInit(int status) {

    }


    @Override
    protected void onStop() {
        super.onStop();
        if (speech != null) {
            speech.destroy();
            Log.d("Page11", "destroy");
        }
    }

}
