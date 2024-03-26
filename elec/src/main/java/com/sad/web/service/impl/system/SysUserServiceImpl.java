package com.sad.web.service.impl.system;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sad.web.dao.system.SysUserRoleDao;
import com.sad.web.entity.system.SysUser;
import com.sad.web.dao.system.SysUserDao;
import com.sad.web.service.system.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Objects;


/**
 * <p>
 * 用户服务实现类
 * </p>
 * @since 2023-07-21 04:55:21
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements ISysUserService {
    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private SysUserDao sysUserDao;

    @Resource
    private SysUserRoleDao sysUserRoleDao;
    //分页获取用户列表
    @Override
    public IPage<SysUser> list(SysUser sysUser, Integer pageNum, Integer pageSize) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper();
        //设置查询项
        queryWrapper.select("user_id", "user_name", "nick_name", "phone", "email", "create_time");
        //设置查询条件
        if (!StringUtils.isEmpty(sysUser.getUserName())) {
            queryWrapper.like("user_name", "%" + sysUser.getUserName() + "%");
        }
        if (!StringUtils.isEmpty(sysUser.getNickName())) {
            queryWrapper.like("nick_name", "%" + sysUser.getNickName() + "%");
        }
        if (!StringUtils.isEmpty(sysUser.getPhone())) {
            queryWrapper.like("phone", "%" + sysUser.getPhone() + "%");
        }
        if (!StringUtils.isEmpty(sysUser.getEmail())) {
            queryWrapper.like("email", "%" + sysUser.getEmail() + "%");
        }
        //按时间倒叙
        queryWrapper.orderByDesc("create_time");
        Page<SysUser> userPage = new Page<SysUser>(pageNum, pageSize);
        //分页查询用户数据
        return sysUserDao.selectPage(userPage, queryWrapper);
    }
    //获取用户详细信息
    @Override
    public SysUser getById(Long userId) {
        SysUser user = sysUserDao.selectUserById(userId);
        return user;
    }
    //删除用户
    @Override
    public int deleteByIds(Long[] userIds) {
        return sysUserDao.deleteBatchIds(Arrays.asList(userIds));
    }
    //添加用户
    @Override
    @Transactional
    public int insert(SysUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        int row = sysUserDao.insert(user);
        sysUserRoleDao.insertBatchUserRole(user.getUserId(), user.getRoleIds());
        return row;
    }
    //修改用户
    @Override
    @Transactional
    public int update(SysUser user) {
        UpdateWrapper<SysUser> wrapper = new UpdateWrapper<SysUser>();
        sysUserRoleDao.deleteByUserId(user.getUserId());
        sysUserRoleDao.insertBatchUserRole(user.getUserId(), user.getRoleIds());
        wrapper.eq("user_id", user.getUserId());
        return sysUserDao.update(user, wrapper);
    }
    //验证用户名唯一性
    @Override
    public boolean checkUserNameUnique(SysUser user) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<SysUser>();
        if(!Objects.isNull(user.getUserId())){
            queryWrapper.ne(SysUser::getUserId,user.getUserId());
        }
        queryWrapper.eq(SysUser::getUserName, user.getUserName());
        SysUser sysUser = sysUserDao.selectOne(queryWrapper);
        if (Objects.isNull(sysUser)) {
            return true;
        }
        return false;
    }


}
