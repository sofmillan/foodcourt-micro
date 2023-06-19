package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.OrderModel;
import com.pragma.powerup.domain.model.OrderModelResp;

import java.util.List;
public interface IOrderPersistencePort {

    OrderModel addOrder(OrderModel orderModel, Long clientId);

    List<OrderModelResp> showOrders(Integer elements, String status, Long idEmployee);
    void updateOrderToPreparation(Long idOrder, Long idEmployee);

    void updateOrderToReady(Long idOrder, Long idEmployee, String code);

    void updateOrderToDelivered(Long idOrder, Long idEmployee, String code);

    Long findClientByOrderId(Long orderId);


    boolean cancelOrder(Long orderId, Long clientId);
}

