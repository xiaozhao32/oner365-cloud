/*
 *    Copyright 2016 10gen Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.oner365.data.commons.auth;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * Jwt验证用户
 * @author zhaoyong
 */
public class AuthUser implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final String id;
    private final String userName;
    private final String password;
    private final String isAdmin;
    private final String tokenType;
    private final List<String> roleList;
    private final List<String> jobList;
    private final List<String> orgList;

    private final String userType;
    private final String menuType;

    @SuppressWarnings("unchecked")
    public AuthUser(JSONObject json) {
        this.id = json.getString("id");
        this.userName = json.getString("userName");
        this.password = json.getString("password");
        this.tokenType = json.getString("tokenType");
        this.isAdmin = json.getString("isAdmin");
        this.userType = json.getString("userType");
        this.menuType = json.getString("menuType");
        this.roleList = json.getObject("roles", List.class);
        this.jobList = json.getObject("jobs", List.class);
        this.orgList = json.getObject("orgs", List.class);
    }

    public String getId() {
        return id;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }
    
    public String getMenuType() {
      return menuType;
    }

    public List<String> getRoleList() {
        return roleList;
    }

    public List<String> getJobList() {
        return jobList;
    }

    public List<String> getOrgList() {
        return orgList;
    }

}
