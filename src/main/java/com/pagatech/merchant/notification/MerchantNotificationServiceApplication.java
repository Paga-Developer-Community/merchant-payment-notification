package com.pagatech.merchant.notification;

import com.fasterxml.jackson.core.Version;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MerchantNotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MerchantNotificationServiceApplication.class, args);
	}


}
