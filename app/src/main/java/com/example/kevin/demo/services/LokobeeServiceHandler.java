package com.example.kevin.demo.services;

import com.example.kevin.demo.modle.BaseResponse;
import com.example.kevin.demo.modle.GetUnFinishedOrderByUser;
import com.example.kevin.demo.network.LokobeeNetFactory;
import com.example.kevin.demo.network.ConstantType;
import com.example.kevin.demo.utils.LoggerUtils;
import retrofit2.Call;
import retrofit2.Response;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * @author by kevin.
 */

public class LokobeeServiceHandler extends Handler {
  private final String TAG = LokobeeServiceHandler.class.getSimpleName();
  private Context mContext;


  public LokobeeServiceHandler(Looper looper, Context context) {
    super(looper);
    this.mContext = context;
  }


  @Override public void handleMessage(Message msg) {
    switch (msg.what) {
      case LokobeeService.START:
        startGetData();
        break;
      default:
        throw new IllegalArgumentException("LokobeeServiceHandler handler received unknown message:" + msg.what);
    }
  }


  private void startGetData() {
    LokobeeNetFactory.getLokobeeApi().GetUnfinishedOrderByBuyer(ConstantType.PARAMETERS_ORDER_STRING).enqueue(
        new retrofit2.Callback<BaseResponse<GetUnFinishedOrderByUser>>() {
          @Override
          public void onResponse(Call<BaseResponse<GetUnFinishedOrderByUser>> call, Response<BaseResponse<GetUnFinishedOrderByUser>> response) {
            LoggerUtils.d(TAG, "" + response.body().getData().getGetUnfinishedOrderByBuyer().size());
          }


          @Override public void onFailure(Call<BaseResponse<GetUnFinishedOrderByUser>> call, Throwable t) {

          }
        });
  }
}
