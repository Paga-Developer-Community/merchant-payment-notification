package com.pagatech.merchant.notification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponse implements Serializable {

    private String productCode;

    private BigDecimal price;

    private String name;

    private boolean isPublic;

    private String shortCode;

}
