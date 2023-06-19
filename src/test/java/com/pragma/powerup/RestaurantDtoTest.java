package com.pragma.powerup;

import com.pragma.powerup.application.dto.request.RestaurantRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RestaurantDtoTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void When_DataIsCorrect_Expect_NoViolation(){
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto("Subway","4859858",
                "3663 Pine Tree Lane","3265698513","https://image.png",1L);

        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        assertTrue(violations.isEmpty());
    }


    @Test
    void When_NameIsNull_Expect_TwoViolation(){
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto(null,"4859858",
                "3663 Pine Tree Lane","3265698513","https://image.png",1L);

        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        assertEquals(2, violations.size());
    }

    @Test
    void When_NameIsEmpty_Expect_TwoViolation(){
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto("","4859858",
                "3663 Pine Tree Lane","3265698513","https://image.png",1L);

        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        assertEquals(2, violations.size());
    }

    @Test
    void When_NameIsNumeric_Expect_OneViolation(){
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto("584515","4859858",
                "3663 Pine Tree Lane","3265698513","https://image.png",1L);

        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        assertEquals(1, violations.size());
    }

    @Test
    void When_NitIsNull_Expect_TwoViolation(){
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto("Subway",null,
                "3663 Pine Tree Lane","3265698513","https://image.png",1L);

        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        assertEquals(2, violations.size());
    }

    @Test
    void When_NitIsEmpty_Expect_OneViolation(){
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto("Subway","",
                "3663 Pine Tree Lane","3265698513","https://image.png",1L);

        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        assertEquals(1, violations.size());
    }

    @Test
    void When_NitHasLetters_Expect_OneViolation(){
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto("Subway","abc4859858",
                "3663 Pine Tree Lane","3265698513","https://image.png",1L);

        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        assertEquals(1, violations.size());
    }

    @Test
    void When_AddressIsNull_Expect_TwoViolation(){
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto("Subway","45823665",
                null,"3265698513","https://image.png",1L);

        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        assertEquals(2, violations.size());
    }


    @Test
    void When_AddressIsEmpty_Expect_OneViolation(){
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto("Subway","45823665",
                "","3265698513","https://image.png",1L);

        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        assertEquals(1, violations.size());
    }

    @Test
    void When_PhoneNumberIsNull_Expect_TwoViolation(){
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto("Subway","45823665",
                "3663 Pine Tree Lane",null,"https://image.png",1L);

        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        assertEquals(2, violations.size());
    }

    @Test
    void When_PhoneNumberIsEmpty_Expect_TwoViolation(){
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto("Subway","45823665",
                "3663 Pine Tree Lane","","https://image.png",1L);

        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        assertEquals(2, violations.size());
    }


    @Test
    void When_PhoneNumberHasLetters_Expect_OneViolation(){
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto("Subway","4859858",
                "3663 Pine Tree Lane","abc326698513","https://image.png",1L);

        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        assertEquals(1, violations.size());
    }


    @Test
    void When_PhoneNumberMoreThan13Digits_Expect_OneViolation(){
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto("Subway","4859858",
                "3663 Pine Tree Lane","4584568458326698513","https://image.png",1L);

        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        assertEquals(1, violations.size());
    }
    @Test
    void When_LogoUrlIsNull_Expect_TwoViolation(){
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto("Subway","45823665",
                "3663 Pine Tree Lane","3235698452",null,1L);

        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        assertEquals(2, violations.size());
    }

    @Test
    void When_LogoUrlIsEmpty_Expect_OneViolation(){
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto("Subway","45823665",
                "3663 Pine Tree Lane","3235698452","",1L);

        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        assertEquals(1, violations.size());
    }

    @Test
    void When_LogoUrlIsNotValid_Expect_OneViolation(){
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto("Subway","45823665",
                "3663 Pine Tree Lane","3235698452","tps://image.png",1L);

        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        assertEquals(1, violations.size());
    }
    @Test
    void When_OwnerIdIsNull_Expect_OneViolation(){
        RestaurantRequestDto restaurantDto = new RestaurantRequestDto("Subway","45823665",
                "3663 Pine Tree Lane","3235698452","https://image.png",null);

        Set<ConstraintViolation<RestaurantRequestDto>> violations = validator.validate(restaurantDto);

        assertEquals(1, violations.size());
    }
}

