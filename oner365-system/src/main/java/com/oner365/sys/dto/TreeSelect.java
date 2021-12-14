package com.oner365.sys.dto;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oner365.util.DataUtils;

/**
 * Treeselect树结构实体类
 * 
 * @author zhaoyong
 */
public class TreeSelect implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private String id;

    /** 节点名称 */
    private String label;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect() {

    }
    
    public TreeSelect(SysOrganizationDto organization) {
        this.id = organization.getId();
        this.label = organization.getOrgName();
        if (!DataUtils.isEmpty(organization.getChildren())) {
            this.children = organization.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
        }
    }

    public TreeSelect(SysMenuDto menu) {
        this.id = menu.getId();
        this.label = menu.getMenuName();
        if (!DataUtils.isEmpty(menu.getChildren())) {
            this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<TreeSelect> getChildren() {
        return children;
    }

    public void setChildren(List<TreeSelect> children) {
        this.children = children;
    }
}
