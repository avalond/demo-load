package com.example.kevin.demo.modle;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * @author by davzhu01 on 8/14/16.
 */

public class Order implements Serializable {
  @SerializedName("id") private String id;
  @SerializedName("uuid") private String uuid;
  @SerializedName("type") public String type;
  @SerializedName("orderStatus") private String orderStatus;
  @SerializedName("note") private String note;
  @SerializedName("expectTime") private long expectTime;
  @SerializedName("totalAmount") private Double totalAmount;


  public String getId() {
    return id;
  }


  public void setId(String id) {
    this.id = id;
  }


  public String getUuid() {
    return uuid;
  }


  public void setUuid(String uuid) {
    this.uuid = uuid;
  }


  public String getType() {
    return type;
  }


  public void setType(String type) {
    this.type = type;
  }


  public String getOrderStatus() {
    return orderStatus;
  }


  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }


  public String getNote() {
    return note;
  }


  public void setNote(String note) {
    this.note = note;
  }


  public long getExpectTime() {
    return expectTime;
  }


  public void setExpectTime(long expectTime) {
    this.expectTime = expectTime;
  }


  public Double getTotalAmount() {
    return totalAmount;
  }


  public void setTotalAmount(Double totalAmount) {
    this.totalAmount = totalAmount;
  }
}
