package blankthings.soundthing.tracks;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import blankthings.soundthing.R;

/**
 * Created by iosif on 6/16/17.
 */

public class TrackViewHolder extends RecyclerView.ViewHolder {

    private TrackView.OnTrackClickedListener listener;

    private ImageView providerLogo;
    private ImageView artwork;
    private TextView title;
    private TextView artist;


    public TrackViewHolder(final View itemView, final TrackView.OnTrackClickedListener listener) {
        super(itemView);
        this.listener = listener;

        providerLogo = (ImageView) itemView.findViewById(R.id.player_logo);
        artwork = (ImageView) itemView.findViewById(R.id.track_art);
        title = (TextView) itemView.findViewById(R.id.track_title);
        artist = (TextView) itemView.findViewById(R.id.track_artist);
    }


    public void bind(final Track track) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onTrackClicked(track);
                }
            }
        });


        title.setText(track.getTitle());
        artist.setText(track.getArtist());
    }
}
