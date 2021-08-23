package com.oner365.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.oner365.sys.entity.SysMenuType;

/**
 * 菜单类型接口
 * @author zhaoyong
 */
public interface ISysMenuTypeDao extends JpaRepository<SysMenuType, String>, JpaSpecificationExecutor<SysMenuType> {

    /**
     * 按类型编号查询数量
     * @param id 主键
     * @param typeCode 类型编号
     * @return int
     */
    @Query(value = "select count(id) as countRow from nt_sys_menu_type where id<>?1 and type_code=?2", nativeQuery = true)
    int countTypeById(String id, String typeCode);

    /**
     * 按类型编号查询
     * @param typeCode 类型编号
     * @return SysMenuType
     */
    @Query(value = "select * from nt_sys_menu_type where type_code=?1", nativeQuery = true)
    SysMenuType getMenuTypeByTypeCode(String typeCode);
}
