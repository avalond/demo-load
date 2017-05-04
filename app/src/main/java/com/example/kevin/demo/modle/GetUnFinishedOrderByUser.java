package com.example.kevin.demo.modle;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

/**
 * @author by kevin.
 */

public class GetUnFinishedOrderByUser implements Serializable {
  @SerializedName("getUnfinishedOrderByBuyer")
  private List<Order> getUnfinishedOrderByBuyer;

  public List<Order> getGetUnfinishedOrderByBuyer() {
    return getUnfinishedOrderByBuyer;
  }


  public void setGetUnfinishedOrderByBuyer(List<Order> getUnfinishedOrderByBuyer) {
    this.getUnfinishedOrderByBuyer = getUnfinishedOrderByBuyer;
  }

}
