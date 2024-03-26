package com.sad.web.controller;


import com.sad.web.entity.Repair;
import com.sad.web.entity.Report;
import com.sad.web.entity.ResponseResult;
import com.sad.web.service.IReportService;
import com.sad.web.utils.poi.ExcelUtil;
import com.sad.web.utils.uuid.UUIDUtil;
import org.assertj.core.util.DateUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 充电桩保修接口
 * </p>
 *
 * @since 2023-12-26 04:24:12
 */
@RestController
@RequestMapping("/repair/report")
public class ReportController extends BaseController {


    @Resource
    private IReportService reportService;


    //故障报告列表
    @GetMapping("/list")
    public ResponseResult list(Report report, @RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize) {
        return ResponseResult.success(reportService.list(report, pageNum, pageSize));
    }

    //添加故障报告
    @PostMapping("/add")
    public ResponseResult add(@RequestBody(required = false) Report report) {
        report.setReportId(UUIDUtil.generateUUID());
        report.setCreateBy(getCurrentLoginUserName());
        report.setCreateTime(DateUtil.now());
        return ResponseResult.success(reportService.add(report));
    }
    //修改故障报告
    @PutMapping("/update")
    public ResponseResult update(@RequestBody(required = false)Report report) {
        report.setUpdateBy(getCurrentLoginUserName());
        report.setUpdateTime(DateUtil.now());
        return ResponseResult.success(reportService.update(report));
    }
    //获取故障报告详情
    @GetMapping("/{reportId}")
    public ResponseResult getInfo(@PathVariable(value = "reportId", required = false) String reportId) {
        Report report = reportService.getById(reportId);
        return ResponseResult.success(report);
    }
    //批量删除故障报告
    @DeleteMapping("/delete/{reportIds}")
    public ResponseResult delete(@PathVariable(value = "reportIds", required = false) String[] reportIds) {
        return ResponseResult.success(reportService.deleteByIds(reportIds));
    }

    //导出故障报告
    @PostMapping ("/export")
    public ResponseResult export(@RequestBody(required = false) Report report) {
        List<Report> list = reportService.listReport(report);
        ExcelUtil<Report> util = new ExcelUtil<Report>(Report.class);
        return util.exportExcel(list, "故障数据");
    }



}
