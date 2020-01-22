package com.pagatech.merchant.notification.model;

import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerValidationResponse  extends Response  {

    private String accountStatus;

    private String firstName;

    private String lastName;

    private String paymentDueDate;

    private boolean isValid;

    private String lastPaymentDate;

    private boolean isDisplayed;

    private String message;

    private String status;

    private String accountNumber;

}
