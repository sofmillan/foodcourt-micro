package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.client.UserClientPort;
import com.pragma.powerup.infrastructure.out.feign.UserClientAdapter;
import com.pragma.powerup.infrastructure.out.feign.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserFeignBeanConfig {
    private final UserServiceClient userServiceClient;

    @Bean
    public UserClientPort userClientPort(){
        return new UserClientAdapter(userServiceClient);
    }
}
