package com.example.servicebroadcastreceiveractivitycomunication;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.timeStamp)
    TextView timeStamp;

    public static final String mMyBroadcastReceiver = "com.example.broadcast.MY_BROADCAST";
    private IntentFilter intentFilter;
    //MyService myService;
    //boolean myServiceBool = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        intentFilter = new IntentFilter(mMyBroadcastReceiver);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(mReceiver, intentFilter);
        Intent serviceIntent = new Intent(this, MyService.class);
        startService(serviceIntent);
     //   bindService(serviceIntent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(mReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, intentFilter);
    }




    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            timeStamp.setText(intent.getStringExtra("data"));
            Log.i("My app,local Broadcast:", "brodcast receive");
        }
    };


/*
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyService.MyBinder myBinder = (MyService.MyBinder) iBinder;
            myService = myBinder.getService();
            myServiceBool = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            myServiceBool = false;
        }
    };*/
}
