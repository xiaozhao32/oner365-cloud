package com.oner365.common.sequence.sequence;

import com.oner365.common.sequence.range.BizName;
import com.oner365.common.sequence.range.SeqRangeMgr;

/**
 * 根据规则生成算法
 * 
 * @author zhaoyong
 */
public interface RangeSequence extends Sequence {

    /**
     * 设置参数
     * 
     * @param paramSeqRangeMgr 对象
     */
    void setSeqRangeMgr(SeqRangeMgr paramSeqRangeMgr);

    /**
     * 设置名称
     * 
     * @param paramBizName 名称
     */
    void setName(BizName paramBizName);
}
