package com.example.mybatis.model;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotBlank;


@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class LoginRequest {
    @NotBlank(message = "username is required")
    private final String username;
    @NotBlank(message = "password is required")
    private final String password;

}