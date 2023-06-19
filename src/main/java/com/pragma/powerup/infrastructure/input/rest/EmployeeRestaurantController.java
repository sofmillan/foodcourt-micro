package com.pragma.powerup.infrastructure.input.rest;


import com.pragma.powerup.application.dto.request.RestaurantEmployeeRequestDto;
import com.pragma.powerup.application.handler.IRestaurantEmployeeHandler;
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

import javax.validation.Valid;


@Tag(name="RestaurantEmployee", description = "RestaurantEmployee related operations")
@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class EmployeeRestaurantController {

    private final IRestaurantEmployeeHandler restaurantEmployeeHandler;

    @Operation(summary = "Associate an employee to your restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee associated successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Some data is not valid, check it", content = @Content),
    })
    @PostMapping("/employee")
    public ResponseEntity<Void> associateEmployee(@RequestBody @Valid RestaurantEmployeeRequestDto restaurantEmployeeRequestDto,
                                               @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token) {
        restaurantEmployeeHandler.associateEmployee(restaurantEmployeeRequestDto, token);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
