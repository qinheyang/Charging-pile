package com.sad.web.service.impl.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sad.web.dao.system.SysRoleMenuDao;
import com.sad.web.entity.system.SysRole;
import com.sad.web.dao.system.SysRoleDao;
import com.sad.web.entity.system.SysUser;
import com.sad.web.security.SecurityUtil;
import com.sad.web.service.system.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 系统角色服务实现类
 * </p>
 * @since 2023-09-11 08:47:03
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRole> implements ISysRoleService {

    @Resource
    private SysRoleDao sysRoleDao;
    @Resource
    private SysRoleMenuDao sysRoleMenuDao;
    //分页获取系统角色列表
    @Override
    public IPage<SysRole> list(SysRole role, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<SysRole> sysRoleLambdaQueryWrapper = new LambdaQueryWrapper<SysRole>();
        sysRoleLambdaQueryWrapper.select(SysRole::getRoleId, SysRole::getRoleName, SysRole::getCreateTime);
        if (!StringUtils.isEmpty(role.getRoleName())) {
            sysRoleLambdaQueryWrapper.like(SysRole::getRoleName, "%" + role.getRoleName() + "%");
        }
        sysRoleLambdaQueryWrapper.orderByDesc(SysRole::getCreateTime);
        Page<SysRole> rolePage = new Page<SysRole>(pageNum, pageSize);
        return sysRoleDao.selectPage(rolePage, sysRoleLambdaQueryWrapper);
    }

    //批量删除角色
    @Override
    public int deleteByIds(Long[] roleIds) {
        sysRoleMenuDao.deleteByRoleIds(roleIds);
        return sysRoleDao.deleteBatchIds(Arrays.asList(roleIds));
    }
    //获取角色详情
    @Override
    public SysRole getById(Long roleId) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<SysRole>();
        queryWrapper.select(SysRole::getRoleId, SysRole::getRoleName);
        queryWrapper.eq(SysRole::getRoleId, roleId);
        return sysRoleDao.selectOne(queryWrapper);
    }
    //添加角色
    @Override
    @Transactional
    public int add(SysRole role) {
        int row = sysRoleDao.insert(role);
        sysRoleMenuDao.insertBatchRoleMenu(role.getRoleId(),role.getMenuIds());
        return row;
    }
    //修改角色
    @Override
    public int update(SysRole role) {
        sysRoleMenuDao.deleteByRoleId(role.getRoleId());
        sysRoleMenuDao.insertBatchRoleMenu(role.getRoleId(),role.getMenuIds());
        LambdaUpdateWrapper<SysRole> updateWrapper = new LambdaUpdateWrapper<SysRole>();
        updateWrapper.eq(SysRole::getRoleId, role.getRoleId());
        return sysRoleDao.update(role, updateWrapper);
    }
    //获取角色下拉列表
    @Override
    public List<SysRole> getAllRoles() {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<SysRole>();
        queryWrapper.select(SysRole::getRoleId, SysRole::getRoleName);
        return sysRoleDao.selectList(queryWrapper);
    }
}
