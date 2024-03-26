package com.sad.web.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sad.web.entity.Repair;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sad.web.entity.Report;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 维修记录表 Mapper 接口
 * </p>
 *
 * @author Qinhy
 * @since 2023-12-27 10:00:54
 */
@Mapper
public interface RepairDao extends BaseMapper<Repair> {

    /**
     * 查询维修记录
     *
     * @param repairId 维修记录主键
     * @return 维修记录
     */
    public Repair selectRepairByRepairId(String repairId);

    /**
     * 查询维修记录列表
     *
     * @param repair 维修记录
     * @return 维修记录集合
     */
    public IPage<Repair> selectRepairList(@Param("page") IPage<Repair> page ,@Param("repair") Repair repair);


    public int addRepair(Repair repair);
    /**
     * 新增维修记录
     *
     * @param repair 维修记录
     * @return 结果
     */
    public int insertRepair(Repair repair);

    /**
     * 修改维修记录
     *
     * @param repair 维修记录
     * @return 结果
     */
    public int updateRepair(Repair repair);


    /**
     * 批量删除维修记录
     *
     * @param repairIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRepairByRepairIds(String[] repairIds);




    public List<Repair> getRepairList( Repair repair);


}
