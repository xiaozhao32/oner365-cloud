package com.oner365.sys.controller.system;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
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

import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.web.controller.BaseController;
import com.oner365.sys.dto.SysJobDto;
import com.oner365.sys.dto.SysMenuOperationDto;
import com.oner365.sys.service.ISysMenuOperationService;
import com.oner365.sys.vo.SysMenuOperationVo;
import com.oner365.sys.vo.check.CheckCodeVo;

/**
 * 菜单操作权限
 *
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/menu/operation")
public class SysMenuOperationController extends BaseController {

    @Resource
    private ISysMenuOperationService menuOperationService;

    /**
     * 列表
     * @param data 查询参数
     * @return PageInfo<SysMenuOperationDto>
     */
    @PostMapping("/list")
    public PageInfo<SysMenuOperationDto> pageList(@RequestBody QueryCriteriaBean data) {
        return menuOperationService.pageList(data);
    }

    /**
     * 获取信息
     * @param id 编号
     * @return SysMenuOperationDto
     */
    @GetMapping("/get/{id}")
    public SysMenuOperationDto getById(@PathVariable String id) {
        return menuOperationService.getById(id);
    }

    /**
     * 修改状态
     * @param id 主键
     * @param status 状态
     * @return Boolean
     */
    @PostMapping("/status/{id}")
    public Boolean editStatus(@PathVariable String id, @RequestParam StatusEnum status) {
        return menuOperationService.editStatus(id, status);
    }

    /**
     * 判断是否存在
     * @param checkCodeVo 查询参数
     * @return Boolean
     */
    @PostMapping("/check")
    public Boolean checkCode(@Validated @RequestBody CheckCodeVo checkCodeVo) {
        if (checkCodeVo != null) {
            return menuOperationService.checkCode(checkCodeVo.getId(), checkCodeVo.getCode());
        }
        return Boolean.FALSE;
    }

    /**
     * 保存
     * @param sysMenuOperationVo 操作对象
     * @return SysMenuOperationDto
     */
    @PutMapping("/save")
    public SysMenuOperationDto save(@Validated @RequestBody SysMenuOperationVo sysMenuOperationVo) {
        return menuOperationService.save(sysMenuOperationVo);
    }

    /**
     * 删除
     * @param ids 编号
     * @return List<Boolean>
     */
    @DeleteMapping("/delete")
    public List<Boolean> delete(@RequestBody String... ids) {
        return Arrays.stream(ids).map(id -> menuOperationService.deleteById(id)).collect(Collectors.toList());
    }

    /**
     * 导出Excel
     * @param data 查询参数
     * @return ResponseEntity<byte[]>
     */
    @PostMapping("/export")
    public ResponseEntity<byte[]> export(@RequestBody QueryCriteriaBean data) {
        List<SysMenuOperationDto> list = menuOperationService.findList(data);

        String[] titleKeys = new String[] { "编号", "操作名称", "操作类型", "状态", "创建时间", "更新时间" };
        String[] columnNames = { "id", "operationName", "operationType", "status", "createTime", "updateTime" };

        String fileName = SysJobDto.class.getSimpleName() + System.currentTimeMillis();
        return exportExcel(fileName, titleKeys, columnNames, list);
    }

}
