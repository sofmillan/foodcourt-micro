package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import com.pragma.powerup.domain.exception.DataNotFoundException;
import com.pragma.powerup.domain.exception.NotModifiableException;
import com.pragma.powerup.infrastructure.out.jpa.entity.CategoryEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.CategoryRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.DishRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {
    private final RestaurantRepository restaurantRepository;
    private final CategoryRepository categoryRepository;
    private final DishRepository dishRepository;
    private  final IDishEntityMapper dishEntityMapper;

    @Override
    public DishModel saveDish(DishModel dishModel) {
        Optional<RestaurantEntity> optionalRestaurant = restaurantRepository.findById(dishModel.getRestaurantId());
        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(dishModel.getCategoryId());

        if(optionalCategory.isEmpty() || optionalRestaurant.isEmpty()){
            throw new DataNotFoundException();
        }
        DishEntity dishEntity = dishEntityMapper.toDishEntity(dishModel);
        dishEntity.setActive(true);
        dishEntity.setRestaurant(optionalRestaurant.get());
        dishEntity.setCategory(optionalCategory.get());

        dishRepository.save(dishEntity);
        return dishEntityMapper.toModel(dishEntity);
    }

    @Override
    public void updateDish(Long id, Map<String, Object> fields) {
        Optional<DishEntity> optionalDish = dishRepository.findById(id);

        if(optionalDish.isEmpty()){
            throw new DataNotFoundException();
        }
        fields.forEach((key, value)->{
            if(!(key.equals("price") || key.equals("description"))){
                throw new NotModifiableException();
            }
            Field field = ReflectionUtils.findField(DishEntity.class,key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, optionalDish.get(),value);
        });
        dishRepository.save(optionalDish.get());
    }

    @Override
    public void updateActiveField(Long id, Map<String, Object> fields) {
        DishEntity dish = dishRepository.findById(id)
                .orElseThrow(DataNotFoundException::new);

        fields.forEach((key, value)->{
            if(!(key.equals("active"))){
                throw new NotModifiableException();
            }
            Field field = ReflectionUtils.findField(DishEntity.class,key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, dish,value);
        });

        dishRepository.save(dish);
    }


    @Override
    public List<DishModel> showMenu(Long restaurantId, Long categoryId, Integer elements) {
        Pageable pageable = PageRequest.of(0,elements,Sort.by(Sort.Direction.ASC, "name"));

        List<DishEntity> dishEntityList = dishRepository.findByRestaurantIdAndCategoryId(restaurantId,categoryId,pageable).getContent();

        return dishEntityList.stream().map(dishEntityMapper::toModel).collect(Collectors.toList());
    }

    @Override
    public Long findDishRestaurant(Long idRestaurant) {
        RestaurantEntity restaurant = restaurantRepository.findById(idRestaurant).orElseThrow(DataNotFoundException::new);
        return restaurant.getOwnerId();
    }


    @Override
    public Long findOwnerByDish(Long dishId){
        DishEntity dish = dishRepository.findById(dishId).orElseThrow(DataNotFoundException::new);
        RestaurantEntity restaurant = restaurantRepository.findById(dish.getRestaurant().getId()).orElseThrow(DataNotFoundException::new);

        return restaurant.getOwnerId();
    }


}
