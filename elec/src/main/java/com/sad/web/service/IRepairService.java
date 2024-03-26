package com.sad.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sad.web.entity.Repair;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sad.web.entity.Report;

import java.util.List;

/**
 * <p>
 * 维修记录表 服务类
 * </p>
 *
 * @author Qinhy
 * @since 2023-12-27 10:00:54
 */
public interface IRepairService extends IService<Repair> {


    IPage<Repair> list(Repair repair, Integer pageNum, Integer pageSize);

    int add(Repair repair);


    int addRepair(Repair repair);

    int update(Repair repair);

    Repair getById(String repairId);

    int deleteByIds(String[] repairIds);

    List<Repair> listRepair(Repair repair);
}
