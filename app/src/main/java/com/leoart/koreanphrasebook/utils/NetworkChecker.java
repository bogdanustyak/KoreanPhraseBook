package com.leoart.koreanphrasebook.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;

/**
 * Created by khrystyna on 6/14/17.
 */

public class NetworkChecker {

    private final NetworkInfo activeNetworkInfo;

    public NetworkChecker(Context context) {
        this.activeNetworkInfo = context.getSystemService(Context.CONNECTIVITY_SERVICE) != null ?
                ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo()
                : null;
    }

    public boolean isNetworkAvailable() {
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
