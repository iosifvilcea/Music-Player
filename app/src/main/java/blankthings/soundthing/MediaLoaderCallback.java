package blankthings.soundthing;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

/**
 * Created by iosif on 6/15/17.
 */

public abstract class MediaLoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context context;


    public MediaLoaderCallback(Context context) {
        this.context = context;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
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

    @Override public void onLoaderReset(Loader<Cursor> loader) {}
}
