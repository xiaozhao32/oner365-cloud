package com.oner365.common.query;

import java.io.Serializable;

/**
 * 查询属性配置
 * @author zhaoyong
 */
public class AttributeBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 相等 EQ, 不相等 NE, 模糊 LIKE, 大于 GT, 小于 LT, 大于等于 GTE, 小于等于 LTE, 和 AND, 或 OR, 包含 IN, 之间 BE
     */
    private String opt;

    /**
     * 键
     */
    private String key;

    /**
     * 值
     */
    private Object val;

    /**
     * 构造对象
     */
    public AttributeBean() {
        super();
    }

    public AttributeBean(String key, Object val) {
        this.key = key;
        this.val = val;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getVal() {
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
