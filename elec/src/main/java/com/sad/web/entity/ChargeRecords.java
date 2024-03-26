package com.sad.web.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sad.web.annotations.Excel;
import com.sad.web.entity.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 充电记录表
 * </p>
 *
 * @author Qinhy
 * @since 2023-12-26 08:53:03
 */
@Getter
@Setter
@TableName("E_CHARGE_RECORDS")
@ApiModel(value = "ChargeRecords对象", description = "充电记录表")
public class ChargeRecords extends BaseEntity {

    @ApiModelProperty("充电记录id")
    @TableId("RECORDS_ID")
    private String recordsId;

    @ApiModelProperty("充电桩code")
    @TableField("PILE_CODE")
    @Excel(name = "充电桩编号",type = Excel.Type.ALL)
    private String pileCode;

    @ApiModelProperty("车牌号")
    @TableField("CAR_NUMBER")
    @Excel(name = "车牌号",type = Excel.Type.ALL)
    private String carNumber;


    @ApiModelProperty("车辆类型")
    @TableField("CAR_TYPE")
    @Excel(name = "车辆类型", readConverterExp = "0=私家车,1=出租车,2=公交车",type = Excel.Type.ALL)
    private String carType;

    @ApiModelProperty("开始充电时间")
    @TableField("BEGIN_TIME")
    @Excel(name = "开始充电时间",width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss",type = Excel.Type.ALL)
    private Date beginTime;

    @ApiModelProperty("结束充电时间")
    @TableField("END_TIME")
    @Excel(name = "结束充电时间",width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss",type = Excel.Type.ALL)
    private Date endTime;

    @ApiModelProperty("充电收益")
    @TableField("END_TIME")
    private BigDecimal chargeEarn;

    @ApiModelProperty("删除标志（0代表未删除，1代表已删除）")
    @TableField("DEL_FLAG")
    @TableLogic
    private String delFlag;

}
