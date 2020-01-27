package com.pagatech.merchant.notification.service;

import com.pagatech.merchant.notification.exception.ModelAlreadyExistException;
import com.pagatech.merchant.notification.exception.ModelNotFoundException;
import com.pagatech.merchant.notification.exception.PaymentNotificationException;
import com.pagatech.merchant.notification.model.*;

import java.util.List;
import java.util.Optional;

public interface MerchantPaymentNotificationService {
    SupportedMerchantServiceResponse getSupportedIntegationServices(RequestServer serverRequest) throws PaymentNotificationException;

    CustomerValidationResponse validateCustomer(CustomerValidationRequestServer customerValidationRequest) throws PaymentNotificationException, ModelNotFoundException;

    Customer getCustomerDetails(String customerAccountNumber);

    List<Customer> retrieveAllCustomers();

    MerchantServiceResponse getMerchantService(RequestServer serverRequest) throws PaymentNotificationException;

    SubmitTransactionResponse submitTransaction(SubmitTransactionRequestServer submitTransactionRequest) throws ModelAlreadyExistException, ModelNotFoundException;

    List<Transaction> retrieveAllTransactions();

    Optional<Transaction> retrieveTransactionByReferenceNumber(String merchantTransactionId);

    List<ServiceResponse> retrieveMerchantServices();
}
