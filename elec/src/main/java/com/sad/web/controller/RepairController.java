package com.sad.web.controller;


import com.sad.web.entity.ChargePile;
import com.sad.web.entity.Repair;
import com.sad.web.entity.ResponseResult;
import com.sad.web.service.IRepairService;
import com.sad.web.utils.poi.ExcelUtil;
import com.sad.web.utils.uuid.UUIDUtil;
import org.assertj.core.util.DateUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 维修记录接口
 * </p>
 *
 * @since 2023-12-27 10:00:54
 */
@RestController
@RequestMapping("/repair/repair")
public class RepairController extends BaseController {

    @Resource
    private IRepairService repairService;


    //维修记录列表
    @GetMapping("/list")
    public ResponseResult list(Repair repair, @RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize) {
        return ResponseResult.success(repairService.list(repair, pageNum, pageSize));
    }

    //添加维修记录
    @PostMapping("/add")
    public ResponseResult add(@RequestBody(required = false) Repair repair) {
        repair.setRepairId(UUIDUtil.generateUUID());
        repair.setCreateBy(getCurrentLoginUserName());
        repair.setCreateTime(DateUtil.now());
        return ResponseResult.success(repairService.addRepair(repair));
    }
    //修改维修记录
    @PutMapping("/update")
    public ResponseResult update(@RequestBody(required = false)Repair repair) {
        repair.setUpdateBy(getCurrentLoginUserName());
        repair.setUpdateTime(DateUtil.now());
        return ResponseResult.success(repairService.update(repair));
    }
    //获取维修记录详情
    @GetMapping("/{repairId}")
    public ResponseResult getInfo(@PathVariable(value = "repairId", required = false) String repairId) {
        Repair repair = repairService.getById(repairId);
        return ResponseResult.success(repair);
    }
    //批量删除维修记录
    @DeleteMapping("/delete/{repairIds}")
    public ResponseResult delete(@PathVariable(value = "repairIds", required = false) String[] repairIds) {
        return ResponseResult.success(repairService.deleteByIds(repairIds));
    }
    //导出维修记录
    @PostMapping ("/export")
    public ResponseResult export(@RequestBody(required = false) Repair repair) {
        List<Repair> list = repairService.listRepair(repair);
        ExcelUtil<Repair> util = new ExcelUtil<Repair>(Repair.class);
        return util.exportExcel(list, repair.getPileCode()+"维修记录");
    }


}
