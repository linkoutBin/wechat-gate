package com.testfork.wechatgate.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

/**
 * Author: xingshulin Date: 2019/2/22 上午11:40
 *
 *
 * Description: 自定义处理类 Version: 1.0
 **/
@Slf4j
@Component
public class wechatBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

  @Override
  public void postProcessBeanFactory(
      ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    log.info("wechat自定义处理类{}", configurableListableBeanFactory.toString());
  }
}
