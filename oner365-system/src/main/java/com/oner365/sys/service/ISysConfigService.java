package com.oner365.sys.service;

import java.util.List;

import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.jpa.service.BaseService;
import com.oner365.sys.dto.SysConfigDto;
import com.oner365.sys.vo.SysConfigVo;

/**
 * nt_sys_config Service 接口
 *
 * @author zhaoyong
 *
 */
public interface ISysConfigService extends BaseService {

    /**
     * 分页查询列表
     * @param data 参数
     * @return PageInfo<SysConfigDto>
     */
    PageInfo<SysConfigDto> pageList(QueryCriteriaBean data);

    /**
     * 查询全部
     * @param data 参数
     * @return List<SysConfigDto>
     */
    List<SysConfigDto> findList(QueryCriteriaBean data);

    /**
     * 查询详情
     * @param id 编号
     * @return SysConfigDto
     */
    SysConfigDto getById(String id);

    /**
     * 保存
     * @param entity 对象
     * @return SysConfig
     */
    SysConfigDto save(SysConfigVo entity);

    /**
     * 删除
     * @param id 编号
     * @return Boolean
     */
    Boolean deleteById(String id);

    /**
     * 更新状态
     * @param id 编号
     * @param status 状态
     * @return Boolean
     */
    Boolean editStatus(String id, StatusEnum status);

    /**
     * 检测配置名称是否存在
     * @param id 主键
     * @param configName 配置名称
     * @return Boolean
     */
    Boolean checkConfigName(String id, String configName);

}
