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




public class Page8 extends AppCompatActivity implements TextToSpeech.OnInitListener {


    private static final int REQ_CODE_SPEECH_INPUT = 100;
    Button Previouspage7, NextPage9, Read8;
    PDFView Page8;

    private MediaPlayer mp;
    static int Sentence = 0;
    String statement = "";
    String text;
    TextToSpeech tts;

    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;

    MediaPlayer pageturn;
    MediaPlayer Crocodile, elephant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page8);

        Page8 = (PDFView) findViewById(R.id.BookPage8);
        Page8.fromAsset("chapter8.pdf").load();

        tts = new TextToSpeech(this, this);

        NextPage9 = (Button) findViewById(R.id.NextPage9);
        Read8 = (Button) findViewById(R.id.read8);

        pageturn = MediaPlayer.create(this, R.raw.pageturn);
        Crocodile = MediaPlayer.create(this,R.raw.crocodile);
        elephant  = MediaPlayer.create(this,R.raw.elephant);

        pageturn.start();
        Crocodile.start();
        elephant.start();


        NextPage9.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Page8.this, Page9.class);
                startActivity(intent);

            }
        });

        Read8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startVoiceInput();
            }
        });

        Previouspage7 = (Button) findViewById(R.id.Previouspage7);

        Previouspage7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Page8.this, Page7.class);
                startActivity(intent);
                pageturn.start();
                Crocodile.stop();
                elephant.stop();
            }
        });


    }

    private void startVoiceInput() { // copy
        if (Sentence == 0) {
           // Sentence = 1;
            statement = "elephant and crocodile had the swimming competition without lion";
            //Log.d("TEST", "Flow 1");
        } else if (Sentence == 1) {
            //Sentence = 2;
            statement = "crocodile was quicker than elephant";
           // Log.d("TEST", "Flow 2");
        } else if (Sentence == 2) {
           // Sentence = 3;
            statement = "but each time crocodile got ahead";
            //Log.d("TEST", "Flow 3");
        }else if (Sentence == 3) {
            //Sentence = 4;
            statement = "elephant tickled him";
           // Log.d("TEST", "Flow 4");
        }else if (Sentence == 4){
            Sentence = 5;
            statement = "tee hee hee went elephant and crocodile";
            Log.d("TEST", "Flow 5");
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
                        /*Toast.makeText(Page8.this,
                                "Its A MATCH" + (result.get(0)), Toast.LENGTH_LONG).show(); */
                        AlertDialog alertDialog = new AlertDialog.Builder(Page8.this).create();
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
                                            Intent intent = new Intent(Page8.this, Page9.class);
                                            startActivity(intent);
                                            pageturn.start();
                                            Crocodile.start();
                                            elephant.start();
                                        }

                                    }
                                });
                        alertDialog.show();


                    } else if (!(result.get(0)).equals(statement)) {
                        Toast.makeText(Page8.this,
                                "Other Message " + (result.get(0)), Toast.LENGTH_LONG).show();
                        AlertDialog alertDialog = new AlertDialog.Builder(Page8.this).create();
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
                Log.d("Page8", "destroy");
            }
        }

    }


