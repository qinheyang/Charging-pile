package com.sad.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sad.web.entity.Report;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 充电桩保修 服务类
 * </p>
 *
 * @author Qinhy
 * @since 2023-12-26 04:24:12
 */
public interface IReportService extends IService<Report> {

    IPage<Report> list(Report report, Integer pageNum, Integer pageSize);
    Report getById(String reportId);

    int add(Report report);

    int update(Report report);

    int deleteByIds(String[] reportIds);

    List<Report> listReport(Report report);
}
