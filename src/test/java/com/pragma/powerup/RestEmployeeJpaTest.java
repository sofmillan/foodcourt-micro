package com.pragma.powerup;

import com.pragma.powerup.domain.exception.DataNotFoundException;
import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.domain.spi.IRestEmployeePersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.adapter.RestEmployeeJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestEmployeeEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.RestaurantEmployeeRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestEmployeeJpaTest {
    RestaurantEmployeeRepository restaurantEmployeeRepository;
    IRestEmployeeEntityMapper restEmployeeEntityMapper;
    RestaurantRepository restaurantRepository;

    IRestEmployeePersistencePort persistence;
    RestaurantEmployeeModel restaurantEmployeeModel;

    @BeforeEach
    void setUp(){
        restaurantEmployeeRepository = mock(RestaurantEmployeeRepository.class);
        restEmployeeEntityMapper = mock(IRestEmployeeEntityMapper.class);
        restaurantRepository = mock(RestaurantRepository.class);
        persistence = new RestEmployeeJpaAdapter(restaurantEmployeeRepository, restEmployeeEntityMapper, restaurantRepository);
    }

    @Test
    void Should_AssociateEmployee(){
        restaurantEmployeeModel = new RestaurantEmployeeModel();
        restaurantEmployeeModel.setRestaurantId(1L);
        restaurantEmployeeModel.setUserId(1L);

        persistence.associateEmployee(restaurantEmployeeModel);

        verify(restaurantEmployeeRepository).save(restEmployeeEntityMapper.toEntity(restaurantEmployeeModel));
    }

    @Test
    void Should_ThrowDataNotFoundException_When_RestaurantIdNotFound(){
        Long ownerId = 1L;
        Long restaurantId = 1L;

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class,
                () -> persistence.findRightOwner(ownerId, restaurantId));
    }

    @Test
    void Should_ReturnTrue_When_OwnerIdMatches(){
        Long ownerId = 1L;
        Long restaurantId = 1L;
        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setId(restaurantId);
        restaurant.setOwnerId(ownerId);

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        assertTrue(persistence.findRightOwner(ownerId, restaurantId));
    }

    @Test
    void Should_ReturnFalse_When_OwnerIdNotMatch(){
        Long ownerId = 1L;
        Long secondOwnerId = 2L;
        Long restaurantId = 1L;
        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setId(restaurantId);
        restaurant.setOwnerId(secondOwnerId);

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        assertFalse(persistence.findRightOwner(ownerId, restaurantId));
    }
}
