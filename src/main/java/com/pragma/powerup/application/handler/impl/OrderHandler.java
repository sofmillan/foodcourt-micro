package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.application.dto.request.SecurityCodeDto;
import com.pragma.powerup.application.dto.response.OrderPageResponseDto;
import com.pragma.powerup.application.handler.IOrderHandler;
import com.pragma.powerup.application.mapper.IOrderRequestMapper;
import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.model.OrderModel;
import com.pragma.powerup.domain.model.OrderModelResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandler implements IOrderHandler {

    private final IOrderServicePort orderServicePort;
    private final IOrderRequestMapper orderRequestMapper;
    @Override
    public void addOrder(OrderRequestDto orderRequestDto, String token) {
        OrderModel orderModel = orderRequestMapper.toOrderModel(orderRequestDto);
        orderServicePort.addOrder(orderModel, token);
    }

    @Override
    public List<OrderPageResponseDto> showOrders(Integer elements, String status, String token) {
        List<OrderModelResp> listModel= orderServicePort.showOrders(elements, status, token);
        return listModel.stream().map(orderRequestMapper::toResponseDto).collect(Collectors.toList());
    }

    @Override
    public void updateOrderToPreparation(Long idOrder, String token) {
        orderServicePort.updateOrderToPreparation(idOrder, token);
    }

    @Override
    public void updateOrderToReady(Long idOrder, String token) {
        orderServicePort.updateOrderToReady(idOrder, token);
    }

    @Override
    public void updateOrderToDelivered(Long idOrder, String token, SecurityCodeDto codeDto) {
        orderServicePort.updateOrderToDelivered(idOrder, token, codeDto.getSecurityCode());
    }

    @Override
    public void cancelOrder(Long orderId, String token) {
        orderServicePort.cancelOrder(orderId, token);
    }
}
