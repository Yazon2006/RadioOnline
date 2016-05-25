package org.radioonline;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static final String URL = "http://www.partyviberadio.com:8016/;stream/1";
    private MediaPlayer mMediaPlayer;

    @ViewById(R.id.playPause_imageView)
    protected ImageView playPause_imageView;
    @ViewById(R.id.artist_textView)
    protected TextView artist_textView;

    @AfterViews
    protected void afterViews() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mMediaPlayer.setDataSource(this, Uri.parse(URL));
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener((mediaPlayer) -> togglePlayPause());
        }
        catch (IOException e) {
            Log.e("Error", "afterViews: ", e);
            return;
        }

        mMediaPlayer.setOnCompletionListener(mp -> togglePlayPause());
    }

    @Click(R.id.playPause_imageView)
    protected void togglePlayPause() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            playPause_imageView.setImageResource(R.drawable.ic_play_arrow_white);
        } else {
            mMediaPlayer.start();
            playPause_imageView.setImageResource(R.drawable.ic_pause_white);
        }
    }

}
