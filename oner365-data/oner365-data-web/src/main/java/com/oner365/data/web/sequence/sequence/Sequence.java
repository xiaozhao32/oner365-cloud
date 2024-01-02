package com.oner365.data.web.sequence.sequence;

/**
 * 分布式生成UUID
 *
 * @author zhaoyong
 *
 */
public interface Sequence {

    /**
     * 生成下一个值
     * 
     * @return long
     */
    long nextValue();

    /**
     * 生成下一个值字符串
     * 
     * @return String
     */
    String nextNo();

    /**
     * 生成下一个id
     * 
     * @return int
     */
    int nextId();
}
