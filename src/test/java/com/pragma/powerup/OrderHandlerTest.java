package com.pragma.powerup;

import com.pragma.powerup.application.dto.request.OrderDishRequestDto;
import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.application.dto.request.SecurityCodeDto;
import com.pragma.powerup.application.dto.response.DishResponseDto;
import com.pragma.powerup.application.dto.response.OrderDishResponseDto;
import com.pragma.powerup.application.dto.response.OrderPageResponseDto;
import com.pragma.powerup.application.handler.IOrderHandler;
import com.pragma.powerup.application.handler.impl.OrderHandler;
import com.pragma.powerup.application.mapper.IOrderRequestMapper;
import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.model.*;
import com.pragma.powerup.domain.usecase.OrderUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderHandlerTest {
    IOrderServicePort orderServicePort;
    IOrderRequestMapper orderRequestMapper;
    IOrderHandler orderHandler;
    String token;

    @BeforeEach
    void setUp(){
        orderServicePort = mock(OrderUseCase.class);
        orderRequestMapper = mock(IOrderRequestMapper.class);
        orderHandler = new OrderHandler(orderServicePort, orderRequestMapper);
        token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJhYmNkMTIzIiwiZXhwaXJ5IjoxNjQ2NjM1NjExMzAxfQ.3Thp81rDFrKXr3WrY1MyMnNK8kKoZBX9lg-JwFznR-M";
    }

    @Test
    void Should_AddOrder(){
        OrderDishModel orderDishModel = new OrderDishModel();
        orderDishModel.setDishId(1L);
        orderDishModel.setAmount(2);

        OrderModel orderModel = new OrderModel();
        orderModel.setRestaurantId(1L);
        orderModel.setOrderDishes(List.of(orderDishModel));

        OrderDishRequestDto orderDishRequestDto = new OrderDishRequestDto();
        orderDishRequestDto.setDishId(orderDishModel.getDishId());
        orderDishRequestDto.setAmount(orderDishModel.getAmount());

        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setRestaurantId(orderModel.getRestaurantId());
        orderRequestDto.setOrderDishes(List.of(orderDishRequestDto));

        when(orderRequestMapper.toOrderModel(orderRequestDto)).thenReturn(orderModel);

        orderHandler.addOrder(orderRequestDto, token);

        verify(orderServicePort).addOrder(orderModel, token);
    }

    @Test
    void Should_UpdateOrderToPreparation(){
        Long orderId = 1L;

        orderHandler.updateOrderToPreparation(orderId, token);

        verify(orderServicePort).updateOrderToPreparation(orderId, token);
    }

    @Test
    void Should_UpdateOrderToReady(){
        Long orderId = 1L;

        orderHandler.updateOrderToReady(orderId, token);

        verify(orderServicePort).updateOrderToReady(orderId, token);
    }

    @Test
    void Should_UpdateOrderToDelivered(){
        Long orderId = 1L;
        SecurityCodeDto securityCodeDto = new SecurityCodeDto();
        securityCodeDto.setSecurityCode("a4c57");
        orderHandler.updateOrderToDelivered(orderId, token, securityCodeDto);

        verify(orderServicePort).updateOrderToDelivered(orderId, token, securityCodeDto.getSecurityCode());
    }

    @Test
    void Should_CancelOrder(){
        Long orderId = 1L;
        orderHandler.cancelOrder(orderId, token);

        verify(orderServicePort).cancelOrder(orderId, token);
    }

    @Test
    void ShouldShowOrders(){
        int numberOfElements = 1;
        String status = "PENDING";

        DishModelResp dishModelResp = new DishModelResp();
        dishModelResp.setDescription("This is the description");
        dishModelResp.setName("Tiramisu");
        dishModelResp.setImageUrl("https://logo.png");
        dishModelResp.setId(1L);

        OrderDishModelResp orderDishModelResp = new OrderDishModelResp();
        orderDishModelResp.setId(1L);
        orderDishModelResp.setAmount(2);
        orderDishModelResp.setDish(dishModelResp);

        OrderModelResp orderModelResp = new OrderModelResp();
        orderModelResp.setClientId(1L);
        orderModelResp.setDate(LocalDate.now());
        orderModelResp.setStatus("PENDING");
        orderModelResp.setId(1L);
        orderModelResp.setOrderDishes(Set.of(orderDishModelResp));

        DishResponseDto dishResponseDto = new DishResponseDto();
        dishResponseDto.setDescription("This is the description");
        dishResponseDto.setName("Tiramisu");
        dishResponseDto.setImageUrl("https://logo.png");
        dishResponseDto.setId(1L);

        OrderDishResponseDto orderDishResponseDto = new OrderDishResponseDto();
        orderDishResponseDto.setId(1L);
        orderDishResponseDto.setAmount(2);
        orderDishResponseDto.setDish(dishResponseDto);

        OrderPageResponseDto orderPageResponseDto = new OrderPageResponseDto();
        orderPageResponseDto.setClientId(1L);
        orderPageResponseDto.setDate(LocalDate.now());
        orderPageResponseDto.setStatus("PENDING");
        orderPageResponseDto.setId(1L);
        orderPageResponseDto.setOrderDishes(Set.of(orderDishResponseDto));

        when(orderServicePort.showOrders(numberOfElements, status, token)).thenReturn(List.of(orderModelResp));
        when(orderRequestMapper.toResponseDto(orderModelResp)).thenReturn(orderPageResponseDto);

        assertThat(orderHandler.showOrders(numberOfElements, status, token).size()).isEqualTo(1);
    }

}
