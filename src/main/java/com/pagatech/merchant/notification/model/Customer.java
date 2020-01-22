package com.pagatech.merchant.notification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer  implements Serializable {

    private String accountStatus;

    private String firstName;

    private String lastName;

    private String paymentDueDate;

    private boolean isValid;

    private String lastPaymentDate;

    private boolean isDisplayed;

    private String accountNumber;
}
