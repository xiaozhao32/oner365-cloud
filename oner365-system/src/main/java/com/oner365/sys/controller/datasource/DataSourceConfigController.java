package com.oner365.sys.controller.datasource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.web.controller.BaseController;
import com.oner365.sys.dto.DataSourceConfigDto;
import com.oner365.sys.service.IDataSourceConfigService;
import com.oner365.sys.vo.DataSourceConfigVo;

/**
 * 数据源
 *
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/datasource")
public class DataSourceConfigController extends BaseController {

    @Resource
    private IDataSourceConfigService service;

    /**
     * 列表
     * @param data 查询参数
     * @return PageInfo<DataSourceConfigDto>
     */
    @PostMapping("/list")
    public PageInfo<DataSourceConfigDto> pageList(@RequestBody QueryCriteriaBean data) {
        return service.pageList(data);
    }

    /**
     * 按id获取信息
     * @param id 编号
     * @return DataSourceConfigDto
     */
    @GetMapping("/get/{id}")
    public DataSourceConfigDto get(@PathVariable String id) {
        return service.getById(id);
    }

    /**
     * 按 connectName 获取信息
     * @param connectName 连接名称
     * @return DataSourceConfigDto
     */
    @GetMapping("/name")
    public DataSourceConfigDto getConnectName(@RequestParam String connectName) {
        return service.getConnectName(connectName);
    }

    /**
     * 保存
     * @param dataSourceConfigVo 数据源对象
     * @return DataSourceConfigDto
     */
    @PutMapping("/save")
    public DataSourceConfigDto save(@Validated @RequestBody DataSourceConfigVo dataSourceConfigVo) {
        return service.save(dataSourceConfigVo);
    }

    /**
     * 删除
     * @param ids 编号
     * @return List<Boolean>
     */
    @DeleteMapping("/delete")
    public List<Boolean> delete(@RequestBody String... ids) {
        return Arrays.stream(ids).map(id -> service.deleteById(id)).collect(Collectors.toList());
    }

}
