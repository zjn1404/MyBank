package com.nqt.mybank.service;

import com.nqt.mybank.model.User;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Component
public class UserService {

    private final JdbcTemplate jdbcTemplate;

    public UserService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        System.out.println("Fetching users data...");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Deleting users data...");
    }

    @Transactional
    public User register(String name) {

        System.out.println("Is a database transaction open=? " + TransactionSynchronizationManager.isActualTransactionActive());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO USERS (ID, NAME) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, Integer.toString(getUserAmount() + 1));
            ps.setString(2, name);

            return ps;
        }, keyHolder);

        String userId = Objects.requireNonNull(keyHolder.getKeys()).isEmpty() ? null : (String) keyHolder.getKeys().values().iterator().next();

        User user = new User();
        user.setUserId(userId);
        user.setName(name);

        return user;
    }

    @Transactional
    public User findById(String userId) {

        System.out.println("Is a database transaction open?= " + TransactionSynchronizationManager.isActualTransactionActive());


        List<User> user = jdbcTemplate.query("SELECT * FROM USERS WHERE ID = ?", new Object[]{userId}, (keySet, rowNum) -> {
            User foundUser = new User();
            foundUser.setUserId(keySet.getString("ID"));
            foundUser.setName(keySet.getString("NAME"));
            return foundUser;
        });

        return user.isEmpty() ? null : user.get(0);
    }

    @Transactional
    private int getUserAmount() {
        Integer userAmount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM USERS", Integer.class) ;

        return userAmount == null ? 0 : userAmount;
    }
}
