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

    private Boolean isValid;

    private String lastPaymentDate;

    private Boolean isDisplayed;

    private String message;

    private String status;


}
