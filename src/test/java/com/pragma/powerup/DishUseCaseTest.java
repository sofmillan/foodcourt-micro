package com.pragma.powerup;

import com.pragma.powerup.domain.api.IDishServicePort;
import com.pragma.powerup.domain.client.UserClientPort;
import com.pragma.powerup.domain.exception.ForbiddenException;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import com.pragma.powerup.domain.usecase.DishUseCase;
import com.pragma.powerup.infrastructure.out.jpa.adapter.DishJpaAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
class DishUseCaseTest {

    private  IDishPersistencePort dishPersistence;
    private  UserClientPort userClientPort;

    private IDishServicePort dishService;
    String token;


    @BeforeEach
    void setUp(){
        userClientPort = mock(UserClientPort.class);
        dishPersistence = mock(DishJpaAdapter.class);
        dishService = new DishUseCase(dishPersistence, userClientPort);
        token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJOjEOTAzMuYW1lIjoiUja3kifQ.rZp7kjty_We0xCM";
    }

    @Test
    void Should_ThrowForbiddenException_When_OwnerIdIsNull(){
        DishModel dishModel = new DishModel();
        dishModel.setRestaurantId(1L);
        when(userClientPort.findOwnerId(token)).thenReturn(null);
        when(dishPersistence.findDishRestaurant(dishModel.getRestaurantId())).thenReturn(1L);
        assertThrows(ForbiddenException.class,
                ()-> dishService.saveDish(dishModel, token));

    }

    @Test
    void Should_ThrowForbiddenException_When_OwnerIsNotRelatedToSave(){
        DishModel dishModel = new DishModel();
        dishModel.setRestaurantId(1L);
        when(userClientPort.findOwnerId(token)).thenReturn(2L);
        when(dishPersistence.findDishRestaurant(dishModel.getRestaurantId())).thenReturn(1L);
        assertThrows(ForbiddenException.class,
                ()-> dishService.saveDish(dishModel, token));
    }

    @Test
    void Should_ThrowForbiddenException_When_OwnerIsNotRelatedToUpdate(){
        Long dishId = 10L;
        Map<String, Object> fields = new HashMap<>();

        fields.put("price",20000);

        when(userClientPort.findOwnerId(token)).thenReturn(2L);
        when(dishPersistence.findOwnerByDish(dishId)).thenReturn(1L);
        assertThrows(ForbiddenException.class,
                ()-> dishService.updateDish(dishId, fields , token));
    }

    @Test
    void Should_ThrowForbiddenException_When_OwnerIsNotRelatedToDeactivate(){
        Long dishId = 10L;
        Map<String, Object> fields = new HashMap<>();

        fields.put("active",false);

        when(userClientPort.findOwnerId(token)).thenReturn(2L);
        when(dishPersistence.findOwnerByDish(dishId)).thenReturn(1L);
        assertThrows(ForbiddenException.class,
                ()-> dishService.updateDish(dishId, fields , token));
    }


    @Test
    void Should_ThrowForbiddenException_When_UserIsNotClient(){
        Long restaurantId = 10L;
        Long categoryId = 2L;
        int numberOfElements = 2;

        when(userClientPort.validateClientByToken(token)).thenReturn(false);

        assertThrows(ForbiddenException.class,
                ()-> dishService.showMenu(restaurantId, categoryId , numberOfElements, token));
    }
}
