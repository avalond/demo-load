package com.example.kevin.demo.network;

/**
 * @author by kevin.
 */

public class ConstantType {
  public static final String SESSION_TOKEN = "r:4848c9ba1efcaa8a8cf177cb41dfec31";
  public static final String SERVER_URL = "https://lokobee.herokuapp.com/graphql/";

  public static final String PARAMETERS_ORDER_STRING
      = "query{getUnfinishedOrderByBuyer{id,totalAmount,seller{logoUrl,name,isLokobeePartner},orderStatus,expectTime}}";
}
