package com.pagatech.merchant.notification.controller;

import com.google.gson.Gson;
import com.pagatech.merchant.notification.exception.ModelAlreadyExistException;
import com.pagatech.merchant.notification.exception.ModelNotFoundException;
import com.pagatech.merchant.notification.exception.PaymentNotificationException;
import com.pagatech.merchant.notification.model.*;
import com.pagatech.merchant.notification.service.MerchantPaymentNotificationService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class MerchantPaymentNotificationController {
    
    private  MerchantPaymentNotificationService merchantPaymentNotificationService;
    private Gson gson;


    public MerchantPaymentNotificationController(MerchantPaymentNotificationService merchantPaymentNotificationService, Gson gson) {
        this.merchantPaymentNotificationService = merchantPaymentNotificationService;
        this.gson = gson;
    }

    @ApiOperation(value = "Get Integration Services ", notes = "This service allows Paga to verify which of the integration " +
            "services the merchant implementation supports in order to configure subsequent interaction with the merchant platform.")
    @PostMapping("/svc/v1/getIntegrationServices")
    public @ResponseBody SupportedMerchantServiceResponse getMerchantSupportedServices(@RequestBody RequestServer requestServer) throws PaymentNotificationException {

        SupportedMerchantServiceResponse supportedMerchantServiceResponse = merchantPaymentNotificationService
                .getSupportedIntegationServices(requestServer);

        log.info("supported merchant service response {} ", supportedMerchantServiceResponse);

        return supportedMerchantServiceResponse;
    }

    @ApiOperation(value = "Validate Customer", notes = "This service allows Paga to verify the existence of a customer in" +
            " good standing with the merchant using several customer properties.")
    @PostMapping("/svc/v1/validateCustomer")
    public @ResponseBody CustomerValidationResponse validateCustomer(@RequestBody CustomerValidationRequest
                                                                                   customerValidationRequest) throws ModelNotFoundException, PaymentNotificationException {

        CustomerValidationResponse  customerValidationResponse = merchantPaymentNotificationService
                .validateCustomer(customerValidationRequest);

        System.out.println(customerValidationResponse);

        return customerValidationResponse;
    }

    @ApiOperation(value = "Get Merchant Services", notes = "This service provides the mechanism for Paga to request a list" +
            " of named merchant services and associated service properties provide by the merchant.")
    @PostMapping("/svc/v1/getMerchantServices")
    public @ResponseBody MerchantServiceResponse getMerchantService(@RequestBody RequestServer requestServer) throws PaymentNotificationException {
        MerchantServiceResponse merchantServiceResponse = merchantPaymentNotificationService.getMerchantService(requestServer);
        log.info("merchant service response {} ", merchantServiceResponse);
        return merchantServiceResponse;
    }


    @ApiOperation(value = "Submit Transaction or Payment Execution", notes = "This service provides the mechanism for Paga " +
            "to submit a payment notification to the merchant systems and receive confirmation of its approval")
    @PostMapping("/svc/v1/submitTransaction")
    public @ResponseBody SubmitTransactionResponse submitTransaction(@RequestBody SubmitTransactionRequest submitTransactionRequest) throws ModelAlreadyExistException, ModelNotFoundException {
        SubmitTransactionResponse submitTransactionResponse =  merchantPaymentNotificationService.submitTransaction(submitTransactionRequest);

        log.info(" submit transaction response{} ",submitTransactionResponse);
        return submitTransactionResponse;
    }

}
