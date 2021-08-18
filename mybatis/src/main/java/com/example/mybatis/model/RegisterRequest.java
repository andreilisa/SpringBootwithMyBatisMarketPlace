package com.example.mybatis.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@AllArgsConstructor
@EqualsAndHashCode
@Data
public class RegisterRequest {
    @NotEmpty(message = "username must be between 4 and 32")
    @Pattern(regexp="^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$",message = "Invalid Input")
    @Size(min = 4, max = 32)
    private final String username;
    @NotEmpty(message = "password must not be empty")
    @Size(min = 6, max = 25)
    private final String password;
    @NotEmpty(message ="Email must not be empty")
    @Email(message = " Email must be valid")
    private final String email;
}
