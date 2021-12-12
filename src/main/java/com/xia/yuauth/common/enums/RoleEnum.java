package com.xia.yuauth.common.enums;

/**
 * description: 角色枚举
 *
 * @author wanghaoxin
 * date     2021/11/28 10:35
 * @version 1.0
 */
public enum RoleEnum {
    /**
     * 默认角色
     */
    DEFAULT_ROLE(1);

    private int roleType;

    RoleEnum(int roleType) {
        this.roleType = roleType;
    }

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }
}
