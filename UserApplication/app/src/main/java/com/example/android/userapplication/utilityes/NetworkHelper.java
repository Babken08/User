package com.example.android.userapplication.utilityes;

import com.example.android.userapplication.Model.DataObject;
import com.example.android.userapplication.Model.NotificationData;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


final public class NetworkHelper {
    private static final String SERVER_KEY = "AAAActLOqW4:APA91bH9vUGcPSTpExpXPkARPVTkkDika73A8rEPca7mFj4CDdWHlWGvdZeukaHdu7KfmPt3c-n8E_fX4aHPbqK7z0RZIN9irSEtQ1YKtsAO6NH2gJfi7ExQ1qThTzYZPgaHlValXeKR";

    public static void sendNotificationRequest(String phoneNumber, String addres) {
        Gson gson = new Gson();
        NotificationData notificationData = new NotificationData();
        DataObject dataObject = new DataObject();
        dataObject.setAddress(addres);
        notificationData.setData(dataObject);
        notificationData.setTo(phoneNumber);

        String json = gson.toJson(notificationData);
        String url = "https://fcm.googleapis.com/fcm/send";

        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "key=" + SERVER_KEY)
                .post(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                Toast.makeText(MainActivity.this, "Failure!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Toast.makeText(MainActivity.this, "OK!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

