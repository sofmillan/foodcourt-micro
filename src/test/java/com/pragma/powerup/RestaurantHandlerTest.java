package com.pragma.powerup;


import com.pragma.powerup.application.dto.request.RestaurantRequestDto;
import com.pragma.powerup.application.handler.IRestaurantHandler;
import com.pragma.powerup.application.handler.impl.RestaurantHandler;
import com.pragma.powerup.application.mapper.IRestaurantRequestMapper;
import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.usecase.RestaurantUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class RestaurantHandlerTest {
    IRestaurantServicePort servicePort;
    IRestaurantRequestMapper restaurantRequestMapper;

    IRestaurantHandler restaurantHandler;

    @BeforeEach
    void setUp(){
        servicePort = mock(RestaurantUseCase.class);
        restaurantRequestMapper = mock(IRestaurantRequestMapper.class);
        restaurantHandler = new RestaurantHandler(servicePort, restaurantRequestMapper);
    }

    @Test
    void Should_SaveRestaurant(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJhYmNkMTIzIiwiZXhwaXJ5IjoxNjQ2NjM1NjExMzAxfQ.3Thp81rDFrKXr3WrY1MyMnNK8kKoZBX9lg-JwFznR-M";

        RestaurantRequestDto requestDto = new RestaurantRequestDto();
        requestDto.setOwnerId(1L);
        requestDto.setNit("5255859695");
        requestDto.setName("Subway");
        requestDto.setPhoneNumber("+5789556");
        requestDto.setAddress("St 26");
        requestDto.setLogoUrl("https://logo.png");


        RestaurantModel restaurantModel = new RestaurantModel();

        restaurantModel.setOwnerId(1L);
        restaurantModel.setNit("5255859695");
        restaurantModel.setName("Subway");
        restaurantModel.setPhoneNumber("+5789556");
        restaurantModel.setAddress("St 26");
        restaurantModel.setLogoUrl("https://logo.png");

        when(restaurantRequestMapper.toRestaurant(requestDto)).thenReturn(restaurantModel);

        restaurantHandler.saveRestaurant(requestDto, token);

        verify(servicePort).saveRestaurant(restaurantModel, token);
    }
}
