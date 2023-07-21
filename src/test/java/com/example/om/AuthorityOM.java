package com.example.om;

import com.example.security.model.Authority;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class AuthorityOM {

    public static Authority newAuthority() {
        return new Authority(randomAlphanumeric(10));
    }
}