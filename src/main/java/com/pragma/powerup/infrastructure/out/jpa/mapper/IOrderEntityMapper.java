package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.*;
import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderDishEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IOrderEntityMapper {
    OrderModel toModel(OrderEntity orderEntity);

    OrderEntity toEntity(OrderModel orderModel);

    OrderDishModel toOrderDishModel(OrderDishEntity orderDishEntity);

    OrderDishEntity toOrderDishEntity(OrderDishModel orderDishModel);

    OrderModelResp toModelResponse(OrderEntity orderEntity);

    OrderDishModelResp toDishModelResponse(OrderDishEntity orderDishEntity);

    DishModelResp todishModelResp(DishEntity dish);

  /*  @Mapping(target = "chefId", source = "entity.userId")
    Long toOrderId(RestaurantEmployeeEntity entity);*/

  /*  default Long convert(RestaurantEmployeeEntity entity) {
        return entity.getUserId() != null ? entity.getUserId() : 0;
    }*/
}
