package com.example.kevin.demo;

import com.example.kevin.demo.services.LokobeeService;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
  Intent intent;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    intent = new Intent(MainActivity.this, LokobeeService.class);
    startService(intent);
  }


  @Override protected void onDestroy() {
    super.onDestroy();
    stopService(intent);
  }
}
