package com.wry.model.query;

import lombok.Data;


@Data
public class PageQuery {
    private int pageNo;
    private int pageSize;
    private String sortField;
    /**
     * 排序方式默认 ASC
     */
    private boolean acs;
}
