package com.pragma.powerup;

import com.pragma.powerup.application.dto.request.OrderDishRequestDto;
import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.application.dto.request.SecurityCodeDto;
import com.pragma.powerup.application.dto.response.DishResponseDto;
import com.pragma.powerup.application.dto.response.OrderDishResponseDto;
import com.pragma.powerup.application.dto.response.OrderPageResponseDto;
import com.pragma.powerup.application.dto.response.RestaurantPageResponseDto;
import com.pragma.powerup.application.handler.IOrderHandler;
import com.pragma.powerup.application.handler.impl.OrderHandler;
import com.pragma.powerup.infrastructure.input.rest.OrderController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class OrderControllerTest {
    IOrderHandler orderHandler;
    OrderController orderController;
    String token;
    OrderRequestDto orderRequestDto;
    ResponseEntity<Void> responseEntity;

    @BeforeEach
    void setUp(){
        orderHandler = mock(OrderHandler.class);
        orderController = new OrderController(orderHandler);
        token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJhYmNkMTIzIiwiZXhwaXJ5IjoxNjQ2NjM1NjExMzAxfQ.3Thp81rDFrKXr3WrY1MyMnNK8kKoZBX9lg-JwFznR-M";
    }

    @Test
    void Should_ReturnResponseEntityCreated_When_RestaurantSaved(){
        OrderDishRequestDto orderDish1 = new OrderDishRequestDto();
        orderDish1.setAmount(2);
        orderDish1.setDishId(1L);

        OrderDishRequestDto orderDish2 = new OrderDishRequestDto();
        orderDish2.setAmount(2);
        orderDish2.setDishId(2L);
        List<OrderDishRequestDto> dishRequestDtoList = new ArrayList<>();
        dishRequestDtoList.add(orderDish1);
        dishRequestDtoList.add(orderDish2);

        orderRequestDto = new OrderRequestDto();
        orderRequestDto.setOrderDishes(dishRequestDtoList);
        orderRequestDto.setRestaurantId(1L);

        doNothing().when(orderHandler).addOrder(orderRequestDto, token);

        ResponseEntity<Void> responseEntity = orderController.addOrder(orderRequestDto, token);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void Should_ReturnResponseEntityOK_When_UpdateToPreparation(){
        Long orderId = 1L;

        doNothing().when(orderHandler).updateOrderToPreparation(orderId,token);

        responseEntity = orderController.updateOrderToPreparation(orderId, token);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void Should_ReturnResponseEntityOK_When_UpdateToReady(){
        Long orderId = 1L;

        doNothing().when(orderHandler).updateOrderToReady(orderId,token);

        responseEntity = orderController.updateOrderToReady(orderId, token);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void Should_ReturnResponseEntityOK_When_UpdateToDelivered(){
        Long orderId = 1L;
        SecurityCodeDto securityCodeDto = new SecurityCodeDto();
        securityCodeDto.setSecurityCode("ab4f3");
        doNothing().when(orderHandler).updateOrderToDelivered(orderId,token, securityCodeDto);

        responseEntity = orderController.updateOrderToDelivered(orderId, token, securityCodeDto);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void Should_ReturnResponseEntityOK_When_CancelOrder(){
        Long orderId = 1L;

        doNothing().when(orderHandler).cancelOrder(orderId,token);

        responseEntity = orderController.cancelOrder(orderId, token);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void Should_ReturnOrderList_When_OrdersFound(){
        DishResponseDto dish = new DishResponseDto();
        dish.setDescription("This is the description");
        dish.setName("Foot long");
        dish.setImageUrl("https://image.png");
        dish.setId(1L);

        OrderDishResponseDto orderDishResponseDto = new OrderDishResponseDto();
        orderDishResponseDto.setAmount(1);
        orderDishResponseDto.setId(1L);
        orderDishResponseDto.setDish(dish);

        Set<OrderDishResponseDto> responseDtoSet = new HashSet<>();
        responseDtoSet.add(orderDishResponseDto);

        OrderPageResponseDto orderPageResponseDto = new OrderPageResponseDto();
        orderPageResponseDto.setClientId(1L);
        orderPageResponseDto.setStatus("PENDING");
        orderPageResponseDto.setDate(LocalDate.of(2024, 6,20));
        orderPageResponseDto.setOrderDishes(responseDtoSet);
        orderPageResponseDto.setId(1L);


        int elements = 1;
        String status = "PENDING";
        when(orderHandler.showOrders(elements, status, token)).thenReturn(List.of(orderPageResponseDto));

        List<OrderPageResponseDto> response = orderController.pageOrders(elements, status, token);

        assertThat(response.size()).isEqualTo(elements);
    }


}
