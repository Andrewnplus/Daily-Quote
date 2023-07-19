package com.example.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Builder
@Setter
public class UserQuoteDto {
   private Long quoteId;
   private String quoteText;
   private String author;

   private Long userQuoteId;
   private String userComment;
   private Date updatedDate;
}
