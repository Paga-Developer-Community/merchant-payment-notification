package com.pagatech.merchant.notification.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitTransactionRequestServer extends RequestServer {

    private Transaction transaction;
}
