package com.oner365.statemachine.constants;

import com.oner365.data.commons.constants.PublicConstants;

/**
 * 状态机常量
 *
 * @author zhaoyong
 *
 */
public class StatemachineConstants extends PublicConstants {

    /** Header */
    public static final String HEADER_NAME = "order";

    /** 待支付 */
    public static final String SOURCE_UNPAY = "UNPAY";

    /** 待收货 */
    public static final String SOURCE_WAIT_RECEIVE = "WAIT_RECEIVE";

    /** 完成 */
    public static final String SOURCE_FINISHED = "FINISHED";

    private StatemachineConstants() {
    }

}
