package com.wry.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


@Data
public class LoginDTO {
    @NotBlank
    private String username;
    @NotBlank
    @Length(min = 1, max = 12)
    private String password;
}
