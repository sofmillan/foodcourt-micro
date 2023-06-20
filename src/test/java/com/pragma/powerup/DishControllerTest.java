package com.pragma.powerup;

import com.pragma.powerup.application.dto.request.DishRequestDto;
import com.pragma.powerup.application.dto.response.DishPageResponseDto;
import com.pragma.powerup.application.handler.IDishHandler;
import com.pragma.powerup.application.handler.impl.DishHandler;
import com.pragma.powerup.infrastructure.input.rest.DishController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class DishControllerTest {
    DishController dishController;
    IDishHandler dishHandler;
    String token;
    DishRequestDto dishRequestDto;

    @BeforeEach
    void setUp(){
        dishHandler = mock(DishHandler.class);
        dishController = new DishController(dishHandler);
        token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJhYmNkMTIzIiwiZXhwaXJ5IjoxNjQ2NjM1NjExMzAxfQ.3Thp81rDFrKXr3WrY1MyMnNK8kKoZBX9lg-JwFznR-M";

    }

    @Test
    void Should_ReturnResponseEntityCreated_When_DishSaved(){
        dishRequestDto = new DishRequestDto();
        dishRequestDto.setRestaurantId(1L);
        dishRequestDto.setCategoryId(2L);
        dishRequestDto.setPrice(10000);
        dishRequestDto.setName("Tiramisu");
        dishRequestDto.setImageUrl("http//:image.png");
        dishRequestDto.setDescription("This is the description");

        doNothing().when(dishHandler).saveDish(dishRequestDto, token);

        ResponseEntity<Void> responseEntity = dishController.saveDish(dishRequestDto, token);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void Should_ReturnResponseEntityOK_When_DishUpdated(){
        Map<String, Object> fields = new HashMap<>();
        fields.put("price",10);

        Long dishId = 1L;

        doNothing().when(dishHandler).updateDish(dishId, fields, token);

        ResponseEntity<Void> responseEntity = dishController.updateDishFields(dishId, fields, token);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void Should_ReturnResponseEntityOK_When_DishActivated(){
        Map<String, Object> fields = new HashMap<>();
        fields.put("active",false);

        Long dishId = 1L;

        doNothing().when(dishHandler).updateActiveField(dishId, fields, token);

        ResponseEntity<Void> responseEntity = dishController.updateActiveField(dishId, fields, token);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void Should_ReturnList_When_ShowMenu() {
        Long restaurantId = 1L;
        Long categoryId = 1L;
        Integer elements = 1;

        DishPageResponseDto dishPageResponseDto = new DishPageResponseDto();
        dishPageResponseDto.setPrice(10000);
        dishPageResponseDto.setName("Tiramisu");
        dishPageResponseDto.setImageUrl("http//:image.png");
        dishPageResponseDto.setDescription("This is the description");
        dishPageResponseDto.setActive(true);

        List<DishPageResponseDto> responseDtoList = new ArrayList<>();
        responseDtoList.add(dishPageResponseDto);

       when(dishHandler.showMenu(restaurantId, categoryId, elements, token)).thenReturn(List.of(dishPageResponseDto));

        List<DishPageResponseDto> response = dishController.showMenu(elements, categoryId, restaurantId, token);

        assertThat(response.size()).isEqualTo(responseDtoList.size());
        assertTrue(response.contains(dishPageResponseDto));


    }
}
