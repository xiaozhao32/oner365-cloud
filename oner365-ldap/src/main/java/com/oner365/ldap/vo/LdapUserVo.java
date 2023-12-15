package com.oner365.ldap.vo;

import java.io.Serializable;

/**
 * Ldap - Vo
 * 
 * @author zhaoyong
 * 
 */
public class LdapUserVo implements Serializable {

  private static final long serialVersionUID = 1L;

  private String commonName;
  private String sn;
  private String givenName;
  private String uid;
  private Integer uidNumber;
  private Integer gidNumber;
  private String password;

  public String getCommonName() {
    return commonName;
  }

  public void setCommonName(String commonName) {
    this.commonName = commonName;
  }

  public String getSn() {
    return sn;
  }

  public void setSn(String sn) {
    this.sn = sn;
  }

  public String getGivenName() {
    return givenName;
  }

  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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

}
