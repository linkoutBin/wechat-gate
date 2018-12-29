package com.testfork.wechatgate.dao.impl;

import com.testfork.wechatgate.dao.WechatDao;
import com.testfork.wechatgate.domain.AppInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author: xingshulin
 * @Date: 2018/12/28 下午4:20
 * @Description: 微信端配置数据层
 * @Version: 1.0
 **/
@Repository
public class WechatDaoImpl implements WechatDao {
    private static String table_name = "appinfo";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<AppInfo> getAppInfos() {
        String sql = "SELECT * FROM " + table_name + " where status = 1";
        return jdbcTemplate.query(sql, new RowMapper<AppInfo>() {
            @Nullable
            @Override
            public AppInfo mapRow(ResultSet resultSet, int i) throws SQLException {
                AppInfo appInfo = new AppInfo();
                appInfo.setId(resultSet.getString("id"));
                appInfo.setAppId(resultSet.getString("appid"));
                appInfo.setKey(resultSet.getString("key"));
                appInfo.setSecret(resultSet.getString("secret"));
                appInfo.setStatus(resultSet.getInt("status"));
                appInfo.setWd(resultSet.getString("wid"));
                appInfo.setToken(resultSet.getString("token"));
                return appInfo;
            }
        });
    }
}
