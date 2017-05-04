package com.example.kevin.demo.services;

import com.example.kevin.demo.utils.LoggerUtils;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;

/**
 * load data service
 */
public class LokobeeService extends Service {
  private final String TAG = LokobeeService.class.getSimpleName();

  private final IBinder mIBinder = new myLocalIbind();
  private Looper mWorkerLooper;
  private LokobeeServiceHandler mWorkerHandler;

  public static final int START = 0x1;


  public LokobeeService() {
  }


  @Override public void onCreate() {
    super.onCreate();
    LoggerUtils.d(TAG, "LokobeeService onCreate ");
    HandlerThread workerThread = new HandlerThread("LokobeeServiceWorker", Process.THREAD_PRIORITY_BACKGROUND);
    workerThread.start();

    mWorkerLooper = workerThread.getLooper();
    mWorkerHandler = new LokobeeServiceHandler(mWorkerLooper, getApplication());
  }


  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    LoggerUtils.d(TAG, "----->>> onStartCommand");
    Message message = mWorkerHandler.obtainMessage();
    message.what = LokobeeService.START;
    message.arg1 = startId;
    if (intent != null) {
      message.setData(intent.getExtras());
    }
    mWorkerHandler.sendMessage(message);

    //is the services get killed restart
    return LokobeeService.START_NOT_STICKY;
  }


  @Override public void onDestroy() {
    Log.d(TAG, "LokobeeService onDestroy ");
    Message message = mWorkerHandler.obtainMessage();
    mWorkerHandler.sendMessage(message);

    //
    super.onDestroy();
  }


  @Override
  public IBinder onBind(Intent intent) {
    return mIBinder;
  }


  private class myLocalIbind extends Binder {
    public LokobeeService getLokobeeService() {
      return LokobeeService.this;
    }
  }
}
