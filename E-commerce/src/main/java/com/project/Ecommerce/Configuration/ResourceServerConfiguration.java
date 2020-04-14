package com.project.Ecommerce.Configuration;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.Ecommerce.Entities.AppUserDetailsService;
import com.project.Ecommerce.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;


@Configuration
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    AppUserDetailsService userDetailsService;

    public ResourceServerConfiguration() {
        super();
    }

    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }


    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").anonymous()
                .antMatchers("/sellerRegistration").anonymous()
                .antMatchers("/customerRegistration").anonymous()
                .antMatchers("/forgotPassword/{email}").anonymous()
                .antMatchers("/setPassword/{token}/{password}").anonymous()
                .antMatchers("/activateUser/{token}").anonymous()

                //Admin accessible URI's
                .antMatchers("/admin/home").hasAnyRole("ADMIN")
                .antMatchers("/deactivateProduct/{productId}").hasAnyRole("ADMIN")
                .antMatchers("/viewSingleProductForAdmin/{productId}").hasAnyRole("ADMIN")
                .antMatchers("/activateProduct/{productId}").hasAnyRole("ADMIN")
                .antMatchers("/updateCategory/{categoryId}").hasAnyRole("ADMIN")
                .antMatchers("/filtering/{categoryId}").hasAnyRole("ADMIN")
                .antMatchers("/addCategoryMetadataField").hasAnyRole("ADMIN")
                .antMatchers("/updateMetadataValues/{categoryId}/{metadataId}").hasAnyRole("ADMIN")
                .antMatchers("/viewAllMetadataValues").hasAnyRole("ADMIN")
                .antMatchers("/viewAMetadataValue/{categoryId}/{metadataId}").hasAnyRole("ADMIN")
                .antMatchers("/addMetadataValues/{categoryId}/{metadataId}").hasAnyRole("ADMIN")
                .antMatchers("/viewCategoryMetadataField").hasAnyRole("ADMIN")
                .antMatchers("/deleteCategoryMetadataField/{id}").hasAnyRole("ADMIN")
                .antMatchers("/activate/{userId}").hasAnyRole("ADMIN")
                .antMatchers("/deactivate/{userId}").hasAnyRole("ADMIN")
                .antMatchers("/allCustomers").hasAnyRole("ADMIN")
                .antMatchers("/allSellers").hasAnyRole("ADMIN")
                .antMatchers("/addNewCategory").hasAnyRole("ADMIN")
                .antMatchers("/addNewCategory/{parentCategoryId}").hasAnyRole("ADMIN")
                .antMatchers("/lockAccount/{userId}").hasAnyRole("ADMIN")
                .antMatchers("/unLockAccount/{userId}").hasAnyRole("ADMIN")
                .antMatchers("/getAllProducts").hasAnyRole("ADMIN")

                //Customer accessible URI's
                .antMatchers("/OrderOneProductFromCart/{cartId}/{paymentMethod}/{AddressId}").hasAnyRole("CUSTOMER")
                .antMatchers("/OrderWholeCart/{AddressId}").hasAnyRole("CUSTOMER")
                .antMatchers("/viewProfile").hasAnyRole("CUSTOMER")
                .antMatchers("/updateProfile").hasAnyRole("CUSTOMER")
                .antMatchers("/viewProfileImage").hasAnyRole("CUSTOMER")
                .antMatchers("/uploadProfilePic").hasAnyRole("CUSTOMER")
                .antMatchers("/editEmail/{token}").hasAnyRole("CUSTOMER")
                .antMatchers("/customer/home").hasAnyRole("CUSTOMER")
                .antMatchers("/emptyCart").hasAnyRole("CUSTOMER")
                .antMatchers("/getSellerAccount").hasAnyRole("CUSTOMER")
                .antMatchers("/showOrderHistory").hasAnyRole("CUSTOMER")
                .antMatchers("/cancelOrder/{orderStatusId}").hasAnyRole("CUSTOMER")
                .antMatchers("/returnRequested/{orderStatusId}").hasAnyRole("CUSTOMER")
                .antMatchers("/addToCart/{productVariationId}/{quantity}").hasAnyRole("CUSTOMER")
                .antMatchers("/viewCart").hasAnyRole("CUSTOMER")
                .antMatchers("/viewSimilarProduct/{productId}").hasAnyRole("CUSTOMER")
                .antMatchers("/viewProduct/{productId}").hasAnyRole("CUSTOMER")
                .antMatchers("/viewAllProductsForCustomer/{categoryId}").hasAnyRole("CUSTOMER")
                .antMatchers("/deleteFromCart/{productVariationId}").hasAnyRole("CUSTOMER")
                .antMatchers("/placeOrder/{productVariationId}/{quantity}/{paymentMethod}/{AddressId}").hasAnyRole("CUSTOMER")

                //customer-admin
                .antMatchers("/detailsOfCustomer").hasAnyRole("ADMIN", "CUSTOMER")
                .antMatchers("/editContact").hasAnyRole("ADMIN", "CUSTOMER")
                .antMatchers("/getAddresses").hasAnyRole("ADMIN", "CUSTOMER")
                .antMatchers("/getCategory").hasAnyRole("ADMIN", "CUSTOMER")
                .antMatchers("/getCategory/{categoryName}").hasAnyRole("ADMIN", "CUSTOMER")
                .antMatchers("/addReview/{product_id}").hasAnyRole("ADMIN", "CUSTOMER")
                .antMatchers("/getAllProductForCustomerAndAdmin/{categoryName}").hasAnyRole("ADMIN", "CUSTOMER")


                //Seller accessible URI's
                .antMatchers("/seller/home").hasAnyRole("SELLER")
                .antMatchers("/getAllProductVariations/{productId}").hasAnyRole("SELLER")
                .antMatchers("/uploadVariationPic/{id}").hasAnyRole("SELLER")
                .antMatchers("/viewSingleProduct/{productId}").hasAnyRole("SELLER")
                .antMatchers("/viewSingleProductVariation/{productVariationId}").hasAnyRole("SELLER")
                .antMatchers("/getCustomerAccount").hasAnyRole("SELLER")
                .antMatchers("/getProducts").hasAnyRole("SELLER")
                .antMatchers("/addProduct/{category}").hasAnyRole("SELLER")
                .antMatchers("/editProduct/{productId}").hasAnyRole("SELLER")
                .antMatchers("/deleteProduct/{productId}").hasAnyRole("SELLER")

                .antMatchers("/addProductVariations/{productId}").hasAnyRole("SELLER")
                .antMatchers("/editProductVariations/{productVariationId}").hasAnyRole("SELLER")
                .antMatchers("/deleteProductVariation/{productVariationId}").hasAnyRole("SELLER")
                .antMatchers("/setStatus/{productVariationId}/{orderStatusId}").hasAnyRole("SELLER")
                .antMatchers("/refundOrder/{orderStatusId}").hasAnyRole("SELLER")


                //Seller-Admin
                .antMatchers("/detailsOfSeller").hasAnyRole("ADMIN", "SELLER")
                .antMatchers("/editSellerDetails").hasAnyRole("ADMIN", "SELLER")
                .antMatchers("getSubcategories").hasAnyRole("ADMIN", "SELLER")
                .antMatchers("/upload").hasAnyRole("ADMIN", "SELLER")
                .antMatchers("/uploadSm").hasAnyRole("ADMIN", "SELLER")


                //URI's accessible by all type of users
                .antMatchers("/addNewAddress").hasAnyRole("ADMIN", "CUSTOMER", "SELLER")
                .antMatchers("/deleteAddress/{AddressId}").hasAnyRole("ADMIN", "CUSTOMER", "SELLER")
                .antMatchers("/doLogout").hasAnyRole("ADMIN", "CUSTOMER", "SELLER")
                .antMatchers("/deleteUser").hasAnyRole("ADMIN", "CUSTOMER", "SELLER")
                .antMatchers("/editUsername").hasAnyRole("ADMIN", "CUSTOMER", "SELLER")
                .antMatchers("/editEmail").hasAnyRole("ADMIN", "CUSTOMER", "SELLER")
                .antMatchers("/editPassword").hasAnyRole("ADMIN", "CUSTOMER", "SELLER")
                .antMatchers("/updateAddress/{addressId}").hasAnyRole("ADMIN", "CUSTOMER", "SELLER")

                .antMatchers("/getReviews/{product_id}").hasAnyRole("ADMIN", "CUSTOMER", "SELLER")
                .antMatchers("/download/{fileName:.+}").hasAnyRole("ADMIN", "CUSTOMER", "SELLER")


                .antMatchers("/v2/api-docs").anonymous()
                .antMatchers("/swagger-ui.html").permitAll()

                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable();
    }
}