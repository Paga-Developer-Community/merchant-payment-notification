package com.pagatech.merchant.notification.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CustomerValidationRequest {

    @NotNull(message = "customer account number cannot be empty")
    private String customerAccountNumber;

    @NotBlank(message = "request must contain either true or false")
    private boolean isTest;

    private String customerFirstName;

    private String customerLastName;
}
