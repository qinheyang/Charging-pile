package com.sad.web.service.impl.system;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sad.web.dao.system.SysUserDao;
import com.sad.web.entity.system.LoginUser;
import com.sad.web.entity.system.SysUser;
import com.sad.web.exception.ServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private SysUserDao sysUserDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>();
        wrapper.eq(SysUser::getUserName,s);
        SysUser user = sysUserDao.selectOne(wrapper);
        if(Objects.isNull(user)){
            throw new ServiceException("用户"+s+"不存在");
        }
        return new LoginUser(user);
    }
}
