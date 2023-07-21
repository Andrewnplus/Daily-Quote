package com.example.model;

import com.example.tool.QuoteDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
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

    @Builder
    public Quote(Long id, String text, String author, QuoteCategory category) {
        this.id = id;
        this.text = text;
        this.author = author;
        this.category = category;
    }

    public Quote() {

    }
}