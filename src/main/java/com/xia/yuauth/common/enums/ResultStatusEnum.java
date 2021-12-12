package com.xia.yuauth.common.enums;

/**
 * description 返回数据状态
 *
 * @author wanghaoxin
 * date     2021/11/28 10:42
 * @version 1.0
 */
public enum ResultStatusEnum {
    /**
     * 成功
     */
    SUCCESS("00000"),

    SYSTEM_ERROR("B0001"),

    PARAMS_NOT_MATCHES("A0421");

    private String code;

    ResultStatusEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
