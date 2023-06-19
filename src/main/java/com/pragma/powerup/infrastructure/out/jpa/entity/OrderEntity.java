package com.pragma.powerup.infrastructure.out.jpa.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name="orders")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_id")
    private Long id;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "status")
    private String status;

    @OneToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;

    @OneToOne
    @JoinColumn(name = "chef_id")
    private RestaurantEmployeeEntity chefId;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<OrderDishEntity> orderDishes;

    private String deliveryCode;

}
