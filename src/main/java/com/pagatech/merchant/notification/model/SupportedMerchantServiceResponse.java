package com.pagatech.merchant.notification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupportedMerchantServiceResponse  {

    private List<String> services = new ArrayList<>();

    @Override
    public String toString() {
        return "SupportedMerchantServiceResponse{" +
                "services=" + services +
                '}';
    }
}
