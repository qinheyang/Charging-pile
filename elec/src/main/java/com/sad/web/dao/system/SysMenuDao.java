package com.sad.web.dao.system;

import com.sad.web.entity.system.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author Qinhy
 * @since 2023-09-16 04:56:29
 */
@Mapper
public interface SysMenuDao extends BaseMapper<SysMenu> {

    List<SysMenu> getMenuListByUserId(@Param("userId")Long userId);

    List<SysMenu> getMenuListByRoleId(@Param("roleId")Long roleId);




}
