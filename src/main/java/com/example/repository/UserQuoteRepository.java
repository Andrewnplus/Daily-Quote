package com.example.repository;

import com.example.model.Quote;
import com.example.model.UserQuote;
import com.example.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserQuoteRepository extends JpaRepository<UserQuote, Long> {
    List<UserQuote> findByUserAndDeletedFalse(User user);
    Optional<UserQuote> findByQuote(Quote quote);
}
