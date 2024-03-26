package com.sad.web.entity.system;

import com.baomidou.mybatisplus.annotation.*;

import com.sad.web.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Qinhy
 * @since 2023-09-11 08:47:03
 */
@Getter
@Setter
@TableName("sys_role")
@KeySequence(value = "SEQ_ROLE")
@ApiModel(value = "SysRole对象", description = "角色表")
public class SysRole extends BaseEntity {

    @ApiModelProperty("角色id")
    @TableId(value = "role_id", type = IdType.INPUT)
    private Long roleId;

    @ApiModelProperty("角色名称")
    @TableField("role_name")
    private String roleName;

    @ApiModelProperty("删除标志（0代表未删除，1代表已删除）")
    @TableField("del_flag")
    @TableLogic
    private String delFlag;

    @ApiModelProperty("菜单ids")
    @TableField(exist = false)
    private Long[] menuIds;

    public boolean isAdmin()
    {
        return isAdmin(this.roleId);
    }

    public static boolean isAdmin(Long roleId)
    {
        return roleId != null && 1L == roleId;
    }

}
