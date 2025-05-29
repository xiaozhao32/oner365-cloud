package com.oner365.swagger.controller.mongodb;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.swagger.client.mongodb.IMongodbPersonClient;
import com.oner365.swagger.dto.PersonDto;
import com.oner365.swagger.vo.PersonVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 人员信息
 *
 * @author zhaoyong
 */
@RestController
@Api(tags = "Mongodb - 人员信息")
@RequestMapping("/mongodb/person")
public class MongodbPersonController {

    @Resource
    private IMongodbPersonClient client;

    /**
     * 人员分页
     * @param data 查询参数
     * @return ResponseData<Page<PersonDto>>
     */
    @ApiOperation("1.获取人员分页")
    @ApiOperationSupport(order = 1)
    @PostMapping("/page")
    public ResponseData<PageInfo<PersonDto>> list(@RequestBody QueryCriteriaBean data) {
        return client.page(data);
    }

    /**
     * 获取人员信息
     * @param id 编号
     * @return ResponseData<PersonDto>
     */
    @ApiOperation("2.按id查询")
    @ApiOperationSupport(order = 2)
    @GetMapping("/get/{id}")
    public ResponseData<PersonDto> get(@PathVariable String id) {
        return client.getById(id);
    }

    /**
     * 人员信息保存
     * @param personVo 职位对象
     * @return ResponseData<PersonDto>
     */
    @ApiOperation("3.保存")
    @ApiOperationSupport(order = 4)
    @PutMapping("/save")
    public ResponseData<PersonDto> save(@RequestBody PersonVo personVo) {
        return client.save(personVo);
    }

    /**
     * 删除人员信息
     * @param ids 编号
     * @return ResponseData<List<Boolean>>
     */
    @ApiOperation("4.删除")
    @ApiOperationSupport(order = 5)
    @DeleteMapping("/delete")
    public ResponseData<List<Boolean>> delete(@RequestBody String... ids) {
        return client.deleteById(ids);
    }

}
