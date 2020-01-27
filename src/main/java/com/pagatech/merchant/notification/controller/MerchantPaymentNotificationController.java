package com.pagatech.merchant.notification.controller;

import com.pagatech.merchant.notification.exception.ModelAlreadyExistException;
import com.pagatech.merchant.notification.exception.ModelNotFoundException;
import com.pagatech.merchant.notification.exception.PaymentNotificationException;
import com.pagatech.merchant.notification.model.*;
import com.pagatech.merchant.notification.service.MerchantPaymentNotificationService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MerchantPaymentNotificationController {
    
    private  MerchantPaymentNotificationService merchantPaymentNotificationService;


    public MerchantPaymentNotificationController(MerchantPaymentNotificationService merchantPaymentNotificationService) {
        this.merchantPaymentNotificationService = merchantPaymentNotificationService;
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get Integration Services ", notes = "This service allows Paga to verify which of the integration " +
            "services the merchant implementation supports in order to configure subsequent interaction with the merchant platform.")
    @PostMapping("/svc/v1/getIntegrationServices")
    public ResponseEntity<SupportedMerchantServiceResponse> getMerchantSupportedServices(@RequestBody RequestServer requestServer) throws PaymentNotificationException {

        SupportedMerchantServiceResponse supportedMerchantServiceResponse = merchantPaymentNotificationService
                .getSupportedIntegationServices(requestServer);

        return ResponseEntity.ok(supportedMerchantServiceResponse);
    }


    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Validate Customer", notes = "This service allows Paga to verify the existence of a customer in" +
            " good standing with the merchant using several customer properties.")
    @PostMapping("/svc/v1/validateCustomer")
    public ResponseEntity<CustomerValidationResponse> validateCustomer(@RequestBody CustomerValidationRequestServer
                                                                                   customerValidationRequest) throws ModelNotFoundException, PaymentNotificationException {

        CustomerValidationResponse  customerValidationResponse = merchantPaymentNotificationService
                .validateCustomer(customerValidationRequest);

        return ResponseEntity.ok(customerValidationResponse);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get Merchant Services", notes = "This service provides the mechanism for Paga to request a list" +
            " of named merchant services and associated service properties provide by the merchant.")
    @PostMapping("/svc/v1/getMerchantServices")
    public ResponseEntity<MerchantServiceResponse> getMerchantService(@RequestBody RequestServer requestServer) throws PaymentNotificationException {
        return ResponseEntity.ok(merchantPaymentNotificationService.getMerchantService(requestServer));
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Submit Transaction or Payment Execution", notes = "This service provides the mechanism for Paga " +
            "to submit a payment notification to the merchant systems and receive confirmation of its approval")
    @PostMapping("/svc/v1/submitTransaction")
    public ResponseEntity<SubmitTransactionResponse> submitTransaction(@RequestBody SubmitTransactionRequestServer submitTransactionRequest) throws ModelAlreadyExistException, ModelNotFoundException {
        return ResponseEntity.ok(merchantPaymentNotificationService.submitTransaction(submitTransactionRequest));
    }

}
