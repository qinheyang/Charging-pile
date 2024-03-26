package com.sad.web.dao.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sad.web.entity.ChargeRecords;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sad.web.entity.vo.CommonCountVo;
import com.sad.web.entity.vo.DCommonCountVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 充电记录表 Mapper 接口
 * </p>
 *
 * @author Qinhy
 * @since 2023-12-26 08:53:03
 */
@Mapper
public interface ChargeRecordsDao extends BaseMapper<ChargeRecords> {
    IPage<ChargeRecords> selectChargeRecordsList(@Param("page") IPage<ChargeRecords> page ,@Param("records") ChargeRecords chargeRecords);


    /**
     * 新增充电记录
     *
     * @param eChargeRecords 充电记录
     * @return 结果
     */
    public int insertChargeRecords(ChargeRecords eChargeRecords);

    /**
     * 修改充电记录
     *
     * @param eChargeRecords 充电记录
     * @return 结果
     */
    public int updateChargeRecords(ChargeRecords eChargeRecords);


    /**
     * 查询充电记录
     *
     * @param recordsId 充电记录主键
     * @return 充电记录
     */
    public ChargeRecords selectChargeRecordsByRecordsId(String recordsId);


    /**
     * 批量删除充电记录
     *
     * @param recordsIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteChargeRecordsByRecordsIds(String[] recordsIds);


    List<ChargeRecords> getChargeRecordsList(@Param("records") ChargeRecords chargeRecords);



    List<CommonCountVo> getRecordsNum();
    List<DCommonCountVo> getRank();


    List<CommonCountVo> getBarChart();
    List<CommonCountVo> getLineChart();



}
