package com.nqt.mybank.web;

import com.nqt.mybank.model.Transaction;
import com.nqt.mybank.model.User;
import com.nqt.mybank.service.TransactionService;
import com.nqt.mybank.service.UserService;
import com.nqt.mybank.web.forms.AddTransactionForm;
import com.nqt.mybank.web.forms.RegisterForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class WebsiteController {

    private final TransactionService transactionService;

    private final UserService userService;

    private final String bankSlogan;

    public WebsiteController(TransactionService transactionService, UserService userService, @Value("${bank.slogan}") String bankSlogan) {
        this.transactionService = transactionService;
        this.userService = userService;
        this.bankSlogan = bankSlogan;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("slogan", bankSlogan);
        model.addAttribute("registerForm", new RegisterForm());
        return "index.html";
    }

    @PostMapping("/")
    public String register(@ModelAttribute @Valid RegisterForm registerForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "index.html";
        }

        User user = userService.register(registerForm.getName());
        model.addAttribute("registerSuccess", true);
        model.addAttribute("userId", user.getUserId());
        model.addAttribute("slogan", bankSlogan);

        return "index.html";
    }

    @GetMapping("/account/{userId}")
    public String transactions(Model model, @PathVariable String userId) {
        List<Transaction> transactionList = transactionService.findByUserId(userId);

        try {
            buildHomepage(model, userId, transactionList);
        } catch (RuntimeException ex) {
            return "redirect:/";
        }

        return "homepage.html";
    }

    @PostMapping("/account/{userId}")
    public String addTransaction(@ModelAttribute @Valid AddTransactionForm addTransactionForm, BindingResult bindingResult, @PathVariable String userId, Model model) {

        if(bindingResult.hasErrors()) {
            return "homepage.html";
        }

        transactionService.create(addTransactionForm.getReceivingUserId(), addTransactionForm.getAmount(), addTransactionForm.getReference());

        return String.format("redirect:/account/%s", userId);
    }

    private void buildHomepage(Model model, String userId, List<Transaction> transactionList) {

        User foundUser = userService.findById(userId);

        if (foundUser == null) {
            throw new RuntimeException();
        }

        model.addAttribute("userName", foundUser.getName());
        model.addAttribute("userId", userId);
        model.addAttribute("transactions", transactionList);

        model.addAttribute("addTransactionForm", new AddTransactionForm());

    }
}
