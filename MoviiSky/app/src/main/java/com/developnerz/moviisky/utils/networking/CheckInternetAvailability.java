package com.developnerz.moviisky.utils.networking;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Rych Emrycho on 8/26/2018 at 10:44 PM.
 * Updated by Rych Emrycho on 8/26/2018 at 10:44 PM.
 */
public class CheckInternetAvailability {
    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager != null &&
                connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isAvailable() &&
                connectivityManager.getActiveNetworkInfo().isConnected();

    }
}
