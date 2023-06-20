package com.pragma.powerup;

import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.adapter.RestaurantJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantJpaTest {
    RestaurantRepository restaurantRepository;
    IRestaurantEntityMapper restaurantEntityMapper;

    IRestaurantPersistencePort restaurantPersistence;
    RestaurantModel restaurant;

    @BeforeEach
    void setUp(){
        restaurantRepository = mock(RestaurantRepository.class);
        restaurantEntityMapper = mock(IRestaurantEntityMapper.class);
        restaurantPersistence = new RestaurantJpaAdapter(restaurantRepository, restaurantEntityMapper);
    }

    @Test
    void Should_SaveRestaurant(){
        restaurant = new RestaurantModel();
        restaurant.setAddress("804 Cone St");
        restaurant.setLogoUrl("https://logo.png");
        restaurant.setName("Subway");
        restaurant.setNit("5895685");
        restaurant.setPhoneNumber("+012555");
        restaurant.setOwnerId(1L);

        restaurantPersistence.saveRestaurant(restaurant);

        verify(restaurantRepository).save(restaurantEntityMapper.toRestaurantEntity(restaurant));
    }

    @Test
    void Should_ReturnFalse_When_RestaurantNitNotExists(){
        String nit = "2502001251";

        when(restaurantRepository.findByNit(nit)).thenReturn(Optional.empty());

        assertFalse(restaurantPersistence.findRestaurantByNit(nit));
    }

    @Test
    void Should_ReturnTrue_When_RestaurantNitExists(){
        String nit = "2502001251";

        when(restaurantRepository.findByNit(nit)).thenReturn(Optional.of(new RestaurantEntity()));

        assertTrue(restaurantPersistence.findRestaurantByNit(nit));
    }

}
