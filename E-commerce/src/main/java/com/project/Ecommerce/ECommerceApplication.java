package com.project.Ecommerce;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.CacheControl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;
import java.util.concurrent.TimeUnit;


@EnableAsync
@RestController
@EnableScheduling
@SpringBootApplication
public class ECommerceApplication implements WebMvcConfigurer {



	@GetMapping("/")
	public String index(){
		return "index";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/admin/home")
	public String adminHome(){
		return "Admin home";
	}

	@Secured("ROLE_CUSTOMER")
	@GetMapping("/customer/home")
	public String customerHome(){
		return "Customer home";
	}
	@Secured("ROLE_SELLER")
	@GetMapping("/seller/home")
	public String sellerHome()
	{
		return "Seller Home";
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry){
		registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/users/")
				.setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
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
