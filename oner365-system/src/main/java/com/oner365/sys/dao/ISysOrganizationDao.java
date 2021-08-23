package com.oner365.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.oner365.sys.entity.SysOrganization;

/**
 * 机构信息接口
 * @author zhaoyong
 */
public interface ISysOrganizationDao extends JpaRepository<SysOrganization, String>,JpaSpecificationExecutor<SysOrganization>{

    /**
     * 查询机构
     * @param orgCode 机构编号
     * @return SysOrganization
     */
    @Query(value = "from SysOrganization where orgCode=?1 ")
    SysOrganization getByCode(String orgCode);

}
