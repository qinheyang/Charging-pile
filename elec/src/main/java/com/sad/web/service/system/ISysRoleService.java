package com.sad.web.service.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sad.web.entity.system.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sad.web.entity.system.SysUser;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Qinhy
 * @since 2023-09-11 08:47:03
 */
public interface ISysRoleService extends IService<SysRole> {

    public IPage<SysRole> list(SysRole role, Integer pageNum, Integer pageSize);


    public int deleteByIds(Long[] roleIds);

    public SysRole getById(Long roleId);

    public int add(SysRole role);

    public int update(SysRole role);

    public List<SysRole> getAllRoles();
}
