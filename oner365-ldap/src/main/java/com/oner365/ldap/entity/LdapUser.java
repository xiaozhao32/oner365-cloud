package com.oner365.ldap.entity;

import java.io.Serializable;

import javax.naming.Name;

import org.springframework.data.domain.Persistable;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Attribute.Type;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.ldap.odm.annotations.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Ldap - Po
 *
 * @author zhaoyong
 *
 */
@Entry(base = "", objectClasses = { "posixAccount", "top", "inetOrgPerson" })
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
public class LdapUser implements Persistable<Object>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Name id;

    @Attribute(name = "cn")
    private String commonName;

    @Attribute(name = "sn")
    private String sn;

    @Attribute(name = "givenName")
    private String givenName;

    @Attribute(name = "uid")
    private String uid;

    @Attribute(name = "uidNumber")
    private Integer uidNumber;

    @Attribute(name = "gidNumber")
    private Integer gidNumber;

    @Attribute(name = "userPassword", type = Type.STRING)
    private String password;

    @Attribute(name = "homeDirectory")
    private String homeDirectory;

    @Attribute(name = "createTimestamp")
    private String createTime;

    @Transient
    private boolean isNew;

    public Name getId() {
        return id;
    }

    public void setId(Name id) {
        this.id = id;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public Integer getUidNumber() {
        return uidNumber;
    }

    public void setUidNumber(Integer uidNumber) {
        this.uidNumber = uidNumber;
    }

    public Integer getGidNumber() {
        return gidNumber;
    }

    public void setGidNumber(Integer gidNumber) {
        this.gidNumber = gidNumber;
    }

    public String getHomeDirectory() {
        return homeDirectory;
    }

    public void setHomeDirectory(String homeDirectory) {
        this.homeDirectory = homeDirectory;
    }

}
