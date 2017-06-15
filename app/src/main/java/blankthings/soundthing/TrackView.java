package blankthings.soundthing;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by iosif on 6/14/17.
 */

public class TrackView extends LinearLayout {


    private ImageView providerLogo;
    private ImageView artwork;
    private TextView trackName;
    private TextView creatorName;


    public TrackView(Context context) {
        super(context);
        init();
    }


    public TrackView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        inflate(getContext(), R.layout.track_view, this);

        providerLogo = (ImageView) findViewById(R.id.player_logo);
        artwork = (ImageView) findViewById(R.id.track_art);
        trackName = (TextView) findViewById(R.id.track_name);
        creatorName = (TextView) findViewById(R.id.track_creator_name);
    }


    public void setArtwork(final Drawable drawable) {
        artwork.setImageDrawable(drawable);
    }


    public void setTrackName(final String name) {
        trackName.setText(name);
    }


    public void setCreatorName(final String name) {
        creatorName.setText(name);
    }


    public ImageView getProviderLogo() {
        return providerLogo;
    }


    public ImageView getArtwork() {
        return artwork;
    }

    public TextView getTrackName() {
        return trackName;
    }


    public TextView getCreatorName() {
        return creatorName;
    }
}
