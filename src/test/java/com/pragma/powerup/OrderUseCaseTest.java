package com.pragma.powerup;

import com.pragma.powerup.application.dto.response.DishResponseDto;
import com.pragma.powerup.application.dto.response.OrderDishResponseDto;
import com.pragma.powerup.application.dto.response.OrderPageResponseDto;
import com.pragma.powerup.domain.client.TwilioClientPort;
import com.pragma.powerup.domain.client.UserClientPort;
import com.pragma.powerup.domain.model.*;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.domain.usecase.OrderUseCase;
import com.pragma.powerup.infrastructure.out.feign.TwilioClientAdapter;
import com.pragma.powerup.infrastructure.out.feign.UserClientAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.OrderJpaAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class OrderUseCaseTest {

    IOrderPersistencePort orderPersistence;
    TwilioClientPort twilioClient;
    UserClientPort userClient;
    OrderUseCase orderService;
    String token;
    OrderModel orderModel;

    @BeforeEach
    void setUp(){
        orderPersistence = mock(OrderJpaAdapter.class);
        twilioClient = mock(TwilioClientAdapter.class);
        userClient = mock(UserClientAdapter.class);
        orderService = new OrderUseCase(orderPersistence, twilioClient, userClient);
        token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJhYmNkMTIzIiwiZXhwaXJ5IjoxNjQ2NjM1NjExMzAxfQ.3Thp81rDFrKXr3WrY1MyMnNK8kKoZBX9lg-JwFznR-M";
    }

    @Test
    void Should_AddOrder(){
        OrderDishModel orderDishModel1 = new OrderDishModel();
        orderDishModel1.setDishId(1L);
        orderDishModel1.setAmount(2);

        OrderDishModel orderDishModel2 = new OrderDishModel();
        orderDishModel2.setDishId(2L);
        orderDishModel2.setAmount(2);

        List<OrderDishModel> orderDishModels = new ArrayList<>();
        orderDishModels.add(orderDishModel1);
        orderDishModels.add(orderDishModel2);

        orderModel = new OrderModel();
        orderModel.setRestaurantId(1L);
        orderModel.setOrderDishes(orderDishModels);

        Long clientId = 10L;
        when(userClient.findClientId(token)).thenReturn(clientId);

        orderService.addOrder(orderModel, token);

        verify(orderPersistence).addOrder(orderModel, clientId);
    }

    @Test
    void Should_UpdateOrderToPreparation(){
        Long orderId = 1L;
        Long employeeId = 5L;

        when(userClient.findEmployeeId(token)).thenReturn(employeeId);

        orderService.updateOrderToPreparation(orderId, token);

        verify(orderPersistence).updateOrderToPreparation(orderId, employeeId);
    }

    @Test
    void Should_UpdateOrderToDelivered(){
        Long orderId = 1L;
        Long employeeId = 5L;
        String code = "f5ea2";
        when(userClient.findEmployeeId(token)).thenReturn(employeeId);

        orderService.updateOrderToDelivered(orderId, token, code);

        verify(orderPersistence).updateOrderToDelivered(orderId, employeeId, code);
    }

    @Test
    void Should_ReturnAFiveDigitCode(){
        String code = orderService.buildTwilioCode();

        assertThat(code.length()).isEqualTo(5);
    }

    @Test
    void SendMessage_Should_ThrowException_When_TwilioServiceCannotBeUsed(){
        String code = "zb1";
        Long clientId = 10L;
        String phoneNumber = "+578444154";

        MessageModel messageModel = new MessageModel();
        messageModel.setSecurityCode(code);
        messageModel.setPhoneNumber(phoneNumber);

        when(userClient.findPhoneByClientId(clientId)).thenReturn(phoneNumber);
        when(twilioClient.sendMessage(messageModel)).thenReturn(false);

        assertThrows(RuntimeException.class,
                ()-> orderService.sendMessage(code, clientId));
    }

    @Test
    void CancelOrder_Should_ThrowException_When_TwilioServiceCannotBeUsed(){
        Long clientId = 10L;
        String phoneNumber = "+578444154";

        CancelModel cancelModel = new CancelModel();
        cancelModel.setPhoneNumber(phoneNumber);

        when(userClient.findPhoneByClientId(clientId)).thenReturn(phoneNumber);
        when(twilioClient.cancel(cancelModel)).thenReturn(false);

        assertThrows(RuntimeException.class,
                ()-> orderService.cancelOrderTwilio(clientId));
    }

    @Test
    void Should_ReturnOrders_When_ShowOrders(){
        Integer numberOfElements = 1;
        String status = "PENDING";
        Long employeeId = 5L;

        when(userClient.findEmployeeId(token)).thenReturn(employeeId);

        orderService.showOrders(numberOfElements, status, token);

        verify(orderPersistence).showOrders(numberOfElements, status, employeeId);
    }


}
