package com.oner365.data.web.sequence.range.impl.name;

import com.oner365.data.commons.util.DateUtil;
import com.oner365.data.web.sequence.range.BizName;

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
