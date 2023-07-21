package com.example.om;

import com.example.controller.dto.UserQuoteDto;

import java.util.Date;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class UserQuoteDtoOM {

    public static UserQuoteDto newUserQuoteDto() {
        return UserQuoteDto.builder()
                .quoteText(randomAlphanumeric(10))
                .author(randomAlphanumeric(10))
                .userComment(randomAlphanumeric(10))
                .updatedDate(new Date())
                .build();
    }
}