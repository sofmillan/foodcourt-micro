package com.pragma.powerup;

import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.client.UserClientPort;
import com.pragma.powerup.domain.exception.DataAlreadyExistsException;
import com.pragma.powerup.domain.exception.DataNotValidException;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.domain.usecase.RestaurantUseCase;
import com.pragma.powerup.domain.exception.ForbiddenException;
import com.pragma.powerup.infrastructure.out.jpa.adapter.RestaurantJpaAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RestaurantUseCaseTest {
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
        token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJhYmNkMTIzIiwiZXhwaXJ5IjoxNjQ2NjM1NjExMzAxfQ.3Thp81rDFrKXr3WrY1MyMnNK8kKoZBX9lg-JwFznR-M";
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
        when(restaurantPersistence.findRestaurantByNit(restaurantModel.getNit())).thenReturn(true);
        when(userClientPort.validateOwnerById(restaurantModel.getOwnerId())).thenReturn(true);


        assertThrows(DataAlreadyExistsException.class,
                ()-> restaurantService.saveRestaurant(restaurantModel, token));
    }

    @Test
    void Should_SaveRestaurant(){

        when(userClientPort.validateAdministrator(token)).thenReturn(true);
        when(restaurantPersistence.findRestaurantByNit(restaurantModel.getNit())).thenReturn(false);
        when(userClientPort.validateOwnerById(restaurantModel.getOwnerId())).thenReturn(true);

        restaurantService.saveRestaurant(restaurantModel, token);


        verify(restaurantPersistence).saveRestaurant(restaurantModel);
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

    @Test
    void Should_FindRestaurants(){
        int numberOfElements = 4;
        when(userClientPort.validateClientByToken(token)).thenReturn(true);

        restaurantService.findRestaurants(numberOfElements, token);

        verify(restaurantPersistence).findRestaurants(numberOfElements);
    }
}

