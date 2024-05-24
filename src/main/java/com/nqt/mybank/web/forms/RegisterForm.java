package com.nqt.mybank.web.forms;


import jakarta.validation.constraints.NotBlank;

public class RegisterForm {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
