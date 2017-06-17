package blankthings.soundthing.tracks;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import blankthings.soundthing.R;
import blankthings.soundthing.api.Track;

/**
 * Created by iosif on 6/16/17.
 */

public class TrackAdapter extends RecyclerView.Adapter<TrackViewHolder> {

    private List<Track> tracklist;
    private TrackView.OnTrackClickedListener onTrackClickedListener;

    public TrackAdapter() {
        tracklist = new ArrayList<>();
    }


    public void setOnTrackClickedListener(TrackView.OnTrackClickedListener listener) {
        onTrackClickedListener = listener;
    }


    public void setTracklist(final List<Track> list) {
        tracklist = list;
        notifyDataSetChanged();
    }


    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.track_view, parent, false);
        return new TrackViewHolder(view, onTrackClickedListener);
    }


    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position) {
        holder.bind(position, tracklist.get(position));
    }


    @Override
    public int getItemCount() {
        return tracklist.size();
    }
}
