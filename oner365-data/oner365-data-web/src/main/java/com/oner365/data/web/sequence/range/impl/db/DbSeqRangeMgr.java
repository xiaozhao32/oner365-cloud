package com.oner365.data.web.sequence.range.impl.db;

import javax.sql.DataSource;

import com.oner365.data.web.sequence.exception.SeqException;
import com.oner365.data.web.sequence.range.SeqRange;
import com.oner365.data.web.sequence.range.SeqRangeMgr;

/**
 * db sequence
 *
 * @author zhaoyong
 *
 */
public class DbSeqRangeMgr implements SeqRangeMgr {

  private int step = 1000;

  private long stepStart = 0L;

  private int retryTimes = 100;

  private DataSource dataSource;

  private String tableName = "range";

  @Override
  public SeqRange nextRange(String name) {
    if (isEmpty(name)) {
      throw new SecurityException("[DbSeqRangeMgr-nextRange] name is empty.");
    }
    for (int i = 0; i < getRetryTimes(); i++) {
      Long oldValue = BaseDbHelper.selectRange(getDataSource(), getRealTableName(), name, getStepStart());
      if (null != oldValue) {
        long newValue = oldValue + getStep();
        if (BaseDbHelper.updateRange(getDataSource(), getRealTableName(), newValue, oldValue, name)) {
          return new SeqRange(oldValue + 1L, newValue);
        }
      }
    }
    throw new SeqException("Retried too many times, retryTimes = " + getRetryTimes());
  }

  @Override
  public void init() {
    checkParam();
    BaseDbHelper.createTable(getDataSource(), getRealTableName());
  }

  private boolean isEmpty(String str) {
    return (null == str || str.trim().length() == 0);
  }

  private String getRealTableName() {
    return getTableName();
  }

  private void checkParam() {
    if (this.step <= 0) {
      throw new SecurityException("[DbSeqRangeMgr-checkParam] step must greater than 0.");
    }
    if (this.stepStart < 0L) {
      throw new SecurityException("[DbSeqRangeMgr-setStepStart] stepStart < 0.");
    }
    if (this.retryTimes <= 0) {
      throw new SecurityException("[DbSeqRangeMgr-setRetryTimes] retryTimes must greater than 0.");
    }
    if (null == this.dataSource) {
      throw new SecurityException("[DbSeqRangeMgr-setDataSource] dataSource is null.");
    }
    if (isEmpty(this.tableName)) {
      throw new SecurityException("[DbSeqRangeMgr-setTableName] tableName is empty.");
    }
  }

  public int getStep() {
    return this.step;
  }

  public void setStep(int step) {
    this.step = step;
  }

  public long getStepStart() {
    return this.stepStart;
  }

  public void setStepStart(long stepStart) {
    this.stepStart = stepStart;
  }

  public int getRetryTimes() {
    return this.retryTimes;
  }

  public void setRetryTimes(int retryTimes) {
    this.retryTimes = retryTimes;
  }

  public DataSource getDataSource() {
    return this.dataSource;
  }

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public String getTableName() {
    return this.tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }
}
