package com.oner365.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.oner365.sys.entity.SysMenuOperation;

/**
 * 菜单操作权限接口
 * @author zhaoyong
 */
public interface ISysMenuOperationDao extends JpaRepository<SysMenuOperation, String>,JpaSpecificationExecutor<SysMenuOperation>{

    /**
     * 查询数量
     * @param id 编号
     * @param typeCode 类型
     * @return int
     */
    @Query(value = "select count(id) as countRow from nt_sys_operation where id<>?1 and operation_type=?2",nativeQuery = true)
    int countTypeById(String id, String typeCode);

}
