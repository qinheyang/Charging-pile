package com.sad.web.service.impl.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sad.web.entity.system.SysMenu;
import com.sad.web.dao.system.SysMenuDao;
import com.sad.web.entity.system.SysRole;
import com.sad.web.entity.system.SysUser;
import com.sad.web.service.system.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 系统菜单服务实现类
 * </p>
 * @since 2023-09-16 09:22:05
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenu> implements ISysMenuService {

    @Resource
    private SysMenuDao sysMenuDao;
    //获取系统菜单列表
    @Override
    public List<SysMenu> list(SysMenu menu) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<SysMenu>();
        if(!StringUtils.isEmpty(menu.getMenuName())){
            queryWrapper.like(SysMenu::getMenuName,"%" + menu.getMenuName() + "%");
        }
        return sysMenuDao.selectList(queryWrapper);
    }
    //添加系统菜单
    @Override
    public int insert(SysMenu menu) {
        return sysMenuDao.insert(menu);
    }
    //修改系统菜单
    @Override
    public int update(SysMenu menu) {
        LambdaUpdateWrapper<SysMenu> updateWrapper = new LambdaUpdateWrapper<SysMenu>();
        updateWrapper.eq(SysMenu::getMenuId, menu.getMenuId());
        return sysMenuDao.update(menu, updateWrapper);
    }
    //获取系统菜单详情
    @Override
    public SysMenu getById(Long menuId) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<SysMenu>();

        queryWrapper.select(SysMenu::getMenuId, SysMenu::getMenuName, SysMenu::getMenuType, SysMenu::getParentId, SysMenu::getPath, SysMenu::getIcon, SysMenu::getComponent, SysMenu::getIsFrame);
        queryWrapper.eq(SysMenu::getMenuId, menuId);
        return sysMenuDao.selectOne(queryWrapper);

    }
    //批量删除系统菜单
    @Override
    public int deleteByIds(Long[] menuIds) {
        return sysMenuDao.deleteBatchIds(Arrays.asList(menuIds));
    }
    //获取用户下权限菜单
    @Override
    public List<SysMenu> getMenuTreeByUserId(Long userId) {
        List<SysMenu> list = null;
        if (SysUser.isAdmin(userId)) {
            LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<SysMenu>();
            queryWrapper.select(SysMenu::getMenuId, SysMenu::getMenuName,SysMenu::getComponent,
                    SysMenu::getPath,SysMenu::getParentId, SysMenu::getMenuType,SysMenu::getIcon);
            queryWrapper.in(SysMenu::getMenuType,"M","C");
            list = sysMenuDao.selectList(queryWrapper);
        } else {
            list = sysMenuDao.getMenuListByUserId(userId);
        }
        List<SysMenu> treeList = new ArrayList<SysMenu>();
        for (SysMenu menu : list) {
            if (Objects.isNull(menu.getParentId())) {
                toTree(menu, list);
                treeList.add(menu);
            }
        }
        return treeList;
    }
    //获取角色下权限菜单
    @Override
    public List<SysMenu> getMenuListByRoId(Long roId) {
        return sysMenuDao.getMenuListByRoleId(roId);
    }
    //将列表转为树
    public void toTree(SysMenu menu, List<SysMenu> list) {
        List<SysMenu> childrenList = new ArrayList<SysMenu>();
        for (SysMenu sysMenu : list) {
            if (Objects.equals(menu.getMenuId(), sysMenu.getParentId())) {
                toTree(sysMenu, list);
                childrenList.add(sysMenu);
            }
        }
        menu.setChildren(childrenList);
    }
}
