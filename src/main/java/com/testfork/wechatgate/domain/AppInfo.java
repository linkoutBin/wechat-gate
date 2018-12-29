package com.testfork.wechatgate.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: xingshulin
 * @Date: 2018/12/28 下午5:09
 * @Description: 公众号配置信息
 * @Version: 1.0
 **/
@ToString
@Data
@NoArgsConstructor
public class AppInfo {
    private String id;
    private String wd;
    private String appId;
    private String token;
    private String key;
    private Integer status;
    private String secret;
}
