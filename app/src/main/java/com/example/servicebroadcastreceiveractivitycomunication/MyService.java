package com.example.servicebroadcastreceiveractivitycomunication;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    public MyService(){}


    private Chronometer chronometer;
    //private final IBinder binder = new Binder();

    private final int INTERVAL = 10*1000;
    private Timer timer = new Timer();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
        //return binder;
    }
/*
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }*/

    @Override
    public void onDestroy() {
        if(timer != null) {
            timer.cancel();
        }
        super.onDestroy();

    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("MyService: ", "works onCreate");

        chronometer = new Chronometer(this);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("My app, MyService: ", "works onStartCommand");


        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long elapsedTime = SystemClock.elapsedRealtime() - chronometer.getBase();
                int hours = (int) (elapsedTime / 3600000);
                int minutes = (int) (elapsedTime - hours * 3600000) / 60000;
                int seconds = (int) (elapsedTime - hours * 3600000 - minutes * 60000) / 1000;
                Log.i("time in service:" , " " + seconds);

                String elapsedTimeString =  minutes + " : " + seconds;
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(MainActivity.mMyBroadcastReceiver);
                broadcastIntent.putExtra("data",elapsedTimeString);

                sendBroadcast(broadcastIntent);
                Log.i("MyService: ", "works thread");
            }
        },0,INTERVAL);

        return super.onStartCommand(intent, flags, startId);
    }
/*
    public void getTimeStamp() {
        long elapsedTime = SystemClock.elapsedRealtime() - chronometer.getBase();
        int hours = (int) (elapsedTime / 3600000);
        int minutes = (int) (elapsedTime - hours * 3600000) / 60000;
        int seconds = (int) (elapsedTime - hours * 3600000 - minutes * 60000) / 1000;
        Log.i("time in service:" , " " + seconds);

        String elapsedTimeString =  minutes + " : " + seconds;
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(MainActivity.mMyBroadcastReceiver);
        broadcastIntent.putExtra("data",elapsedTimeString);

        sendBroadcast(broadcastIntent);
    }


    class MyBinder extends Binder{
        public MyService getService() {
            return  MyService.this;
        }
    }*/
}
