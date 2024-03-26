package com.sad.web.service.system;

import com.sad.web.entity.system.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Qinhy
 * @since 2023-09-16 09:22:05
 */
public interface ISysMenuService extends IService<SysMenu> {

    public List<SysMenu> list(SysMenu menu);

    public int insert(SysMenu menu);

    public int  update(SysMenu menu);

    public SysMenu getById(Long menuId);

    public int deleteByIds(Long[] menuIds);

    public List<SysMenu> getMenuTreeByUserId(Long userId);

    public List<SysMenu> getMenuListByRoId(Long roId);
}
