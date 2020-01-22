package com.pagatech.merchant.notification.model;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubmitTransactionResponse extends Response{

    private String confirmationCode;

    private String merchantStatus;

    private String uniqueTransactionId;

    private String customerReference;

    private String message;

    private String status;
}
