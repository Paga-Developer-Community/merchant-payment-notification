package com.pagatech.merchant.notification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction implements Serializable {

    private BigDecimal merchantAmount;

    private String utcTransactionDateTime;

    private String channel;

    private String description;

    private List<ServiceResponse> services;

    private String transactionType;

    private String pagaTransactionId;

    private BigDecimal totalAmount;

    private boolean isCredit;

    private String customerReference;

    private String customerFirstName;

    private String merchantTransactionId;

    private String currency;

    private String customerLastName;

    private String customerPhoneNumber;
}
