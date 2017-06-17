package blankthings.soundthing;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by iosif on 6/16/17.
 */

public class Utility {

    public static final int PERMISSION_OK = 0;
    public static final int PERMISSION_SHOW_RATIONALE = 1;
    public static final int PERMISSION_REQUESTED = 2;

    private Utility() {}

    /**
     * @return should show permission rationale.
     */
    public static int arePermissionsGranted(final Activity activity, final String permission, final int permissionResult) {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return PERMISSION_SHOW_RATIONALE;

            } else {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, permissionResult);
                return PERMISSION_REQUESTED;
            }
        }

        return PERMISSION_OK;
    }

}
