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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.oner365.common.cache.RedisCache;
import com.oner365.common.cache.annotation.RedisCacheAble;
import com.oner365.common.cache.annotation.RedisCachePut;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.ExistsEnum;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.query.Criteria;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.common.query.Restrictions;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dao.ISysUserDao;
import com.oner365.sys.dao.ISysUserJobDao;
import com.oner365.sys.dao.ISysUserOrgDao;
import com.oner365.sys.dao.ISysUserRoleDao;
import com.oner365.sys.dto.LoginUserDto;
import com.oner365.sys.entity.SysJob;
import com.oner365.sys.entity.SysOrganization;
import com.oner365.sys.entity.SysRole;
import com.oner365.sys.entity.SysUser;
import com.oner365.sys.entity.SysUserJob;
import com.oner365.sys.entity.SysUserOrg;
import com.oner365.sys.entity.SysUserRole;
import com.oner365.sys.service.ISysJobService;
import com.oner365.sys.service.ISysOrganizationService;
import com.oner365.sys.service.ISysRoleService;
import com.oner365.sys.service.ISysUserService;
import com.oner365.util.DataUtils;
import com.oner365.util.DateUtil;
import com.oner365.util.JwtUtils;
import com.oner365.util.RequestUtils;

/**
 * ISysUserService实现类
 * 
 * @author zhaoyong
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserServiceImpl.class);

    private static final String CACHE_LOGIN_NAME = "Auth:Login";
    private static final String CACHE_NAME = "SysUser";

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ISysUserDao userDao;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysOrganizationService sysOrganizationService;

    @Autowired
    private ISysJobService sysJobService;

    @Autowired
    private ISysUserRoleDao userRoleDao;

    @Autowired
    private ISysUserOrgDao userOrgDao;

    @Autowired
    private ISysUserJobDao userJobDao;

    @Value("${ACCESS_TOKEN_SECRET}")
    public String accessTokenSecret;

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    public LoginUserDto login(String userName, String p) {
        String password = DigestUtils.md5Hex(p).toUpperCase();
        SysUser user = getUserByUserName(userName, password);
        if (user != null) {
            String key = CACHE_LOGIN_NAME + ":" + userName;
            JSONObject cache = redisCache.getCacheObject(key);
            if (cache != null) {
                return JSON.toJavaObject(cache, LoginUserDto.class);
            }

            Date time = DateUtil.after(new Date(), 30 * 24, Calendar.HOUR);
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
            String accessToken = JwtUtils.generateToken(tokenJson.toJSONString(), time, accessTokenSecret);

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
    public Page<SysUser> pageList(QueryCriteriaBean data) {
        try {
            Pageable pageable = QueryUtils.buildPageRequest(data);
            Page<SysUser> page = userDao.findAll(QueryUtils.buildCriteria(data), pageable);
            page.getContent().forEach(this::setName);
            return page;
        } catch (Exception e) {
            LOGGER.error("Error pageList:", e);
        }
        return null;
    }

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public List<SysUser> findList(QueryCriteriaBean data) {
        try {
            return userDao.findAll(QueryUtils.buildCriteria(data));
        } catch (Exception e) {
            LOGGER.error("Error findList:", e);
        }
        return Collections.emptyList();
    }

    @Override
    @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    public SysUser getById(String id) {
        try {
            Optional<SysUser> optional = userDao.findById(id);
            if (optional.isPresent()) {
                SysUser entity = optional.get();
                setName(entity);
                return entity;
            }
        } catch (Exception e) {
            LOGGER.error("Error getById:", e);
        }
        return null;
    }

    private SysUser getUserByUserName(String userName, String password) {
        Criteria<SysUser> criteria = new Criteria<>();
        criteria.add(Restrictions.eq(SysConstants.USER_NAME, userName));
        criteria.add(Restrictions.eq(SysConstants.PASS, password));
        criteria.add(Restrictions.eq(SysConstants.STATUS, StatusEnum.YES.getOrdinal()));
        Optional<SysUser> optional = userDao.findOne(criteria);
        return optional.orElse(null);
    }

    private void setName(SysUser entity) {
        List<String> roleList = userRoleDao.findUserRoleByUserId(entity.getId());
        entity.setRoles(roleList);
        entity.setRoleNameList(
                roleList.stream().map(s -> sysRoleService.getById(s).getRoleName()).collect(Collectors.toList()));

        List<String> jobList = userJobDao.findUserJobByUserId(entity.getId());
        entity.setJobs(jobList);
        entity.setJobNameList(
                jobList.stream().map(s -> sysJobService.getById(s).getJobName()).collect(Collectors.toList()));

        List<String> orgList = userOrgDao.findUserOrgByUserId(entity.getId());
        entity.setOrgs(orgList);
        entity.setOrgNameList(
                orgList.stream().map(s -> sysOrganizationService.getById(s).getOrgName()).collect(Collectors.toList()));
    }

    @Override
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public SysUser saveUser(SysUser entity) {
        try {
            LocalDateTime time = LocalDateTime.now();
            entity.setActiveStatus(StatusEnum.YES.getOrdinal());
            entity.setCreateTime(time);
            entity.setLastTime(time);

            List<String> roles = entity.getRoles();
            List<String> jobs = entity.getJobs();
            List<String> orgs = entity.getOrgs();
            entity = userDao.save(entity);

            // 删除用户角色关联
            userRoleDao.deleteUserRoleByUserId(entity.getId());
            for (String id : roles) {
                SysRole sysRole = sysRoleService.getById(id);
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setSysRole(sysRole);
                sysUserRole.setSysUser(entity);
                userRoleDao.save(sysUserRole);
            }

            // 删除用户职位关联
            userJobDao.deleteUserJobByUserId(entity.getId());
            for (String id : jobs) {
                SysJob sysJob = sysJobService.getById(id);
                SysUserJob sysUserJob = new SysUserJob();
                sysUserJob.setSysJob(sysJob);
                sysUserJob.setSysUser(entity);
                sysUserJob.setPositionOrder(Integer.parseInt(PublicConstants.DEFAULT_VALUE));
                sysUserJob.setStatus(StatusEnum.YES.getOrdinal());
                sysUserJob.setCreateTime(time);
                sysUserJob.setUpdateTime(time);
                userJobDao.save(sysUserJob);
            }

            // 删除用户单位关联
            userOrgDao.deleteUserOrgByUserId(entity.getId());
            for (String id : orgs) {
                SysOrganization sysOrg = sysOrganizationService.getById(id);
                SysUserOrg sysUserOrg = new SysUserOrg();
                sysUserOrg.setSysOrganization(sysOrg);
                sysUserOrg.setSysUser(entity);
                sysUserOrg.setPositionOrder(Integer.parseInt(PublicConstants.DEFAULT_VALUE));
                sysUserOrg.setStatus(StatusEnum.YES.getOrdinal());
                sysUserOrg.setCreateTime(time);
                sysUserOrg.setUpdateTime(time);
                userOrgDao.save(sysUserOrg);
            }
            entity.setRoles(roles);
            entity.setJobs(jobs);
            entity.setOrgs(orgs);
            setName(entity);
            return entity;
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
        return ResultEnum.SUCCESS.getOrdinal();
    }

    @Override
    public long checkUserName(String userId, String userName) {
        try {
            Criteria<SysUser> criteria = new Criteria<>();
            criteria.add(Restrictions.eq(SysConstants.USER_NAME, DataUtils.trimToNull(userName)));
            if (!Strings.isNullOrEmpty(userId)) {
                criteria.add(Restrictions.ne(SysConstants.ID, userId));
            }
            return userDao.count(criteria);
        } catch (Exception e) {
            LOGGER.error("Error checkUserName:", e);
        }
        return ExistsEnum.NO.getOrdinal();
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public Integer editPassword(String id, String p) {
        Optional<SysUser> optional = userDao.findById(id);
        if (optional.isPresent()) {
            optional.get().setPassword(DigestUtils.md5Hex(p).toUpperCase());
            userDao.save(optional.get());
            return ResultEnum.SUCCESS.getOrdinal();
        }
        return ResultEnum.ERROR.getOrdinal();
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public Integer editStatus(String id, String status) {
        Optional<SysUser> optional = userDao.findById(id);
        if (optional.isPresent()) {
            optional.get().setStatus(status);
            userDao.save(optional.get());
            return ResultEnum.SUCCESS.getOrdinal();
        }
        return ResultEnum.ERROR.getOrdinal();
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @RedisCachePut(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public SysUser update(SysUser sysUser) {
        SysUser result = userDao.save(sysUser);

        String key = CACHE_LOGIN_NAME + ":" + sysUser.getUserName();
        redisCache.deleteObject(key);
        return result;
    }

}
