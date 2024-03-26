package com.sad.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sad.web.entity.ChargePile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sad.web.entity.system.SysRole;
import com.sad.web.entity.vo.CommonCountVo;

import java.util.List;

/**
 * <p>
 * 充电桩信息表 服务类
 * </p>
 *
 * @author Qinhy
 * @since 2023-12-25 09:46:48
 */
public interface IChargePileService extends IService<ChargePile> {

    IPage<ChargePile> list(ChargePile chargePile, Integer pageNum, Integer pageSize);

    int add(ChargePile pile);

    int update(ChargePile pile);


    ChargePile getById(String pileId);

    int deleteByIds(String[] pileIds);

    List<ChargePile> getPiles(ChargePile pile);


    List<ChargePile> pileList(ChargePile chargePile);

    List<CommonCountVo> getPileNum();

    List<CommonCountVo> getRingNum();

    List<ChargePile> getPileMap();
}
