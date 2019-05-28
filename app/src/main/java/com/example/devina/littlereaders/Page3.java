package com.example.devina.littlereaders;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.provider.Telephony;
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

public class Page3 extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    PDFView Page3;
    Button Previouspage2, NextPage4, Read3;

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
        setContentView(R.layout.activity_page3);

        Page3 = (PDFView) findViewById(R.id.BookPage3);
        Page3.fromAsset("chapter3.pdf").load();

        tts = new TextToSpeech(this, this);

        NextPage4 = (Button) findViewById(R.id.NextPage4);
        Read3 = (Button) findViewById(R.id.read3);

        final MediaPlayer pageturn = MediaPlayer.create(this, R.raw.pageturn);
        final MediaPlayer Cheetah = MediaPlayer.create(Page3.this, R.raw.cheetah);
        //Cheetah.start();

        pageturn.start();
        Cheetah.start();

        NextPage4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Page3.this, Page4.class);
                startActivity(intent);

            }
        });

        Read3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startVoiceInput();
            }
        });

        Previouspage2 = (Button) findViewById(R.id.Previouspage2);

        Previouspage2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Page3.this, Page2.class);
                startActivity(intent);
                pageturn.start();
                Cheetah.stop();
                //Elephant.start();
                //Elephant.stop();
            }
        });


    }

    private void startVoiceInput() { // copy
        if (Sentence == 0) {
            // Sentence = 1;
            statement = "come play with me Lion said Cheetah";
            //Log.d("TEST", "Flow 1");
        } else if (Sentence == 1) {
            // Sentence = 2;
            statement = "catch me if you can";
            //  Log.d("TEST", "Flow 2");
        } else if (Sentence == 2) {
            //  Sentence = 3;
            statement = "zoom zoom went Cheetah";
            //  Log.d("TEST", "Flow 3");
        } else if (Sentence == 3) {
            //  Sentence = 4;
            statement = "I dont want to play said Lion Ill lose";
            //  Log.d("TEST", "Flow 4");
            // } else if (Sentence == 4) {
            //    Sentence = 5;
            //  Log.d("TEST", "Flow 5");
        }


        tts.setLanguage(Locale.US);
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
                        // Toast.makeText(Page3.this,
                        // "Its A MATCH" + (result.get(0)), Toast.LENGTH_LONG).show();
                        AlertDialog alertDialog = new AlertDialog.Builder(Page3.this).create();
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
                                            Sentence = 2;
                                            startVoiceInput();
                                        } else if (Sentence == 2) {
                                            Sentence = 3;
                                            startVoiceInput();
                                        } else if (Sentence == 3) {
                                            Sentence = 4;
                                            startVoiceInput();
                                            Intent intent = new Intent(Page3.this, Page4.class);
                                            startActivity(intent);
                                            pageturn.start();
                                            Cheetah.start();
                                        }

                                    }

                                });
                        alertDialog.show();


                    } else if (!(result.get(0)).equals(statement)) {
                        Toast.makeText(Page3.this,
                                "Other Message " + (result.get(0)), Toast.LENGTH_LONG).show();
                        AlertDialog alertDialog = new AlertDialog.Builder(Page3.this).create();
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
            Log.d("Page3", "destroy");
        }
    }

}

