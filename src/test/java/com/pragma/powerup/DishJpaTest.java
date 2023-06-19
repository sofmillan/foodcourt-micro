package com.pragma.powerup;

import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.exception.DataNotFoundException;
import com.pragma.powerup.domain.exception.NotModifiableException;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.adapter.DishJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.entity.CategoryEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.CategoryRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.DishRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DishJpaTest {

    private RestaurantRepository restaurantRepository;

    private CategoryRepository categoryRepository;

    private DishRepository dishRepository;

    private IDishEntityMapper dishEntityMapper;

    private IDishPersistencePort dishPersistence;
    private DishModel dishModel;

    Long dishIdSearch;


    @BeforeEach
    void setUp(){
        restaurantRepository = mock(RestaurantRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        dishRepository = mock(DishRepository.class);
        dishEntityMapper = mock(IDishEntityMapper.class);
        dishPersistence = new DishJpaAdapter(restaurantRepository, categoryRepository, dishRepository, dishEntityMapper);
        dishModel = new DishModel();
        dishModel.setActive(true);
        dishModel.setRestaurantId(1L);
        dishModel.setCategoryId(2L);
        dishModel.setPrice(10000);
        dishModel.setName("Tiramisu");
        dishModel.setImageUrl("http//:image.png");
        dishIdSearch = 1L;
    }

    @Test
    void Should_ThrowException_When_RestaurantIdNotExist(){

        when(restaurantRepository.findById(dishModel.getRestaurantId())).thenReturn(Optional.empty());
        when(categoryRepository.findById(dishModel.getCategoryId())).thenReturn(Optional.of(new CategoryEntity()));

        assertThrows(DataNotFoundException.class,
                ()-> dishPersistence.saveDish(dishModel));
    }

    @Test
    void Should_ThrowException_When_CategoryIdNotExist(){
        when(restaurantRepository.findById(dishModel.getRestaurantId())).thenReturn(Optional.of(new RestaurantEntity()));
        when(categoryRepository.findById(dishModel.getCategoryId())).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class,
                ()-> dishPersistence.saveDish(dishModel));

    }

    @Test
    void Should_UpdateActiveFields(){
        Map<String, Object> fields = new HashMap<>();
        fields.put("active",false);

        when(dishRepository.findById(dishIdSearch)).thenReturn(Optional.of(new DishEntity()));

        dishPersistence.updateActiveField(dishIdSearch,fields);

        verify(dishRepository).save(any());
    }

    @Test
    void Should_UpdatePrice(){

        Map<String, Object> fields = new HashMap<>();
        fields.put("price",20000);

        when(dishRepository.findById(dishIdSearch)).thenReturn(Optional.of(new DishEntity()));
        dishPersistence.updateDish(dishIdSearch,fields);
        verify(dishRepository).save(any());
    }

    @Test
    void Should_UpdateDescription(){

        Map<String, Object> fields = new HashMap<>();
        fields.put("description","This is the new description");

        when(dishRepository.findById(dishIdSearch)).thenReturn(Optional.of(new DishEntity()));
        dishPersistence.updateDish(dishIdSearch,fields);

        verify(dishRepository).save(any());
    }

    @Test
    void Should_ThrowException_When_DishIdNotFound(){

        Map<String, Object> fields = new HashMap<>();
        fields.put("price",20000);

        when(dishRepository.findById(dishIdSearch)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class,
                ()-> dishPersistence.updateDish(dishIdSearch, fields));
    }


    @Test
    void Should_ThrowException_When_UpdateNameByUpdateDish(){
        Map<String, Object> fields = new HashMap<>();
        fields.put("name","Pasta");

        when(dishRepository.findById(dishIdSearch)).thenReturn(Optional.of(new DishEntity()));

        assertThrows(NotModifiableException.class,
                ()-> dishPersistence.updateDish(dishIdSearch, fields));
    }

    @Test
    void Should_ThrowException_When_UpdateCategoryIdByUpdateDish(){
        Map<String, Object> fields = new HashMap<>();
        fields.put("categoryId",2L);

        when(dishRepository.findById(dishIdSearch)).thenReturn(Optional.of(new DishEntity()));

        assertThrows(NotModifiableException.class,
                ()-> dishPersistence.updateDish(dishIdSearch, fields));
    }


    @Test
    void Should_ThrowException_When_UpdateRestaurantIdByUpdateDish(){
        Map<String, Object> fields = new HashMap<>();
        fields.put("restaurantId",2L);

        when(dishRepository.findById(dishIdSearch)).thenReturn(Optional.of(new DishEntity()));

        assertThrows(NotModifiableException.class,
                ()-> dishPersistence.updateDish(dishIdSearch, fields));
    }

    @Test
    void Should_ThrowException_When_UpdateActiveByUpdateDish(){
        Map<String, Object> fields = new HashMap<>();
        fields.put("active",false);

        when(dishRepository.findById(dishIdSearch)).thenReturn(Optional.of(new DishEntity()));

        assertThrows(NotModifiableException.class,
                ()-> dishPersistence.updateDish(dishIdSearch, fields));
    }

    @Test
    void Should_ThrowException_When_UpdatePriceByUpdateActiveField(){
        Map<String, Object> fields = new HashMap<>();
        fields.put("price",20000);

        when(dishRepository.findById(dishIdSearch)).thenReturn(Optional.of(new DishEntity()));

        assertThrows(NotModifiableException.class,
                ()-> dishPersistence.updateActiveField(dishIdSearch, fields));
    }

    @Test
    void Should_ThrowException_When_UpdateDescriptionByUpdateActiveField(){
        Map<String, Object> fields = new HashMap<>();
        fields.put("description","This is a description");

        when(dishRepository.findById(dishIdSearch)).thenReturn(Optional.of(new DishEntity()));

        assertThrows(NotModifiableException.class,
                ()-> dishPersistence.updateActiveField(dishIdSearch, fields));
    }

    @Test
    void Should_ThrowException_When_UpdateCategoryIdByUpdateActiveField(){

        Map<String, Object> fields = new HashMap<>();
        fields.put("categoryId",2L);

        when(dishRepository.findById(dishIdSearch)).thenReturn(Optional.of(new DishEntity()));

        assertThrows(NotModifiableException.class,
                ()-> dishPersistence.updateActiveField(dishIdSearch, fields));
    }

    @Test
    void Should_ThrowException_When_UpdateNameByUpdateActiveField(){
        Map<String, Object> fields = new HashMap<>();
        fields.put("name","Pasta");

        when(dishRepository.findById(dishIdSearch)).thenReturn(Optional.of(new DishEntity()));

        assertThrows(NotModifiableException.class,
                ()-> dishPersistence.updateActiveField(dishIdSearch, fields));
    }
    @Test
    void Should_ThrowException_When_UpdateRestaurantIdByUpdateActiveField(){
        Map<String, Object> fields = new HashMap<>();
        fields.put("restaurantId",2L);

        when(dishRepository.findById(dishIdSearch)).thenReturn(Optional.of(new DishEntity()));

        assertThrows(NotModifiableException.class,
                ()-> dishPersistence.updateActiveField(dishIdSearch, fields));
    }


}
