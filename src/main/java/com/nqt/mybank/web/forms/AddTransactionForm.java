package com.nqt.mybank.web.forms;

import jakarta.validation.constraints.*;

public class AddTransactionForm {

    @NotBlank
    @Pattern(regexp = "\\d+")
    private String receivingUserId;

    @Min(1)
    @Max(100)
    private int amount;

    @NotBlank
    @Size(min = 1, max = 50)
    private String reference;

    public String getReceivingUserId() {
        return receivingUserId;
    }

    public void setReceivingUserId(String receivingUserId) {
        this.receivingUserId = receivingUserId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
