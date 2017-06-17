package blankthings.soundthing.tracks;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.List;

import blankthings.soundthing.api.Track;

/**
 * Created by iosif on 6/14/17.
 */

public class TrackView extends RecyclerView {

    private TrackAdapter adapter;

    public TrackView(Context context) {
        super(context);
        init();
    }


    public TrackView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        adapter = new TrackAdapter();
        setAdapter(adapter);

        final RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        setLayoutManager(manager);

        final DividerItemDecoration decoration =
                new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
        addItemDecoration(decoration);
    }


    public void setTracks(final List<Track> tracks) {
        adapter.setTracklist(tracks);
    }


    public void setOnTrackClickedListener(OnTrackClickedListener onTrackClickedListener) {
        adapter.setOnTrackClickedListener(onTrackClickedListener);
    }


    /**
     * TODO
     *   should be moved to MVP.
     */
    public interface OnTrackClickedListener {

        void onTrackClicked(int trackPosition, Track track);

    }
}
