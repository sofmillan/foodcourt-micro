package com.pragma.powerup.infrastructure.out.jpa.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="order_dish")
@Data
public class OrderDishEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_dish_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private DishEntity dish;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    @Column(name = "amount")
    private Integer amount;


}
