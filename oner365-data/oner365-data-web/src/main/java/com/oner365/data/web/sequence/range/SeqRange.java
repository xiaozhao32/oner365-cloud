package com.oner365.data.web.sequence.range;

import java.util.concurrent.atomic.AtomicLong;

/**
 * sequence range
 *
 * @author zhaoyong
 */
public class SeqRange {

    private final long min;

    private final long max;

    private final AtomicLong value;

    private volatile boolean over = false;

    public SeqRange(long min, long max) {
        this.min = min;
        this.max = max;
        this.value = new AtomicLong(min);
    }

    public long getAndIncrement() {
        long currentValue = this.value.getAndIncrement();
        if (currentValue > this.max) {
            this.over = true;
            return -1L;
        }
        return currentValue;
    }

    public long getMin() {
        return this.min;
    }

    public long getMax() {
        return this.max;
    }

    public boolean isOver() {
        return this.over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    @Override
    public String toString() {
        return "max: " + this.max + ", min: " + this.min + ", value: " + this.value;
    }
}
