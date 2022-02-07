package com.oner365.sys.service.impl;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oner365.common.cache.RedisCache;
import com.oner365.common.cache.annotation.RedisCacheAble;
import com.oner365.common.cache.annotation.RedisCachePut;
import com.oner365.common.cache.constants.CacheConstants;
import com.oner365.common.config.properties.AccessTokenProperties;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.ExistsEnum;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.Criteria;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.common.query.Restrictions;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dao.ISysJobDao;
import com.oner365.sys.dao.ISysOrganizationDao;
import com.oner365.sys.dao.ISysRoleDao;
import com.oner365.sys.dao.ISysUserDao;
import com.oner365.sys.dao.ISysUserJobDao;
import com.oner365.sys.dao.ISysUserOrgDao;
import com.oner365.sys.dao.ISysUserRoleDao;
import com.oner365.sys.dto.LoginUserDto;
import com.oner365.sys.dto.SysUserDto;
import com.oner365.sys.entity.SysJob;
import com.oner365.sys.entity.SysOrganization;
import com.oner365.sys.entity.SysRole;
import com.oner365.sys.entity.SysUser;
import com.oner365.sys.entity.SysUserJob;
import com.oner365.sys.entity.SysUserOrg;
import com.oner365.sys.entity.SysUserRole;
import com.oner365.sys.service.ISysUserService;
import com.oner365.sys.vo.SysUserVo;
import com.oner365.util.DataUtils;
import com.oner365.util.DateUtil;
import com.oner365.util.JwtUtils;
import com.oner365.util.RequestUtils;

