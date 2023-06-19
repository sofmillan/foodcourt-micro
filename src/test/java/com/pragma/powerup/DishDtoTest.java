package com.pragma.powerup;

import com.pragma.powerup.application.dto.request.DishRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DishDtoTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void When_DataIsCorrect_Expect_NoViolation(){
        DishRequestDto dishDto = new DishRequestDto("Foot Long",30000,"This is the description",
                "https:image.png",1L,1L);

        Set<ConstraintViolation<DishRequestDto>> violations = validator.validate(dishDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void When_NameIsNull_Expect_TwoViolation(){
        DishRequestDto dishDto = new DishRequestDto(null,30000,"This is the description",
                "https:image.png",1L,1L);

        Set<ConstraintViolation<DishRequestDto>> violations = validator.validate(dishDto);

        assertEquals(2, violations.size());
    }

    @Test
    void When_NameIsEmpty_Expect_OneViolation(){
        DishRequestDto dishDto = new DishRequestDto("",30000,"This is the description",
                "https:image.png",1L,1L);

        Set<ConstraintViolation<DishRequestDto>> violations = validator.validate(dishDto);

        assertEquals(1, violations.size());
    }

    @Test
    void When_PriceIsNull_Expect_OneViolation(){
        DishRequestDto dishDto = new DishRequestDto("Foot Long",null,"This is the description",
                "https:image.png",1L,1L);

        Set<ConstraintViolation<DishRequestDto>> violations = validator.validate(dishDto);

        assertEquals(1, violations.size());
    }

    @Test
    void When_PriceIsNegative_Expect_OneViolation(){
        DishRequestDto dishDto = new DishRequestDto("Foot Long",-30000,"This is the description",
                "https:image.png",1L,1L);

        Set<ConstraintViolation<DishRequestDto>> violations = validator.validate(dishDto);

        assertEquals(1, violations.size());
    }
    @Test
    void When_DescriptionIsNull_Expect_TwoViolation(){
        DishRequestDto dishDto = new DishRequestDto("Foot Long",30000,null,
                "https:image.png",1L,1L);

        Set<ConstraintViolation<DishRequestDto>> violations = validator.validate(dishDto);

        assertEquals(2, violations.size());
    }

    @Test
    void When_DescriptionIsEmpty_Expect_OneViolation(){
        DishRequestDto dishDto = new DishRequestDto("Subway",30000,"",
                "https:image.png",1L,1L);

        Set<ConstraintViolation<DishRequestDto>> violations = validator.validate(dishDto);

        assertEquals(1, violations.size());
    }

    @Test
    void When_ImageUrlIsNull_Expect_TwoViolation(){
        DishRequestDto dishDto = new DishRequestDto("Foot Long",30000,"This is the description",
                null,1L,1L);

        Set<ConstraintViolation<DishRequestDto>> violations = validator.validate(dishDto);

        assertEquals(2, violations.size());
    }
    @Test
    void When_ImageUrlIsEmpty_Expect_OneViolation(){
        DishRequestDto dishDto = new DishRequestDto("Foot Long",30000,"This is the description",
                "",1L,1L);

        Set<ConstraintViolation<DishRequestDto>> violations = validator.validate(dishDto);

        assertEquals(1, violations.size());
    }

    @Test
    void When_ImageUrlIsNotValid_Expect_OneViolation(){
        DishRequestDto dishDto = new DishRequestDto("Foot Long",30000,"This is the description",
                "htp:image.png",1L,1L);

        Set<ConstraintViolation<DishRequestDto>> violations = validator.validate(dishDto);

        assertEquals(1, violations.size());
    }
    @Test
    void When_CategoryIdIsNull_Expect_OneViolation(){
        DishRequestDto dishDto = new DishRequestDto("Foot Long",30000,"This is the description",
                "https:image.png",null,1L);

        Set<ConstraintViolation<DishRequestDto>> violations = validator.validate(dishDto);

        assertEquals(1, violations.size());
    }

    @Test
    void When_RestaurantIdIsNull_Expect_OneViolation(){
        DishRequestDto dishDto = new DishRequestDto("Foot Long",30000,"This is the description",
                "https:image.png",1L,null);

        Set<ConstraintViolation<DishRequestDto>> violations = validator.validate(dishDto);

        assertEquals(1, violations.size());
    }
}
