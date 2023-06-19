package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.client.TwilioClientPort;
import com.pragma.powerup.domain.client.UserClientPort;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.domain.usecase.OrderUseCase;
import com.pragma.powerup.infrastructure.out.jpa.adapter.OrderJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfigOrder {

    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final OrderDishRepository orderDishRepository;
    private final RestaurantEmployeeRepository chefRepository;
    private final TwilioClientPort twilioClientPort;
    private final UserClientPort userClientPort;

    @Bean
    public IOrderPersistencePort orderPersistencePort(){
        return new OrderJpaAdapter(restaurantRepository, dishRepository, orderRepository, orderEntityMapper, orderDishRepository, chefRepository);
    }

    @Bean
    public IOrderServicePort orderServicePort(){
        return new OrderUseCase(orderPersistencePort(), twilioClientPort, userClientPort);
    }
}

