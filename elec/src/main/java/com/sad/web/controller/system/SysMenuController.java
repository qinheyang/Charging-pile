package com.sad.web.controller.system;


import com.sad.web.controller.BaseController;
import com.sad.web.entity.ResponseResult;
import com.sad.web.entity.system.SysMenu;
import com.sad.web.entity.system.SysRole;
import com.sad.web.entity.system.SysUser;
import com.sad.web.security.SecurityUtil;
import com.sad.web.service.system.ISysMenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.xml.bind.annotation.W3CDomHandler;

/**
 * <p>
 *  系统菜单接口
 * </p>
 *
 * @since 2023-09-16 09:22:05
 */
@RestController
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController {
    @Resource
    private ISysMenuService sysMenuService;
    //获取菜单列表
    @GetMapping("/list")
    public ResponseResult list(SysMenu menu) {
        return ResponseResult.success(sysMenuService.list(menu));
    }
    //添加菜单
    @PostMapping("/add")
    public ResponseResult add(@RequestBody(required = false) SysMenu menu) {
        menu.setCreateBy(getCurrentLoginUserName());
        return ResponseResult.success(sysMenuService.insert(menu));
    }
    //修改菜单
    @PutMapping("/update")
    public ResponseResult update(@RequestBody(required = false) SysMenu menu) {
        return ResponseResult.success(sysMenuService.update(menu));
    }
    //获取菜单详情
    @GetMapping( "/{menuId}")
    public ResponseResult getInfo(@PathVariable(value = "menuId",required = false) Long menuId) {
        return ResponseResult.success(sysMenuService.getById(menuId));
    }
    //批量删除菜单
    @DeleteMapping("/delete/{menuIds}")
    public ResponseResult delete(@PathVariable(value = "menuIds",required = false) Long [] menuIds) {
        return ResponseResult.success(sysMenuService.deleteByIds(menuIds));
    }
    //获取当前用户能权限下的菜单树
    @GetMapping( "/treeselect")
    public ResponseResult treeSelect(){
        return ResponseResult.success(sysMenuService.getMenuTreeByUserId(getCurrentUserId()));
    }
    //获取某角色下的菜单
    @GetMapping("/rolemenu/{roleId}")
    public ResponseResult getRoleMenu(@RequestParam(value = "roleId",required = false)Long roleId){
        return ResponseResult.success(sysMenuService.getMenuListByRoId(roleId));
    }

}
