package com.sad.web.service.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sad.web.entity.system.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Qinhy
 * @since 2023-07-21 04:55:21
 */
public interface ISysUserService extends IService<SysUser> {
    public IPage<SysUser> list(SysUser sysUser, Integer pageNum, Integer pageSize);

    public SysUser getById(Long userId);


    public int  deleteByIds(Long[] userIds);

    public int  insert(SysUser user);

    public int update(SysUser user);

    public boolean checkUserNameUnique(SysUser user);
}
