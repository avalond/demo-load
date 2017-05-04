package com.example.kevin.demo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * load data service
 */
public class MyService extends Service {
  public static final String TAG = MyService.class.getSimpleName();


  public MyService() {
  }


  @Override public void onCreate() {
    Log.d(TAG, "MyService onCreate ");
    super.onCreate();
  }



  @Override public void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "MyService onDestroy ");
  }


  @Override
  public IBinder onBind(Intent intent) {
    // TODO: Return the communication channel to the service.
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
