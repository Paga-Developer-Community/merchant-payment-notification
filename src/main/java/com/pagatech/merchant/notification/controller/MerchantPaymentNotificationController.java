package com.pagatech.merchant.notification.controller;

import com.pagatech.merchant.notification.exception.ModelAlreadyExistException;
import com.pagatech.merchant.notification.exception.ModelNotFoundException;
import com.pagatech.merchant.notification.exception.PaymentNotificationException;
import com.pagatech.merchant.notification.model.*;
import com.pagatech.merchant.notification.service.MerchantPaymentNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MerchantPaymentNotificationController {
    
    private  MerchantPaymentNotificationService merchantPaymentNotificationService;

    public MerchantPaymentNotificationController(MerchantPaymentNotificationService merchantPaymentNotificationService) {
        this.merchantPaymentNotificationService = merchantPaymentNotificationService;
    }

    @PostMapping("/svc/v1/getIntegrationServices")
    public ResponseEntity<SupportedMerchantServiceResponse> getMerchantSupportedServices(@RequestBody ServerPaymentRequest serverPaymentRequest) throws PaymentNotificationException {

        SupportedMerchantServiceResponse supportedMerchantServiceResponse = merchantPaymentNotificationService
                .getSupportedIntegationServices(serverPaymentRequest);

        return ResponseEntity.ok(supportedMerchantServiceResponse);
    }


    @PostMapping("/svc/v1/validateCustomer")
    public ResponseEntity<CustomerValidationResponse> validateCustomer(@RequestBody CustomerValidationRequest
                                                                                   customerValidationRequest) throws ModelNotFoundException, PaymentNotificationException {

        CustomerValidationResponse  customerValidationResponse = merchantPaymentNotificationService
                .validateCustomer(customerValidationRequest);

        return ResponseEntity.ok(customerValidationResponse);
    }

    @PostMapping("/svc/v1/getMerchantServices")
    public ResponseEntity<MerchantServiceResponse> getMerchantService(@RequestBody ServerPaymentRequest serverPaymentRequest) throws PaymentNotificationException {
        return ResponseEntity.ok(merchantPaymentNotificationService.getMerchantService(serverPaymentRequest));
    }

    @PostMapping("/svc/v1/submitTransaction")
    public ResponseEntity<SubmitTransactionResponse> submitTransaction(@RequestBody SubmitTransactionRequest submitTransactionRequest) throws ModelAlreadyExistException, ModelNotFoundException {
        return ResponseEntity.ok(merchantPaymentNotificationService.submitTransaction(submitTransactionRequest));
    }

}
