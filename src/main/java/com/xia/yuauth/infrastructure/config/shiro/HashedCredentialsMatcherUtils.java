package com.xia.yuauth.infrastructure.config.shiro;

import org.apache.shiro.crypto.hash.Sha256Hash;

/**
 * description: 加密存储工具类
 *
 * @author wanghaoxin
 * date     2022/1/16 16:32
 * @version 1.0
 */
public class HashedCredentialsMatcherUtils {
    /**
     * use sha256 encrypt
     */
    public static char[] encryptPassword(char[] password) {
        Sha256Hash hashPassword = new Sha256Hash(password, null, ShiroConfig.HASH_TIMES);
        return hashPassword.toString().toCharArray();
    }
}
