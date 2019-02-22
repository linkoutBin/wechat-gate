package com.testfork.wechatgate.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import java.util.List;

@DAO(catalog = "wechat")
public interface WechatJadDAO {

  @SQL("select id from t_bank_bin where status = :1")
  List<Integer> getBankIds(int status);
}
