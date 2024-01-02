package com.oner365.data.web.sequence.properties;

import com.oner365.data.commons.constants.PublicConstants;

/**
 * sequence properties
 *
 * @author zhaoyong
 */
public class BaseSequenceProperties {

    private int step = 1000;

    private long stepStart = 0L;

    private String bizName = PublicConstants.NAME;

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public long getStepStart() {
        return stepStart;
    }

    public void setStepStart(long stepStart) {
        this.stepStart = stepStart;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

}
