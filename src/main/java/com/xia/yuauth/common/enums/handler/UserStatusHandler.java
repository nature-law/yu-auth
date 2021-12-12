package com.xia.yuauth.common.enums.handler;

import com.xia.yuauth.common.enums.UserStatusEnum;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * description: 用户枚举转换器
 *
 * @author wanghaoxin
 * date     2021/12/5 17:51
 * @version 1.0
 */
@Component
public class UserStatusHandler implements TypeHandler<UserStatusEnum> {

    @Override
    public void setParameter(PreparedStatement ps, int i, UserStatusEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getStatus());
    }

    @Override
    public UserStatusEnum getResult(ResultSet rs, String columnName) throws SQLException {
        return UserStatusEnum.get(rs.getInt(columnName));
    }

    @Override
    public UserStatusEnum getResult(ResultSet rs, int columnIndex) throws SQLException {
        return UserStatusEnum.get(rs.getInt(columnIndex));
    }

    @Override
    public UserStatusEnum getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return UserStatusEnum.get(cs.getInt(columnIndex));
    }
}
