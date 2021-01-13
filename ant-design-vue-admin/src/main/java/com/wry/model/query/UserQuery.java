package com.wry.model.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author cjbi
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserQuery extends PageQuery {
    private Long id;
    private String username;
    private Integer enabled;

}
