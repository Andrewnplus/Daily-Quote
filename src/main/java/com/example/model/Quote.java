package com.example.model;

import com.example.tool.QuoteCategoryConverter;
import com.example.tool.QuoteDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Table
@JsonDeserialize(using = QuoteDeserializer.class)
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 2000)
    private String text;
    private String author;
    private QuoteCategory category;
}