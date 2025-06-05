package com.oner365.shardingsphere.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oner365.data.commons.constants.PublicConstants;

/**
 * 主从测试
 *
 * @author zhaoyong
 */
@Entity
@Table(name = "t_master_slave")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class MasterSlave implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = PublicConstants.UUID)
    private String id;

    /**
     * 订单id
     */
    @Column(name = "test_name", nullable = false)
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
