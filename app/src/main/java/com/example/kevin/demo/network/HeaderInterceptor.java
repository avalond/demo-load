package com.example.kevin.demo.network;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import android.text.TextUtils;

/**
 * @author by kevin.
 */

public class HeaderInterceptor implements Interceptor {
  @Override public Response intercept(Chain chain) throws IOException {
    Request originalRequest = chain.request();
    if (!TextUtils.isEmpty(ConstantType.SESSION_TOKEN)) {
      Request authorised = originalRequest.newBuilder()
          .header("Content-type", "application/json")
          .header("Authorization", ConstantType.SESSION_TOKEN)// sessionToken
          .method(originalRequest.method(), originalRequest.body())
          .build();
      return chain.proceed(authorised);
    }
    Request authorised = originalRequest.newBuilder()
        .header("Content-type", "application/json")
        .method(originalRequest.method(), originalRequest.body())
        .build();
    return chain.proceed(authorised);
  }
}
