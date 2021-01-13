package com.wry.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


@ApiModel
@Data
public class LoginDTO {

    @NotBlank
    @ApiModelProperty("用户名")
    private String username;
    @NotBlank
    @Length(min = 1, max = 12)
    @ApiModelProperty("密码")
    private String password;
}
