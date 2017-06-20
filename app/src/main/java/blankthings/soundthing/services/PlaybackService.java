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

    public static final String ACTION_PLAY = "blankthings.soundthing.action.PLAY";
    public static final String ACTION_STOP = "blankthings.soundthing.action.STOP";
    public static final String ACTION_PREVIOUS = "blankthings.soundthing.action.PREVIOUS";
    public static final String ACTION_NEXT = "blankthings.soundthing.action.NEXT";

    public static final String PICKED_TRACK_KEY = "PICKED_TRACK_KEY";
    public static final String TRACKS_KEY = "TRACKS_KEY";

    private State state;

    public enum State {
        PREPARING,
        STARTED,
        PAUSED,
        STOPPED
    }

    private MediaPlayer mediaPlayer;
    private ArrayList<Track> tracklist;
    private int currentTrack;


    @Override
    public void onCreate() {
        super.onCreate();

        state = State.PREPARING;
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
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            final String path = tracklist.get(currentTrack).getPath();
            mediaPlayer.setDataSource(path);

        } catch (IOException ex) {
            Log.e(TAG, ex.getMessage());
        }

        mediaPlayer.prepareAsync();
        state = State.STARTED;
    }


    private void next() {
        incrementTrack();
        play();
    }


    private void incrementTrack() {
        if (currentTrack < tracklist.size()) {
            currentTrack++;
        } else {
            currentTrack = 0;
        }
    }


    private void decrementTrack() {
        if (currentTrack > 0) {
            currentTrack--;
        } else {
            currentTrack = tracklist.size()-1;
        }
    }


    private void previous() {
        decrementTrack();
        play();
    }


    public void stop() {
        state = State.STOPPED;

        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;

        stopSelf();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final String action = intent.getAction();
        switch (action) {

            case ACTION_PLAY:
                currentTrack = intent.getIntExtra(PICKED_TRACK_KEY, 0);
                tracklist = intent.getParcelableArrayListExtra(TRACKS_KEY);
                play();
                break;

            case ACTION_STOP:
                stop();
                break;

            case ACTION_NEXT:
                next();
                break;

            case ACTION_PREVIOUS:
                previous();
                break;
        }

        return START_NOT_STICKY;
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d(TAG, "Completed.");
        stop();
    }


    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.e(TAG, "MediaPlayerError.");
        mp.stop();
        return false;
    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
