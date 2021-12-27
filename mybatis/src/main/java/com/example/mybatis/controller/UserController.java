package com.example.mybatis.controller;

import com.example.mybatis.model.BadRequestException;
import com.example.mybatis.model.LoginRequest;
import com.example.mybatis.model.RegisterRequest;
import com.example.mybatis.service.UserService;
import com.example.mybatis.util.JwtUtil;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api
@RestController
@RequestMapping(path = "api/v1/user")
@AllArgsConstructor
@Validated
public class UserController {
    @Autowired
    private final JwtUtil jwtUtil;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final UserService userService;

    @PostMapping("/register")
    public String register( @RequestBody @Valid RegisterRequest request) {
        return userService.register(request);

    }

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody @Valid LoginRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new BadRequestException("Invalid username or password");
        }
        return jwtUtil.generateToken(authRequest.getUsername());
    }

}
