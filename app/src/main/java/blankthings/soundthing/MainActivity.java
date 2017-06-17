package blankthings.soundthing;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import blankthings.soundthing.api.Track;
import blankthings.soundthing.services.PlaybackService;
import blankthings.soundthing.tracks.TrackView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int EXTERNAL_STORAGE_PERM_RESULT = 100;

    private TrackView trackView;

    private TrackLoader trackLoader;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
    }


    @Override
    protected void onResume() {
        super.onResume();
        checkPermissions();
    }


    private void setupViews() {
        trackView = (TrackView) findViewById(R.id.track_view);
        trackView.setOnTrackClickedListener(onTrackClick);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(onButtonClick);
    }


    private void checkPermissions() {
        final int permissionType = Utility.arePermissionsGranted(
                this, Manifest.permission.READ_EXTERNAL_STORAGE, EXTERNAL_STORAGE_PERM_RESULT);
        if (permissionType == Utility.PERMISSION_SHOW_RATIONALE) {
            showDialog();

        } else if (permissionType == Utility.PERMISSION_OK) {
            initTrackReceiver();
        }
    }


    private void initTrackReceiver() {
        if (trackLoader == null) {
            trackLoader = new TrackLoader(this, getSupportLoaderManager());
            trackLoader.setCallback(new TrackLoader.TracklistLoadCallback() {
                @Override
                public void onTracklistRetrieved(final List<Track> tracks) {
                    trackView.setTracks(tracks);
                }
            });
        }
    }


    private void showDialog() {
        Toast.makeText(this, "hey. i'm a toast. i should be a dialog. thanks.", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        final boolean permissionGranted = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        if (requestCode == EXTERNAL_STORAGE_PERM_RESULT && permissionGranted) {
            initTrackReceiver();
        }
    }


    private View.OnClickListener onButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO: 6/16/17 service stuff here.
        }
    };


    private TrackView.OnTrackClickedListener onTrackClick = new TrackView.OnTrackClickedListener() {
        @Override
        public void onTrackClicked(int trackPosition, Track track) {
            Log.e(TAG, "track clicked: " + track.getTitle());

            final Intent intent = new Intent(MainActivity.this, PlaybackService.class);
            intent.putExtra(PlaybackService.TRACKS_KEY, (ArrayList) trackLoader.getTracklist());
            intent.putExtra(PlaybackService.PICKED_TRACK_KEY, trackPosition);
            startService(intent);
        }
    };
}
