package com.sad.web.controller;


import com.sad.web.entity.ChargePile;
import com.sad.web.entity.ResponseResult;
import com.sad.web.service.IChargePileService;
import com.sad.web.utils.poi.ExcelUtil;
import com.sad.web.utils.uuid.UUIDUtil;
import org.assertj.core.util.DateUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 * 充电桩信息表 前端控制器
 * </p>
 *
 * @author Qinhy
 * @since 2023-12-25 09:46:48
 */
@RestController
@RequestMapping("/charge/pile")
public class ChargePileController extends BaseController {

    @Resource
    private IChargePileService chargePileService;

    @GetMapping("/list")
    public ResponseResult list(ChargePile chargePile, @RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize) {
        return ResponseResult.success(chargePileService.list(chargePile, pageNum, pageSize));
    }


    @PostMapping("/add")
    public ResponseResult add(@RequestBody(required = false) ChargePile pile) {
        pile.setPileId(UUIDUtil.generateUUID());
        pile.setCreateBy(getCurrentLoginUserName());
        pile.setCreateTime(DateUtil.now());
        return ResponseResult.success(chargePileService.add(pile));
    }

    @PutMapping("/update")
    public ResponseResult update(@RequestBody(required = false) ChargePile pile) {
        pile.setUpdateBy(getCurrentLoginUserName());
        pile.setUpdateTime(DateUtil.now());
        return ResponseResult.success(chargePileService.update(pile));
    }

    @GetMapping("/{pileId}")
    public ResponseResult getInfo(@PathVariable(value = "pileId", required = false) String pileId) {
       ChargePile pile = chargePileService.getById(pileId);
        return ResponseResult.success(pile);
    }

    @DeleteMapping("/delete/{pileIds}")
    public ResponseResult delete(@PathVariable(value = "pileIds", required = false) String[] pileIds) {
        return ResponseResult.success(chargePileService.deleteByIds(pileIds));
    }



    @GetMapping("/get-piles")
    public ResponseResult getPiles(ChargePile pile) {
        return ResponseResult.success(chargePileService.getPiles(pile));
    }



    @PostMapping("/export")
    public ResponseResult export(@RequestBody(required = false)ChargePile pile) {
        List<ChargePile> list = chargePileService.pileList(pile);
        ExcelUtil<ChargePile> util = new ExcelUtil<ChargePile>(ChargePile.class);
        return util.exportExcel(list, "充电桩数据");
    }

    @GetMapping("/get-pile-num")
    public ResponseResult getPileNum(){
        return ResponseResult.success(chargePileService.getPileNum());
    }


    @GetMapping("/get-ring-num")
    public ResponseResult getRingNum(){
        return ResponseResult.success(chargePileService.getRingNum());
    }


    @GetMapping("/get-pile-map")
    public ResponseResult getPileMap(){
        return ResponseResult.success(chargePileService.getPileMap());
    }
}

