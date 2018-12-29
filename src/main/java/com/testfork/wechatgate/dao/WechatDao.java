package com.testfork.wechatgate.dao;

import com.testfork.wechatgate.domain.AppInfo;

import java.util.List;

public interface WechatDao {
    List<AppInfo> getAppInfos();
}
