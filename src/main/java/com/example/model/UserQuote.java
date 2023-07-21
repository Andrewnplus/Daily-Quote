package com.example.model;

import com.example.security.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@Table
public class UserQuote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 使用者名稱
    private Date updatedDate;
    private String comment;
    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "quote_id", nullable = false)
    private Quote quote;

    @Builder
    public UserQuote(Long id, User user, Date updatedDate, String comment, boolean deleted, Quote quote) {
        this.id = id;
        this.user = user;
        this.updatedDate = updatedDate;
        this.comment = comment;
        this.deleted = deleted;
        this.quote = quote;

    }

    public UserQuote() {

    }
}

