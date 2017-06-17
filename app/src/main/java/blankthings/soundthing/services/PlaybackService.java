package blankthings.soundthing.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import blankthings.soundthing.api.Track;

/**
 * Created by iosif on 6/16/17.
 */

public class PlaybackService
        extends Service
        implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {

    public static final String TAG = PlaybackService.class.getSimpleName();
    public static final String PICKED_TRACK_KEY = "PICKED_TRACK_KEY";
    public static final String TRACKS_KEY = "TRACKS_KEY";

    private MediaPlayer mediaPlayer;
    private ArrayList<Track> tracklist;
    private int currentTrack;


    @Override
    public void onCreate() {
        super.onCreate();

        Log.e(TAG, "service created.");

        currentTrack = 0;
        initMediaPlayer();
    }


    private void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
    }


    private void play() {
        mediaPlayer.reset();
        mediaPlayer.prepareAsync();
        mediaPlayer.start();
    }


    private void updateTrackIndex() {
        if (currentTrack < tracklist.size()) {
            currentTrack++;
        } else {
            currentTrack = 0;
        }
    }


    private void loadTrack() {
        try {
            final String path = tracklist.get(currentTrack).getPath();
            mediaPlayer.setDataSource(path);

        } catch (IOException ex) {
            Log.e(TAG, ex.getMessage());
        }
    }


    private void prepareNextSong() {
        updateTrackIndex();
        loadTrack();
        play();
    }


    public void stop() {
        mediaPlayer.stop();
        mediaPlayer.release();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        currentTrack = intent.getIntExtra(PICKED_TRACK_KEY, 0);
        tracklist = intent.getParcelableArrayListExtra(TRACKS_KEY);

        Log.e(TAG, "onStartCommand.");

        loadTrack();
        play();

        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        prepareNextSong();
    }


    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        // TODO: 6/16/17
        return false;
    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}
