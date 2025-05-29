package com.oner365.statemachine.enums;

import com.oner365.data.commons.enums.BaseEnum;

/**
 * 枚举 - 订单状态
 *
 * @author zhaoyong
 */
public enum OrderStateEnum implements BaseEnum {

    /** 待支付 */
    UNPAY("待支付"),
    /** 待收货 */
    WAIT_RECEIVE("待收货"),
    /** 完成 */
    FINISHED("完成");

    /**
     * 名称
     */
    private final String name;

    /**
     * 构造方法
     * @param name 名称
     */
    OrderStateEnum(String name) {
        this.name = name;
    }

    /**
     * get name
     * @return name
     */
    @Override
    public String getName() {
        return name;
    }

}
