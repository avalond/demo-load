package com.example.kevin.demo.services;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * 灰色保活
 * Created by kevin.
 */

public class LokobeeAliveService extends Service {
  private static final int SERVICES_ID = 666;


  public LokobeeAliveService() {
  }


  @Override public void onCreate() {
    super.onCreate();
  }


  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    if (Build.VERSION.SDK_INT < 18) {
      //API <18 隐藏  Notification 上的图标
      startForeground(SERVICES_ID, new Notification());
    } else {
      Intent innerIntent = new Intent(this, LokobeeKeepInnerService.class);
      startService(innerIntent);
      startForeground(SERVICES_ID, new Notification());
    }
    return super.onStartCommand(intent, flags, startId);
  }


  @Nullable @Override public IBinder onBind(Intent intent) {
    return null;
  }


  public static class LokobeeKeepInnerService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
      startForeground(SERVICES_ID, new Notification());
      stopForeground(true);
      stopSelf();
      return super.onStartCommand(intent, flags, startId);
    }


    @Nullable @Override public IBinder onBind(Intent intent) {
      return null;
    }
  }
}
