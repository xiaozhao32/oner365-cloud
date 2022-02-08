package com.oner365.sys.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.oner365.common.cache.annotation.RedisCacheAble;
import com.oner365.common.cache.annotation.RedisCachePut;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.ExistsEnum;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.query.Criteria;
import com.oner365.common.query.Restrictions;
import com.oner365.datasource.constants.DataSourceConstants;
import com.oner365.datasource.util.DataSourceUtil;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dao.ISysOrganizationDao;
import com.oner365.sys.dto.SysOrganizationDto;
import com.oner365.sys.dto.TreeSelect;
import com.oner365.sys.entity.DataSourceConfig;
import com.oner365.sys.entity.SysOrganization;
import com.oner365.sys.mapper.SysOrganizationMapper;
import com.oner365.sys.service.ISysOrganizationService;
import com.oner365.sys.vo.DataSourceConfigVo;
import com.oner365.sys.vo.SysOrganizationVo;
import com.oner365.util.DataUtils;

/**
 * 机构接口实现类
 *
 * @author zhaoyong
 */
@Service
public class SysOrganizationServiceImpl implements ISysOrganizationService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SysOrganizationServiceImpl.class);

  private static final String CACHE_NAME = "SysOrganization";

  @Autowired
  private ISysOrganizationDao dao;

  @Autowired
  private SysOrganizationMapper organizationMapper;

  @Override
  @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
  public SysOrganizationDto getById(String id) {
    try {
      Optional<SysOrganization> optional = dao.findById(id);
      return convert(optional.orElse(null), SysOrganizationDto.class);
    } catch (Exception e) {
      LOGGER.error("Error getById: ", e);
    }
    return null;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public int deleteById(String id) {
    dao.deleteById(id);
    return ResultEnum.SUCCESS.getCode();
  }

  @Override
  public long checkCode(String orgId, String code, String type) {
    try {
      Criteria<SysOrganization> criteria = new Criteria<>();
      criteria.add(Restrictions.eq(type, code));
      if (!DataUtils.isEmpty(orgId)) {
        criteria.add(Restrictions.ne(SysConstants.ID, orgId));
      }
      return dao.count(criteria);
    } catch (Exception e) {
      LOGGER.error("Error checkCode:", e);
    }
    return ExistsEnum.NO.getCode();
  }

  @Override
  public boolean checkConnection(String id) {
    try {
      Optional<SysOrganization> optional = dao.findById(id);
      if (optional.isPresent()) {
        DataSourceConfig config = optional.get().getDataSourceConfig();
        if (config != null) {
          return DataSourceUtil.isConnection(config.getDriverName(), config.getUrl(), config.getUserName(),
              config.getPassword());
        }
      }
    } catch (Exception e) {
      LOGGER.error("Error checkConnection:", e);
    }
    return false;
  }

  @Override
  public boolean isConnection(String dsType, String ip, int port, String dbName, String userName, String pwd) {
    String driverName;
    String url;
    if (DataSourceConstants.DB_TYPE_MYSQL.equals(dsType)) {
      driverName = DataSourceConstants.DRIVER_NAME_MYSQL;
      url = "jdbc:mysql://" + ip + ":" + port + PublicConstants.DELIMITER + dbName;
    } else {
      driverName = DataSourceConstants.DRIVER_NAME_ORACLE;
      url = "jdbc:oracle:thin:@" + ip + ":" + port + ":" + dbName;
    }
    return DataSourceUtil.isConnection(driverName, url, userName, pwd);
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @RedisCachePut(value = CACHE_NAME, key = PublicConstants.KEY_ID)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public SysOrganizationDto save(SysOrganizationVo vo) {
    if (DataUtils.isEmpty(vo.getId())) {
      vo.setId(vo.getOrgCode());
      vo.setStatus(StatusEnum.YES.getCode());
      vo.setCreateTime(LocalDateTime.now());
    }

    vo.setUpdateTime(LocalDateTime.now());

    DataSourceConfigVo dataSourceConfigVo = vo.getDataSourceConfigVo();
    if (dataSourceConfigVo != null) {
      String driverName;
      String url;
      if (DataSourceConstants.DB_TYPE_MYSQL.equals(dataSourceConfigVo.getDbType())) {
        driverName = DataSourceConstants.DRIVER_NAME_MYSQL;
        url = "jdbc:mysql://" + dataSourceConfigVo.getIp() + ":" + dataSourceConfigVo.getPort() + PublicConstants.DELIMITER
            + dataSourceConfigVo.getDbName();
      } else {
        driverName = DataSourceConstants.DRIVER_NAME_ORACLE;
        url = "jdbc:oracle:thin:@" + dataSourceConfigVo.getIp() + ":" + dataSourceConfigVo.getPort() + ":"
            + dataSourceConfigVo.getDbName();
      }
      dataSourceConfigVo.setDsType(DataSourceConstants.DS_TYPE_DB);
      dataSourceConfigVo.setDriverName(driverName);
      dataSourceConfigVo.setUrl(url);
      dataSourceConfigVo.setCreateTime(LocalDateTime.now());
      dataSourceConfigVo.setUpdateTime(LocalDateTime.now());
      vo.setDataSourceConfigVo(dataSourceConfigVo);
    }
    SysOrganization po = convert(vo, SysOrganization.class);
    po.setDataSourceConfig(convert(vo.getDataSourceConfigVo(), DataSourceConfig.class));
    SysOrganization entity = dao.save(po);
    return convert(entity, SysOrganizationDto.class);
  }

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public List<SysOrganizationDto> findListByParentId(String parentId) {
    Criteria<SysOrganization> criteria = new Criteria<>();
    criteria.add(Restrictions.eq("parentId", parentId));
    return convert(dao.findAll(criteria), SysOrganizationDto.class);
  }

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public SysOrganizationDto getByCode(String orgCode) {
    Assert.notNull(orgCode, "orgCode is not empty.");
    Criteria<SysOrganization> criteria = new Criteria<>();
    criteria.add(Restrictions.eq("orgCode", orgCode));
    return convert(dao.findOne(criteria), SysOrganizationDto.class);
  }

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public List<TreeSelect> buildTreeSelect(List<SysOrganizationDto> orgList) {
    List<SysOrganizationDto> menuTrees = buildTree(orgList);
    return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
  }

  @Override
  public List<SysOrganizationDto> buildTree(List<SysOrganizationDto> orgList) {
    List<SysOrganizationDto> returnList = new ArrayList<>();
    for (SysOrganizationDto t : orgList) {
      // 根据传入的某个父节点ID,遍历该父节点的所有子节点
      if (SysConstants.DEFAULT_PARENT_ID.equals(t.getParentId())) {
        recursionFn(orgList, t);
        returnList.add(t);
      }
    }
    if (returnList.isEmpty()) {
      returnList = orgList;
    }
    return returnList;
  }

  /**
   * 递归列表
   *
   * @param list 列表
   * @param t    对象
   */
  private void recursionFn(List<SysOrganizationDto> list, SysOrganizationDto t) {
    // 得到子节点列表
    List<SysOrganizationDto> childList = getChildList(list, t);
    if (!childList.isEmpty()) {
      t.setChildren(childList);
      // 判断是否有子节点
      childList.stream().filter(tChild -> hasChild(list, tChild)).<Consumer<? super SysOrganizationDto>>map(tChild -> n -> recursionFn(list, n)).forEach(childList::forEach);
    }
  }

  /**
   * 得到子节点列表
   */
  private List<SysOrganizationDto> getChildList(List<SysOrganizationDto> list, SysOrganizationDto t) {
    return list.stream().filter(n -> n.getParentId().equals(t.getId())).collect(Collectors.toList());
  }

  /**
   * 判断是否有子节点
   */
  private boolean hasChild(List<SysOrganizationDto> list, SysOrganizationDto t) {
    return !getChildList(list, t).isEmpty();
  }

  @Override
  public List<SysOrganizationDto> selectList(SysOrganizationVo vo) {
    List<SysOrganization> list = organizationMapper.selectList(convert(vo, SysOrganization.class));
    return convert(list, SysOrganizationDto.class);
  }

  @Override
  public List<String> selectListByUserId(String userId) {
    return organizationMapper.selectListByUserId(userId);
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public Integer changeStatus(String id, String status) {
    Optional<SysOrganization> optional = dao.findById(id);
    if (optional.isPresent()) {
      SysOrganization entity = optional.get();
      entity.setStatus(status);
      dao.save(entity);
      return ResultEnum.SUCCESS.getCode();
    }
    return ResultEnum.ERROR.getCode();
  }

}
