package com.sad.web.controller.system;


import com.sad.web.controller.BaseController;
import com.sad.web.entity.ResponseResult;
import com.sad.web.entity.system.SysMenu;
import com.sad.web.entity.system.SysRole;
import com.sad.web.entity.system.SysUser;
import com.sad.web.service.system.ISysMenuService;
import com.sad.web.service.system.ISysRoleService;
import com.sad.web.service.system.ISysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 系统角色接口
 * </p>
 *
 * @since 2023-09-11 08:47:03
 */
@RestController
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {
    @Resource
    private ISysRoleService sysRoleService;

    @Resource
    private ISysMenuService sysMenuService;

    //角色列表
    @GetMapping("/list")
    public ResponseResult list(SysRole role, @RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize) {
        return ResponseResult.success(sysRoleService.list(role, pageNum, pageSize));
    }

    //添加角色
    @PostMapping("/add")
    public ResponseResult add(@RequestBody(required = false) SysRole role) {
        return ResponseResult.success(sysRoleService.add(role));
    }
    //修改角色
    @PutMapping("/update")
    public ResponseResult update(@RequestBody(required = false) SysRole role) {
        return ResponseResult.success(sysRoleService.update(role));
    }
    //获取角色详情
    @GetMapping("/{roleId}")
    public ResponseResult getInfo(@PathVariable(value = "roleId", required = false) Long roleId) {
        SysRole role = sysRoleService.getById(roleId);
        role.setMenuIds(sysMenuService.getMenuListByRoId(roleId).stream().map(SysMenu::getMenuId).toArray(Long[]::new));
        return ResponseResult.success(role);
    }
    //批量删除角色
    @DeleteMapping("/delete/{roleIds}")
    public ResponseResult delete(@PathVariable(value = "roleIds", required = false) Long[] roleIds) {
        return ResponseResult.success(sysRoleService.deleteByIds(roleIds));
    }

}
