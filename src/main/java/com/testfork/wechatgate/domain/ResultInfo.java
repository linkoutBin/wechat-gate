package com.testfork.wechatgate.domain;

import lombok.Data;

/**
 * @Author: xingshulin
 * @Date: 2019/2/1 下午12:06
 * @Description: TODO
 * @Version: 1.0
 **/
@Data
public class ResultInfo {

  boolean result = true;
  String reason = "";

  public ResultInfo() {
  }

  public ResultInfo(boolean result, String reason) {
    this.result = result;
    this.reason = reason;
  }
}
