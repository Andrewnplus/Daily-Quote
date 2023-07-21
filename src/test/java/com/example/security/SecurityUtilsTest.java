package com.example.security;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;

import mockit.*;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;


public class SecurityUtilsTest {
    @Test
    public void getCurrentUsername_withUserDetailsPrincipal() {
        final UserDetails userDetails = new User(randomAlphanumeric(10), randomAlphanumeric(10), Set.of());
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        final SecurityContextImpl securityContext = new SecurityContextImpl(authentication);

        new MockUp<SecurityContextHolder>() {
            @Mock
            public SecurityContext getContext() {
                return securityContext;
            }
        };

        assertThat(SecurityUtils.getCurrentUsername()).isPresent().as(userDetails.getUsername());
    }

    @Test
    public void getCurrentUsername_withStringPrincipal() {
        String expectedUsername = randomAlphanumeric(10);
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(expectedUsername, null);
        final SecurityContextImpl securityContext = new SecurityContextImpl(authentication);
        new MockUp<SecurityContextHolder>() {
            @Mock
            public SecurityContext getContext() {
                return securityContext;
            }
        };

        assertThat(SecurityUtils.getCurrentUsername()).isPresent().as(expectedUsername);
    }

    @Test
    public void getCurrentUsername_withNullAuthentication() {
        assertThat(SecurityUtils.getCurrentUsername()).isNotPresent();
    }
}