package com.testfork.wechatgate.controller;

import com.testfork.wechatgate.exception.BusinessException;
import com.testfork.wechatgate.utils.EncoderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @Author: xingshulin
 * @Date: 2018/12/27 上午11:53
 * @Description: 微信服务通知消息处理类
 * @Version: 1.0
 **/
@RestController
@RequestMapping("wx")
public class WechatMessageController {

    private static Logger logger = LoggerFactory.getLogger(WechatMessageController.class);

    @Value("${wechat.token}")
    private String token;

    @RequestMapping("/sign")
    public String verify(HttpServletRequest request) throws BusinessException {
        //获取微信服务器验证数据
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        //微信规则校验
        String[] params = {signature, timestamp, nonce, echostr};
        if (checkParamsIfNull(params)) {
            logger.error("微信服务器验证参数校验失败[存在空值字段]:【{}】", request.getQueryString());
            return null;
        }
        params = new String[]{timestamp, nonce, token};
        Arrays.sort(params);
        String sign = null;
        try {
            sign = EncoderUtil.getSha1Hex(StringUtils.arrayToDelimitedString(params, ""));
        } catch (BusinessException e) {
            logger.error("验证微信服务器消息来源异常", e);
        }

        if (signature.equalsIgnoreCase(sign)) {
            logger.info("微信服务器消息来源验证通过");
            String body = dealWithRequestBody(request);
            return StringUtils.isEmpty(body) ? null : wechatMessageProcess(body);
        }
        logger.error("微信服务器消息来源验证失败,【{}】", request.getQueryString());
        return null;
    }

    private String wechatMessageProcess(String body) {

        return null;
    }

    private String dealWithRequestBody(HttpServletRequest request) throws BusinessException {
        String bodyString;
        try {
            InputStream is = request.getInputStream();
            int sum = 0;
            int len;
            byte[] temp = new byte[1024];
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while ((len = is.read(temp)) != -1) {
                byteBuffer.put(temp);
                sum += len;
            }
            byte[] body = new byte[sum];
            for (int i = 0; i < sum; i++) {
                body[i] = byteBuffer.get(i);
            }
            bodyString = new String(body, Charset.forName("UTF-8"));
        } catch (IOException ioe) {
            String err = "读取微信服务器通知内容失败";
            logger.error(err, ioe);
            throw new BusinessException(err);
        }
        logger.info("读取到的微信服务器通知内容为：{}", bodyString);
        return StringUtils.isEmpty(bodyString) ? null : bodyString;
    }

    private static boolean checkParamsIfNull(String... params) {
        for (String param : params) {
            if (StringUtils.isEmpty(param)) {
                return true;
            }
        }
        return false;
    }

}
