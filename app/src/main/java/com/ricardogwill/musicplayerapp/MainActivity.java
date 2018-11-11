package com.ricardogwill.musicplayerapp;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MediaPlayer mediaPlayer;
    private ImageView artistIV;
    private TextView leftTimeTV, rightTimeTV;
    private SeekBar seekBar;
    private Button prevB, playB, nextB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpUI();
    }

    public void setUpUI() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer = mediaPlayer.create(getApplicationContext(), R.raw.aakash_gandhi_borderless);

        artistIV = findViewById(R.id.circleIV);
        leftTimeTV = findViewById(R.id.leftTimeTV);
        rightTimeTV = findViewById(R.id.rightTimeTV);
        seekBar = findViewById(R.id.seekBar);
        prevB = findViewById(R.id.prevB);
        playB = findViewById(R.id.playB);
        nextB = findViewById(R.id.nextB);

        prevB.setOnClickListener(this);
        playB.setOnClickListener(this);
        nextB.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.prevB:
                // Todo code.
                break;

            case R.id.playB:
                if (mediaPlayer.isPlaying()) {
                    pauseMusic();
                } else {
                    startMusic();
                }
                break;

            case R.id.nextB:
                // Todo code.
                break;
        }
    }

    public void pauseMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            playB.setBackgroundResource(android.R.drawable.ic_media_play);
        }
    }

    public void startMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
            playB.setBackgroundResource(android.R.drawable.ic_media_pause);
        }
    }
}
