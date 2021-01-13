package com.wry.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserBatchDeleteDTO {
    @NotEmpty
    private Long[] ids;
}
