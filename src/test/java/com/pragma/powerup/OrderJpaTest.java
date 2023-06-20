package com.pragma.powerup;

import com.pragma.powerup.domain.exception.DataNotFoundException;
import com.pragma.powerup.domain.exception.DataNotValidException;
import com.pragma.powerup.domain.exception.ForbiddenException;
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

class OrderJpaTest {
    RestaurantRepository restaurantRepository;
    DishRepository dishRepository;
    OrderRepository orderRepository;
    IOrderEntityMapper orderEntityMapper;
    OrderDishRepository orderDishRepository;
    RestaurantEmployeeRepository chefRepository;
    IOrderPersistencePort orderPersistence;

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
    void Should_ThrowForbiddenException_When_UpdateToPreparationWithWrongEmployee(){
        Long orderId = 1L;
        Long employeeId = 1L;
        OrderEntity order = new OrderEntity();
        RestaurantEntity restaurant = new RestaurantEntity();
        RestaurantEmployeeEntity chef = new RestaurantEmployeeEntity();

        restaurant.setId(1L);
        chef.setRestaurantId(2L);
        order.setRestaurant(restaurant);
        order.setStatus("PENDING");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(chefRepository.findByUserId(employeeId)).thenReturn(Optional.of(chef));

        assertThrows(ForbiddenException.class,
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
                () -> orderPersistence.updateOrderToReady(orderId, employeeId, code));
    }

    @Test
    void Should_ThrowForbiddenException_When_UpdateToReadyWithWrongEmployee(){
        Long orderId = 1L;
        Long employeeId = 1L;
        String code = "a5c63";
        OrderEntity order = new OrderEntity();
        RestaurantEntity restaurant = new RestaurantEntity();
        RestaurantEmployeeEntity chef = new RestaurantEmployeeEntity();

        restaurant.setId(1L);
        chef.setRestaurantId(2L);
        order.setRestaurant(restaurant);
        order.setStatus("IN_PREPARATION");
        order.setDeliveryCode(code);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(chefRepository.findByUserId(employeeId)).thenReturn(Optional.of(chef));
        assertThrows(ForbiddenException.class,
                () -> orderPersistence.updateOrderToReady(orderId, employeeId, code));
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
    void Should_ThrowForbiddenException_When_UpdateToDeliveredWithWrongEmployee(){
        Long orderId = 1L;
        Long employeeId = 1L;
        String code = "a5c63";
        OrderEntity order = new OrderEntity();
        RestaurantEntity restaurant = new RestaurantEntity();
        RestaurantEmployeeEntity chef = new RestaurantEmployeeEntity();

        restaurant.setId(1L);
        chef.setRestaurantId(2L);
        order.setRestaurant(restaurant);
        order.setStatus("READY");
        order.setDeliveryCode(code);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(chefRepository.findByUserId(employeeId)).thenReturn(Optional.of(chef));
        assertThrows(ForbiddenException.class,
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

    @Test
    void CancelOrder_Should_ThrowDataNotFoundException_When_OrderId_NotFound(){
        Long orderId = 1L;
        Long clientId = 123L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class,
                () -> orderPersistence.cancelOrder(orderId, clientId));
    }

    @Test
    void CancelOrder_Should_ReturnFalse_When_StatusIsInvalid(){
        Long orderId = 1L;
        Long clientId = 123L;
        OrderEntity order = new OrderEntity();
        RestaurantEntity restaurant = new RestaurantEntity();
        RestaurantEmployeeEntity chef = new RestaurantEmployeeEntity();

        restaurant.setId(1L);
        chef.setRestaurantId(restaurant.getId());
        order.setRestaurant(restaurant);
        order.setStatus("READY");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        order.setRestaurant(restaurant);
        order.setStatus("READY");

        assertFalse(orderPersistence.cancelOrder(orderId, clientId));
    }

    @Test
    void CancelOrder_Should_ReturnTrue_When_StatusIsValid(){
        Long orderId = 1L;
        Long clientId = 123L;
        OrderEntity order = new OrderEntity();
        RestaurantEntity restaurant = new RestaurantEntity();
        RestaurantEmployeeEntity chef = new RestaurantEmployeeEntity();

        restaurant.setId(1L);
        chef.setRestaurantId(restaurant.getId());
        order.setRestaurant(restaurant);
        order.setStatus("READY");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        order.setRestaurant(restaurant);
        order.setStatus("PENDING");

        assertTrue(orderPersistence.cancelOrder(orderId, clientId));
    }

    @Test
    void FindClientByOrder_Should_ThrowDataNotFoundException_When_OrderIdNotFound(){
        Long orderId = 1L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class,
                () -> orderPersistence.findClientByOrderId(orderId));
    }

}
