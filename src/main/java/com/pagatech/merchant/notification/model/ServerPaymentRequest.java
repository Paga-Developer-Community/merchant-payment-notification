package com.pagatech.merchant.notification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServerPaymentRequest {

    @NotNull(message = "request must contain either true or false")
    private boolean isTest;

}
