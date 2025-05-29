package com.oner365.sys.service;

import java.time.LocalDateTime;
import java.util.List;

import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.jpa.service.BaseService;
import com.oner365.sys.dto.SysLogDto;
import com.oner365.sys.vo.SysLogVo;

/**
 * 日志接口
 *
 * @author zhaoyong
 */
public interface ISysLogService extends BaseService {

    /**
     * 查询分页列表
     * @param data 查询参数
     * @return PageInfo
     */
    PageInfo<SysLogDto> pageList(QueryCriteriaBean data);

    /**
     * 查询列表
     * @param data 查询参数
     * @return List
     */
    List<SysLogDto> findList(QueryCriteriaBean data);

    /**
     * 查询详情
     * @param id 编号
     * @return SysLogDto
     */
    SysLogDto getById(String id);

    /**
     * 保存
     * @param sysLog 对象
     */
    void save(SysLogVo sysLog);

    /**
     * 删除
     * @param id 编号
     * @return Boolean
     */
    Boolean deleteById(String id);

    /**
     * 按日期删除
     * @param dateTime 日期
     * @return Boolean
     */
    Boolean deleteLog(LocalDateTime dateTime);

}
