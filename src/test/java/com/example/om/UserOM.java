package com.example.om;

import com.example.security.model.User;
import java.util.Set;

import static com.example.om.AuthorityOM.newAuthority;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class UserOM {

    public static User newUser(final String username) {
        final User user = newUser();
        user.setUsername(username);
        return user;
    }

    public static User newUser() {
        return User.builder()
                .username(randomAlphanumeric(10))
                .password(randomAlphanumeric(10))
                .authorities(Set.of(newAuthority()))
                .build();
    }
}