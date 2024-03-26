package com.sad.web.dao.system;

import com.sad.web.entity.system.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author Qinhy
 * @since 2023-07-21 04:55:21
 */

public interface SysUserDao extends BaseMapper<SysUser> {
    public SysUser selectUserById(@Param("userId") Long userId);

}
