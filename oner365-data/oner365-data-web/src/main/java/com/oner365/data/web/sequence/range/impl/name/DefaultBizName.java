package com.oner365.data.web.sequence.range.impl.name;

import com.oner365.data.web.sequence.range.BizName;

/**
 * sequence name
 *
 * @author zhaoyong
 */
public class DefaultBizName implements BizName {

    private final String bizName;

    public DefaultBizName(String bizName) {
        this.bizName = bizName;
    }

    @Override
    public String create() {
        return this.bizName;
    }

}
