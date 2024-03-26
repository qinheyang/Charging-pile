package com.sad.web.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sad.web.annotations.Excel;
import com.sad.web.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>
 * 维修记录表
 * </p>
 *
 * @author Qinhy
 * @since 2023-12-27 10:00:54
 */
@Getter
@Setter
@TableName("E_REPAIR")
@ApiModel(value = "Repair对象", description = "维修记录表")
public class Repair extends BaseEntity {

    @ApiModelProperty("维修id")
    @TableId("REPAIR_ID")
    private String repairId;

    @ApiModelProperty("报告id")
    @TableField("REPORT_ID")
    private String reportId;

    @ApiModelProperty("维修描述")
    @TableField("REPAIR_DESCRIPTION")
    @Excel(name = "维修描述",type = Excel.Type.EXPORT)
    private String repairDescription;

    @TableField(exist = false)
    @Excel(name = "维修人",type = Excel.Type.EXPORT)
    private String repairName;
    @TableField(exist = false)
    @Excel(name = "上报人",type = Excel.Type.EXPORT)
    private String reportName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(exist = false)
    @Excel(name = "维修时间",width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss",type = Excel.Type.EXPORT)
    private Date repairTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(exist = false)
    @Excel(name = "上报时间",width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss",type = Excel.Type.EXPORT)
    private Date reportTime;

    @TableField(exist = false)
    @Excel(name = "故障类型", readConverterExp = "0=插头损坏,1=线路断裂,2=接触器故障,3=电源失效,4=其它",type = Excel.Type.EXPORT)
    private String faultType;

    @TableField(exist = false)
    private String pileCode;


}
