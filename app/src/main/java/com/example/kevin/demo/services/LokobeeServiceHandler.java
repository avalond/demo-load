package com.example.kevin.demo.services;

import com.example.kevin.demo.modle.BaseResponse;
import com.example.kevin.demo.modle.GetUnFinishedOrderByUser;
import com.example.kevin.demo.modle.Order;
import com.example.kevin.demo.network.LokobeeNetFactory;
import com.example.kevin.demo.network.ConstantType;
import com.example.kevin.demo.provider.LokobeeOrderProvider;
import com.example.kevin.demo.utils.LoggerUtils;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * @author by kevin.
 */

public class LokobeeServiceHandler extends Handler {
  private final String TAG = LokobeeServiceHandler.class.getSimpleName();
  private Context mContext;
  private List<Order> mOrderList;


  public LokobeeServiceHandler(Looper looper, Context context) {
    super(looper);
    this.mContext = context;
  }


  @Override public void handleMessage(Message msg) {
    switch (msg.what) {
      case LokobeeService.START_Get_DATA:
        startGetData();
        break;
      default:
        break;
    }
  }


  private void startGetData() {
    LokobeeNetFactory.getLokobeeApi().GetUnfinishedOrderByBuyer(ConstantType.PARAMETERS_ORDER_STRING).enqueue(
        new retrofit2.Callback<BaseResponse<GetUnFinishedOrderByUser>>() {
          @Override
          public void onResponse(Call<BaseResponse<GetUnFinishedOrderByUser>> call, Response<BaseResponse<GetUnFinishedOrderByUser>> response) {
            LoggerUtils.d(TAG, "" + response.body().getData().getGetUnfinishedOrderByBuyer().size());
            if (response.body().getData().getGetUnfinishedOrderByBuyer() == null) {
              LoggerUtils.d(TAG, "order is empty");
            } else {
              mOrderList = response.body().getData().getGetUnfinishedOrderByBuyer();
              processOrderResult(mOrderList);
            }
          }


          @Override public void onFailure(Call<BaseResponse<GetUnFinishedOrderByUser>> call, Throwable t) {

          }
        });
  }


  private void processOrderResult(List<Order> orderList) {
    Uri contentUri = getContentUri();
    mContext.getContentResolver().delete(contentUri, null, null);
    ContentValues contentValues = LokobeeOrderProvider.OrderContentValues(orderList);
    mContext.getContentResolver().insert(contentUri, contentValues);
  }


  private Uri getContentUri() {
    return LokobeeOrderProvider.ORDER_CONTEXT_URI;
  }
}
