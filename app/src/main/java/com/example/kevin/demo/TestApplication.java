package com.example.kevin.demo;

import android.app.Application;

/**
 * Created by kevin on 17-5-5.
 */

public class TestApplication extends Application {

  private static TestApplication sApplication;


  public static TestApplication getApplication() {
    return sApplication;
  }


  @Override public void onCreate() {
    super.onCreate();
  }
}
