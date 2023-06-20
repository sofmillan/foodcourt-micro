package com.pragma.powerup;

import com.pragma.powerup.application.dto.request.DishRequestDto;
import com.pragma.powerup.application.handler.IDishHandler;
import com.pragma.powerup.application.handler.impl.DishHandler;
import com.pragma.powerup.application.mapper.IDishRequestMapper;
import com.pragma.powerup.domain.api.IDishServicePort;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.usecase.DishUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

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
        DishRequestDto dishRequestDto = new DishRequestDto();
        dishRequestDto.setRestaurantId(1L);
        dishRequestDto.setCategoryId(2L);
        dishRequestDto.setPrice(10000);
        dishRequestDto.setName("Tiramisu");
        dishRequestDto.setImageUrl("http//:image.png");
        dishRequestDto.setDescription("This is the description");

        DishModel dishModel = new DishModel();
        dishModel.setRestaurantId(1L);
        dishModel.setCategoryId(2L);
        dishModel.setPrice(10000);
        dishModel.setName("Tiramisu");
        dishModel.setImageUrl("http//:image.png");
        dishModel.setDescription("This is the description");

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
}
