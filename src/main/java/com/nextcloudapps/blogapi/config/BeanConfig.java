package com.nextcloudapps.blogapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public RestTemplate signInRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    public RestTemplate signUpRestTemplate(){
        return new RestTemplate();
    }
}
