package com.oner365.swagger.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Ldap - Vo
 *
 * @author zhaoyong
 *
 */
@ApiModel(value = "LDAP 用户信息")
public class LdapUserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组织名称")
    private String commonName;

    @ApiModelProperty(value = "序列号")
    private String sn;

    @ApiModelProperty(value = "名称")
    private String givenName;

    @ApiModelProperty(value = "账号id")
    private String uid;

    @ApiModelProperty(value = "uidNumber")
    private Integer uidNumber;

    @ApiModelProperty(value = "gidNumber")
    private Integer gidNumber;

    @ApiModelProperty(value = "密码")
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
