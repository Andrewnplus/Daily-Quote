package com.example.controller;


import com.example.controller.dto.UserQuoteDto;
import com.example.model.UserQuote;
import com.example.service.UserQuoteService;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.jupiter.api.Test;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static com.example.om.UserQuoteDtoOM.newUserQuoteDto;
import static com.example.om.UserQuoteOM.newUserQuote;
import static org.apache.commons.lang3.RandomUtils.nextLong;
import static org.assertj.core.api.Assertions.assertThat;



public class UserQuoteControllerTest {

    @Tested
    private UserQuoteController unit;

    @Injectable
    private UserQuoteService userQuoteService;

    @Test
    public void getRandomUserQuote() {
        final UserQuoteDto userQuoteDto = new UserQuoteDto();
        new Expectations() {{
            userQuoteService.getUserQuoteDtoInRandom();
            result = userQuoteDto;
        }};
        assertThat(unit.getRandomUserQuote())
                .returns(userQuoteDto, ResponseEntity::getBody)
                .returns(HttpStatus.OK, ResponseEntity::getStatusCode);
    }

    @Test
    public void getUserQuotes() {
        final List<UserQuote> userQuotes = List.of(newUserQuote());
        new Expectations() {{
            userQuoteService.getUserQuotesByUserId();
            result = userQuotes;
        }};

        assertThat(unit.getUserQuotes())
                .returns(userQuotes, ResponseEntity::getBody)
                .returns(HttpStatus.OK, ResponseEntity::getStatusCode);
    }

    @Test
    public void createUserQuote() {
        final UserQuoteDto inputDto = newUserQuoteDto();
        final UserQuoteDto outputDto = newUserQuoteDto();
        new Expectations() {{
            userQuoteService.create(inputDto);
            result = outputDto;
        }};

        assertThat(unit.createUserQuote(inputDto))
                .returns(outputDto, ResponseEntity::getBody)
                .returns(HttpStatus.CREATED, ResponseEntity::getStatusCode);
    }

    @Test
    public void updateUserQuote() {
        final UserQuoteDto inputDto = newUserQuoteDto();
        final UserQuoteDto outputDto = newUserQuoteDto();
        new Expectations() {{
            userQuoteService.update(inputDto);
            result = outputDto;
        }};
        assertThat(unit.updateUserQuote(inputDto))
                .returns(outputDto, ResponseEntity::getBody)
                .returns(HttpStatus.OK, ResponseEntity::getStatusCode);
    }

    @Test
    public void deleteUserQuote_deletedSuccessfully() {
        final Long id = nextLong();
        new Expectations() {{
            userQuoteService.softDelete(id);
            result = true;
        }};

        assertThat(unit.deleteUserQuote(id))
                .returns(HttpStatus.NO_CONTENT, ResponseEntity::getStatusCode);
    }

    @Test
    public void deleteUserQuote_deletedUnsuccessfully() {
        final Long id = nextLong();
        new Expectations() {{
            userQuoteService.softDelete(id);
            result = false;
        }};
        assertThat(unit.deleteUserQuote(id))
                .returns(HttpStatus.NOT_FOUND, ResponseEntity::getStatusCode);
    }
}