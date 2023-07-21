package com.example.service;

import com.example.controller.dto.UserQuoteDto;
import com.example.model.Quote;
import com.example.model.UserQuote;
import com.example.repository.UserQuoteRepository;
import com.example.security.model.User;
import com.example.security.service.UserService;
import mockit.Verifications;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;


import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;

import static com.example.om.QuoteOM.newQuote;
import static com.example.om.UserOM.newUser;
import static com.example.om.UserQuoteDtoOM.newUserQuoteDto;
import static com.example.om.UserQuoteOM.newUserQuote;
import static org.apache.commons.lang3.RandomUtils.nextLong;
import static org.assertj.core.api.Assertions.assertThat;


public class UserQuoteServiceTest {

    @Tested
    UserQuoteService unit;
    @Injectable
    UserQuoteRepository userQuoteRepository;
    @Injectable
    QuoteService quoteService;
    @Injectable
    UserService userService;

    @Test
    public void create() {
        final long quoteId = nextLong();
        final UserQuoteDto userQuoteDto = newUserQuoteDto();
        userQuoteDto.setQuoteId(quoteId);
        final Quote quote = newQuote();
        final User user = newUser();

        new Expectations() {{
            quoteService.getQuote(userQuoteDto.getQuoteId());
            result = Optional.of(quote);

            userService.getUserWithAuthorities();
            result = Optional.of(user);
        }};

       unit.create(userQuoteDto);

        new Verifications() {{
            UserQuote capturedUserQuote;
            userQuoteRepository.save(capturedUserQuote = withCapture());
            assertThat(capturedUserQuote)
                    .returns(userQuoteDto.getUserComment(), UserQuote::getComment)
                    .returns(quote, UserQuote::getQuote)
                    .returns(user, UserQuote::getUser);
        }};
    }

    @Test
    public void update() {
        final UserQuoteDto userQuoteDto = newUserQuoteDto();
        final UserQuote userQuote = newUserQuote();

        new Expectations() {{
            userQuoteRepository.findById(userQuoteDto.getUserQuoteId());
            result = Optional.of(userQuote);
        }};

        assertThat(unit.update(userQuoteDto).getUserComment()).isEqualTo(userQuoteDto.getUserComment());

        new Verifications() {{
            userQuoteRepository.save(userQuote);
        }};
    }

    @Test
    public void getUserQuoteDtoInRandom_userQuoteDoesNotExist() {
        final Quote quote = newQuote();

        new Expectations() {{
            quoteService.getRandomQuote();
            result = quote;

            userQuoteRepository.findByQuote(quote);
            result = Optional.empty();
        }};

        assertThat(unit.getUserQuoteDtoInRandom())
                .returns(quote.getId(), UserQuoteDto::getQuoteId)
                .returns(quote.getText(), UserQuoteDto::getQuoteText)
                .returns(quote.getAuthor(), UserQuoteDto::getAuthor);
    }

    @Test
    public void getUserQuoteDtoInRandom() {
        final Quote quote = newQuote();
        final UserQuote userQuote = newUserQuote();
        userQuote.setDeleted(false);

        new Expectations() {{
            quoteService.getRandomQuote();
            result = quote;

            userQuoteRepository.findByQuote(quote);
            result = Optional.of(userQuote);
        }};

        assertThat(unit.getUserQuoteDtoInRandom())
                .returns(quote.getId(), UserQuoteDto::getQuoteId)
                .returns(quote.getText(), UserQuoteDto::getQuoteText)
                .returns(quote.getAuthor(), UserQuoteDto::getAuthor)
                .returns(userQuote.getId(), UserQuoteDto::getUserQuoteId)
                .returns(userQuote.getComment(), UserQuoteDto::getUserComment);

    }

    @Test
    public void softDelete_deleteSuccessfully() {
        final UserQuote userQuote = newUserQuote();
        final long userQuoteId = nextLong();

        new Expectations() {{
            userQuoteRepository.findById(userQuoteId);
            result = Optional.of(userQuote);
        }};

        assertThat(unit.softDelete(userQuoteId)).isTrue();
        new Verifications() {{
            UserQuote capturedUserQuote;
            userQuoteRepository.save(capturedUserQuote = withCapture());
            assertThat(capturedUserQuote.isDeleted()).isTrue();

            userQuoteRepository.save(capturedUserQuote);
        }};


    }

    @Test
    public void softDelete_deleteUnsuccessfully() {
        final long userQuoteId = nextLong();
        new Expectations() {{
            userQuoteRepository.findById(userQuoteId);
            result = Optional.empty();
        }};

        assertThat(unit.softDelete(userQuoteId)).isFalse();
    }

    @Test
    public void testGetUserQuotesByUserId() {
        final User user = newUser();
        final List<UserQuote> userQuotes = List.of(newUserQuote());

        new Expectations() {{
            userService.getUserWithAuthorities();
            result = Optional.of(user);

            userQuoteRepository.findByUserAndDeletedFalse(user);
            result = userQuotes;
        }};

        assertThat(unit.getUserQuotesByUserId()).isEqualTo(userQuotes);
    }
}