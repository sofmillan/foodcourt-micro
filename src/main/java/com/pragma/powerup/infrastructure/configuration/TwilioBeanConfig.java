package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.client.TwilioClientPort;
import com.pragma.powerup.application.mapper.IMessageRequestMapper;
import com.pragma.powerup.infrastructure.out.feign.TwilioClientAdapter;
import com.pragma.powerup.infrastructure.out.feign.TwilioServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TwilioBeanConfig {

    private final IMessageRequestMapper messageRequestMapper;
    private final TwilioServiceClient twilioServiceClient;

    @Bean
    public TwilioClientPort twilioClientPort(){
        return new TwilioClientAdapter(twilioServiceClient, messageRequestMapper);
    }
}
