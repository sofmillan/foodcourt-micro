package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.OrderRequestDto;
import com.pragma.powerup.application.dto.request.SecurityCodeDto;
import com.pragma.powerup.application.dto.response.OrderPageResponseDto;
import com.pragma.powerup.application.handler.IOrderHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Orders", description = "Order related operations")
@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderHandler orderHandler;

    @Operation(summary = "Add a new order",
            description = "As a client, you can order a specific number of dishes from a restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order added successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Some data is not valid, check it", content = @Content),
    })
    @PostMapping("/order")
    public ResponseEntity<Void> addOrder(@RequestBody OrderRequestDto orderRequestDto,
                         @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token){
        orderHandler.addOrder(orderRequestDto, token);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Page orders",
            description = "As an employee, you can look for a specific number of orders of the restaurant you work for")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders paged successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Some data is not valid, check it", content = @Content),
            @ApiResponse(responseCode = "404", description = "Some info is was not found", content = @Content)
    })
    @GetMapping("/orders")
    public List<OrderPageResponseDto> pageOrders(@Parameter(description = "Number of orders for page") @RequestParam Integer numberOfElements,
                                                 @Parameter(description = "Order status") @RequestParam String status,
                                                 @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token){
        return  orderHandler.showOrders(numberOfElements, status, token);
    }


    @Operation(summary = "Update order status to 'in preparation' ",
            description = "As an employee, you can take an order and update its status to 'in preparation'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Some data is not valid, check it", content = @Content),
            @ApiResponse(responseCode = "404", description = "Some info is was not found", content = @Content)
    })
    @PatchMapping("order/{orderId}/preparation")
    public ResponseEntity<Void> updateOrderToPreparation(@Parameter(description = "Order id") @PathVariable Long orderId,
                                                         @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token){
        orderHandler.updateOrderToPreparation(orderId, token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("order/{orderId}/ready")
    public ResponseEntity<Void> updateOrderToReady(@Parameter(description = "Order id") @PathVariable Long orderId,
                                                   @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token){
        orderHandler.updateOrderToReady(orderId, token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("order/{orderId}/delivered")
    public ResponseEntity<Void> updateOrderToDelivered(@Parameter(description = "Order id") @PathVariable Long orderId,
                                                       @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token,
                                                       @RequestBody SecurityCodeDto securityCodeDto){
        orderHandler.updateOrderToDelivered(orderId, token, securityCodeDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("order/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@Parameter(description = "Order id") @PathVariable Long orderId,
                                            @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token){
        orderHandler.cancelOrder( orderId, token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

