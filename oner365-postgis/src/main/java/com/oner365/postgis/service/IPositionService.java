package com.oner365.postgis.service;

import java.util.List;

import com.oner365.postgis.dto.PositionDto;
import com.oner365.postgis.vo.PositionVo;

/**
 * Position Service
 *
 * @author zhaoyong
 */
public interface IPositionService {

    /**
     * 查询列表
     * @return 集合
     */
    List<PositionDto> findList();

    /**
     * get
     * @param id 主键
     * @return PositionDto
     */
    PositionDto getById(String id);

    /**
     * 保存
     * @param vo 请求对象
     * @return PositionDto
     */
    PositionDto save(PositionVo vo);

    /**
     * 删除
     * @param id 编号
     * @return Boolean
     */
    Boolean deleteById(String id);

}
