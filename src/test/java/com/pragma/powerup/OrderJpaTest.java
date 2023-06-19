package com.pragma.powerup;

import com.pragma.powerup.domain.exception.DataNotFoundException;
import com.pragma.powerup.domain.exception.DataNotValidException;
import com.pragma.powerup.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.adapter.OrderJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEmployeeEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderJpaTest {
    private  RestaurantRepository restaurantRepository;
    private  DishRepository dishRepository;
    private  OrderRepository orderRepository;
    private  IOrderEntityMapper orderEntityMapper;
    private  OrderDishRepository orderDishRepository;
    private  RestaurantEmployeeRepository chefRepository;
    private IOrderPersistencePort orderPersistence;

    @BeforeEach
    void setUp(){
        restaurantRepository = mock(RestaurantRepository.class);
        dishRepository = mock(DishRepository.class);
        orderRepository = mock(OrderRepository.class);
        orderEntityMapper = mock(IOrderEntityMapper.class);
        chefRepository = mock(RestaurantEmployeeRepository.class);
        orderDishRepository = mock(OrderDishRepository.class);
        orderPersistence = new OrderJpaAdapter(restaurantRepository, dishRepository, orderRepository, orderEntityMapper, orderDishRepository, chefRepository);
    }

    @Test
    void Should_ThrowDataNotFoundException_When_EmployeeIdNotFound(){
        int elements = 2;
        String status = "PENDING";
        Long employeeId = 1L;

        when(chefRepository.findByUserId(employeeId)).thenThrow(DataNotFoundException.class);

        assertThrows(DataNotFoundException.class,
                () -> orderPersistence.showOrders(elements, status, employeeId));
    }

    @Test
    void Should_ThrowDataNotValidException_When_UpdateToPreparationWithInvalidStatus(){
        Long orderId = 1L;
        Long employeeId = 1L;
        OrderEntity order = new OrderEntity();
        RestaurantEntity restaurant = new RestaurantEntity();
        RestaurantEmployeeEntity chef = new RestaurantEmployeeEntity();

        restaurant.setId(1L);
        chef.setRestaurantId(restaurant.getId());
        order.setRestaurant(restaurant);
        order.setStatus("READY");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(chefRepository.findByUserId(employeeId)).thenReturn(Optional.of(chef));

        assertThrows(DataNotValidException.class,
                () -> orderPersistence.updateOrderToPreparation(orderId, employeeId));
    }



    @Test
    void Should_ThrowDataNotValidException_When_UpdateToReadyWithInvalidStatus(){
        Long orderId = 1L;
        Long employeeId = 1L;
        String code = "a5c63";
        OrderEntity order = new OrderEntity();
        RestaurantEntity restaurant = new RestaurantEntity();
        RestaurantEmployeeEntity chef = new RestaurantEmployeeEntity();

        restaurant.setId(1L);
        chef.setRestaurantId(restaurant.getId());
        order.setRestaurant(restaurant);
        order.setStatus("PENDING");
        order.setDeliveryCode(code);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(chefRepository.findByUserId(employeeId)).thenReturn(Optional.of(chef));
        assertThrows(DataNotValidException.class,
                () -> orderPersistence.updateOrderToDelivered(orderId, employeeId, code));
    }

    @Test
    void Should_ThrowDataNotValidException_When_UpdateToDeliveredWithInvalidStatus(){
        Long orderId = 1L;
        Long employeeId = 1L;
        String code = "a5c63";
        OrderEntity order = new OrderEntity();
        RestaurantEntity restaurant = new RestaurantEntity();
        RestaurantEmployeeEntity chef = new RestaurantEmployeeEntity();

        restaurant.setId(1L);
        chef.setRestaurantId(restaurant.getId());
        order.setRestaurant(restaurant);
        order.setStatus("PENDING");
        order.setDeliveryCode(code);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(chefRepository.findByUserId(employeeId)).thenReturn(Optional.of(chef));
        assertThrows(DataNotValidException.class,
                () -> orderPersistence.updateOrderToDelivered(orderId, employeeId, code));
    }

    @Test
    void Should_ThrowDataNotFoundException_When_EmployeeIdNotFoundInUpdateToPreparation(){
        Long orderId = 1L;
        Long employeeId = 1L;
        OrderEntity order = new OrderEntity();
        RestaurantEntity restaurant = new RestaurantEntity();
        RestaurantEmployeeEntity chef = new RestaurantEmployeeEntity();

        restaurant.setId(1L);
        chef.setRestaurantId(restaurant.getId());
        order.setRestaurant(restaurant);
        order.setStatus("READY");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(chefRepository.findByUserId(employeeId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class,
                () -> orderPersistence.updateOrderToPreparation(orderId, employeeId));
    }

    @Test
    void Should_ThrowDataNotFoundException_When_EmployeeIdNotFoundInUpdateToReady(){
        Long orderId = 1L;
        Long employeeId = 1L;
        String code = "a5b67";
        OrderEntity order = new OrderEntity();
        RestaurantEntity restaurant = new RestaurantEntity();
        RestaurantEmployeeEntity chef = new RestaurantEmployeeEntity();

        restaurant.setId(1L);
        chef.setRestaurantId(restaurant.getId());
        order.setRestaurant(restaurant);
        order.setStatus("READY");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(chefRepository.findByUserId(employeeId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class,
                () -> orderPersistence.updateOrderToReady(orderId, employeeId, code));
    }

    @Test
    void Should_ThrowDataNotFoundException_When_EmployeeIdNotFoundInUpdateToDelivered(){
        Long orderId = 1L;
        Long employeeId = 1L;
        String code = "a5b67";
        OrderEntity order = new OrderEntity();
        RestaurantEntity restaurant = new RestaurantEntity();
        RestaurantEmployeeEntity chef = new RestaurantEmployeeEntity();

        restaurant.setId(1L);
        chef.setRestaurantId(restaurant.getId());
        order.setRestaurant(restaurant);
        order.setStatus("READY");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(chefRepository.findByUserId(employeeId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class,
                () -> orderPersistence.updateOrderToReady(orderId, employeeId, code));
    }

}
