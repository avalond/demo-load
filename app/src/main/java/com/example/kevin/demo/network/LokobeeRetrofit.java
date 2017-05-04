package com.example.kevin.demo.network;

import com.example.kevin.demo.BuildConfig;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author by kevin.
 */

public class LokobeeRetrofit {
  private final LokobeeApi mLokobeeApi;


  LokobeeRetrofit() {

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    if (BuildConfig.DEBUG) {
      HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
     // logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
      httpClient.addInterceptor(logging);
    }

    httpClient.connectTimeout(60, TimeUnit.SECONDS);
    httpClient.readTimeout(60, TimeUnit.SECONDS);
    httpClient.writeTimeout(60, TimeUnit.SECONDS);
    httpClient.addInterceptor(new HeaderInterceptor());

    OkHttpClient mClient = httpClient.build();

    Retrofit.Builder builder = new Retrofit.Builder();
    builder.baseUrl(ConstantType.SERVER_URL);
    builder.addConverterFactory(GsonConverterFactory.create());
    builder.client(mClient);

    Retrofit lokobeeRetrofit = builder.build();
    mLokobeeApi = lokobeeRetrofit.create(LokobeeApi.class);
  }


  public LokobeeApi getLokobeeApi() {
    return mLokobeeApi;
  }
}
