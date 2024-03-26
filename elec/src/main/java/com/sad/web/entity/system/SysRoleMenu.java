package com.sad.web.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sad.web.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色菜单关系表
 * </p>
 *
 * @author Qinhy
 * @since 2023-09-26 09:39:09
 */
@Getter
@Setter
@TableName("sys_role_menu")
@ApiModel(value = "SysRoleMenu对象", description = "角色菜单关系表")
public class SysRoleMenu extends BaseEntity {

    @ApiModelProperty("角色id")
    @TableField("role_id")
    private Long roleId;

    @ApiModelProperty("菜单id")
    @TableField("menu_id")
    private Long menuId;


}
