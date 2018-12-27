package com.testfork.wechatgate.utils;

import com.testfork.wechatgate.exception.BusinessException;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: xingshulin
 * @Date: 2018/12/27 下午2:10
 * @Description: 加密类 (eg: signature=b89d4513b5fda794714a47fd2cabf1e1385dd85b&echostr=3489552456423997118&timestamp=1545894580&nonce=1251432476)
 * @Version: 1.0
 **/

public class EncoderUtil {

    private static final char[] HEX_DIGEST = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String getSha1Hex(String message) throws BusinessException {
        if (StringUtils.isEmpty(message))
            throw new BusinessException("待加密信息不能为空");
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(message.getBytes());
            return getHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new BusinessException("获取SHA1加密信息失败", e);
        }
    }

    private static String getHexString(byte[] digest) {
        int len = digest.length;
        StringBuilder sb = new StringBuilder(len * 2);
        for (int i = 0; i < len; i++) {
            sb.append(HEX_DIGEST[digest[i] >> 4 & 0x0f]);
            sb.append(HEX_DIGEST[digest[i] & 0x0f]);
        }
        return sb.toString();
    }

}
