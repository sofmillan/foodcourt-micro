package com.pragma.powerup;

import com.pragma.powerup.application.dto.request.OrderDishRequestDto;
import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.application.dto.request.SecurityCodeDto;
import com.pragma.powerup.application.handler.IOrderHandler;
import com.pragma.powerup.application.handler.impl.OrderHandler;
import com.pragma.powerup.application.mapper.IOrderRequestMapper;
import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.model.OrderDishModel;
import com.pragma.powerup.domain.model.OrderModel;
import com.pragma.powerup.domain.usecase.OrderUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        OrderDishRequestDto orderDishRequestDto = new OrderDishRequestDto();
        orderDishRequestDto.setDishId(1L);
        orderDishRequestDto.setAmount(2);

        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setRestaurantId(1L);
        orderRequestDto.setOrderDishes(List.of(orderDishRequestDto));

        OrderDishModel orderDishModel = new OrderDishModel();
        orderDishModel.setDishId(1L);
        orderDishModel.setAmount(2);

        OrderModel orderModel = new OrderModel();
        orderModel.setRestaurantId(1L);
        orderModel.setOrderDishes(List.of(orderDishModel));

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
}
