package com.sad.web.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sad.web.annotations.Excel;
import com.sad.web.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 充电桩保修
 * </p>
 *
 * @author Qinhy
 * @since 2023-12-26 04:24:12
 */
@Getter
@Setter
@TableName("E_REPORT")
@ApiModel(value = "Report对象", description = "充电桩保修")
public class Report extends BaseEntity {

    @TableId("REPORT_ID")
    private String reportId;

    @ApiModelProperty("故障类型")
    @TableField("FAULT_TYPE")
    @Excel(name = "故障类型", readConverterExp = "0=插头损坏,1=线路断裂,2=接触器故障,3=电源失效,4=其它",type = Excel.Type.EXPORT)
    private String faultType;

    @ApiModelProperty("是否维修0未维修，1维修")
    @TableField("REPAIRED")
    @Excel(name = "是否维修", readConverterExp = "0=未维修,1=已维修",type = Excel.Type.EXPORT)
    private String repaired;

    @ApiModelProperty("故障描述")
    @TableField("FAULT_DESCRIPTION")
    @Excel(name = "故障描述",type = Excel.Type.EXPORT)
    private String faultDescription;

    @ApiModelProperty("故障充电桩编号")
    @TableField("PILE_CODE")
    @Excel(name = "故障充电桩编号",type = Excel.Type.EXPORT)
    private String pileCode;

    @ApiModelProperty("删除标志（0代表未删除，1代表已删除）")
    @TableField("DEL_FLAG")
    @TableLogic
    private String delFlag;

    @ApiModelProperty("上报人")
    @TableField(exist = false)
    @Excel(name = "上报人",type = Excel.Type.EXPORT)
    private String nickName;


}
