package com.example.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserQuoteDto {
    private Long quoteId;
    private String quoteText;
    private String author;

    private Long userQuoteId;
    private String userComment;
    private Date updatedDate;

    @Builder
    public UserQuoteDto(Long quoteId, String quoteText, String author, Long userQuoteId, String userComment, Date updatedDate) {
        this.quoteId = quoteId;
        this.quoteText = quoteText;
        this.author = author;
        this.userQuoteId = userQuoteId;
        this.userComment = userComment;
        this.updatedDate = updatedDate;
    }

    public UserQuoteDto() {
    }
}
