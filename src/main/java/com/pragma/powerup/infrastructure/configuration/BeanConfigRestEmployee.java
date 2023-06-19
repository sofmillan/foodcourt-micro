package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.IRestEmployeeServicePort;
import com.pragma.powerup.domain.client.UserClientPort;
import com.pragma.powerup.domain.spi.IRestEmployeePersistencePort;
import com.pragma.powerup.domain.usecase.RestaurantEmployeeUseCase;
import com.pragma.powerup.infrastructure.out.jpa.adapter.RestEmployeeJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestEmployeeEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.RestaurantEmployeeRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfigRestEmployee {
    private final IRestEmployeeEntityMapper restEmployeeEntityMapper;
    private final RestaurantEmployeeRepository restaurantEmployeeRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserClientPort userClientPort;

    @Bean
    public IRestEmployeePersistencePort restEmployeePersistencePort(){
        return new RestEmployeeJpaAdapter(restaurantEmployeeRepository, restEmployeeEntityMapper, restaurantRepository);

    }

    @Bean
    public IRestEmployeeServicePort restEmployeeServicePort(){
        return new RestaurantEmployeeUseCase(restEmployeePersistencePort(), userClientPort);
    }
}
