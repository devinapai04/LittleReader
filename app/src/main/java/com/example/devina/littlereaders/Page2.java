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


public class Page2 extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    Button Previouspage1, NextPage3, Read2;
    PDFView Page2;


    private MediaPlayer mp;
    static int Sentence = 0;
    String statement = "";
    String text;
    TextToSpeech tts;

    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;

    MediaPlayer pageturn;
    MediaPlayer Cheetah;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

        Page2 = (PDFView) findViewById(R.id.BookPage2);
        Page2.fromAsset("chapter2.pdf").load();

        tts = new TextToSpeech(this, this);

        NextPage3 = (Button) findViewById(R.id.NextPage3);
        Read2 = (Button) findViewById(R.id.read2);

        pageturn = MediaPlayer.create(this, R.raw.pageturn);
        Cheetah = MediaPlayer.create(Page2.this, R.raw.cheetah);

        NextPage3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Page2.this, Page3.class);
                startActivity(intent);
                pageturn.start();
                Cheetah.start();
            }
        });

        Read2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startVoiceInput();
            }
        });


        Previouspage1 = (Button) findViewById(R.id.Previouspage1);

        Previouspage1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Page2.this, Page1.class);
                startActivity(intent);
                pageturn.start();
                Cheetah.stop();
                //Cheetah.start();

            }
        });

    }

    private void startVoiceInput() { // copy

        if (Sentence == 0) {
            statement = "it was a sunny day in the jungle";
        } else if (Sentence == 1) {
            statement = "all the animals were out playing";
        }

        tts.setLanguage(Locale.US);
        //String myText1 = "It was a sunny day in the Jungle";
        //String myText2 = "All the animals were out playing";
        tts.speak(statement, TextToSpeech.QUEUE_FLUSH, null);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please say " + statement);
                startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
                //speech.startListening(recognizerIntent);
            }
        }, 5000);


    }

    /**
     * Receiving speech input
     */

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
                       /* Toast.makeText(Page2.this,
                                "Its A MATCH" + (result.get(0)), Toast.LENGTH_LONG).show();*/
                        //Log.d("statement",result.get(0));
                        AlertDialog alertDialog = new AlertDialog.Builder(Page2.this).create();
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
                                            Intent intent = new Intent(Page2.this, Page3.class);
                                            startActivity(intent);
                                            pageturn.start();
                                            Cheetah.start();
                                        }

                                    }
                                });
                        alertDialog.show();


                    } else if (!(result.get(0)).equals(statement)) {
                        Toast.makeText(Page2.this,
                                "Other Message " + (result.get(0)), Toast.LENGTH_LONG).show();

                        AlertDialog alertDialog = new AlertDialog.Builder(Page2.this).create();
                        alertDialog.setTitle("Ooops !!");
                        alertDialog.setMessage("Lets Try again, Repeat after me !!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        startVoiceInput();
                                      /*  tts.setLanguage(Locale.US);
                                        String myText1 = "It was a sunny day in the Jungle";
                                        String myText2 = "All the animals were out playing";
                                        tts.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
                                        tts.speak(myText2, TextToSpeech.QUEUE_ADD, null);*/

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
            Log.d("Page2", "destroy");
        }
    }

}





