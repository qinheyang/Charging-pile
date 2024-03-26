package com.sad.web.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sad.web.entity.ChargeRecords;
import com.sad.web.entity.Repair;
import com.sad.web.dao.RepairDao;
import com.sad.web.service.IRepairService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 维修记录服务实现类
 * </p>
 *
 * @since 2023-12-27 10:00:54
 */
@Service
public class RepairServiceImpl extends ServiceImpl<RepairDao, Repair> implements IRepairService {
    @Resource
    private RepairDao repairDao;

    //获取维修记录列表分页版
    @Override
    public IPage<Repair> list(Repair repair, Integer pageNum, Integer pageSize) {
        Page<Repair> page = new Page<Repair>(pageNum, pageSize);
        return repairDao.selectRepairList(page, repair);
    }
    //添加维修记录
    @Override
    public int add(Repair repair) {
        return repairDao.insertRepair(repair);
    }
    //添加维修并修改充电状态
    @Override
    public int addRepair(Repair repair) {
        return repairDao.addRepair(repair);
    }
    //修改维修记录
    @Override
    public int update(Repair repair) {
        return repairDao.updateRepair(repair);
    }
    //获取维修记录详情
    @Override
    public Repair getById(String repairId) {
        return repairDao.selectRepairByRepairId(repairId);
    }
    //批量删除维修记录
    @Override
    public int deleteByIds(String[] repairIds) {
        return deleteByIds(repairIds);
    }
    //获取维修记录不分页版
    @Override
    public List<Repair> listRepair(Repair repair) {
        return repairDao.getRepairList(repair);
    }
}
