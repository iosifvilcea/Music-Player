package blankthings.soundthing.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import blankthings.soundthing.services.PlaybackService;

/**
 * Created by iosif on 6/19/17.
 */

public class PlaybackReceiver extends BroadcastReceiver {

    public static final String TAG = PlaybackReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(android.media.AudioManager.ACTION_AUDIO_BECOMING_NOISY)) {
            Log.d(TAG, "Headphones unplugged.");

            final Intent serviceIntent = new Intent(context, PlaybackService.class);
            serviceIntent.setAction(PlaybackService.ACTION_STOP);
            context.startService(serviceIntent);
        }
        
    }

}
