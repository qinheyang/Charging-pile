package com.sad.web.controller;


import com.sad.web.entity.ChargePile;
import com.sad.web.entity.ChargeRecords;
import com.sad.web.entity.ResponseResult;
import com.sad.web.service.IChargePileService;
import com.sad.web.service.IChargeRecordsService;
import com.sad.web.utils.poi.ExcelUtil;
import com.sad.web.utils.uuid.UUIDUtil;
import org.assertj.core.util.DateUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 充电记录接口
 * </p>
 *
 * @since 2023-12-26 08:53:03
 */
@RestController
@RequestMapping("/charge/records")
public class ChargeRecordsController extends BaseController {
    @Resource
    private IChargeRecordsService chargeRecordsService;


    @Resource
    private IChargePileService chargePileService;
    //获取充电桩记录列表
    @GetMapping("/list")
    public ResponseResult list(ChargeRecords chargeRecords, @RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize) {
        return ResponseResult.success(chargeRecordsService.list(chargeRecords, pageNum, pageSize));
    }

    //添加充电桩记录
    @PostMapping("/add")
    public ResponseResult add(@RequestBody(required = false) ChargeRecords chargeRecords) {
        chargeRecords.setRecordsId(UUIDUtil.generateUUID());
        chargeRecords.setCreateBy(getCurrentLoginUserName());
        chargeRecords.setCreateTime(DateUtil.now());
        return ResponseResult.success(chargeRecordsService.add(chargeRecords));
    }
    //修改充电桩记录
    @PutMapping("/update")
    public ResponseResult update(@RequestBody(required = false) ChargeRecords chargeRecords) {
        chargeRecords.setUpdateBy(getCurrentLoginUserName());
        chargeRecords.setUpdateTime(DateUtil.now());
        return ResponseResult.success(chargeRecordsService.update(chargeRecords));
    }
    //获取某个充电桩记录详细信息
    @GetMapping("/{recordsId}")
    public ResponseResult getInfo(@PathVariable(value = "recordsId", required = false) String recordsId) {
        ChargeRecords records = chargeRecordsService.getById(recordsId);
        return ResponseResult.success(records);
    }
    //批量删除某个充电桩记录详细信息
    @DeleteMapping("/delete/{recordsIds}")
    public ResponseResult delete(@PathVariable(value = "recordsIds", required = false) String[] recordsIds) {
        return ResponseResult.success(chargeRecordsService.deleteByIds(recordsIds));
    }
    //导出充电桩记录
    @PostMapping("/export")
    public ResponseResult export(@RequestBody(required = false) ChargeRecords chargeRecords) {
        List<ChargeRecords> list = chargeRecordsService.getChargeRecordsList(chargeRecords);
        ExcelUtil<ChargeRecords> util = new ExcelUtil<ChargeRecords>(ChargeRecords.class);
        return util.exportExcel(list, "充电记录");
    }

    //导入充电桩记录
    @PostMapping("/import")
    public ResponseResult importData(@RequestPart(value = "file") MultipartFile file) throws Exception {
        ExcelUtil<ChargeRecords> util = new ExcelUtil<ChargeRecords>(ChargeRecords.class);
        List<ChargeRecords> list = util.importExcel(file.getInputStream());
        String message = chargeRecordsService.importChargeRecords(list);
        return ResponseResult.success(message);
    }

    //分组获取充电记录（按时间范围）
    @GetMapping("/get-records-num")
    public ResponseResult getRecordsNum() {
        return ResponseResult.success(chargeRecordsService.getRecordsNum());
    }
    //分组获取充电收益（按地区）
    @GetMapping("/get-rank")
    public ResponseResult getRank() {
        return ResponseResult.success(chargeRecordsService.getRank());
    }
    //统计充电各类车辆数量（近七天）
    @GetMapping("/get-bar")
    public ResponseResult getBarChart() {
        return ResponseResult.success(chargeRecordsService.getBarChart());
    }
    //获取近七天收益
    @GetMapping("/get-line")
    public ResponseResult getLineChart() {
        return ResponseResult.success(chargeRecordsService.getLineChart());
    }

}
