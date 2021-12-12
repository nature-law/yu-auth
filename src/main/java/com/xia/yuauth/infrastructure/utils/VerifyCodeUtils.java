package com.xia.yuauth.infrastructure.utils;

/**
 * description: 验证码生成工具
 *
 * @author wanghaoxin
 * date     2021/12/11 17:20
 * @version 1.0
 */
public class VerifyCodeUtils {
    private static final String BASE_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * description: 返回验证码
     *
     * @return char[] 验证码
     */
    public static char[] getRandomChars() {
        char[] rands = new char[6];
        for (int i = 0; i < 6; i++) {
            int rand = (int) (Math.random() * 35);
            rands[i] = BASE_CHARS.charAt(rand);
        }
        return rands;
    }
}
