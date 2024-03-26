package com.sad.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sad.web.dao.system.ChargePileDao;
import com.sad.web.entity.ChargeRecords;
import com.sad.web.entity.Report;
import com.sad.web.dao.ReportDao;
import com.sad.web.service.IReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 充电桩故障报告服务实现类
 * </p>
 * @since 2023-12-26 04:24:12
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportDao, Report> implements IReportService {
    @Resource
    private ReportDao reportDao;
    //分页查询故障报告列表
    @Override
    public IPage<Report> list(Report report, Integer pageNum, Integer pageSize) {
        Page<Report> page = new Page<Report>(pageNum, pageSize);
        return reportDao.selectReportList(page,report);
    }
    //获取故障报告详情
    @Override
    public Report getById(String reportId) {
        return reportDao.selectReportByReportId(reportId);
    }
    //添加故障报告
    @Override
    public int add(Report report) {
        return reportDao.insertReport(report);
    }
    //修改故障报告
    @Override
    public int update(Report report) {
        return reportDao.updateReport(report);
    }
    //批量删除故障报告
    @Override
    public int deleteByIds(String[] reportIds) {
        return reportDao.deleteReportByReportIds(reportIds);
    }
    //不分页查询故障报告列表
    @Override
    public List<Report> listReport(Report report) {
        return reportDao.getReportList(report);
    }
}
