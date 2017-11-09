package com.example.android.userapplication.utilityes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        String status = NetworkUtil.getConnectivityStatusString(context);
        Log.i("sssssssssssssssss" ,  "status " + status);

//        Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }
}
