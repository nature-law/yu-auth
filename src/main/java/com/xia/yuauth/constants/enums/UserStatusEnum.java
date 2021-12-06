package com.xia.yuauth.constants.enums;

import com.xia.yuauth.exception.ServiceException;
import javassist.NotFoundException;

/**
 * description: 用户状态
 *
 * @author Administrator
 * @date 2021/11/29 15:30
 */
public enum UserStatusEnum implements EnumConvertible {
    /**
     * 在线
     */
    ONLINE(1),

    /**
     * 下线
     */
    OFFLINE(2);

    private int status;

    UserStatusEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static UserStatusEnum get(Object status) {
        UserStatusEnum[] values = values();
        for (UserStatusEnum value : values) {
            if (Integer.valueOf(value.getStatus()).equals(status)) {
                return value;
            }
        }
        throw new ServiceException(new NotFoundException("Enum not found!"), "未找到编码: %s", status);
    }

    @Override
    public Integer getValue() {
        return getStatus();
    }
}
