package com.oner365.swagger.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Ldap - UserDto
 * 
 * @author zhaoyong
 * 
 */
@ApiModel(value = "LDAP 人员信息")
public class LdapUserDto implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "域名称")
  private String commonName;
  
  @ApiModelProperty(value = "名称")
  private String sn;
  
  @ApiModelProperty(value = "姓氏")
  private String givenName;
  
  @ApiModelProperty(value = "姓名")
  private String uid;
  
  @ApiModelProperty(value = "账号ID")
  private Integer uidNumber;
  
  @ApiModelProperty(value = "组ID")
  private Integer gidNumber;
  
  @ApiModelProperty(value = "密码")
  private String password;
  
  @ApiModelProperty(value = "地址")
  private String homeDirectory;
  
  @ApiModelProperty(value = "创建时间")
  private String createTime;
  
  @ApiModelProperty(value = "是否新建")
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