/**
 * 系统用户接口实现类
 *
 * @author zhaoyong
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SysUserServiceImpl.class);

  private static final String CACHE_NAME = "SysUser";

  @Autowired
  private RedisCache redisCache;

  @Autowired
  private ISysUserDao userDao;

  @Autowired
  private ISysRoleDao sysRoleDao;

  @Autowired
  private ISysOrganizationDao sysOrganizationDao;

  @Autowired
  private ISysJobDao sysJobDao;

  @Autowired
  private ISysUserRoleDao userRoleDao;

  @Autowired
  private ISysUserOrgDao userOrgDao;

  @Autowired
  private ISysUserJobDao userJobDao;

  @Autowired
  private AccessTokenProperties tokenProperties;

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public LoginUserDto login(String userName, String p, String ip) {
    String password = DigestUtils.md5Hex(p).toUpperCase();
    SysUser user = getUserByUserName(userName, password, ip);
    if (user != null) {
      String key = CacheConstants.CACHE_LOGIN_NAME + userName;
      JSONObject cache = redisCache.getCacheObject(key);
      if (cache != null) {
        return JSON.toJavaObject(cache, LoginUserDto.class);
      }

      Date time = DateUtil.after(DateUtil.getDate(), tokenProperties.getExpireTime(), Calendar.MINUTE);
      JSONObject tokenJson = new JSONObject();
      tokenJson.put(RequestUtils.TOKEN_TYPE, "login");

      tokenJson.put(SysConstants.ID, user.getId());
      tokenJson.put(SysConstants.USER_NAME, user.getUserName());
      tokenJson.put(SysConstants.PASS, user.getPassword());
      tokenJson.put(SysConstants.IS_ADMIN, user.getIsAdmin());
      tokenJson.put(SysConstants.USER_TYPE, user.getUserType());

      List<String> roles = userRoleDao.findUserRoleByUserId(user.getId());
      List<String> orgs = userOrgDao.findUserOrgByUserId(user.getId());
      List<String> jobs = userJobDao.findUserJobByUserId(user.getId());
      tokenJson.put(SysConstants.ROLES, roles);
      tokenJson.put(SysConstants.JOBS, jobs);
      tokenJson.put(SysConstants.ORGS, orgs);
      String accessToken = JwtUtils.generateToken(tokenJson.toJSONString(), time, tokenProperties.getSecret());

      LoginUserDto result = new LoginUserDto();
      result.setAccessToken(accessToken);
      result.setExpireTime(time.getTime());

      result.setRealName(user.getRealName());
      result.setUserId(user.getId());
      result.setIsAdmin(user.getIsAdmin());
      result.setAvatar(user.getAvatar());
      result.setRoles(roles);
      result.setJobs(jobs);
      result.setOrgs(orgs);
      redisCache.setCacheObject(key, result, PublicConstants.EXPIRE_TIME, TimeUnit.MINUTES);

      return result;
    }
    return null;
  }

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public PageInfo<SysUserDto> pageList(QueryCriteriaBean data) {
    try {
      Page<SysUser> page = userDao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildPageRequest(data));
      page.getContent().forEach(this::setName);
      return convertDto(page);
    } catch (Exception e) {
      LOGGER.error("Error pageList:", e);
    }
    return null;
  }

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public List<SysUserDto> findList(QueryCriteriaBean data) {
    try {
      return convertDto(userDao.findAll(QueryUtils.buildCriteria(data)));
    } catch (Exception e) {
      LOGGER.error("Error findList:", e);
    }
    return Collections.emptyList();
  }

  @Override
  @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
  public SysUserDto getById(String id) {
    try {
      Optional<SysUser> optional = userDao.findById(id);
      if (optional.isPresent()) {
        SysUser entity = optional.get();
        setName(entity);
        return convertDto(entity);
      }
    } catch (Exception e) {
      LOGGER.error("Error getById:", e);
    }
    return null;
  }

  private SysUser getUserByUserName(String userName, String password, String ip) {
    Criteria<SysUser> criteria = new Criteria<>();
    criteria.add(Restrictions.eq(SysConstants.USER_NAME, userName));
    criteria.add(Restrictions.eq(SysConstants.PASS, password));
    criteria.add(Restrictions.eq(SysConstants.STATUS, StatusEnum.YES.getCode()));
    Optional<SysUser> optional = userDao.findOne(criteria);
    if (optional.isPresent()) {
      SysUser sysUser = optional.get();
      sysUser.setLastIp(ip);
      sysUser.setLastTime(LocalDateTime.now());
      return userDao.save(sysUser);
    }
    return null;
  }

  private void setName(SysUser entity) {
    List<String> roleList = userRoleDao.findUserRoleByUserId(entity.getId());
    entity.setRoles(roleList);
    entity
        .setRoleNameList(roleList.stream().map(s -> sysRoleDao.getById(s).getRoleName()).collect(Collectors.toList()));

    List<String> jobList = userJobDao.findUserJobByUserId(entity.getId());
    entity.setJobs(jobList);
    entity.setJobNameList(jobList.stream().map(s -> sysJobDao.getById(s).getJobName()).collect(Collectors.toList()));

    List<String> orgList = userOrgDao.findUserOrgByUserId(entity.getId());
    entity.setOrgs(orgList);
    entity.setOrgNameList(
        orgList.stream().map(s -> sysOrganizationDao.getById(s).getOrgName()).collect(Collectors.toList()));
  }

  /**
   * 转换对象
   * 
   * @return SysUser
   */
  private SysUser toPojo(SysUserVo vo) {
    SysUser result = new SysUser();
    result.setId(vo.getId());
    result.setActiveStatus(vo.getActiveStatus());
    result.setAvatar(vo.getAvatar());
    result.setCreateTime(vo.getCreateTime());
    result.setDefaultPassword(vo.getDefaultPassword());
    result.setEmail(vo.getEmail());
    result.setIdCard(vo.getIdCard());
    result.setIsAdmin(vo.getIsAdmin());
    result.setLastIp(vo.getLastIp());
    result.setLastTime(vo.getLastTime());
    result.setPassword(vo.getPassword());
    result.setPhone(vo.getPhone());
    result.setRealName(vo.getRealName());
    result.setRemark(vo.getRemark());
    result.setSex(vo.getSex());
    result.setStatus(vo.getStatus());
    result.setUserCode(vo.getUserCode());
    result.setUserName(vo.getUserName());
    result.setUserType(vo.getUserType());

    result.setJobNameList(vo.getJobNameList());
    result.setJobs(vo.getJobs());
    result.setRoleNameList(vo.getRoleNameList());
    result.setRoles(vo.getRoles());
    result.setOrgNameList(vo.getOrgNameList());
    result.setOrgs(vo.getOrgs());

    return result;
  }

  @Override
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public SysUserDto saveUser(SysUserVo vo) {
    try {
      SysUser entity = toPojo(vo);
      LocalDateTime time = LocalDateTime.now();
      entity.setActiveStatus(StatusEnum.YES.getCode());
      entity.setCreateTime(time);
      entity.setLastTime(time);

      List<String> roles = entity.getRoles();
      List<String> jobs = entity.getJobs();
      List<String> orgs = entity.getOrgs();
      entity = userDao.save(entity);

      // 删除用户角色关联
      userRoleDao.deleteUserRoleByUserId(entity.getId());
      for (String id : roles) {
        SysRole sysRole = sysRoleDao.getById(id);
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setSysRole(sysRole);
        sysUserRole.setSysUser(entity);
        userRoleDao.save(sysUserRole);
      }

      // 删除用户职位关联
      userJobDao.deleteUserJobByUserId(entity.getId());
      for (String id : jobs) {
        SysJob sysJob = sysJobDao.getById(id);
        SysUserJob sysUserJob = new SysUserJob();
        sysUserJob.setSysJob(sysJob);
        sysUserJob.setSysUser(entity);
        sysUserJob.setPositionOrder(1);
        sysUserJob.setStatus(StatusEnum.YES.getCode());
        sysUserJob.setCreateTime(time);
        sysUserJob.setUpdateTime(time);
        userJobDao.save(sysUserJob);
      }

      // 删除用户单位关联
      userOrgDao.deleteUserOrgByUserId(entity.getId());
      for (String id : orgs) {
        SysOrganization sysOrg = sysOrganizationDao.getById(id);
        SysUserOrg sysUserOrg = new SysUserOrg();
        sysUserOrg.setSysOrganization(sysOrg);
        sysUserOrg.setSysUser(entity);
        sysUserOrg.setPositionOrder(1);
        sysUserOrg.setStatus(StatusEnum.YES.getCode());
        sysUserOrg.setCreateTime(time);
        sysUserOrg.setUpdateTime(time);
        userOrgDao.save(sysUserOrg);
      }
      entity.setRoles(roles);
      entity.setJobs(jobs);
      entity.setOrgs(orgs);
      setName(entity);
      return convertDto(entity);
    } catch (Exception e) {
      LOGGER.error("Error saveUser: ", e);

      throw new ProjectRuntimeException();
    }
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public Integer deleteById(String id) {
    userJobDao.deleteUserJobByUserId(id);
    userOrgDao.deleteUserOrgByUserId(id);
    userRoleDao.deleteUserRoleByUserId(id);
    userDao.deleteById(id);
    return ResultEnum.SUCCESS.getCode();
  }

  @Override
  public long checkUserName(String userId, String userName) {
    try {
      Criteria<SysUser> criteria = new Criteria<>();
      criteria.add(Restrictions.eq(SysConstants.USER_NAME, DataUtils.trimToNull(userName)));
      if (!DataUtils.isEmpty(userId)) {
        criteria.add(Restrictions.ne(SysConstants.ID, userId));
      }
      return userDao.count(criteria);
    } catch (Exception e) {
      LOGGER.error("Error checkUserName:", e);
    }
    return ExistsEnum.NO.getCode();
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public Integer editPassword(String id, String p) {
    Optional<SysUser> optional = userDao.findById(id);
    if (optional.isPresent()) {
      optional.get().setPassword(DigestUtils.md5Hex(p).toUpperCase());
      userDao.save(optional.get());
      return ResultEnum.SUCCESS.getCode();
    }
    return ResultEnum.ERROR.getCode();
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public Integer editStatus(String id, String status) {
    Optional<SysUser> optional = userDao.findById(id);
    if (optional.isPresent()) {
      optional.get().setStatus(status);
      userDao.save(optional.get());
      return ResultEnum.SUCCESS.getCode();
    }
    return ResultEnum.ERROR.getCode();
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @RedisCachePut(value = CACHE_NAME, key = PublicConstants.KEY_ID)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public SysUserDto updateAvatar(String id, String avatar) {
    SysUser entity = userDao.getById(id);
    entity.setAvatar(avatar);
    userDao.save(entity);
    
    String key = CacheConstants.CACHE_LOGIN_NAME + entity.getUserName();
    redisCache.deleteObject(key);
    return convertDto(entity);
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @RedisCachePut(value = CACHE_NAME, key = PublicConstants.KEY_ID)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public SysUserDto updateUserProfile(SysUserVo sysUserVo) {
    SysUser entity = userDao.getById(sysUserVo.getId());
    entity.setEmail(sysUserVo.getEmail());
    entity.setRealName(sysUserVo.getRealName());
    entity.setPhone(sysUserVo.getPhone());
    entity.setSex(sysUserVo.getSex());
    entity.setLastTime(LocalDateTime.now());
    userDao.save(entity);
    
    String key = CacheConstants.CACHE_LOGIN_NAME + entity.getUserName();
    redisCache.deleteObject(key);
    return convertDto(entity);
  }

}
