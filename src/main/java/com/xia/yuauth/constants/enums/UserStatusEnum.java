package com.xia.yuauth.constants.enums;

/**
 * description: 用户状态
 *
 * @author Administrator
 * @date 2021/11/29 15:30
 */
public enum UserStatusEnum {
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

	public static UserStatusEnum get(int status) {
		UserStatusEnum[] values = values();
		for (UserStatusEnum value : values) {
			if (value.getStatus() == status) {
				return value;
			}
		}
		throw new RuntimeException("未找到编码");
	}
}
