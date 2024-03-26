package com.sad.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sad.web.dao.system.ChargePileDao;
import com.sad.web.entity.ChargePile;
import com.sad.web.entity.ChargeRecords;
import com.sad.web.dao.system.ChargeRecordsDao;
import com.sad.web.entity.vo.CommonCountVo;
import com.sad.web.entity.vo.DCommonCountVo;
import com.sad.web.exception.ServiceException;
import com.sad.web.security.SecurityUtil;
import com.sad.web.service.IChargeRecordsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sad.web.utils.DateUtil;
import com.sad.web.utils.StringUtils;
import com.sad.web.utils.uuid.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 充电记录服务实现类
 * </p>
 *
 * @since 2023-12-26 08:53:03
 */
@Service
public class ChargeRecordsServiceImpl extends ServiceImpl<ChargeRecordsDao, ChargeRecords> implements IChargeRecordsService {
    @Resource
    private ChargeRecordsDao chargeRecordsDao;
    @Resource
    private ChargePileDao chargePileDao;
    //分页获取充电记录列表
    @Override
    public IPage<ChargeRecords> list(ChargeRecords chargeRecords, Integer pageNum, Integer pageSize) {
        Page<ChargeRecords> page = new Page<ChargeRecords>(pageNum, pageSize);
        return chargeRecordsDao.selectChargeRecordsList(page, chargeRecords);
    }
    //添加充电记录
    @Override
    public int add(ChargeRecords chargeRecords) {
        Long beginTime = chargeRecords.getBeginTime().getTime();
        Long endTime = chargeRecords.getEndTime().getTime();
        //计算充电时长
        long hours =(long) Math.ceil((double) (endTime- beginTime)/ 60 / 60 / 1000);
        //获取充电桩收费标准
        ChargePile pile = chargePileDao.selectChargePileByPileCode(chargeRecords.getPileCode());
        //计算本次充电桩收费
        chargeRecords.setChargeEarn(pile.getChargeStandard().multiply(new BigDecimal(hours)));
        return chargeRecordsDao.insertChargeRecords(chargeRecords);
    }
    //修改充电记录
    @Override
    public int update(ChargeRecords chargeRecords) {
        Long beginTime = chargeRecords.getBeginTime().getTime();
        Long endTime = chargeRecords.getEndTime().getTime();
        //计算充电时长
        long hours =(long) Math.ceil((double) (endTime- beginTime)/ 60 / 60 / 1000);
        //获取充电桩收费标准
        ChargePile pile = chargePileDao.selectChargePileByPileCode(chargeRecords.getPileCode());
        //计算本次充电桩收费
        chargeRecords.setChargeEarn(pile.getChargeStandard().multiply(new BigDecimal(hours)));
        return chargeRecordsDao.updateChargeRecords(chargeRecords);
    }
    //批量删除充电记录
    @Override
    public int deleteByIds(String[] recordsIds) {
        return chargeRecordsDao.deleteChargeRecordsByRecordsIds(recordsIds);
    }
    //获取充电记录的详细信息
    @Override
    public ChargeRecords getById(String recordsId) {
        return chargeRecordsDao.selectChargeRecordsByRecordsId(recordsId);
    }
    //获取充电记录列表不分页
    @Override
    public List<ChargeRecords> getChargeRecordsList(ChargeRecords chargeRecords) {
        return chargeRecordsDao.getChargeRecordsList(chargeRecords);
    }
    //导入充电记录
    @Override
    @Transactional
    public String importChargeRecords(List<ChargeRecords> list) {
        if (StringUtils.isNull(list) || list.size() == 0)
        {
            throw new ServiceException("导入的数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        Date now = DateUtil.getNowDate();
        for (ChargeRecords records : list) {
            try {
                ChargePile chargePile =   chargePileDao.selectChargePileByPileCode(records.getPileCode());
                if(chargePile==null){
                    failureNum++;
                    int current_num = failureNum+successNum;
                    String msg = "<br/>第"+current_num+"条数据导入失败：";
                    failureMsg.append(msg+records.getPileCode()+"不存在");
                    continue;
                }
                records.setRecordsId(UUIDUtil.generateUUID());
                records.setCreateTime(now);
                Long beginTime = records.getBeginTime().getTime();
                Long endTime = records.getEndTime().getTime();
                long hours =(long) Math.ceil((double) (endTime- beginTime)/ 60 / 60 / 1000);
                ChargePile pile = chargePileDao.selectChargePileByPileCode(records.getPileCode());
                records.setChargeEarn(pile.getChargeStandard().multiply(new BigDecimal(hours)));
                records.setCreateBy(SecurityUtil.getLoginUser().getUsername());
                chargeRecordsDao.insertChargeRecords(records);
                successNum++;
                //successMsg.append("<br/>" + successNum + "第"+failureNum+successNum+"数据导入成功");
            }catch (Exception e){
                failureNum++;
                int current_num = failureNum+successNum;
                String msg = "<br/>第"+current_num+"条数据导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条");
        }
        return successMsg.toString();
    }
    //按时间统计充电流量
    @Override
    public List<CommonCountVo> getRecordsNum() {
        return chargeRecordsDao.getRecordsNum();
    }
    //获取各地区充电收益
    @Override
    public List<DCommonCountVo> getRank() {
        return chargeRecordsDao.getRank();
    }
    //获取不同充电车辆类型的数量
    @Override
    public List<CommonCountVo> getBarChart() {
        return chargeRecordsDao.getBarChart();
    }
    //获取仅七天的数量
    @Override
    public List<CommonCountVo> getLineChart() {
        return chargeRecordsDao.getLineChart();
    }
}
