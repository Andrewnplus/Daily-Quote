package com.example.service;

import com.example.model.Quote;
import com.example.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuoteService {
    private final QuoteRepository quoteRepository;

    @Autowired
    public QuoteService(final QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @Cacheable("quotes")
    public Optional<Quote> getQuote(final long id) {
        return quoteRepository.findById(id);
    }

    public Quote getRandomQuote() {
        return quoteRepository.findRandomQuote();
    }
}
