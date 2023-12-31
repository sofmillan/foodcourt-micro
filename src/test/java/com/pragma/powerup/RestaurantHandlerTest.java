package com.pragma.powerup;


import com.pragma.powerup.application.dto.request.RestaurantRequestDto;
import com.pragma.powerup.application.dto.response.RestaurantPageResponseDto;
import com.pragma.powerup.application.handler.IRestaurantHandler;
import com.pragma.powerup.application.handler.impl.RestaurantHandler;
import com.pragma.powerup.application.mapper.IRestaurantRequestMapper;
import com.pragma.powerup.domain.api.IRestaurantServicePort;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.usecase.RestaurantUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class RestaurantHandlerTest {
    IRestaurantServicePort servicePort;
    IRestaurantRequestMapper restaurantRequestMapper;

    IRestaurantHandler restaurantHandler;
    String token;

    @BeforeEach
    void setUp(){
        servicePort = mock(RestaurantUseCase.class);
        restaurantRequestMapper = mock(IRestaurantRequestMapper.class);
        restaurantHandler = new RestaurantHandler(servicePort, restaurantRequestMapper);
        token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJhYmNkMTIzIiwiZXhwaXJ5IjoxNjQ2NjM1NjExMzAxfQ.3Thp81rDFrKXr3WrY1MyMnNK8kKoZBX9lg-JwFznR-M";

    }

    @Test
    void Should_SaveRestaurant(){
        RestaurantModel restaurantModel = new RestaurantModel();

        restaurantModel.setOwnerId(1L);
        restaurantModel.setNit("5255859695");
        restaurantModel.setName("Subway");
        restaurantModel.setPhoneNumber("+5789556");
        restaurantModel.setAddress("St 26");
        restaurantModel.setLogoUrl("https://logo.png");
        restaurantModel.setId(1L);

        RestaurantRequestDto requestDto = new RestaurantRequestDto();
        requestDto.setOwnerId(restaurantModel.getOwnerId());
        requestDto.setNit(restaurantModel.getNit());
        requestDto.setName(restaurantModel.getName());
        requestDto.setPhoneNumber(restaurantModel.getPhoneNumber());
        requestDto.setAddress(restaurantModel.getAddress());
        requestDto.setLogoUrl(restaurantModel.getLogoUrl());


        when(restaurantRequestMapper.toRestaurant(requestDto)).thenReturn(restaurantModel);

        restaurantHandler.saveRestaurant(requestDto, token);

        verify(servicePort).saveRestaurant(restaurantModel, token);
    }

    @Test
    void Should_FindOneRestaurant(){
        int numberOfElements = 1;
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setOwnerId(1L);
        restaurantModel.setNit("5255859695");
        restaurantModel.setName("Subway");
        restaurantModel.setPhoneNumber("+5789556");
        restaurantModel.setAddress("St 26");
        restaurantModel.setLogoUrl("https://logo.png");
        restaurantModel.setId(1L);

        RestaurantPageResponseDto restaurantPageResponseDto = new RestaurantPageResponseDto();
        restaurantPageResponseDto.setName(restaurantModel.getName());
        restaurantPageResponseDto.setLogoUrl(restaurantModel.getLogoUrl());


        when(servicePort.findRestaurants(numberOfElements, token)).thenReturn(List.of(restaurantModel));
        when(restaurantRequestMapper.toResponseDto(restaurantModel)).thenReturn(restaurantPageResponseDto);


        assertThat(restaurantHandler.findRestaurants(numberOfElements, token).size()).isEqualTo(1);
    }
}
