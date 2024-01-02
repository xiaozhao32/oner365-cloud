
package com.oner365.data.web.sequence.sequence.impl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.oner365.data.commons.util.DateUtil;
import com.oner365.data.web.sequence.exception.SeqException;
import com.oner365.data.web.sequence.range.BizName;
import com.oner365.data.web.sequence.range.SeqRange;
import com.oner365.data.web.sequence.range.SeqRangeMgr;
import com.oner365.data.web.sequence.sequence.RangeSequence;;

/**
 * sequence default range
 *
 * @author zhaoyong
 */
public class DefaultRangeSequence implements RangeSequence {

  private final Lock lock = new ReentrantLock();

  private SeqRangeMgr seqRangeMgr;

  private SeqRange currentRange;

  private BizName bizName;

  @Override
  public long nextValue() {
    String name = this.bizName.create();
    if (null == this.currentRange) {
      this.lock.lock();
      try {
        this.currentRange = this.seqRangeMgr.nextRange(name);
      } finally {
        this.lock.unlock();
      }
    }
    long value = this.currentRange.getAndIncrement();
    if (value == -1L) {
      this.lock.lock();
      try {
        while (true) {
          if (this.currentRange.isOver()) {
            this.currentRange = this.seqRangeMgr.nextRange(name);
          }
          value = this.currentRange.getAndIncrement();
          if (value == -1L) {
            continue;
          }
          break;
        }
      } finally {
        this.lock.unlock();
      }
    }
    if (value < 0L) {
      throw new SeqException("Sequence value overflow, value = " + value);
    }
    return value;
  }

  @Override
  public int nextId() {
    long value;
    this.lock.lock();
    try {
      value = System.currentTimeMillis() / 1000L;
    } finally {
      this.lock.unlock();
    }
    if (value <= 0L) {
      throw new SeqException("Sequence value overflow, value = " + value);
    }
    return (int) value;
  }

  @Override
  public String nextNo() {
    return String.format("%s%05d",
        DateUtil.dateToString(DateUtil.getDate(), DateUtil.DATE_HH + DateUtil.DATE_MM + DateUtil.DATE_SS), nextValue());
  }

  @Override
  public void setSeqRangeMgr(SeqRangeMgr seqRangeMgr) {
    this.seqRangeMgr = seqRangeMgr;
  }

  @Override
  public void setName(BizName name) {
    this.bizName = name;
  }
}
