package com.project.Ecommerce;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;


@EnableAsync
@RestController
@EnableScheduling
@SpringBootApplication
public class ECommerceApplication {


	@GetMapping("/")
	public String index(){
		return "index";
	}

	@GetMapping("/admin/home")
	public String adminHome(){
		return "Admin home";
	}

	@GetMapping("/customer/home")
	public String customerHome(){
		return "Customer home";
	}
	@GetMapping("/seller/home")
	public String sellerHome()
	{
		return "Seller Home";
	}



	@Bean
	public ObjectMapper objectMapper()
	{
		return new ObjectMapper();
	}


	public static void main(String[] args) {

		SpringApplication.run(ECommerceApplication.class, args);

		}



}
