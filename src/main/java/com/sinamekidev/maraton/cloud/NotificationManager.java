package com.sinamekidev.maraton.cloud;

import okhttp3.*;

import java.io.IOException;


public class NotificationManager {
    private static NotificationManager notificationManager = null;
    private NotificationManager(){

    }
    public static NotificationManager getInstance(){
        if (notificationManager == null){
            notificationManager = new NotificationManager();
        }
        return notificationManager;
    }
    public void sendNotification(String user) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "{}");
        Request request = new Request.Builder()
                .url("https://smn.ap-southeast-1.myhuaweicloud.com/v2/2b585181b04b4b81a69a894f689fbbb0/notifications/topics")
                .method("POST", body)
                .addHeader("X", "")
                .build();
        Response response = client.newCall(request).execute();
    }
}
