package com.wry.model.page;





import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;
import java.util.List;


public class PageWrapper<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<T> data;

    private long totalCount;

    private long pageNo;

    private long pageSize;


    public PageWrapper() {
    }

    public PageWrapper(Page<T> page) {
        this.data = page.getRecords();
        this.totalCount = page.getTotal();
        this.pageSize = page.getSize();
        this.pageNo = page.getCurrent();
    }

    public PageWrapper(List<T> data, long totalCount, int pageNo, int pageSize) {
        this.data = data;
        this.totalCount = totalCount;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getPageNo() {
        return pageNo;
    }

    public void setPageNo(long pageNo) {
        this.pageNo = pageNo;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }
}
