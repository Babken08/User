package com.example.android.userapplication.Service;

import android.app.IntentService;
import android.content.Intent;

import com.example.android.userapplication.utilityes.NetworkHelper;

public class NotificationActionService extends IntentService {

    public static final String ACTION_ACCEPT = "Accept";
    public static final String ACTION_REJECT = "Reject";

    public NotificationActionService() {
        super("NotificationActionService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {
            final String action = intent.getAction();
            final String phoneNumber = intent.getStringExtra("PhoneNumber");
            if (ACTION_ACCEPT.equals(action)) {
                NetworkHelper.sendNotificationRequest(phoneNumber, "OK");
                Intent i = new Intent(NotificationActionService.this, GPSTracker.class);
                getApplicationContext().startService(i);
            } else if (ACTION_REJECT.equals(action)) {
                NetworkHelper.sendNotificationRequest(phoneNumber, "CANCEL");
            }
        }
    }
}
