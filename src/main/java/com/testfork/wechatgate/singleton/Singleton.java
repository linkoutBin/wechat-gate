package com.testfork.wechatgate.singleton;

import org.springframework.beans.factory.DisposableBean;
import lombok.extern.slf4j.Slf4j;

/**
 * Author: xingshulin Date: 2019/2/25 下午2:05
 *
 *
 * Description: 单例类 Version: 1.0
 **/
@Slf4j
public class Singleton implements DisposableBean {

  private Singleton() {
    log.info("初始化单例父类");
  }

  private static class SingletonInstance {

    private static final Singleton INSTANCE = new Singleton();

    private SingletonInstance() {
      log.info("初始化单例内部类");
    }
  }

  public static Singleton getInstance() {
    return SingletonInstance.INSTANCE;
  }

  public void print(String message) {
    log.info(message);
  }

  @Override
  public void destroy() throws Exception {
    log.info("销毁singleton");
  }
}
