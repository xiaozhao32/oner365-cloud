package com.oner365.ldap.dto;

import java.io.Serializable;

/**
 * Ldap - UserDto
 *
 * @author zhaoyong
 *
 */
public class LdapUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String commonName;

    private String sn;

    private String givenName;

    private String uid;

    private Integer uidNumber;

    private Integer gidNumber;

    private String password;

    private String homeDirectory;

    private String createTime;

    private boolean isNew;

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
