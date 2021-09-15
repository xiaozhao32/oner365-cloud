package com.oner365.sys.controller.system;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.sys.entity.SysLog;
import com.oner365.sys.service.ISysLogService;
import com.oner365.util.DateUtil;

/**
 * 系统日志控制器
 * 
 * @author zhaoyong
 */
@RestController
@RequestMapping("/log")
public class SysLogController extends BaseController {

    @Autowired
    private ISysLogService logService;

    /**
     * 保存
     * 
     * @param paramJson 菜单类型对象
     * @return ResponseData
     */
    @PutMapping("/save")
    public Map<String, Object> save(@RequestBody JSONObject paramJson) {
        SysLog sysLog = JSON.toJavaObject(paramJson, SysLog.class);
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, PublicConstants.ERROR_CODE);
        if (sysLog != null) {
            SysLog entity = logService.save(sysLog);

            result.put(PublicConstants.CODE, PublicConstants.SUCCESS_CODE);
            result.put(PublicConstants.MSG, entity);
        }
        return result;
    }

    /**
     * 获取信息
     * 
     * @param id 编号
     * @return SysLog
     */
    @GetMapping("/get/{id}")
    public SysLog get(@PathVariable String id) {
        return logService.getById(id);
    }

    /**
     * 列表
     * 
     * @param data 查询参数
     * @return Page<SysLog>
     */
    @PostMapping("/list")
    public Page<SysLog> list(@RequestBody QueryCriteriaBean data) {
        return logService.pageList(data);
    }

    /**
     * 删除
     * 
     * @param ids 编号
     * @return Integer
     */
    @DeleteMapping("/delete")
    public Integer delete(@RequestBody String... ids) {
        int code = 0;
        for (String id : ids) {
            code = logService.deleteById(id);
        }
        return code;
    }

    /**
     * 按日期删除日志
     * 
     * @param date 日期
     * @return Map<String, Object>
     */
    @DeleteMapping("/deleteLog")
    public Map<String, Object> deleteLog(@RequestParam("date") String date) {
        int code = logService.deleteLog(DateUtil.stringToDate(date, DateUtil.FULL_DATE_FORMAT));
        Map<String, Object> result = Maps.newHashMap();
        result.put(PublicConstants.CODE, code);
        return result;
    }

    /**
     * 导出日志
     * 
     * @param data 查询参数
     * @return ResponseEntity<byte[]>
     */
    @PostMapping("/export")
    public ResponseEntity<byte[]> exportItem(@RequestBody QueryCriteriaBean data) {
        List<SysLog> list = logService.findList(data);

        String[] titleKeys = new String[] { "编号", "请求IP", "请求方法", "服务名称", "请求地址", "请求内容", "创建时间" };
        String[] columnNames = { "id", "operationIp", "methodName", "operationName", "operationPath",
                "operationContext", "createTime" };

        String fileName = SysLog.class.getSimpleName() + System.currentTimeMillis();
        return exportExcel(fileName, titleKeys, columnNames, list);
    }

}
