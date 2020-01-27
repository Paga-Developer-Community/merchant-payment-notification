package com.pagatech.merchant.notification.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerValidationRequestServer extends RequestServer {

    @NotNull(message = "customer account number cannot be empty")
    private String customerAccountNumber;

    private String customerFirstName;

    private String customerLastName;
}
