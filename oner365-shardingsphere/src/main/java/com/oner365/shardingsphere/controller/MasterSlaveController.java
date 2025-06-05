package com.oner365.shardingsphere.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.data.web.controller.BaseController;
import com.oner365.shardingsphere.entity.MasterSlave;
import com.oner365.shardingsphere.service.IMasterSlaveService;
import com.oner365.shardingsphere.vo.MasterSlaveVo;

/**
 * 主从测试
 *
 * @author zhaoyong
 */
@RestController
@RequestMapping("/master-slave")
public class MasterSlaveController extends BaseController {

    @Resource
    private IMasterSlaveService service;

    /**
     * 列表查询
     * @return List<MasterSlave>
     */
    @GetMapping("/list")
    public List<MasterSlave> findList() {
        return service.findList();
    }

    /**
     * 保存
     * @param vo 保存对象
     * @return 是否成功
     */
    @PostMapping("/save")
    public Boolean save(@RequestBody MasterSlaveVo vo) {
        return service.save(vo);
    }

}
