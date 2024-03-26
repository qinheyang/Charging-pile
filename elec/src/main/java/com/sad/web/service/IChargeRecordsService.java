package com.sad.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sad.web.entity.ChargeRecords;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sad.web.entity.vo.CommonCountVo;
import com.sad.web.entity.vo.DCommonCountVo;

import java.util.List;

/**
 * <p>
 * 充电记录表 服务类
 * </p>
 *
 * @author Qinhy
 * @since 2023-12-26 08:53:03
 */
public interface IChargeRecordsService extends IService<ChargeRecords> {

    IPage<ChargeRecords> list(ChargeRecords chargeRecords, Integer pageNum, Integer pageSize);

    int add(ChargeRecords chargeRecords);

    int update(ChargeRecords chargeRecords);

    int deleteByIds(String[] recordsIds);

    ChargeRecords getById(String recordsId);


    List<ChargeRecords> getChargeRecordsList(ChargeRecords chargeRecords);

    String importChargeRecords(List<ChargeRecords> list);


    List<CommonCountVo>getRecordsNum();

    List<DCommonCountVo> getRank();

    List<CommonCountVo> getBarChart();
    List<CommonCountVo> getLineChart();
}
