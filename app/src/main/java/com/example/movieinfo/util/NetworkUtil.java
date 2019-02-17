package com.example.movieinfo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by RSPL on 20-Nov-17.
 */

public class NetworkUtil {
    private NetworkUtil() {
        //this utility class in not publicly instantiable
    }
    public  static boolean isConnected(Context context){
        ConnectivityManager connectivity = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        //If connectivity object is not null
        if (connectivity != null) {
            //Get network info - Mobile internet access
            NetworkInfo info = connectivity.getActiveNetworkInfo();

            if (info != null) {
                //Look for whether device is currently connected to Mobile internet
                if (info.isConnected()) {
                    return true;
                }


            }
        }
        return false;
    }

}
