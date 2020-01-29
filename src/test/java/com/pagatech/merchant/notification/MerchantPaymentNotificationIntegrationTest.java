package com.pagatech.merchant.notification;

import com.pagatech.merchant.notification.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import javax.xml.bind.DatatypeConverter;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MerchantPaymentNotificationIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("As a user I want to be able to get supported services")
    void testSendSingleSms() {
        //act | when
        SupportedMerchantServiceResponse response =
                restTemplate.postForObject("/svc/v1/getIntegrationServices", setupHttpEntityServerPaymentRequest(),
                        SupportedMerchantServiceResponse.class);
        //assert |then

        assertThat(!response.getServices().isEmpty())
                .as("Response size should not be empty  but is %s", response.getServices().size())
                .isTrue();
    }


    @Test
    @DisplayName("want to validate customer request")
    void testValidateCustomer(){
        CustomerValidationResponse response = restTemplate.postForObject("/svc/v1/validateCustomer",
                setupHttpEntityCustomerValidation(), CustomerValidationResponse.class);

        assertThat(response.getStatus())
                .as("Response code should be 200 OK but is %s", response.getStatus())
                .isEqualTo("SUCCESS");
    }

    @Test
    @DisplayName("want to get the merchant services")
    void testGetMerchantServices(){
        MerchantServiceResponse  response = restTemplate.postForObject("/svc/v1/getMerchantServices",
                setupHttpEntityServerPaymentRequest(), MerchantServiceResponse.class);

        assertThat(response.getStatus())
                .as("Response Status should be SUCCESS  but is %s", response.getStatus())
                .isEqualTo("SUCCESS");
    }

  /*  @Test
    @DisplayName("want to test submit transaction")
    void testSubmitTransaction(){
        ResponseEntity<SubmitTransactionResponse>response = restTemplate.postForEntity("/svc/v1/submitTransaction",
                setupHttpEntityServerPaymentRequest(), SubmitTransactionResponse.class);
        assertThat(response.getStatusCode())
                .as("Response code should be 200 OK but is %s", response.getStatusCode())
                .isEqualTo(HttpStatus.OK);
    }*/



    private HttpEntity<RequestServer> setupHttpEntityServerPaymentRequest() {
        HttpHeaders requestHeaders = getHttpHeaders();
        RequestServer requestServer = RequestServer.builder()
                .isTest(true)
                .build();
         return new HttpEntity<>(requestServer, requestHeaders);

    }

    private HttpHeaders getHttpHeaders() {
        String authorizationHeader = "Basic " + DatatypeConverter.printBase64Binary(("james" + ":" + "password").getBytes());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        requestHeaders.add("Authorization", authorizationHeader);
        return requestHeaders;
    }

    private HttpEntity<CustomerValidationRequest> setupHttpEntityCustomerValidation() { HttpHeaders requestHeaders = getHttpHeaders();

        //arrange | given
       return new HttpEntity<>(CustomerValidationRequest.builder()
               .customerAccountNumber("1100110011")
               .isTest(true)
               .customerFirstName("Babs")
               .customerLastName("James")
               .build(), requestHeaders);

    }
}
