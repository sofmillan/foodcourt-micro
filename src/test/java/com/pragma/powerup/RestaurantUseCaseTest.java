package com.pragma.powerup;

import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.client.UserClientPort;
import com.pragma.powerup.domain.exception.DataNotValidException;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.domain.usecase.RestaurantUseCase;
import com.pragma.powerup.domain.exception.ForbiddenException;
import com.pragma.powerup.infrastructure.out.jpa.adapter.RestaurantJpaAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RestaurantUseCaseTest {
    UserClientPort userClientPort;
    IRestaurantPersistencePort restaurantPersistence;
    IRestaurantServicePort restaurantService;

    RestaurantModel restaurantModel;
    String token;

    @BeforeEach
    void setUp(){
        userClientPort = mock(UserClientPort.class);
        restaurantPersistence = mock(RestaurantJpaAdapter.class);
        restaurantService = new RestaurantUseCase(restaurantPersistence, userClientPort);
        restaurantModel = new RestaurantModel();
        restaurantModel.setNit("8001582");
        restaurantModel.setOwnerId(1L);
        restaurantModel.setPhoneNumber("+5845855");
        restaurantModel.setLogoUrl("https://image.png");
        restaurantModel.setAddress("1234 NW Bobcat Lane");
        token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJOjEOTAzMuYW1lIjoiUja3kifQ.rZp7kjty_We0xCM";
    }
    @Test
    void Should_ThrowForbiddenException_When_UserIsNotAdministrator(){

        when(userClientPort.validateAdministrator(token)).thenReturn(false);
        when(restaurantPersistence.findRestaurantByNit(restaurantModel.getNit())).thenReturn(true);
        assertThrows(ForbiddenException.class,
                ()-> restaurantService.saveRestaurant(restaurantModel, token));

    }

    @Test
    void Should_ThrowDataNotValidException_When_OwnerIdNotRelated(){
        when(userClientPort.validateAdministrator(token)).thenReturn(true);
        when(userClientPort.validateOwnerById(restaurantModel.getOwnerId())).thenReturn(false);
        when(restaurantPersistence.findRestaurantByNit(restaurantModel.getNit())).thenReturn(false);

        assertThrows(DataNotValidException.class,
                ()-> restaurantService.saveRestaurant(restaurantModel, token));
    }

    @Test
    void Should_ThrowDataAlreadyExistsException_When_NitAlreadyExists(){

        when(userClientPort.validateAdministrator(token)).thenReturn(true);
        when(userClientPort.validateOwnerById(restaurantModel.getOwnerId())).thenReturn(false);
        when(restaurantPersistence.findRestaurantByNit(token)).thenReturn(false);

        assertThrows(DataNotValidException.class,
                ()-> restaurantService.saveRestaurant(restaurantModel, token));
    }

    @Test
    void Should_ThrowForbiddenException_When_UserIsNotClient(){

        int numberOfElements = 4;

        when(userClientPort.validateClientByToken(token)).thenReturn(false);

        assertThrows(ForbiddenException.class,
                ()-> restaurantService.findRestaurants(numberOfElements, token));
    }

    @Test
    void Should_ThrowForbiddenException_When_NumberOfElementsIsNegative(){

        int numberOfElements = -4;

        when(userClientPort.validateClientByToken(token)).thenReturn(true);

        assertThrows(DataNotValidException.class,
                ()-> restaurantService.findRestaurants(numberOfElements, token));
    }
}

