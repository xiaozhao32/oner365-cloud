package com.oner365.common.page;

import java.io.Serializable;

import com.oner365.common.constants.PublicConstants;

/**
 * 分页对象
 * @author zhaoyong
 */
public class PageInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页码
     */
    private int pageIndex = 1;

    /**
     * 总页数
     */
    private long totalPages;

    /**
     * 每页记录数
     */
    private int pageSize = PublicConstants.PAGE_SIZE;

    /**
     * 总记录数
     */
    private long totalResult;

    /**
     * 上一页
     */
    private int prePage = 1;

    /**
     * 下一页
     */
    private long nextPage;

    public PageInfo() {

    }

    /**
     * mybatis page
     * @param pageIndex 页码
     * @param totalResult 总条数
     */
    public PageInfo(int pageIndex, long totalResult) {
        this.pageIndex = pageIndex;
        this.totalPages = (totalResult - 1) / pageSize + 1;
        this.totalResult = totalResult;

        this.prePage = this.pageIndex - 1;
        if (this.prePage <= 0) {
            this.prePage = this.pageIndex;
        }
        this.nextPage = this.pageIndex + 1L;
        if (this.nextPage > this.totalPages) {
            this.nextPage = this.totalPages;
        }

    }

    /**
     * jpa page
     * @param pageIndex 页码
     * @param pageSize 分页条数
     * @param totalResult 总条数
     */
    public PageInfo(int pageIndex, int pageSize, long totalResult) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalPages = (totalResult - 1) / pageSize + 1;
        this.totalResult = totalResult;

        this.prePage = this.pageIndex - 1;
        if (this.prePage <= 0) {
            this.prePage = this.pageIndex;
        }
        this.nextPage = this.pageIndex + 1L;
        if (this.nextPage > this.totalPages) {
            this.nextPage = this.totalPages;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(long totalResult) {
        this.totalPages = (totalResult - 1) / pageSize + 1;
        this.totalResult = totalResult;
    }

    public long getNextPage() {
        return nextPage;
    }

    public void setNextPage(long nextPage) {
        this.nextPage = nextPage;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

}
