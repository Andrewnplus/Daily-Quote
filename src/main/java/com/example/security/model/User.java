package com.example.security.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table
public class User {
    @JsonIgnore
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 1)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String username;

    @JsonIgnore
    @Column
    @NotNull
    private String password;

    @JsonIgnore
    @Column
    @NotNull
    private boolean activated;

    @ManyToMany
    @JoinTable(
            name = "USER_AUTHORITY",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_NAME", referencedColumnName = "NAME")})
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();

    @Builder
    public User(Long id, String username, String password, boolean activated, Set<Authority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.activated = activated;
        this.authorities = authorities;
    }

    public User() {

    }
}
