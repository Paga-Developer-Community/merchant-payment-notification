package com.pagatech.merchant.notification.service;

import com.pagatech.merchant.notification.exception.ModelAlreadyExistException;
import com.pagatech.merchant.notification.exception.ModelNotFoundException;
import com.pagatech.merchant.notification.exception.PaymentNotificationException;
import com.pagatech.merchant.notification.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class MerchantPaymentNotificationServiceImpl implements MerchantPaymentNotificationService {


    @Override
    public SupportedMerchantServiceResponse getSupportedIntegationServices(ServerPaymentRequest serverRequest) throws PaymentNotificationException {

        if(serverRequest == null){
            throw new PaymentNotificationException("request cannot be null");
        }

        log.info("about to get merchant supported services {} ",serverRequest);

        List<String> services = new ArrayList<>();
        services.add("SUBMIT_PAYMENT");
        services.add("VALIDATE_CUSTOMER");
        services.add("QUERY_PAYMENTS");
        services.add("GET_MERCHANT_SERVICES");

        return SupportedMerchantServiceResponse.builder()
                .services(services)
                .build();

    }

    @Override
    public CustomerValidationResponse validateCustomer(CustomerValidationRequest customerValidationRequest) throws ModelNotFoundException {

        log.info("about to validate customer {} ", customerValidationRequest);

       Customer customer = getCustomerDetails(customerValidationRequest.getCustomerAccountNumber());

       if(customer == null){
          throw new ModelNotFoundException("customer does not exist");
       }

        return CustomerValidationResponse.builder()
                .status("SUCCESS")
                .accountNumber(customer.getAccountNumber())
                .accountStatus(customer.getAccountStatus())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .lastPaymentDate(customer.getLastPaymentDate())
                .message("successfully retrieved customer")
                .paymentDueDate(customer.getPaymentDueDate())
                .isDisplayed(true)
                .isValid(true)
                .build();
    }

    @Override
     public Customer getCustomerDetails(String customerAccountNumber) {

        Customer foundCustomer = null;

        for(Customer customer: retrieveAllCustomers()){
            if(customer.getAccountNumber().equalsIgnoreCase(customerAccountNumber)){
                foundCustomer = customer;
            }
        }

        return foundCustomer;
    }

    @Override
    public List<Customer> retrieveAllCustomers(){
        List<Customer> customers = new ArrayList<>();

        customers.add(Customer.builder()
                .accountStatus("active")
                .firstName("James")
                .isDisplayed(true)
                .lastName("Owolabi")
                .accountNumber("1100110011")
                .lastPaymentDate("27/10/2010")
                .paymentDueDate("21/11/2010")
                .isValid(true)
                .build());

        customers.add(Customer.builder()
                .accountStatus("active")
                .firstName("Gbenga")
                .isDisplayed(true)
                .lastName("Adeyanju")
                .accountNumber("1200110011")
                .lastPaymentDate("27/10/2010")
                .paymentDueDate("21/11/2010")
                .isValid(true)
                .build());

        customers.add(Customer.builder()
                .accountStatus("Inactive")
                .firstName("Isreal")
                .isDisplayed(true)
                .lastName("Tosin")
                .accountNumber("1300110011")
                .lastPaymentDate("27/12/2010")
                .paymentDueDate("21/11/2011")
                .isValid(true)
                .build());

        return customers;
    }

    @Override
    public MerchantServiceResponse getMerchantService(ServerPaymentRequest serverRequest) throws PaymentNotificationException {
        MerchantServiceResponse merchantServiceResponse = new MerchantServiceResponse();

        if(serverRequest == null){
            throw new PaymentNotificationException("request cannot be null");
        }
       List<ServiceResponse> serviceResponses = retrieveMerchantServices();

        merchantServiceResponse.setStatus("SUCCESS");

        merchantServiceResponse.setServices(serviceResponses);

        return merchantServiceResponse;

    }

    @Override
    public SubmitTransactionResponse submitTransaction(SubmitTransactionRequest submitTransactionRequest) throws ModelAlreadyExistException, ModelNotFoundException {
        log.info("submit transaction request {} ", submitTransactionRequest);

        //check if customer exist
        Customer foundCustomer = getCustomerDetails(submitTransactionRequest.getTransaction().getCustomerReference());

        if(foundCustomer == null){
            throw new ModelNotFoundException("user does not exist");
        }
        //check if transaction already exist using the merchant transaction identifier
        Optional<Transaction> foundTransaction =
                retrieveTransactionByReferenceNumber(submitTransactionRequest.getTransaction()
                        .getMerchantTransactionId());

        if(foundTransaction.isPresent()){
            throw new ModelAlreadyExistException("transaction already exist");
        }

        //TODO: any other logic can be performed here

        return SubmitTransactionResponse.builder()
                .confirmationCode("xyz82892")
                .customerReference(foundCustomer.getAccountNumber())
                .merchantStatus("000")
                .uniqueTransactionId("72889291978")
                .status("SUCCESS")
                .message("transaction completed sucessfully")
                .build();
    }

    @Override
    public List<Transaction> retrieveAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        log.info("transaction to be retrieved ");

       transactions.add(Transaction.builder()
               .services(retrieveMerchantServices())
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
               .merchantTransactionId("BP-C_2018061315273249_1448479_XBFJX")
               .build());

       return transactions;
    }

    @Override
    public Optional<Transaction> retrieveTransactionByReferenceNumber(String merchantTransactionId) {
        
        Transaction foundTransaction;
        
        for(Transaction transaction : retrieveAllTransactions()){
            if(merchantTransactionId.equalsIgnoreCase(transaction.getMerchantTransactionId())){
                foundTransaction = transaction;
                log.info("found transaction {} ", foundTransaction.getMerchantTransactionId());
                return Optional.of(foundTransaction);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<ServiceResponse> retrieveMerchantServices() {
        List<ServiceResponse> services = new ArrayList<>();

        services.add(ServiceResponse.builder()
                .isPublic(true)
                .name("Race-Cars")
                .price(new BigDecimal(800))
                .shortCode("Race101")
                .productCode("111001")
                .build());

        services.add(ServiceResponse.builder()
                .isPublic(true)
                .name("Adventure")
                .price(new BigDecimal(1500))
                .shortCode("Adventure101")
                .productCode("111002")
                .build());

        return services;

    }
}
