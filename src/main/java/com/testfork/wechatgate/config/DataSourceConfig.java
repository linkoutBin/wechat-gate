package com.testfork.wechatgate.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

/**
 * Author: xingshulin Date: 2019/2/21 下午6:44
 *
 *
 * Description: 数据库配置类 Version: 1.0
 **/
@Configuration
public class DataSourceConfig {

  @Bean(name = "jade.dataSource.wechat")
  @Qualifier("jade.dataSource.wechat")
  @ConfigurationProperties(prefix = "db.datasource")
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean
  public JdbcTemplate jdbcTemplate(@Qualifier("jade.dataSource.wechat") DataSource dataSource) {
    return new JdbcTemplate(dataSource);

  }
}
