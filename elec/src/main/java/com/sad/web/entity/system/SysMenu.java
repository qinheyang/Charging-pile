package com.sad.web.entity.system;

import com.baomidou.mybatisplus.annotation.*;
import com.sad.web.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author Qinhy
 * @since 2023-09-16 04:56:29
 */
@Getter
@Setter
@TableName("sys_menu")
@KeySequence(value = "SEQ_MENU")
@ApiModel(value = "SysMenu对象", description = "菜单表")
public class SysMenu extends BaseEntity {

    @ApiModelProperty("菜单id")
    @TableId(value = "menu_id", type = IdType.INPUT)
    private Long menuId;

    @ApiModelProperty("菜单名称")
    @TableField("menu_name")
    private String menuName;

    @ApiModelProperty("菜单父级id")
    @TableField(value = "parent_id",updateStrategy = FieldStrategy.IGNORED)
    private Long parentId;

    @ApiModelProperty("地址")
    @TableField("path")
    private String path;

    @ApiModelProperty("图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty("组件地址")
    @TableField("component")
    private String component;

    @ApiModelProperty("菜单类型")
    @TableField("menu_type")
    private String menuType;

    @ApiModelProperty("是否是内联地址（0是，1不是）")
    @TableField("is_frame")
    private String isFrame;

    @ApiModelProperty("删除标志（0代表未删除，1代表已删除）")
    @TableField("del_flag")
    @TableLogic
    private String delFlag;
    
    @TableField(exist = false)
    private List<SysMenu> children;


}
