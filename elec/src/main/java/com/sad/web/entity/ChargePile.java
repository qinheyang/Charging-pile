package com.sad.web.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sad.web.annotations.Excel;
import com.sad.web.entity.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 充电桩信息表
 * </p>
 *
 * @author Qinhy
 * @since 2023-12-25 09:46:48
 */
@Getter
@Setter
@TableName("E_CHARGE_PILE")
@ApiModel(value = "ChargePile对象", description = "充电桩信息表")
public class ChargePile extends BaseEntity {

    @ApiModelProperty("id")
    @TableId("PILE_ID")
    private String pileId;

    @ApiModelProperty("充电桩编号")
    @TableField("PILE_CODE")
    @Excel(name = "充电桩编号",type = Excel.Type.EXPORT)
    private String pileCode;

    @ApiModelProperty("经度")
    @TableField("LATITUDE")
    private BigDecimal latitude;

    @ApiModelProperty("维度")
    @TableField("LONGITUDE")
    private BigDecimal longitude;


    @ApiModelProperty("所属地区")
    @TableField("DISTRICT")
   // @Excel(name = "所属地区", type = Excel.Type.EXPORT)
    private String district;

    @ApiModelProperty("充电速度")
    @TableField("SPEED")
    @Excel(name = "充电速度",type = Excel.Type.EXPORT)
    private String speed;

    @ApiModelProperty("0 正常，1故障")
    @TableField("PILE_STATE")
    @Excel(name = "状态", readConverterExp = "0=正常,1=故障",type = Excel.Type.EXPORT)
    private String pileState;

    @ApiModelProperty("连接要求")
    @TableField("REQUIREMENT")
    @Excel(name = "连接要求",type = Excel.Type.EXPORT)
    private String requirement;

    @ApiModelProperty("价格")
    @TableField("PRICE")
    @Excel(name = "价格", cellType = Excel.ColumnType.NUMERIC,type = Excel.Type.EXPORT)
    private Long price;

    @ApiModelProperty("安装时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("INSTALL_TIME")
    @Excel(name = "安装时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    private Date installTime;

    @ApiModelProperty("最后一次检修时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("MAINTENANCE_TIME")
    private Date maintenanceTime;

    @ApiModelProperty("检修周期（单位月）")
    @TableField("MAINTENANCE_CYCLE")
    @Excel(name = "检修周期（单位月)",type = Excel.Type.EXPORT)
    private Integer maintenanceCycle;

    @ApiModelProperty("收费标准")
    @TableField("CHARGE_STANDARD")
    @Excel(name = "收费标准",prompt = "按每小时收费",type = Excel.Type.EXPORT, cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal chargeStandard;

    @ApiModelProperty("最大输出功率")
    @TableField("OUTPUT_POWER")
    @Excel(name = "最大输出功率",type = Excel.Type.EXPORT)
    private String outputPower;

    @ApiModelProperty("删除标志（0代表未删除，1代表已删除）")
    @TableField("DEL_FLAG")
    @TableLogic
    private String delFlag;



    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(exist = false)
    private Date beginInstallTime;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(exist = false)
    private Date endInstallTime;


}
