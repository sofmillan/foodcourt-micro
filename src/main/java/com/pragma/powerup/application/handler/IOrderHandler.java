package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.application.dto.request.SecurityCodeDto;
import com.pragma.powerup.application.dto.response.OrderPageResponseDto;
import java.util.List;
public interface IOrderHandler {
    void addOrder(OrderRequestDto orderRequestDto, String token);
    List<OrderPageResponseDto> showOrders(Integer elements, String status, String token);
    void updateOrderToPreparation(Long idOrder, String token);

    void updateOrderToReady(Long idOrder, String token);

    void updateOrderToDelivered(Long idOrder, String token, SecurityCodeDto codeDto);

    void cancelOrder(Long orderId, String token );
}
