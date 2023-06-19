package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.OrderDishRequestDto;
import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.application.dto.response.DishResponseDto;
import com.pragma.powerup.application.dto.response.OrderDishResponseDto;
import com.pragma.powerup.application.dto.response.OrderPageResponseDto;
import com.pragma.powerup.domain.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderRequestMapper {

    OrderModel toOrderModel(OrderRequestDto orderRequestDto);

    OrderRequestDto toOrderDto(OrderModel orderModel);

    OrderDishModel toOrderDishModel(OrderDishRequestDto orderDishRequestDto);

    OrderDishRequestDto toOrderDishDto(OrderDishModel orderDishModel);

    OrderPageResponseDto toResponseDto(OrderModelResp orderModelResp);

    DishResponseDto todishResponse(DishModelResp dishModelResp);

    OrderDishResponseDto toOrderDishResponse(OrderDishModelResp orderDishModelResp);
}
