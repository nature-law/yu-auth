package com.xia.yuauth.controller.web;

/**
 * description: 分页参数
 *
 * @author wanghaoxin
 * date     2021/12/5 21:45
 * @version 1.0
 */
public class PageParams {
    private Integer pageNum;
    private Integer pageSize;
    private Boolean isPaging;
    private Sort sort;

    public PageParams() {
    }

    class Sort {

        class Order {

        }
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Boolean getPaging() {
        return isPaging;
    }

    public void setPaging(Boolean paging) {
        isPaging = paging;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
