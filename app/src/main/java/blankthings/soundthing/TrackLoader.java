package blankthings.soundthing;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import blankthings.soundthing.api.Track;

/**
 * Created by iosif on 6/16/17.
 */

public class TrackLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = TrackLoader.class.getSimpleName();

    public TracklistLoadCallback callback;
    private Context context;
    private List<Track> tracklist;

    public TrackLoader(Context context, LoaderManager loaderManager) {
        if (context == null || loaderManager == null) {
            throw new IllegalArgumentException("parameters cannot be null.");
        }

        this.context = context;
        tracklist = new ArrayList<>();
        loaderManager.initLoader(1, null, this);
    }


    public void setCallback(TracklistLoadCallback callback) {
        this.callback = callback;
    }


    private void queryTracks(final Cursor cursor) {
        if (cursor == null) {
            Log.e(TAG, "Querying tracks has failed.");
            return;
        }

        final int idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
        final int durationColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
        final int pathColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        final int artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        final int titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        final int albumColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);

        if (cursor.getCount() != 0) {
            while(cursor.moveToNext()) {
                final Track track = new Track(
                        cursor.getLong(idColumn),
                        cursor.getLong(durationColumn),
                        cursor.getString(pathColumn),
                        cursor.getString(titleColumn),
                        cursor.getString(artistColumn),
                        cursor.getString(albumColumn));

                tracklist.add(track);
            }
        }

        notifyQueryComplete();
    }


    private void notifyQueryComplete() {
        if (callback != null) {
            callback.onTracklistRetrieved(tracklist);
        }
    }


    public List<Track> getTracklist() {
        return tracklist;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        final String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA
        };

        return new CursorLoader(
                context,
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        queryTracks(data);
    }


    @Override public void onLoaderReset(Loader<Cursor> loader) {
    }


    /**
     * Notifies tracks have been loaded.
     */
    public interface TracklistLoadCallback {

        void onTracklistRetrieved(List<Track> tracks);

    }

}
