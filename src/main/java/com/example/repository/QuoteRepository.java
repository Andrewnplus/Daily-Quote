package com.example.repository;

import com.example.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM quote ORDER BY RAND() LIMIT 1")
    Quote findRandomQuote();
}

