package com.senati.itanesturismo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;

public final class NetworkUtils {

    private NetworkUtils() { }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
            (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkCapabilities capabilities =
            cm.getNetworkCapabilities(cm.getActiveNetwork());

        return capabilities != null &&
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
    }
}
