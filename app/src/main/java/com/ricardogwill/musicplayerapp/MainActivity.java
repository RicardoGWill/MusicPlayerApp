package com.ricardogwill.musicplayerapp;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MediaPlayer mediaPlayer;
    // No ImageView yet.
    private ImageView artistIV;
    private TextView leftTimeTV, rightTimeTV;
    private SeekBar seekBar;
    private Button prevB, playB, nextB;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpUI();

        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override // Automatically generated
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // This lets the user seek to a point along the SeekBar.
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }

                // This updates the TextViews that show the times from the beginning and end of the song.
                SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
                int currentPosition = mediaPlayer.getCurrentPosition();
                int duration = mediaPlayer.getDuration();
                leftTimeTV.setText(dateFormat.format(new Date(currentPosition)));
                rightTimeTV.setText(dateFormat.format(new Date(duration - currentPosition)));
            }

            @Override // Automatically generated
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override // Automatically generated
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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

    // This is used, along with the "setOnClickListener(this)" above instead of making separate on-click listeners.
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.prevB:
                backMusic();
                break;

            case R.id.playB:
                if (mediaPlayer.isPlaying()) {
                    pauseMusic();
                } else {
                    startMusic();
                }
                break;

            case R.id.nextB:
                nextMusic();
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
            updateThread();
            playB.setBackgroundResource(android.R.drawable.ic_media_pause);
        }
    }

    public void backMusic() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                pauseMusic();
                mediaPlayer.seekTo(0);
                startMusic();
                updateThread();
            } else {
                mediaPlayer.seekTo(0);
                updateThread();
            }

        }
    }

    // Note that in this case, there is no next song.
    public void nextMusic() {
        if (mediaPlayer != null) {
            Toast.makeText(this, "Sorry!  Just one song for now!", Toast.LENGTH_SHORT).show();
        }
    }

    // This updates the SeekBar progress visually.
    public void updateThread() {
        thread = new Thread() {
            @Override
            public void run() {

                try {
                    while (mediaPlayer != null) {
                        thread.sleep(50);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Set SeekBar position after getting the position and duration.
                                int newPosition = mediaPlayer.getCurrentPosition();
                                int newMax = mediaPlayer.getDuration();
                                seekBar.setProgress(newPosition);
                                seekBar.setMax(newMax);

                                // Update the text.
                                leftTimeTV.setText(String.valueOf(new SimpleDateFormat("mm:ss")
                                    .format(new Date(mediaPlayer.getCurrentPosition()))));
                                rightTimeTV.setText(String.valueOf(new SimpleDateFormat("mm:ss")
                                    .format(new Date(mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition()))));
                            }
                        });
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        thread.start();
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        thread.interrupt();
        thread = null;
        super.onDestroy();
    }
}
