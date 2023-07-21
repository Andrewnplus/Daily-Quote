package com.example.controller;

import com.example.controller.dto.UserQuoteDto;
import com.example.model.UserQuote;
import com.example.service.QuoteService;
import com.example.service.UserQuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserQuoteController {

    private final UserQuoteService userQuoteService;

    @Autowired
    public UserQuoteController(UserQuoteService userQuoteService) {
        this.userQuoteService = userQuoteService;
    }

    @GetMapping(value = "/userQuote", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserQuoteDto> getRandomUserQuote() {
        return ResponseEntity.ok(userQuoteService.getUserQuoteDtoInRandom());
    }

    @PostMapping(value = "/userQuote", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserQuoteDto> createUserQuote(@RequestBody UserQuoteDto userQuoteDto) {
        UserQuoteDto createdUserQuoteDTO = userQuoteService.create(userQuoteDto);
        return new ResponseEntity<>(createdUserQuoteDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/userQuote", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserQuoteDto> updateUserQuote(@RequestBody UserQuoteDto userQuoteDto) {
        return ResponseEntity.ok(userQuoteService.update(userQuoteDto));
    }

    @PatchMapping(value = "/userQuote/{userQuoteId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> deleteUserQuote(@PathVariable Long userQuoteId) {
        boolean deleted = userQuoteService.softDelete(userQuoteId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/userQuotes", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<UserQuote>> getUserQuotes() {
        return ResponseEntity.ok(userQuoteService.getUserQuotesByUserId());
    }
}
