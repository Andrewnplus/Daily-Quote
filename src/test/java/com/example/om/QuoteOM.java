package com.example.om;

import com.example.model.Quote;
import com.example.model.QuoteCategory;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class QuoteOM {
    public static Quote newQuote() {
        return Quote.builder()
                .text(randomAlphanumeric(10))
                .author(randomAlphanumeric(10))
                .category(QuoteCategory.GOD)
                .build();
    }
}