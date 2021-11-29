package com.xia.yuauth.domain.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.time.LocalDateTime;

/**
 * description: 所有的实体应该继承此类, 此类的 id 会通过 自动生成， 此类的 更新时间和创建时间会自动生成
 *
 * @author Administrator
 * @date 2021/11/29 13:42
 */
public abstract class BaseEntity {
	@Id
	private Long id;

	private LocalDateTime updateTime;
	private LocalDateTime createTime;
	private Long operatorId;
	@Version
	private Long version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}
