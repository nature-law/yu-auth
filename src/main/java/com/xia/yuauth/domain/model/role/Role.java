package com.xia.yuauth.domain.model.role;

import com.xia.yuauth.domain.model.entity.BaseEntity;
import org.springframework.data.relational.core.mapping.Table;

/**
 * description: 角色信息
 *
 * @author wanghaoxin
 * date     2021/11/29 22:52
 * @version 1.0
 */
@Table(value = "t_sys_user")
public class Role extends BaseEntity {
    private String name;
}
