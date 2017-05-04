package com.example.kevin.demo.network;

/**
 * @author by kevin.
 */

public class LokobeeNetFactory {
  private static final Object monitor = new Object();
  private static LokobeeApi sLokobeeApi;

  public static LokobeeApi getLokobeeApi(){
    synchronized (monitor){
      if (sLokobeeApi==null){
        sLokobeeApi=new LokobeeRetrofit().getLokobeeApi();
      }
      return  sLokobeeApi;
    }
  }
}
