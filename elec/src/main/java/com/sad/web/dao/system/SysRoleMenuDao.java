package com.sad.web.dao.system;

import com.sad.web.entity.system.SysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色菜单关系表 Mapper 接口
 * </p>
 *
 * @author Qinhy
 * @since 2023-09-26 09:39:09
 */
@Mapper
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenu> {

    public int insertBatchRoleMenu(@Param("roleId") Long roleId, @Param("menuIds") Long[] menuIds);


    public int  deleteByRoleId(@Param("roleId")Long roleId);

    public int deleteByRoleIds(Long[] roleIds);
}
