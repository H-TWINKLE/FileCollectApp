package com.twinkle.hfilm.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.twinkle.hfilm.inter.Spider;

public class SpiderService extends Service {
    public SpiderService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        try {
            Spider spider = new Spider();
            spider.get_tv(getApplicationContext());
            spider.get_film(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
