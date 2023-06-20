package com.pragma.powerup;

import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.adapter.RestaurantJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

   /* @Test
    void Should_ReturnList_When_FindRestaurants(){
        Integer numberOfElements = 1;

        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setAddress("804 Cone St");
        restaurantEntity.setLogoUrl("https://logo.png");
        restaurantEntity.setName("Subway");
        restaurantEntity.setNit("5895685");
        restaurantEntity.setPhoneNumber("+012555");
        restaurantEntity.setOwnerId(1L);
        restaurantEntity.setId(1L);
        restaurant = new RestaurantModel();
        restaurant.setAddress("804 Cone St");
        restaurant.setLogoUrl("https://logo.png");
        restaurant.setName("Subway");
        restaurant.setNit("5895685");
        restaurant.setPhoneNumber("+012555");
        restaurant.setOwnerId(1L);
        restaurant.setId(1L);

        Pageable pageable = PageRequest.of(0,numberOfElements);

        List<RestaurantEntity> restaurantEntityList = new ArrayList<>();
        restaurantEntityList.add(restaurantEntity);
        restaurantRepository.save(restaurantEntity);
        System.out.println(restaurantRepository.findAll(pageable));

        when(restaurantRepository.findAll(pageable).getContent()).thenReturn(List.of(restaurantEntity));

        when(restaurantEntityMapper.toModel(restaurantEntity)).thenReturn(restaurant);

        assertThat(restaurantPersistence.findRestaurants(numberOfElements).size()).isEqualTo(numberOfElements);
    }*/

}
