package com.ezgroceries.shoppinglist.config;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@Configuration
@Configuration
@ComponentScan(basePackages = "com.ezgroceries.shoppinglist.model.*")
public class GeneralConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return  new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS).disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

}
