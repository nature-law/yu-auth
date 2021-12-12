package com.xia.yuauth.domain.model.user;

import com.xia.yuauth.common.enums.UserStatusEnum;
import com.xia.yuauth.domain.model.entity.BaseEntity;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;


/**
 * description: 用户基本信息
 *
 * @author Andy Wong
 * date 2021/11/25 9:37
 */
@Table(value = "t_sys_user")
public class User extends BaseEntity {

    private String username;
    private String telephone;
    private String mail;
    private String password;
    private String remark;

    private UserStatusEnum status;

    @PersistenceConstructor
    public User() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public UserStatusEnum getStatus() {
        return status;
    }

    public void setStatus(UserStatusEnum status) {
        this.status = status;
    }
}
