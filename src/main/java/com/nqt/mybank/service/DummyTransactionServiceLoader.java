package com.nqt.mybank.service;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class DummyTransactionServiceLoader {

    private final TransactionService transactionService;

    private final UserService userService;

    public DummyTransactionServiceLoader(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @PostConstruct
    public void create() {
        System.out.println("Creating transactions");
        userService.register("Tuong");
        userService.register("Phat");

        transactionService.create("1", 5, "hmm");
        transactionService.create("1", 5, "hello");
    }
}
