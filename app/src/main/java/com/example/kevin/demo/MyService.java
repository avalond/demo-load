package com.example.kevin.demo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * load data service
 */
public class MyService extends Service {
  public MyService() {
  }


  @Override public void onCreate() {
    super.onCreate();
  }


  @Override
  public IBinder onBind(Intent intent) {
    // TODO: Return the communication channel to the service.
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
