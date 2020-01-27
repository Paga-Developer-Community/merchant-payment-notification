package com.pagatech.merchant.notification;

import com.pagatech.merchant.notification.exception.ModelAlreadyExistException;
import com.pagatech.merchant.notification.exception.ModelNotFoundException;
import com.pagatech.merchant.notification.exception.PaymentNotificationException;
import com.pagatech.merchant.notification.model.*;
import com.pagatech.merchant.notification.service.MerchantPaymentNotificationService;
import com.pagatech.merchant.notification.service.MerchantPaymentNotificationServiceImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class MerchantPaymentNotificationTest {

    private MerchantPaymentNotificationService merchantPaymentNotificationService;
    private RequestServer serverRequest;


    @BeforeEach
    void setUp() {
        merchantPaymentNotificationService = new MerchantPaymentNotificationServiceImpl();
        serverRequest = RequestServer.builder()
                .isTest(true)
                .build();


    }

    @Test
    @DisplayName("check if get supported integration service is not empty")
    void getSupportedIntegrationServicesTest() throws PaymentNotificationException {

        SupportedMerchantServiceResponse supportedMerchantServiceResponse =
                merchantPaymentNotificationService.getSupportedIntegationServices(serverRequest);

        assertThat(supportedMerchantServiceResponse.getServices()).isNotEmpty();

    }

    @Test
    @DisplayName("check if server request is not null")
    void checkIfRequestIsNull() {

        Exception exception = assertThrows(PaymentNotificationException.class, () ->
            merchantPaymentNotificationService.getSupportedIntegationServices(null)
        );

        String expectedMessage = "request cannot be null";
        String actualMessage = exception.getMessage();

        assertThat(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("validate customer a valid customer")
    void customerValidationTest() throws PaymentNotificationException, ModelNotFoundException {
        CustomerValidationRequestServer customerValidationRequest = new CustomerValidationRequestServer();

        String validAccountNumber = "1100110011";
        customerValidationRequest.setCustomerAccountNumber(validAccountNumber);

        CustomerValidationResponse customerValidationResponse =
                merchantPaymentNotificationService.validateCustomer(customerValidationRequest);

        assertThat(customerValidationResponse).isNotNull();

        assertThat(customerValidationResponse.getStatus()).isEqualToIgnoringCase("SUCCESS");
    }


    @Test
    @DisplayName("validate customer invalid customer")
    void validateInvalidCustomerTest() {
        CustomerValidationRequestServer customerValidationRequest = new CustomerValidationRequestServer();

        String invalidAccountNumber = "28392901";
        customerValidationRequest.setCustomerAccountNumber(invalidAccountNumber);

        Assertions.assertThrows(ModelNotFoundException.class, () ->
                    merchantPaymentNotificationService.validateCustomer(customerValidationRequest)
        );
    }


    @Test
    @DisplayName("check that get customers is not empty")
    void testCreateCustomer() {
        List<Customer> customers;

        customers = merchantPaymentNotificationService.retrieveAllCustomers();

        assertThat(!customers.isEmpty());
    }

    @Test
    @DisplayName("check that customer is not null")
    void testGetCustomer() {
        Customer customer;

        customer = merchantPaymentNotificationService.getCustomerDetails("1100110011");

        String expectedResult = "1100110011";

        String actualResult = customer.getAccountNumber();

        assertThat(actualResult).isEqualToIgnoringCase(expectedResult);

        assertThat(customer).isNotNull();
    }

    @Test
    @DisplayName("get services that belong to a merchant")
    void testGetMerchantServices() throws PaymentNotificationException {
        MerchantServiceResponse merchantServiceResponse = merchantPaymentNotificationService.getMerchantService(serverRequest);

        assertThat(merchantServiceResponse).isNotNull();

        assertThat(merchantServiceResponse.getServices()).isNotEmpty();
    }

    @Test
    @DisplayName("get services that belong to a merchant")
    void testIfMerchantServiceRequestIsNull()  {

        Assertions.assertThrows(PaymentNotificationException.class, () ->
            merchantPaymentNotificationService.getMerchantService(null)
        );
    }

    @Test
    @DisplayName("create or perform a valid submit transaction request")
    void testCreateSubmitTransaction() throws ModelAlreadyExistException, ModelNotFoundException {
        SubmitTransactionRequestServer submitTransactionRequest = new SubmitTransactionRequestServer();

        submitTransactionRequest.setTransaction(Transaction.builder()
                        .services(merchantPaymentNotificationService.retrieveMerchantServices())
                        .channel("ONLINE")
                        .currency("NGN")
                        .description("Customer Bill Payment")
                        .transactionType("BILL_PAY")
                        .customerReference("1300110011")
                        .utcTransactionDateTime("2019-06-13T15:27:32")
                        .merchantAmount(new BigDecimal(980))
                        .pagaTransactionId("wxyxq")
                        .totalAmount(new BigDecimal(1000))
                        .isCredit(true)
                        .customerFirstName("James")
                        .customerLastName("Femi")
                        .customerPhoneNumber("08090090090")
                        .merchantTransactionId("BP-C")
                        .build());

        SubmitTransactionResponse submitTransactionResponse = merchantPaymentNotificationService
                .submitTransaction(submitTransactionRequest);

        assertThat(submitTransactionResponse).isNotNull();

    }


    @Test
    @DisplayName("create or perform a valid submit transaction request")
    void createSubmitTransactionShouldReturnModelAlreadyExistException() {
        SubmitTransactionRequestServer submitTransactionRequest = new SubmitTransactionRequestServer();
        submitTransactionRequest.setTransaction(Transaction.builder()
                .merchantTransactionId("BP-C_2018061315273249_1448479_XBFJX")
                .customerReference("1100110011")
                .build());

        Assertions.assertThrows(ModelAlreadyExistException.class, () ->
                merchantPaymentNotificationService.submitTransaction(submitTransactionRequest)
        );

    }

    @Test
    @DisplayName("create a list of transaction, transaction list should not be empty")
    void testRetrieveAllTransactions(){
        List<Transaction> transactions;
        transactions = merchantPaymentNotificationService.retrieveAllTransactions();
        assertThat(transactions)
                .as("transaction list should be greater than 0 but is %s", transactions.size())
                .isNotEmpty();
    }

    @Test
    @DisplayName("get an existing transaction using merchant transaction id")
    void testRetrieveTransactionByReferenceNumber(){
        String merchantTransactionId = "BP-C_2018061315273249_1448479_XBFJX";
        Optional<Transaction> transaction= merchantPaymentNotificationService.retrieveTransactionByReferenceNumber(merchantTransactionId);
        assertThat(transaction).isNotEmpty();
    }


}
