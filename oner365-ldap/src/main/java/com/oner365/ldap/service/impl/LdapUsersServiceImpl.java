package com.oner365.ldap.service.impl;

import java.util.List;
import java.util.Optional;

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
  public List<LdapUser> findList() {
    return repository.findAll();
  }

  @Override
  public boolean authenticate(String userName, String password) {
    EqualsFilter filter = new EqualsFilter("uid", userName);
    return ldapTemplate.authenticate(LdapUtils.emptyLdapName(), filter.toString(), password);
  }

  @Override
  public LdapUser getUser(String userName) {
    Name name = getName(userName);
    Optional<LdapUser> optional = repository.findById(name);
    return optional.orElse(null);
  }

  @Override
  @Transactional
  public LdapUser createUser(LdapUserVo vo) {
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
    return repository.save(user);
  }

  @Override
  @Transactional
  public LdapUser updateUser(LdapUserVo vo) {
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
    return repository.save(user);
  }

  @Override
  @Transactional
  public void deleteUser(LdapUser user) {
    repository.delete(user);
  }

  private Name getName(String cn) {
    return LdapNameBuilder.newInstance().add("OU", "user").add("CN", cn).build();
  }

}
