package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.client.TwilioClientPort;
import com.pragma.powerup.domain.client.UserClientPort;
import com.pragma.powerup.domain.model.CancelModel;
import com.pragma.powerup.domain.model.MessageModel;
import com.pragma.powerup.domain.model.OrderModel;
import com.pragma.powerup.domain.model.OrderModelResp;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;

import java.util.List;
import java.util.UUID;


public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final TwilioClientPort twilioClientPort;
    private final UserClientPort userClientPort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort, TwilioClientPort twilioClientPort, UserClientPort userClientPort) {
        this.orderPersistencePort = orderPersistencePort;
        this.twilioClientPort = twilioClientPort;
        this.userClientPort = userClientPort;
    }

    @Override
    public void addOrder(OrderModel orderModel, String token) {
        Long clientId = userClientPort.findClientId(token);
        orderPersistencePort.addOrder(orderModel, clientId);
    }

    @Override
    public List<OrderModelResp> showOrders(Integer elements, String status, String token) {
        Long employeeId = userClientPort.findEmployeeId(token);
        return orderPersistencePort.showOrders(elements, status, employeeId);
    }

    @Override
    public void updateOrderToPreparation(Long idOrder, String token) {
        Long employeeId = userClientPort.findEmployeeId(token);
        orderPersistencePort.updateOrderToPreparation(idOrder, employeeId);
    }


    public void updateOrderToReady(Long idOrder, String token) {
        Long employeeId = userClientPort.findEmployeeId(token);
        String code = buildTwilioCode();

        orderPersistencePort.updateOrderToReady(idOrder, employeeId, code);
        Long clientId = orderPersistencePort.findClientByOrderId(idOrder);
        sendMessage(code, clientId);
    }

    @Override
    public void updateOrderToDelivered(Long orderId, String token, String code) {
        Long employeeId = userClientPort.findEmployeeId(token);
        orderPersistencePort.updateOrderToDelivered(orderId, employeeId, code);
    }

    @Override
    public void cancelOrder(Long idOrder, String token) {
        Long clientId = userClientPort.findClientId(token);
        boolean result = orderPersistencePort.cancelOrder(idOrder, clientId);
        if(!result){
            cancelOrderTwilio(clientId);
        }
    }


    public String buildTwilioCode() {
        String randomUUID = UUID.randomUUID().toString();
        String  code = randomUUID.substring(0,5);
        return code;
    }

    public void sendMessage ( String code, Long clientId){
        String clientPhoneNumber = userClientPort.findPhoneByClientId(clientId);
        try{
            MessageModel messageModel = new MessageModel();
            messageModel.setPhoneNumber(clientPhoneNumber);
            messageModel.setSecurityCode(code);
            boolean result = twilioClientPort.sendMessage(messageModel);
            if(!result){
                throw new RuntimeException("Error using twilio");
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void cancelOrderTwilio (Long clientId){
        String clientPhoneNumber = userClientPort.findPhoneByClientId(clientId);
        try{
            CancelModel cancelModel = new CancelModel();
            cancelModel.setPhoneNumber(clientPhoneNumber);
            boolean result = twilioClientPort.cancel(cancelModel);
            if(!result){
                throw new RuntimeException("Error using twilio");
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }



}
