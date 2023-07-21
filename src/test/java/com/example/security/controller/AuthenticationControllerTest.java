package com.example.security.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.security.controller.dto.LoginDto;
import com.example.security.jwt.TokenProvider;
import mockit.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;

import java.util.Objects;

public class AuthenticationControllerTest {
    @Tested
    private AuthenticationController unit;
    @Injectable
    private TokenProvider tokenProvider;
    @Injectable
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Mocked AuthenticationManager authenticationManager;
    @Mocked Authentication authentication;

    @Test
    public void authorize() {
        final String username = randomAlphanumeric(10);
        final String password = randomAlphanumeric(10);
        final LoginDto loginDto = new LoginDto(username, password, false);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        final String token = randomAlphanumeric(10);

        new Expectations() {{
            authenticationManagerBuilder.getObject();
            result = authenticationManager;

            authenticationManager.authenticate(authenticationToken);
            result = authentication;

            tokenProvider.createToken(authentication, loginDto.isRememberMe());
            result = token;
        }};

        assertThat(unit.authorize(loginDto))
                .returns(HttpStatus.OK.value(), ResponseEntity::getStatusCodeValue)
                .returns(token, jwtToken -> Objects.requireNonNull(jwtToken.getBody()).idToken());

        new Verifications() {{
            tokenProvider.createToken(authentication, loginDto.isRememberMe());
        }};
    }
}