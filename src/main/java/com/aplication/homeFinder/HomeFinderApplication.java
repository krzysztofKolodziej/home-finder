package com.aplication.homeFinder;

import com.aplication.homeFinder.offer.model.Currency;
import com.aplication.homeFinder.offer.service.ExchangeClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HomeFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeFinderApplication.class, args);

	}

}
