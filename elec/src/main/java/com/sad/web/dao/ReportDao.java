package com.sad.web.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sad.web.entity.Report;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 充电桩保修 Mapper 接口
 * </p>
 *
 * @author Qinhy
 * @since 2023-12-26 04:24:12
 */
@Mapper
public interface ReportDao extends BaseMapper<Report> {

    /**
     * 查询
     *
     * @param reportId 主键
     * @return
     */
    public Report selectReportByReportId(String reportId);

    /**
     * 查询列表
     *
     * @param report
     * @return 集合
     */
    public IPage<Report> selectReportList(@Param("page") IPage<Report> page, @Param("report") Report report);

    /**
     * 新增
     *
     * @param eReport
     * @return 结果
     */
    public int insertReport(Report eReport);

    /**
     * 修改
     *
     * @param report
     * @return 结果
     */
    public int updateReport(Report report);

    /**
     * 删除
     *
     * @param reportId 主键
     * @return 结果
     */
    public int deleteReportByReportId(String reportId);

    /**
     * 批量删除
     *
     * @param reportIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteReportByReportIds(String[] reportIds);


    public List<Report> getReportList(@Param("report") Report report);


}
