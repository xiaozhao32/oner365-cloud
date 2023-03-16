package com.oner365.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.oner365.sys.entity.SysMenu;

/**
 * 系统菜单接口
 * @author zhaoyong
 */
public interface ISysMenuDao extends JpaRepository<SysMenu, String>,JpaSpecificationExecutor<SysMenu>{

    /**
     * 按菜单类型编号查询
     * @param typeCode 类型编号
     * @return List
     */
    @Query(value = "select m.* from nt_sys_menu    m, nt_sys_menu_type t where m.menu_type_id=t.id and t.type_code=?1 and m.status='1' order by m.menu_order ",nativeQuery = true)
    List<SysMenu> findMenuByTypeCode(String typeCode);

}
