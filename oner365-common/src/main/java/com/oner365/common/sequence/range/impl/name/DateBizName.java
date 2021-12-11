package com.oner365.common.sequence.range.impl.name;

import com.oner365.common.sequence.range.BizName;
import com.oner365.util.DateUtil;

/**
 * sequence name
 *
 * @author zhaoyong
 */
public class DateBizName implements BizName {

    private String bizName;

    public DateBizName() {
    }

    public DateBizName(String bizName) {
        this.bizName = bizName;
    }

    @Override
    public String create() {
        return this.bizName + DateUtil.getCurrentDate();
    }
}
