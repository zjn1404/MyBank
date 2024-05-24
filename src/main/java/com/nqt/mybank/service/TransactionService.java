package com.nqt.mybank.service;

import com.nqt.mybank.model.Transaction;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionService {

    private final String slogan;

    private final UserService userService;

    private final JdbcTemplate jdbcTemplate;

    public TransactionService(@Value("${bank.slogan}") String slogan, UserService userService, JdbcTemplate jdbcTemplate) {
        this.slogan = slogan;
        this.userService = userService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        System.out.println("Fetching data...");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Deleting data...");
    }

    @Transactional
    public final List<Transaction> findAll() {

        System.out.println("Is a database transaction open? = " + TransactionSynchronizationManager.isActualTransactionActive());

        return jdbcTemplate.query("SELECT * FROM TRANSACTIONS", (resultSet, rowNum) -> {
           Transaction transaction = new Transaction();

           transaction.setId(resultSet.getObject("ID").toString());
           transaction.setAmount(resultSet.getInt("AMOUNT"));
           transaction.setTimestamp(resultSet.getTimestamp("TIME_STAMP"));
           transaction.setReference(resultSet.getString("REFERENCE"));
           transaction.setSlogan(resultSet.getString("SLOGAN"));

           return transaction;
        });
    }

    @Transactional
    public Transaction create(String userId, int amount, String reference) {

        System.out.println("Is a database transaction open? = " + TransactionSynchronizationManager.isActualTransactionActive());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO TRANSACTIONS (AMOUNT, TIME_STAMP, REFERENCE, SLOGAN) VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, amount);
            ps.setTimestamp(2, timestamp);
            ps.setString(3, reference);
            ps.setString(4, slogan);

            return ps;
        }, keyHolder);

        UUID transactionId = !keyHolder.getKeys().isEmpty() ? (UUID) keyHolder.getKeys().values().iterator().next() : null;

        Transaction transaction = new Transaction();
        transaction.setId(transactionId.toString());
        transaction.setAmount(amount);
        transaction.setTimestamp(timestamp);
        transaction.setReference(reference);
        transaction.setSlogan(slogan);

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO TRANSACTIONS_USERS (TRANSACTION_ID, USER_ID) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);

            ps.setObject(1, transactionId);
            ps.setString(2, userId);

            return ps;
        }, keyHolder);

        return transaction;
    }

    @Transactional
    public List<Transaction> findByUserId(String userId) {

        System.out.println("Is a database transaction open? = " + TransactionSynchronizationManager.isActualTransactionActive());

        return jdbcTemplate.query("SELECT * FROM TRANSACTIONS t JOIN TRANSACTIONS_USERS tu ON t.ID = tu.TRANSACTION_ID WHERE tu.USER_ID = ?", new Object[]{userId}, (resultSet, rowNUm) -> {
            Transaction transaction = new Transaction();

            transaction.setId(resultSet.getObject("ID").toString());
            transaction.setAmount(resultSet.getInt("AMOUNT"));
            transaction.setTimestamp(resultSet.getTimestamp("TIME_STAMP"));
            transaction.setReference(resultSet.getString("REFERENCE"));
            transaction.setSlogan(resultSet.getString("SLOGAN"));

            return transaction;
        });
    }
}
