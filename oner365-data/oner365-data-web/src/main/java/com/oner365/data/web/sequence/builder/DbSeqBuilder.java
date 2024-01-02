package com.oner365.data.web.sequence.builder;

import com.oner365.data.web.sequence.range.BizName;
import com.oner365.data.web.sequence.range.impl.db.DbSeqRangeMgr;
import com.oner365.data.web.sequence.sequence.Sequence;
import com.oner365.data.web.sequence.sequence.impl.DefaultRangeSequence;

import javax.sql.DataSource;

/**
 * sequence db builder
 *
 * @author zhaoyong
 */
public class DbSeqBuilder implements SeqBuilder {

    private DataSource dataSource;

    private BizName bizName;

    private String tableName = "sequence";

    private int retryTimes = 100;

    private int step = 1000;

    private long stepStart = 0L;

    public static DbSeqBuilder create() {
        return new DbSeqBuilder();
    }

    @Override
    public Sequence build() {
        DbSeqRangeMgr dbSeqRangeMgr = new DbSeqRangeMgr();
        dbSeqRangeMgr.setDataSource(this.dataSource);
        dbSeqRangeMgr.setTableName(this.tableName);
        dbSeqRangeMgr.setRetryTimes(this.retryTimes);
        dbSeqRangeMgr.setStep(this.step);
        dbSeqRangeMgr.setStepStart(this.stepStart);
        dbSeqRangeMgr.init();
        DefaultRangeSequence sequence = new DefaultRangeSequence();
        sequence.setName(this.bizName);
        sequence.setSeqRangeMgr(dbSeqRangeMgr);
        return sequence;
    }

    public DbSeqBuilder dataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    public DbSeqBuilder tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public DbSeqBuilder retryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
        return this;
    }

    public DbSeqBuilder step(int step) {
        this.step = step;
        return this;
    }

    public DbSeqBuilder bizName(BizName bizName) {
        this.bizName = bizName;
        return this;
    }

    public DbSeqBuilder stepStart(long stepStart) {
        this.stepStart = stepStart;
        return this;
    }
}
