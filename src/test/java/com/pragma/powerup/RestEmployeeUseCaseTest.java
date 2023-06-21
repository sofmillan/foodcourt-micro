package com.pragma.powerup;

import com.pragma.powerup.domain.api.IRestEmployeeServicePort;
import com.pragma.powerup.domain.client.UserClientPort;
import com.pragma.powerup.domain.exception.DataNotValidException;
import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.domain.spi.IRestEmployeePersistencePort;
import com.pragma.powerup.domain.usecase.RestaurantEmployeeUseCase;
import com.pragma.powerup.infrastructure.out.feign.UserClientAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.RestEmployeeJpaAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RestEmployeeUseCaseTest {

    IRestEmployeePersistencePort restEmployeePersistence;
    UserClientPort userClientPort;
    String token;
    IRestEmployeeServicePort restEmployeeService;
    RestaurantEmployeeModel restaurantEmployeeModel;

    @BeforeEach
    void setUp(){
        restEmployeePersistence = mock(RestEmployeeJpaAdapter.class);
        userClientPort = mock(UserClientAdapter.class);
        restEmployeeService = new RestaurantEmployeeUseCase(restEmployeePersistence, userClientPort);
        token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJhYmNkMTIzIiwiZXhwaXJ5IjoxNjQ2NjM1NjExMzAxfQ.3Thp81rDFrKXr3WrY1MyMnNK8kKoZBX9lg-JwFznR-M";
    }

    @Test
    void Should_AssociateEmployee(){
        restaurantEmployeeModel = new RestaurantEmployeeModel();
        restaurantEmployeeModel.setUserId(1L);
        restaurantEmployeeModel.setRestaurantId(2L);

        Long ownerId = 3L;

        when(userClientPort.findOwnerId(token)).thenReturn(ownerId);
        when(restEmployeePersistence.findRightOwner(ownerId, restaurantEmployeeModel.getRestaurantId())).thenReturn(true);

        restEmployeeService.associateEmployee(restaurantEmployeeModel, token);

        verify(restEmployeePersistence).associateEmployee(restaurantEmployeeModel);
    }

    @Test
    void Should_ThrowDataNotValidException_When_OwnerDoesNotMatch(){
        restaurantEmployeeModel = new RestaurantEmployeeModel();
        restaurantEmployeeModel.setUserId(1L);
        restaurantEmployeeModel.setRestaurantId(2L);

        Long ownerId = restaurantEmployeeModel.getUserId();

        when(userClientPort.findOwnerId(token)).thenReturn(ownerId);
        when(restEmployeePersistence.findRightOwner(ownerId, restaurantEmployeeModel.getRestaurantId())).thenReturn(false);

        assertThrows(DataNotValidException.class,
                () -> restEmployeeService.associateEmployee(restaurantEmployeeModel, token));
    }
}
