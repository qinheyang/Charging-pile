package com.sad.web.dao.system;

import com.sad.web.entity.ChargePile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.sad.web.entity.vo.CommonCountVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 充电桩信息表 Mapper 接口
 * </p>
 * @since 2023-12-25 09:46:48
 */
@Mapper
public interface ChargePileDao extends BaseMapper<ChargePile> {
    ChargePile selectChargePileByPileId(String pileId);

    int insertChargePile(ChargePile chargePile);

    int updateChargePile(ChargePile chargePile);
    int deleteChargePileByPileIds(String[] pileIds);

    ChargePile selectChargePileByPileCode(String pileCode);

    List<ChargePile> selectChargePileByPileIds(String[] pileIds);

    List<CommonCountVo> getPileNum();

    List<CommonCountVo> getRingNum();

    List<ChargePile> getPileMap();
}
