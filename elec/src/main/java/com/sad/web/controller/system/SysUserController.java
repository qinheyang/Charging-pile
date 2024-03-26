package com.sad.web.controller.system;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sad.web.controller.BaseController;
import com.sad.web.entity.ResponseResult;
import com.sad.web.entity.system.SysRole;
import com.sad.web.entity.system.SysUser;
import com.sad.web.service.system.ISysRoleService;
import com.sad.web.service.system.ISysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户接口
 * </p>
 * @since 2023-07-21 04:55:21
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysRoleService sysRoleService;
    //获取用户列表
    @GetMapping("/list")
    public ResponseResult list(SysUser user, @RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize) {
        return ResponseResult.success(sysUserService.list(user, pageNum, pageSize));
    }
    //添加用户
    @PostMapping("/add")
    public ResponseResult add(@RequestBody(required = false) SysUser user) {
        if(!sysUserService.checkUserNameUnique(user)){
            return ResponseResult.error("用户名已存在");
        }
        user.setCreateBy(getCurrentLoginUserName());
        return ResponseResult.success(sysUserService.insert(user));
    }
    //修改用户
    @PutMapping("/update")
    public ResponseResult update(@RequestBody(required = false) SysUser user) {
        if(!sysUserService.checkUserNameUnique(user)){
            return ResponseResult.error("用户名已存在");
        }
        return ResponseResult.success(sysUserService.update(user));

    }
    //获取用户详细信息
    @GetMapping({"/{userId}", "/"})
    public ResponseResult getInfo(@PathVariable(value = "userId", required = false) Long userId) {
        ResponseResult result = ResponseResult.success();
        List<SysRole> roles = sysRoleService.getAllRoles();
        if (!Objects.isNull(userId)) {
            SysUser sysUser = sysUserService.getById(userId);
            sysUser.setRoleIds(sysUser.getRoles().stream().map(SysRole::getRoleId).toArray(Long[]::new));
            result.put(ResponseResult.DATA_TAG,sysUser);
        }else{
            userId =getCurrentUserId();
        }
        result.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        return result;
    }
    //批量删除用户信息
    @DeleteMapping("/delete/{userIds}")
    public ResponseResult delete(@PathVariable(value = "userIds", required = false) Long[] userIds) {
        return ResponseResult.success(sysUserService.deleteByIds(userIds));
    }
}
