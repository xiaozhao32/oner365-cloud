package com.oner365.ldap.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.naming.Name;

import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.ldap.config.properties.LdapProperties;
import com.oner365.ldap.constants.LdapConstants;
import com.oner365.ldap.dto.LdapUserDto;
import com.oner365.ldap.entity.LdapUser;
import com.oner365.ldap.repository.ILdapUsersRepository;
import com.oner365.ldap.service.ILdapUsersService;
import com.oner365.ldap.vo.LdapUserVo;

/**
 * Ldap 实现类
 *
 * @author zhaoyong
 */
@Service
public class LdapUsersServiceImpl implements ILdapUsersService {

    @Resource
    private ILdapUsersRepository repository;

    @Resource
    public LdapTemplate ldapTemplate;

    @Resource
    public LdapProperties ldapProperties;

    @Override
    public List<LdapUserDto> findList() {
        List<LdapUser> list = repository.findAll();
        return builderList(list);
    }

    @Override
    public boolean authenticate(String userName, String password) {
        EqualsFilter filter = new EqualsFilter("uid", userName);
        return ldapTemplate.authenticate(LdapUtils.emptyLdapName(), filter.toString(), password);
    }

    @Override
    public LdapUserDto getUser(String userName) {
        Name name = getName(userName);
        Optional<LdapUser> optional = repository.findById(name);
        LdapUser entity = optional.orElse(null);
        return builder(entity);
    }

    @Override
    @Transactional
    public LdapUserDto createUser(LdapUserVo vo) {
        Name name = getName(vo.getCommonName());
        LdapUser user = new LdapUser();
        user.setCommonName(vo.getCommonName());
        user.setUid(vo.getUid());
        user.setUidNumber(vo.getUidNumber());
        user.setGivenName(vo.getGivenName());
        user.setGidNumber(vo.getGidNumber());
        user.setSn(vo.getSn());
        user.setId(name);
        user.setPassword(vo.getPassword());
        user.setHomeDirectory(LdapConstants.DIRECTORY + vo.getCommonName());
        user.setNew(Boolean.TRUE);
        LdapUser entity = repository.save(user);
        return builder(entity);
    }

    @Override
    @Transactional
    public LdapUserDto updateUser(LdapUserVo vo) {
        Name name = getName(vo.getCommonName());
        LdapUser user = new LdapUser();
        user.setCommonName(vo.getCommonName());
        user.setUid(vo.getUid());
        user.setUidNumber(vo.getUidNumber());
        user.setGivenName(vo.getGivenName());
        user.setGidNumber(vo.getGidNumber());
        user.setSn(vo.getSn());
        user.setId(name);
        user.setPassword(vo.getPassword());
        user.setNew(Boolean.FALSE);
        LdapUser entity = repository.save(user);
        return builder(entity);
    }

    private LdapUserDto builder(LdapUser entity) {
        if (entity != null) {
            LdapUserDto result = new LdapUserDto();
            result.setCommonName(entity.getCommonName());
            result.setCreateTime(entity.getCreateTime());
            result.setGidNumber(entity.getGidNumber());
            result.setGivenName(entity.getGivenName());
            result.setHomeDirectory(entity.getHomeDirectory());
            result.setNew(entity.isNew());
            result.setPassword(entity.getPassword());
            result.setSn(entity.getSn());
            result.setUid(entity.getUid());
            result.setUidNumber(entity.getUidNumber());
            return result;
        }
        return null;
    }

    private LdapUser build(LdapUserDto dto) {
        Name name = getName(dto.getCommonName());
        LdapUser user = new LdapUser();
        user.setCommonName(dto.getCommonName());
        user.setUid(dto.getUid());
        user.setUidNumber(dto.getUidNumber());
        user.setGivenName(dto.getGivenName());
        user.setGidNumber(dto.getGidNumber());
        user.setSn(dto.getSn());
        user.setId(name);
        user.setPassword(dto.getPassword());
        user.setHomeDirectory(LdapConstants.DIRECTORY + dto.getCommonName());
        user.setNew(Boolean.TRUE);
        return user;
    }

    private List<LdapUserDto> builderList(List<LdapUser> list) {
        return list.stream().map(this::builder).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteUser(LdapUserDto user) {
        LdapUser entity = build(user);
        repository.delete(entity);
    }

    private Name getName(String cn) {
        return LdapNameBuilder.newInstance().add("OU", "user").add("CN", cn).build();
    }

}
