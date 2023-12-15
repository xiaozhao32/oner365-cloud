package com.oner365.ldap.repository;

import org.springframework.data.ldap.repository.LdapRepository;

import com.oner365.ldap.entity.LdapUser;

/**
 * Ldap Repository
 * 
 * @author zhaoyong
 */
public interface ILdapUsersRepository extends LdapRepository<LdapUser> {

}
