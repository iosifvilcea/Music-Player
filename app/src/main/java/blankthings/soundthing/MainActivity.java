package blankthings.soundthing;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import blankthings.soundthing.tracks.Track;
import blankthings.soundthing.tracks.TrackView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int EXTERNAL_STORAGE_PERM_RESULT = 100;

    private TrackView trackView;
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
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {


                final TextView tv = new TextView(this);
                tv.setText("ACCESS PLS");

                final AlertDialog.Builder br = new AlertDialog.Builder(this);
                final AlertDialog dialog = br.setView(tv)
                        .setPositiveButton("Yuh", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Nah", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();

                dialog.show();

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        EXTERNAL_STORAGE_PERM_RESULT);

            }

        } else {
            getSupportLoaderManager().initLoader(1, null, loaderCallback);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        final boolean permissionGranted =
                grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        if (requestCode == EXTERNAL_STORAGE_PERM_RESULT && permissionGranted) {
            getSupportLoaderManager().initLoader(1, null, loaderCallback);
        }
    }


    private void getAlbums(final Cursor cursor) {
        List<Track> list = new ArrayList<>();
        if (cursor != null && cursor.getCount() != 0) {
            while(cursor.moveToNext()) {
                String artist = "";
                final int artistPos = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                if (artistPos > 0) {
                    artist = cursor.getString(artistPos);
                }

                String path = "";
                final int dataPos = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                if (dataPos > 0) {
                    path = cursor.getString(dataPos);
                }

                String title = "";
                final int titlePos = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                if (titlePos > 0) {
                    title = cursor.getString(titlePos);
                }

                final String txt = String.format("%s - %s : %s", title, artist, path);
                Log.e(TAG, txt);

                list.add(new Track(path, title, artist));
            }
        }

        trackView.setTracks(list);
    }


    private MediaLoaderCallback loaderCallback = new MediaLoaderCallback(this) {
        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            getAlbums(data);
        }
    };


    private View.OnClickListener onButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO: 6/16/17 service stuff here.
        }
    };


    private TrackView.OnTrackClickedListener onTrackClick = new TrackView.OnTrackClickedListener() {
        @Override
        public void onTrackClicked(Track track) {
            Log.e(TAG, "track clicked: " + track.getTitle());
            // TODO: 6/16/17 start the service ? or change song if existing service is started?
        }
    };
}
