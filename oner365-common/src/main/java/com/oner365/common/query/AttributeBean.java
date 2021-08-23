package com.oner365.common.query;

import java.io.Serializable;

/**
 * 查询属性配置
 * 
 * @author zhaoyong
 */
public class AttributeBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String opt;

    private String key;

    private String val;

    public AttributeBean() {

    }

    public AttributeBean(String key, String val) {
        this.key = key;
        this.val = val;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }
}
