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

public class Page9 extends AppCompatActivity  implements TextToSpeech.OnInitListener {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    Button Previouspage8, NextPage10, Read9;
    PDFView Page9;

    private MediaPlayer mp;
    static int Sentence = 0;
    String statement = "";
    String text;
    TextToSpeech tts;

    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;

    MediaPlayer pageturn;
    MediaPlayer Sadlion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page9);

        Page9 = (PDFView) findViewById(R.id.BookPage9);
        Page9.fromAsset("chapter9.pdf").load();

        tts = new TextToSpeech(this, this);


        NextPage10 = (Button) findViewById(R.id.NextPage10);
        Read9 = (Button) findViewById(R.id.read9);

        pageturn = MediaPlayer.create(this, R.raw.pageturn);
        Sadlion  = MediaPlayer.create(this, R.raw.sadlion);

        pageturn.start();
        Sadlion.start();

        NextPage10.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Page9.this, Page10.class);
                startActivity(intent);

            }
        });

        Read9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startVoiceInput();
            }
        });

        Previouspage8 = (Button) findViewById(R.id.Previouspage8);

        Previouspage8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Page9.this, Page8.class);
                startActivity(intent);
                pageturn.start();
                Sadlion.stop();
            }
        });


    }

    private void startVoiceInput() { // copy
        if (Sentence == 0) {
           // Sentence = 1;
            statement = "lion sat sadly by himself watching crocodile and elephant swim";
           // Log.d("TEST", "Flow 1");
        } else if (Sentence == 1) {

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
                      /*  Toast.makeText(Page9.this,
                                "Its A MATCH" + (result.get(0)), Toast.LENGTH_LONG).show(); */
                        AlertDialog alertDialog = new AlertDialog.Builder(Page9.this).create();
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
                                            Intent intent = new Intent(Page9.this, Page10.class);
                                            startActivity(intent);
                                            pageturn.start();
                                            Sadlion.start();

                                        }
                                    }
                                });
                        alertDialog.show();


                    } else {
                        Toast.makeText(Page9.this,
                                "Other Message " + (result.get(0)), Toast.LENGTH_LONG).show();
                        AlertDialog alertDialog = new AlertDialog.Builder(Page9.this).create();
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
            Log.d("Page9", "destroy");
        }
    }

}

