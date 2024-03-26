package com.sad.web.service.system;


import com.sad.web.entity.system.SysUser;

public interface ILoginService {
    String login(SysUser user);

    void logout();
}
