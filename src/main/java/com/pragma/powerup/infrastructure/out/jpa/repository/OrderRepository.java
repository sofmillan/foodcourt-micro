package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    @Query( value="select count(*) from orders where restaurant_id=?2 and client_id=?1 and (status='PENDING' OR status='READY')",
            nativeQuery = true)
    Integer countOrders(Long clientId, Long restaurantId);


    @Query("Select o from OrderEntity o where o.restaurant.id =?1")
    List<OrderEntity> showOrders( Long restaurantId);
}

