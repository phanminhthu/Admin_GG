package com.danazone.gomgom.admin;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class MyService extends Service {
    public MyService() {
    }
    Socket socket;
    Handler hd = new Handler();
    String s ="";
    boolean chayAn = false;
    MediaPlayer mediaPlayer;
    Vibrator v;
    int i =0;
    boolean send = false;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         Log.d("aaa", "onStartCommand");

        return START_STICKY;
    }


    @Override
    public void onCreate() {
         Log.d("aaa", "onCreate");

        sqlite db2= new sqlite(this,"tokenAdmin.sqlite",null,1);
        //tao bang
        db2.QueryData("CREATE TABLE IF NOT EXISTS chayAn(OK VACHAR)");
        //truy van

        Cursor contro2 = db2.GetData("SELECT * FROM chayAn");
        while (contro2.moveToNext())
        {
            if (contro2.getInt(0)==0){
                chayAn = true;
            } else {chayAn = false;}
        }




         if (chayAn==true) {


             mContext = getApplicationContext();
             try {
                 socket = IO.socket(mContext.getString(R.string.sv));
             } catch (URISyntaxException e) {
                 e.printStackTrace();
             }

             sqlite db = new sqlite(this, "tokenAdmin.sqlite", null, 1);
             //tao bang
             db.QueryData("CREATE TABLE IF NOT EXISTS token(TK VACHAR)");
             //truy van
             Cursor contro = db.GetData("SELECT * FROM token");

             while (contro.moveToNext()) {
                 s = contro.getString(0);
             }

             if (!socket.connected()) {
                 socket.connect();
             }

             hd.removeMessages(0);
             hd.postDelayed(new Runnable() {
                 @Override
                 public void run() {
                        if (!send)
                        {socket.emit("kiemTraDangNhapAdmin",s);}
                        send = true;

                     hd.postDelayed(this, 15000);
                     Log.d("aaa", "kiemTraDangNhapAdmin");
                 }}
             , 5000);

             socket.on("kiemTraDangNhapAdmin", new Emitter.Listener() {
                 @Override
                 public void call(Object... args) {
                     send = false;
                     if (args[0].toString().matches("false")) {
                     if (i>5){hd.removeMessages(0); stopSelf();}else {i++;}
                     }
                     else {i=0;}


                 }
             });

             socket.on("thongBaoAdmin", new Emitter.Listener() {
                 @Override
                 public void call(Object... args) {
                     createNotification("Gom Gom - Tài Xế", "Tài xế vừa yêu cầu xác thực thông tin!");
                 }
             });


         }
        super.onCreate();
    }

    @Override
    public void onDestroy() {
         Log.d("aaa", "onDestroy");
        hd.removeMessages(0);
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MyService.super.onCreate();
        }
        else
        {
            Intent restartServiceIntent = new Intent(getApplicationContext(), MyService.class);
            restartServiceIntent.setPackage(getPackageName());
            restartServiceIntent.putExtra("chayAn", true);
            PendingIntent restartServicePendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
            AlarmManager alarmService = (AlarmManager) getApplicationContext().
                    getSystemService(Context.ALARM_SERVICE);
            alarmService.set(
                    AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + 5000,
                    restartServicePendingIntent);

        }


        Log.d("aaa", "onTaskRemoved");
        super.onTaskRemoved(rootIntent);
    }


    private Context mContext;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    public void createNotification(String title, String message)
    {
        Log.d("aaa", "tb1");
        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");

       try {
           wakeLock.acquire();
       }
       catch (Exception e){}
        Log.d("aaa", "tb2");
        /**Creates an explicit intent for an Activity in your app**/
        Intent resultIntent = new Intent(getApplicationContext() , mhToken.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext,
                0 /* Request code */, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setSmallIcon(R.drawable.ic_logo_mini);
        mBuilder.setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent);
       mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
          //  notificationChannel.enableVibration(true);
           // notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);

            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        if (v!=null){if(v.hasVibrator()){v.cancel(); } v = null;}
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(2000,VibrationEffect.DEFAULT_AMPLITUDE));
        }else{
            //deprecated in API 26
            v.vibrate(2000);

        }
         Log.d("aaa", "tb3");
        if (mediaPlayer!=null){if (mediaPlayer.isPlaying()){mediaPlayer.stop();}mediaPlayer = null;}
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.ring_sms);
        mediaPlayer.start();


        assert mNotificationManager != null;
        mNotificationManager.notify(0 /* Request Code */, mBuilder.build());
    }








}
