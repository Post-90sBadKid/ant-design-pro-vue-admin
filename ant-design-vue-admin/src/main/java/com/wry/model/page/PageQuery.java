package com.wry.model.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel
@Data
public class PageQuery {
    @ApiModelProperty("页码")
    private int pageNo;
    @ApiModelProperty("每页显示的数量")
    private int pageSize;
    @ApiModelProperty("排序字段名称")
    private String sortField;
    @ApiModelProperty("排序方式默认 ASC")
    private boolean asc;
}
