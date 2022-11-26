package com.oner365.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.oner365.sys.entity.SysOrganization;

/**
 * 机构信息接口
 * @author zhaoyong
 */
@Repository
public interface ISysOrganizationDao extends JpaRepository<SysOrganization, String>,JpaSpecificationExecutor<SysOrganization>{

}
