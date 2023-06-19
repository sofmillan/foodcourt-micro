package com.pragma.powerup.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    System.Logger.Level feignLoggerLevel(){
        return System.Logger.Level.INFO;
    }
}