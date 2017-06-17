package blankthings.soundthing;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;

/**
 * Created by iosif on 6/15/17.
 */

public abstract class MediaLoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context context;


    public MediaLoaderCallback(Context context) {
        this.context = context;
    }
}
