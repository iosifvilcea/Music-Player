package blankthings.soundthing;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int EXTERNAL_STORAGE_PERM_RESULT = 100;

    private TrackView trackView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupTrack();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermissions();
    }

    private void setupTrack() {
        trackView = (TrackView) findViewById(R.id.track_view);
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

            getSupportLoaderManager().initLoader(1, null, loaderCallbacks);
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
            getSupportLoaderManager().initLoader(1, null, loaderCallbacks);
        }
    }


    private void getAlbums(final Cursor cursor) {
        if (cursor != null && cursor.getCount() != 0) {
            while (cursor.moveToNext()) {

                String artist = "";
                final int artistPos = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                if (artistPos > 0) {
                    artist = cursor.getString(artistPos);

                }

                Log.e(TAG, artist);
            }
        }

    }


    private LoaderManager.LoaderCallbacks loaderCallbacks =
            new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            String[] projection = {
                    MediaStore.Audio.Media._ID,
                    MediaStore.Audio.Media.ARTIST,
            };

            return new CursorLoader(
                    MainActivity.this,
                    MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                    projection,
                    null,
                    null,
                    null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            getAlbums(data);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            Log.e(TAG, "Loader Reset. Do something. Probably.");
        }
    };
}
