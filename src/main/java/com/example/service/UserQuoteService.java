package com.example.service;

import com.example.controller.dto.UserQuoteDto;
import com.example.model.Quote;
import com.example.model.UserQuote;
import com.example.repository.UserQuoteRepository;
import com.example.security.model.User;
import com.example.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class UserQuoteService {
    private final UserQuoteRepository userQuoteRepository;
    private final QuoteService quoteService;
    private final UserService userService;

    @Autowired
    public UserQuoteService(UserQuoteRepository userQuoteRepository, QuoteService quoteService, UserService userService) {
        this.userQuoteRepository = userQuoteRepository;
        this.quoteService = quoteService;
        this.userService = userService;
    }

    public UserQuoteDto create(final UserQuoteDto userQuoteDto) {
        quoteService.getQuote(userQuoteDto.getQuoteId()).map(quote -> {
            final User user = userService.getUserWithAuthorities().orElseThrow();
            final UserQuote userQuote = UserQuote.builder()
                    .comment(userQuoteDto.getUserComment())
                    .quote(quote)
                    .updatedDate(new Date())
                    .user(user)
                    .build();
            userQuoteRepository.save(userQuote);
            userQuoteDto.setUserQuoteId(userQuote.getId());
            return userQuoteDto;
        }).orElseThrow(() -> new RuntimeException("Corresponding Quote is not found"));
        return userQuoteDto;
    }

    public UserQuoteDto update(final UserQuoteDto userQuoteDto) {
        userQuoteRepository.findById(userQuoteDto.getUserQuoteId()).map(userQuote -> {
            userQuote.setComment(userQuoteDto.getUserComment());
            userQuote.setUpdatedDate(new Date());
            userQuoteRepository.save(userQuote);
            return userQuoteDto;
        }).orElseThrow(() -> new RuntimeException("UserQuote not found"));
        return userQuoteDto;
    }


    public UserQuoteDto getUserQuoteDtoInRandom() {
        final Quote quote = quoteService.getRandomQuote();
        final Optional<UserQuote> userQuoteOptional = userQuoteRepository.findByQuote(quote);
        if (userQuoteOptional.isPresent() && !userQuoteOptional.get().isDeleted()) {
            final UserQuote userQuote = userQuoteOptional.get();
            return UserQuoteDto.builder()
                    .userQuoteId(userQuote.getId())
                    .quoteText(quote.getText())
                    .author(quote.getAuthor())
                    .quoteId(quote.getId())
                    .userComment(userQuote.getComment())
                    .updatedDate(userQuote.getUpdatedDate())
                    .build();
        }
        return UserQuoteDto.builder()
                .quoteText(quote.getText())
                .author(quote.getAuthor())
                .quoteId(quote.getId())
                .build();
    }

    public boolean softDelete(final Long id) {
        final AtomicBoolean deleted = new AtomicBoolean(false);
        final Optional<UserQuote> userQuoteOptional = userQuoteRepository.findById(id);
        userQuoteOptional.ifPresent(userQuote -> {
            userQuote.setDeleted(true);
            userQuoteRepository.save(userQuote);
            deleted.set(true);
        });
        return deleted.get();
    }

    public List<UserQuote> getUserQuotesByUserId() {
        final User user = userService.getUserWithAuthorities().orElseThrow();
        return userQuoteRepository.findByUserAndDeletedFalse(user);
    }
}

