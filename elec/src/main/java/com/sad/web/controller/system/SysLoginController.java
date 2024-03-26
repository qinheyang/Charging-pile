package com.sad.web.controller.system;


import com.sad.web.constant.SystemConstant;
import com.sad.web.controller.BaseController;
import com.sad.web.entity.ResponseResult;
import com.sad.web.entity.system.SysMenu;
import com.sad.web.entity.system.SysUser;
import com.sad.web.security.SecurityUtil;
import com.sad.web.service.system.ILoginService;
import com.sad.web.service.system.ISysMenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统登录接口
 */
@RestController
public class SysLoginController extends BaseController {
    @Resource
    private ILoginService loginService;

    @Resource
    private ISysMenuService menuService;
    //登录
    @PostMapping("/login")
    public ResponseResult login(@RequestBody SysUser user) {
        ResponseResult responseResult = ResponseResult.success();
        responseResult.put(SystemConstant.TOKEN, loginService.login(user));
        return responseResult;
    }
    //获取路由
    @GetMapping("/getRouters")
    public ResponseResult getRouters() {
        Long userId = SecurityUtil.getUserId();
        List<SysMenu> list = menuService.getMenuTreeByUserId(userId);
        return ResponseResult.success(list);
    }

    //获取当前用户
    @GetMapping("/getCurrentUser")
    public ResponseResult getCurrentUser() {

        return ResponseResult.success(getCurrentLoginUser());
    }
//    @PostMapping("/logout")
//    public ResponseResult login() {
//        ResponseResult responseResult = ResponseResult.success();
//
//        return responseResult;
//    }


}
