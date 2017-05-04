package com.example.kevin.demo.modle;

import java.util.List;

/**
 * @author by kevin.
 */

public class BaseResponse<T> {

  private T data;
  private List<ErrorBean> errors;


  public T getData() {
    return data;
  }


  public void setData(T data) {
    this.data = data;
  }


  public BaseResponse(List<ErrorBean> errors) {
    this.errors = errors;
  }


  public List<ErrorBean> getErrors() {
    return errors;
  }
}
