package com.sad.web.entity.system;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.sad.web.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author Qinhy
 * @since 2023-07-21 04:55:21
 */
@Getter
@Setter
@TableName("sys_user")
@KeySequence(value = "SEQ_USER")
@ApiModel(value = "SysUser对象", description = "用户表")
public class SysUser extends BaseEntity {

    @ApiModelProperty("主键")
    @TableId(value = "user_id", type = IdType.INPUT)
    private Long userId;

    @ApiModelProperty("用户名")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty("昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty("手机号")
    @TableField("phone")
    private String phone;


    @ApiModelProperty("删除标志（0代表未删除，1代表已删除）")
    @TableField("del_flag")
    @TableLogic
    private String delFlag;

    @ApiModelProperty("角色ids")
    @TableField(exist = false)
    private Long[] roleIds;


    @TableField(exist = false)
    private List<SysRole> roles;


    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }


}
