package com.sad.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sad.web.entity.ChargePile;
import com.sad.web.dao.system.ChargePileDao;

import com.sad.web.entity.vo.CommonCountVo;
import com.sad.web.service.IChargePileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 充电桩服务实现类
 * </p>
 *
 * @since 2023-12-25 09:46:48
 */
@Service
public class ChargePileServiceImpl extends ServiceImpl<ChargePileDao, ChargePile> implements IChargePileService {
    @Resource
    private ChargePileDao chargePileDao;

    @Override
    //查询充电桩列表
    public IPage<ChargePile> list(ChargePile chargePile, Integer pageNum, Integer pageSize) {
        //设置查询
        LambdaQueryWrapper<ChargePile> lambdaQueryWrapper = new LambdaQueryWrapper<ChargePile>();
        lambdaQueryWrapper.select(ChargePile::getPileId, ChargePile::getPileCode, ChargePile::getPileState, ChargePile::getRequirement, ChargePile::getSpeed, ChargePile::getChargeStandard, ChargePile::getInstallTime, ChargePile::getOutputPower, ChargePile::getMaintenanceCycle, ChargePile::getPrice, ChargePile::getMaintenanceTime, ChargePile::getCreateTime);
        //对充电桩编号进行模糊搜索
        if (!StringUtils.isEmpty(chargePile.getPileCode())) {
            lambdaQueryWrapper.like(ChargePile::getPileCode, "%" + chargePile.getPileCode() + "%");
        }
        //对充电桩状态进行搜索
        if (!StringUtils.isEmpty(chargePile.getPileState())) {
            lambdaQueryWrapper.eq(ChargePile::getPileState,chargePile.getPileState());
        }
        //对充电桩充电速度进行模糊搜索
        if (!StringUtils.isEmpty(chargePile.getSpeed())) {
            lambdaQueryWrapper.like(ChargePile::getSpeed, "%" + chargePile.getSpeed() + "%");
        }
        //对充电桩输出功率进行模糊搜索
        if (!StringUtils.isEmpty(chargePile.getOutputPower())) {
            lambdaQueryWrapper.like(ChargePile::getOutputPower, "%" + chargePile.getOutputPower() + "%");
        }
        //对充电桩连接要求进行模糊搜索
        if (!StringUtils.isEmpty(chargePile.getRequirement())) {
            lambdaQueryWrapper.like(ChargePile::getRequirement, "%" + chargePile.getRequirement() + "%");
        }
        //对充电桩价钱进行搜索
        if (chargePile.getPrice()!=null) {
            lambdaQueryWrapper.eq(ChargePile::getPrice,chargePile.getPrice());
        }
        //对充电桩安装时间进行检索
        if(chargePile.getBeginInstallTime()!=null){
            lambdaQueryWrapper.between(ChargePile::getInstallTime,chargePile.getBeginInstallTime(),chargePile.getEndInstallTime());
        }
        lambdaQueryWrapper.orderByDesc(ChargePile::getCreateTime);
        Page<ChargePile> page = new Page<ChargePile>(pageNum, pageSize);
        return chargePileDao.selectPage(page, lambdaQueryWrapper);
    }
    //添加充电桩
    @Override
    public int add(ChargePile pile) {
        return chargePileDao.insertChargePile(pile);
    }
    //修改充电桩
    @Override
    public int update(ChargePile pile) {
        return chargePileDao.updateChargePile(pile);
    }
    //获取充电桩详情
    @Override
    public ChargePile getById(String pileId) {
        return chargePileDao.selectChargePileByPileId(pileId);
    }
    //批量删除充电桩信息
    @Override
    public int deleteByIds(String[] pileIds) {
        return chargePileDao.deleteChargePileByPileIds(pileIds);
    }
    //获取充电桩下拉列表
    @Override
    public List<ChargePile> getPiles(ChargePile pile) {
        LambdaQueryWrapper<ChargePile> lambdaQueryWrapper = new LambdaQueryWrapper<ChargePile>();
        lambdaQueryWrapper.select(ChargePile::getPileCode, ChargePile::getPileState);
        if(!StringUtils.isEmpty(pile.getPileState())){
            lambdaQueryWrapper.eq(ChargePile::getPileState,pile.getPileState());
        }
        return chargePileDao.selectList(lambdaQueryWrapper);
    }
    //获取充电桩列表（不分页）
    @Override
    public List<ChargePile> pileList(ChargePile chargePile) {
        LambdaQueryWrapper<ChargePile> lambdaQueryWrapper = new LambdaQueryWrapper<ChargePile>();
        lambdaQueryWrapper.select(ChargePile::getPileId, ChargePile::getPileCode, ChargePile::getPileState, ChargePile::getRequirement, ChargePile::getSpeed, ChargePile::getChargeStandard, ChargePile::getInstallTime, ChargePile::getOutputPower, ChargePile::getMaintenanceCycle, ChargePile::getPrice, ChargePile::getMaintenanceTime, ChargePile::getCreateTime);
        if (!StringUtils.isEmpty(chargePile.getPileCode())) {
            lambdaQueryWrapper.like(ChargePile::getPileCode, "%" + chargePile.getPileCode() + "%");
        }
        if (!StringUtils.isEmpty(chargePile.getPileState())) {
            lambdaQueryWrapper.eq(ChargePile::getPileState,chargePile.getPileState());
        }
        if (!StringUtils.isEmpty(chargePile.getSpeed())) {
            lambdaQueryWrapper.like(ChargePile::getSpeed, "%" + chargePile.getSpeed() + "%");
        }
        if (!StringUtils.isEmpty(chargePile.getOutputPower())) {
            lambdaQueryWrapper.like(ChargePile::getOutputPower, "%" + chargePile.getOutputPower() + "%");
        }
        if (!StringUtils.isEmpty(chargePile.getRequirement())) {
            lambdaQueryWrapper.like(ChargePile::getRequirement, "%" + chargePile.getRequirement() + "%");
        }
        if (chargePile.getPrice()!=null) {
            lambdaQueryWrapper.eq(ChargePile::getPrice,chargePile.getPrice());
        }
        if(chargePile.getBeginInstallTime()!=null){
            lambdaQueryWrapper.between(ChargePile::getInstallTime,chargePile.getBeginInstallTime(),chargePile.getEndInstallTime());
        }
        lambdaQueryWrapper.orderByDesc(ChargePile::getCreateTime);
        return chargePileDao.selectList(lambdaQueryWrapper);
    }
    //分组统计充电桩数量
    @Override
    public List<CommonCountVo> getPileNum() {
        return chargePileDao.getPileNum();
    }
    //按地区分组统计充电桩数量
    @Override
    public List<CommonCountVo> getRingNum() {
        return chargePileDao.getRingNum();
    }
    //获取充电桩经纬度
    @Override
    public List<ChargePile> getPileMap() {
        return chargePileDao.getPileMap();
    }
}
