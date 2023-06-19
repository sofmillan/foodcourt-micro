package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.OrderModel;
import com.pragma.powerup.domain.model.OrderModelResp;

import java.util.List;

public interface IOrderServicePort {

    void addOrder(OrderModel orderModel, String token);

    List<OrderModelResp> showOrders(Integer elements, String status, String token);

    void updateOrderToPreparation(Long idOrder, String token);

    void updateOrderToReady(Long idOrder, String token);

    void updateOrderToDelivered(Long idOrder, String token, String code);

    void cancelOrder(Long idOrder, String token);
}
