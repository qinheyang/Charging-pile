package com.sad.web.dao.system;

import com.sad.web.entity.system.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户角色关联表 Mapper 接口
 * </p>
 *
 * @author Qinhy
 * @since 2023-09-25 09:39:38
 */
@Mapper
public interface SysUserRoleDao extends BaseMapper<SysUserRole> {

   public int insertBatchUserRole(@Param("userId") Long userId, @Param("roleIds") Long[] roleIds);

   public int  deleteByUserId(@Param("userId")Long userId);
}
