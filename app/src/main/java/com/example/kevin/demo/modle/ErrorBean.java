package com.example.kevin.demo.modle;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * @author by kevin.
 */

public class ErrorBean implements Serializable {

  @SerializedName("message") public String message;


  public String getMessage() {
    return message;
  }


  public void setMessage(String message) {
    this.message = message;
  }


}
