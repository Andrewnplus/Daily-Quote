package com.example.om;

import com.example.model.UserQuote;

import static com.example.om.UserOM.newUser;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class UserQuoteOM {

    public static UserQuote newUserQuote() {
        return UserQuote.builder()
                .user(newUser())
                .comment(randomAlphanumeric(10))
                .quote(QuoteOM.newQuote())
                .build();
    }
}