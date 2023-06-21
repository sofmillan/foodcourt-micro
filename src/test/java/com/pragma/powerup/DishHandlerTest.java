package com.pragma.powerup;

import com.pragma.powerup.application.dto.request.DishRequestDto;
import com.pragma.powerup.application.dto.response.DishPageResponseDto;
import com.pragma.powerup.application.handler.IDishHandler;
import com.pragma.powerup.application.handler.impl.DishHandler;
import com.pragma.powerup.application.mapper.IDishRequestMapper;
import com.pragma.powerup.domain.api.IDishServicePort;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.usecase.DishUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class DishHandlerTest {
    IDishServicePort dishService;
    IDishRequestMapper dishRequestMapper;
    IDishHandler dishHandler;
    String token;

    @BeforeEach
    void setUp(){
        dishService = mock(DishUseCase.class);
        dishRequestMapper = mock(IDishRequestMapper.class);
        dishHandler = new DishHandler(dishService, dishRequestMapper);
        token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJhYmNkMTIzIiwiZXhwaXJ5IjoxNjQ2NjM1NjExMzAxfQ.3Thp81rDFrKXr3WrY1MyMnNK8kKoZBX9lg-JwFznR-M";
    }

    @Test
    void Should_SaveDish(){
        DishModel dishModel = new DishModel();
        dishModel.setRestaurantId(1L);
        dishModel.setCategoryId(2L);
        dishModel.setPrice(10000);
        dishModel.setName("Tiramisu");
        dishModel.setImageUrl("http//:image.png");
        dishModel.setDescription("This is the description");

        DishRequestDto dishRequestDto = new DishRequestDto();
        dishRequestDto.setRestaurantId(dishModel.getRestaurantId());
        dishRequestDto.setCategoryId(dishModel.getCategoryId());
        dishRequestDto.setPrice(dishModel.getPrice());
        dishRequestDto.setName(dishModel.getName());
        dishRequestDto.setImageUrl(dishModel.getImageUrl());
        dishRequestDto.setDescription(dishModel.getDescription());

        when(dishRequestMapper.toDish(dishRequestDto)).thenReturn(dishModel);

        dishHandler.saveDish(dishRequestDto, token);

        verify(dishService).saveDish(dishModel, token);
    }

    @Test
    void Should_UpdateDish(){
        Long dishId = 1L;
        Map<String, Object> fields = new HashMap<>();
        fields.put("price",20000);

        dishHandler.updateDish(dishId, fields, token);

        verify(dishService).updateDish(dishId, fields, token);
    }

    @Test
    void Should_UpdateActive(){
        Long dishId = 1L;
        Map<String, Object> fields = new HashMap<>();
        fields.put("active",true);

        dishHandler.updateActiveField(dishId, fields, token);

        verify(dishService).updateActiveField(dishId, fields, token);
    }

    @Test
    void Should_ShowMenu(){
        Long restaurantId = 1L;
        Long categoryId = 1L;
        int numberOfElements = 1;

        DishPageResponseDto dishPageResponseDto = new DishPageResponseDto();
        dishPageResponseDto.setDescription("This is the description");
        dishPageResponseDto.setName("Tiramisu");
        dishPageResponseDto.setPrice(10);
        dishPageResponseDto.setImageUrl("http//:image.png");
        dishPageResponseDto.setActive(true);

        DishModel dishModel = new DishModel();
        dishModel.setId(1L);
        dishModel.setRestaurantId(1L);
        dishModel.setCategoryId(2L);
        dishModel.setActive(dishPageResponseDto.isActive());
        dishModel.setPrice(dishPageResponseDto.getPrice());
        dishModel.setName(dishPageResponseDto.getName());
        dishModel.setImageUrl(dishPageResponseDto.getImageUrl());
        dishModel.setDescription(dishPageResponseDto.getDescription());


        when(dishService.showMenu(restaurantId, categoryId, numberOfElements, token)).thenReturn(List.of(dishModel));
        when(dishRequestMapper.toPageDto(dishModel)).thenReturn(dishPageResponseDto);


        assertThat(dishHandler.showMenu(restaurantId, categoryId, numberOfElements, token).size()).isEqualTo(1);
    }
}
