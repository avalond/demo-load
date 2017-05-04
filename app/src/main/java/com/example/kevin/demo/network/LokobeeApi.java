package com.example.kevin.demo.network;

import com.example.kevin.demo.modle.BaseResponse;
import com.example.kevin.demo.modle.GetUnFinishedOrderByUser;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author by kevin.
 */

public interface LokobeeApi {
  //getUnfinishedOrderByBuyer
  @POST("/graphql")
  Call<BaseResponse<GetUnFinishedOrderByUser>> GetUnfinishedOrderByBuyer(@Query("query") String parameters);
}
