package com.wry.model.page;

import com.wry.common.util.BeanFieldUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.util.Optional;


@ApiModel
@Data
public class PageQuery {
    @ApiModelProperty("页码")
    private int pageNo;
    @ApiModelProperty("每页显示的数量")
    private int pageSize;
    @ApiModelProperty("排序字段名称")
    @Getter(value = AccessLevel.NONE)
    private String sortField;
    @ApiModelProperty(hidden = true)
    @Getter(value = AccessLevel.NONE)
    private boolean asc;
    @ApiModelProperty("排序方式默认 ASC")
    private String sortOrder;

    public boolean isAsc() {
        return Optional.ofNullable(this.sortOrder).orElse("ascend").equals("ascend");
    }

    public String getSortField() {
        return BeanFieldUtil.HumpToUnderline(Optional.ofNullable(this.sortField).orElse("id"));
    }
}
