package com.example.devina.littlereaders;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.util.ArrayList;
import java.util.Locale;


public class Page4 extends AppCompatActivity  implements TextToSpeech.OnInitListener{

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    Button Previouspage3, NextPage5, Read4;
    PDFView Page4;

    private MediaPlayer mp;
    static int Sentence = 0;
    String statement = "";
    String text;
    TextToSpeech tts;

    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;

    MediaPlayer pageturn;
    MediaPlayer elephant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page4);

        Page4 = (PDFView) findViewById(R.id.BookPage4);
        Page4.fromAsset("chapter4.pdf").load();

        tts = new TextToSpeech(this, this);

        NextPage5 = (Button) findViewById(R.id.NextPage5);
        Read4 = (Button) findViewById(R.id.read4);

        pageturn = MediaPlayer.create(this, R.raw.pageturn);
        elephant = MediaPlayer.create(Page4.this, R.raw.elephant);

        pageturn.start();
        elephant.start();


        NextPage5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Page4.this, Page5.class);
                startActivity(intent);


            }
        });

        Read4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startVoiceInput();
            }
        });

        Previouspage3 = (Button) findViewById(R.id.Previouspage3);

        Previouspage3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Page4.this, Page3.class);
                startActivity(intent);
                pageturn.start();
                elephant.stop();
            }
        });

    }

    private void startVoiceInput() { // copy
        if(Sentence == 0){
            Sentence = 1;
            statement = "come play with me lion said elephant";
            Log.d("TEST","Flow 1");
        }else if(Sentence == 1){
            Sentence = 2;
            statement = "we can throw rocks";
            Log.d("TEST","Flow 2");
        }else if (Sentence == 2) {
            Sentence = 3;
            statement = "ka pow ka pow went elephant";
            Log.d("TEST", "Flow 3");
        }else if (Sentence == 3){
            Sentence = 4;
            statement = "i dont want to play said lion i'll lose.";
            Log.d("TEST", "Flow 4");
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


                    Log.d("statement",result.get(0));
                    Log.d("statement",statement);
                    Log.d("sentence",String.valueOf(Sentence));

                    if ((result.get(0)).equals(statement)) {
                        // Toast.makeText(Page4.this,
                        //       "Its A MATCH" + (result.get(0)), Toast.LENGTH_LONG).show();
                        AlertDialog alertDialog = new AlertDialog.Builder(Page4.this).create();
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
                                            Intent intent = new Intent(Page4.this, Page3.class);
                                            startActivity(intent);
                                            pageturn.start();
                                            elephant.start();
                                        }

                                    }
                                });
                        alertDialog.show();





                    } else if  (!(result.get(0)).equals(statement)) {
                        Toast.makeText(Page4.this,
                                "Other Message " + (result.get(0)), Toast.LENGTH_LONG).show();
                        AlertDialog alertDialog = new AlertDialog.Builder(Page4.this).create();
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
            Log.d("Page4", "destroy");
        }
    }

}



