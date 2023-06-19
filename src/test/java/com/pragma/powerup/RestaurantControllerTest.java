package com.pragma.powerup;

import com.pragma.powerup.application.dto.request.RestaurantRequestDto;
import com.pragma.powerup.application.handler.IRestaurantHandler;
import com.pragma.powerup.application.handler.impl.RestaurantHandler;
import com.pragma.powerup.infrastructure.input.rest.RestaurantController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class RestaurantControllerTest {

    IRestaurantHandler restaurantHandler;
    RestaurantController restaurantController;
    String token;
    RestaurantRequestDto restaurantRequestDto;


    @BeforeEach
    void setUp(){
        restaurantHandler = mock(RestaurantHandler.class);
        restaurantController = new RestaurantController(restaurantHandler);
        token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJhYmNkMTIzIiwiZXhwaXJ5IjoxNjQ2NjM1NjExMzAxfQ.3Thp81rDFrKXr3WrY1MyMnNK8kKoZBX9lg-JwFznR-M";
    }

    @Test
    void Should_ReturnResponseEntityCreated_When_RestaurantCreated(){
        restaurantRequestDto = new RestaurantRequestDto();
        restaurantRequestDto.setAddress("804 Cone St");
        restaurantRequestDto.setLogoUrl("https://logo.png");
        restaurantRequestDto.setName("Subway");
        restaurantRequestDto.setNit("5895685");
        restaurantRequestDto.setPhoneNumber("+012555");
        restaurantRequestDto.setOwnerId(1L);

        doNothing().when(restaurantHandler).saveRestaurant(restaurantRequestDto, token);

        ResponseEntity<Void> responseEntity = restaurantController.saveRestaurant(restaurantRequestDto, token);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(restaurantHandler).saveRestaurant(restaurantRequestDto, token);

    }

}
