package com.oner365.gateway.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页
 * 
 * @author zhaoyong
 */
public class PageInfo<T> implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 当前页码
   */
  private int pageIndex = 1;

  /**
   * 每页记录数
   */
  private int size = 15;

  /**
   * 对象集合
   */
  private List<T> content = new ArrayList<>();

  /**
   * 总记录数
   */
  private long totalElements;

  /**
   * 总页数
   */
  private long totalPages;

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
   * jpa page
   * 
   * @param content     集合
   * @param pageIndex   页码
   * @param pageSize    分页条数
   * @param totalResult 总条数
   */
  public PageInfo(List<T> content, int pageIndex, int size, long totalElements) {
    this.content = content;
    this.pageIndex = pageIndex;
    this.size = size;
    this.totalPages = (totalElements - 1) / size + 1;
    this.totalElements = totalElements;

    this.prePage = this.pageIndex - 1;
    if (this.prePage <= 0) {
      this.prePage = this.pageIndex;
    }
    this.nextPage = this.pageIndex + 1L;
    if (this.nextPage > this.totalPages) {
      this.nextPage = this.totalPages;
    }
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public long getTotalElements() {
    return totalElements;
  }

  public void setTotalElements(long totalElements) {
    this.totalElements = totalElements;
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

  public List<T> getContent() {
    return content;
  }

  public void setContent(List<T> content) {
    this.content = content;
  }

}
