package com.pagatech.merchant.notification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitTransactionRequest extends ServerPaymentRequest{

    private Transaction transaction;
}
