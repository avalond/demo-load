package com.example.kevin.demo.services;

import com.example.kevin.demo.database.LokobeeDatabaseTable;
import com.example.kevin.demo.modle.BaseResponse;
import com.example.kevin.demo.modle.GetUnFinishedOrderByUser;
import com.example.kevin.demo.modle.Order;
import com.example.kevin.demo.network.ConstantType;
import com.example.kevin.demo.network.LokobeeNetFactory;
import com.example.kevin.demo.provider.LokobeeOrderProvider;
import com.example.kevin.demo.utils.LoggerUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
      case LokobeeService.DELETE_ORDER:
        deleteOrderWithId(msg);
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

    for (Order order : orderList) {
      ContentValues contentValues = LokobeeOrderProvider.OrderContentValues(order);
      mContext.getContentResolver().insert(contentUri, contentValues);
    }

    LoggerUtils.e(TAG, "processOrderResult---->>");
  }


  private List<Order> removeDuplicate(List<Order> orderList) {
    Set<Order> set = new HashSet<>();
    List<Order> newlist = new ArrayList<>();
    for (Order element : orderList) {
      if (set.add(element)) {
        newlist.add(element);
      }
    }
    return newlist;
  }


  public void deleteOrderWithId(Message message) {
    String orderId = message.getData().getString("orderId");
    //send server delete if server back message is ok, delete with database

    Uri uri = getContentUri();
    mContext.getContentResolver().delete(uri, LokobeeDatabaseTable.COLUMN_ORDER_ID + "=?", new String[] { orderId });
  }


  private Uri getContentUri() {
    return LokobeeOrderProvider.ORDER_CONTEXT_URI;
  }
}
