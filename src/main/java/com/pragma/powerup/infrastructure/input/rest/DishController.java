package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.DishRequestDto;
import com.pragma.powerup.application.dto.response.DishPageResponseDto;
import com.pragma.powerup.application.handler.IDishHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Tag(name="Dishes", description = "Dish related operations")
@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class DishController {

    private final IDishHandler dishHandler;


    @Operation(summary = "Add a new dish",
            description = "As an owner, you can add a new dish to your restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dish added successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Some data is not valid, check it", content = @Content),
            @ApiResponse(responseCode = "403", description = "You do not have access to this request", content = @Content)

    })
    @PostMapping("/dish")
    public ResponseEntity<Void> saveDish(@RequestBody @Valid DishRequestDto dishRequestDto,
                                         @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token) {

        dishHandler.saveDish(dishRequestDto, token);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(summary = "Update the price or description of a dish",
            description = "As an owner, you can update  price or description fields from a dish of your own restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dish updated successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Some data is not valid, check it", content = @Content),
            @ApiResponse(responseCode = "403", description = "You do not have access to this request", content = @Content)
    })
    @PatchMapping("/dish/{id}")
    public ResponseEntity<Void> updateDishFields(@Parameter(description = "Dish id") @PathVariable Long id,
                                                 @RequestBody Map<String, Object> fields,
                                                 @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token){

        dishHandler.updateDish(id,fields, token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Activate or Deactivate a dish",
            description = "As an owner, you can activate or deactivate a dish of your own restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dish updated successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Some data is not valid, check it", content = @Content),
            @ApiResponse(responseCode = "403", description = "You do not have access to this request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Some info is was not found", content = @Content)
    })
    @PatchMapping("/active/{id}")
    public ResponseEntity<Void> updateActiveField( @Parameter(description = "Dish id") @PathVariable Long id,
                                                   @RequestBody Map<String, Object> fields,
                                                   @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token){

        dishHandler.updateActiveField(id,fields, token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Show dishes by category",
            description = "As a client, you can look for dishes filtered by category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dish were brought successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Some data is not valid, check it", content = @Content),
            @ApiResponse(responseCode = "403", description = "You do not have access to this request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Some info is was not found", content = @Content)
    })
    @GetMapping("/restaurant/{restaurantId}")
    public List<DishPageResponseDto> showMenu(@Parameter(description = "Number of dishes you want to see") @RequestParam Integer numberOfElements,
                                              @Parameter(description = "Category Id")  @RequestParam Long categoryId,
                                              @Parameter(description = "Restaurant Id") @PathVariable Long restaurantId,
                                              @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token){

        return dishHandler.showMenu(restaurantId, categoryId, numberOfElements, token);
    }


}

