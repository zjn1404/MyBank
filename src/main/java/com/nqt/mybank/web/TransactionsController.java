package com.nqt.mybank.web;

import com.nqt.mybank.dto.TransactionDTO;
import com.nqt.mybank.model.Transaction;
import com.nqt.mybank.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class TransactionsController {

    private final TransactionService transactionService;

    public TransactionsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transactions")
    List<Transaction> transactions() {
        return this.transactionService.findAll();
    }

    @PostMapping("/{userId}/transactions")
    Transaction createTransaction(@PathVariable String userId, @RequestBody @Valid TransactionDTO transactionDTO) {
        return transactionService.create(userId, transactionDTO.getAmount(), transactionDTO.getReference());
    }
}
