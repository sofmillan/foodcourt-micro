package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.RestaurantRequestDto;
import com.pragma.powerup.application.dto.response.RestaurantPageResponseDto;
import com.pragma.powerup.application.handler.IRestaurantHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Tag(name="Restaurant", description = "Restaurant related operations")
@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class RestaurantController {
    private final IRestaurantHandler restaurantHandler;
    @Operation(summary = "Create a new restaurant",
            description = "As an administrator, you can create a new restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurant created successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Some data is not valid, check it", content = @Content),
            @ApiResponse(responseCode = "409", description = "Restaurant already exists", content = @Content)
    })
    @PostMapping("/restaurant")
    public ResponseEntity<Void> saveRestaurant(@RequestBody @Valid RestaurantRequestDto restaurantRequestDto,
                                               @RequestHeader("Authorization") String token) {
        restaurantHandler.saveRestaurant(restaurantRequestDto, token);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Show restaurants by alphabetical order ",
            description = "As a client, you can look for a specific number of restaurants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurants were brought successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Some data is not valid, check it", content = @Content),
            @ApiResponse(responseCode = "403", description = "You do not have access to this request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Some info is was not found", content = @Content)
    })
    @GetMapping("/restaurant")
    public List<RestaurantPageResponseDto> findRestaurants(@RequestParam Integer numberOfElements,
                                                           @RequestHeader("Authorization") String token){
        return restaurantHandler.findRestaurants(numberOfElements, token);
    }


}
