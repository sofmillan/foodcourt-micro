package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.OrderModel;
import com.pragma.powerup.domain.model.OrderModelResp;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.infrastructure.constants.Status;
import com.pragma.powerup.domain.exception.DataNotFoundException;
import com.pragma.powerup.domain.exception.DataNotValidException;
import com.pragma.powerup.domain.exception.ForbiddenException;
import com.pragma.powerup.infrastructure.out.jpa.entity.*;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final OrderDishRepository orderDishRepository;
    private final RestaurantEmployeeRepository chefRepository;
    @Override
    public OrderModel addOrder(OrderModel orderModel, Long clientId) {
        OrderEntity orderEntity = new OrderEntity();
        List<Long> dishIdsList = new ArrayList<>();
        List<Integer> amountList = new ArrayList<>();
        RestaurantEntity restaurant = restaurantRepository.findById(orderModel.getRestaurantId())
                .orElseThrow(DataNotFoundException::new);

        Integer countOrdersByClients = orderRepository.countOrders(clientId, orderModel.getRestaurantId());
        if(countOrdersByClients>0){
            throw new DataNotValidException();
        }
        orderModel.getOrderDishes().forEach(orderDish->{
            dishIdsList.add(orderDish.getDishId());
            amountList.add(orderDish.getAmount());
        });

        List<DishEntity> dishes = dishRepository.findAllById(dishIdsList);

        boolean isPartOfTheRestaurant = validateDish(orderModel.getRestaurantId(), dishes);
        if(!isPartOfTheRestaurant){
            throw new DataNotValidException();
        }
        orderEntity.setClientId(clientId);
        orderEntity.setDate(LocalDate.now());
        orderEntity.setRestaurant(restaurant);
        orderEntity.setStatus(Status.PENDING.getMessage());
        orderEntity.setOrderDishes(new HashSet<>());
        for(int i = 0; i< dishes.size();i++){
            OrderDishEntity orderDishEntity = new OrderDishEntity();
            orderDishEntity.setDish(dishes.get(i));
            orderDishEntity.setAmount(amountList.get(i));
            orderEntity.getOrderDishes().add(orderDishEntity);
        }

        OrderEntity savedOrder = orderRepository.save(orderEntity);
        for(OrderDishEntity orderDishEntity:orderEntity.getOrderDishes() ){
            orderDishEntity.setOrderEntity(savedOrder);
        }

        orderDishRepository.saveAll(orderEntity.getOrderDishes());

        return orderEntityMapper.toModel(orderEntity);
    }

    @Override
    public List<OrderModelResp> showOrders(Integer elements, String status, Long idEmployee) {

        RestaurantEmployeeEntity chef = chefRepository.findByUserId(idEmployee).orElseThrow(DataNotFoundException::new);

        Pageable pageable = PageRequest.of(0,elements);

        List<OrderEntity> orderEntityList = orderRepository.findAll(pageable).getContent();

        List<OrderEntity> orderEntityListFilters = orderEntityList
                .stream()
                .filter(order -> order.getStatus().equals(status))
                .filter(order -> order.getRestaurant().getId().equals(chef.getRestaurantId()))
                .collect(Collectors.toList());

        return orderEntityListFilters.stream().map(orderEntityMapper::toModelResponse).collect(Collectors.toList());
    }

    @Override
    public void updateOrderToPreparation(Long idOrder, Long employeeId) {
        OrderEntity orderEntity = orderRepository.findById(idOrder).orElseThrow(DataNotFoundException::new);
        RestaurantEmployeeEntity chef = chefRepository.findByUserId(employeeId).orElseThrow(DataNotFoundException::new);
        if(!chef.getRestaurantId().equals(orderEntity.getRestaurant().getId())){
            throw new ForbiddenException();
        }

        if(!orderEntity.getStatus().equals(Status.PENDING.getMessage())){
            throw new DataNotValidException();
        }
        orderEntity.setChefId(chef);
        orderEntity.setStatus(Status.IN_PREPARATION.getMessage());
        orderRepository.save(orderEntity);
    }

    @Override
    public void updateOrderToReady(Long idOrder, Long employeeId, String code) {
        OrderEntity orderEntity = orderRepository.findById(idOrder).orElseThrow(DataNotFoundException::new);

        RestaurantEmployeeEntity chef = chefRepository.findByUserId(employeeId).orElseThrow(DataNotFoundException::new);
        if(!chef.getRestaurantId().equals(orderEntity.getRestaurant().getId())){
            throw new ForbiddenException();
        }

        if(!orderEntity.getStatus().equals(Status.IN_PREPARATION.getMessage())){
            throw new DataNotValidException();
        }

        orderEntity.setStatus(Status.READY.getMessage());
        orderEntity.setDeliveryCode(code);
        orderRepository.save(orderEntity);
    }

    @Override
    public void updateOrderToDelivered(Long orderId, Long employeeId, String code) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(DataNotFoundException::new);

        RestaurantEmployeeEntity chef = chefRepository.findByUserId(employeeId).orElseThrow(DataNotFoundException::new);
        if(!chef.getRestaurantId().equals(orderEntity.getRestaurant().getId())){
            throw new ForbiddenException();
        }

        if(!orderEntity.getDeliveryCode().equals(code)){
            throw new DataNotValidException();
        }

        if(!orderEntity.getStatus().equals(Status.READY.getMessage())){
            throw new DataNotValidException();
        }
        orderEntity.setStatus(Status.DELIVERED.getMessage());
        orderRepository.save(orderEntity);
    }



    @Override
    public Long findClientByOrderId(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow(DataNotFoundException::new);
        return order.getClientId();
    }

    @Override
    public boolean cancelOrder(Long orderId, Long clientId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(DataNotFoundException::new);
        if(!orderEntity.getStatus().equals(Status.PENDING.getMessage())){
            return false;
        }
        orderEntity.setStatus(Status.CANCELLED.getMessage());
        orderRepository.save(orderEntity);
        return true;
    }


    boolean validateDish(Long restaurantId, List<DishEntity> dishEntityList){
        for(DishEntity dish: dishEntityList){
            if(!dish.getRestaurant().getId().equals(restaurantId)){
                return false;
            }
        }
        return true;
    }




}
