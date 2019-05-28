package com.example.devina.littlereaders;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;


public class Congratulatorypage extends AppCompatActivity {

    private Button Backtohome;
    MediaPlayer Congratulatorsound;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulatorypage);

        Congratulatorsound = MediaPlayer.create(Congratulatorypage.this, R.raw.kidscheering);
        Congratulatorsound.start();

        Backtohome = (Button) findViewById(R.id.Home);

        Backtohome.setOnClickListener(new View.OnClickListener() {// Assigned onclicklistener to Back to home button
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Congratulatorypage.this, Welcome.class); //
                startActivity(intent); // This method sends the intent to the Android system, which launches the Page1 class.


    }

});
    }

}

