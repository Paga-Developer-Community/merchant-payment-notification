package com.pagatech.merchant.notification.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitTransactionRequest{

    @NotBlank(message = "request must contain either true or false")
    private boolean isTest;

    private Transaction transaction;
}
