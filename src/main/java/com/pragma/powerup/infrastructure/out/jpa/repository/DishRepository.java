package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.domain.Page;
public interface DishRepository extends JpaRepository<DishEntity, Long> {

    List<DishEntity> findByRestaurant(Long idRestaurant);

    Page<DishEntity> findByRestaurantIdAndCategoryId(Long idRestaurant, Long idCategory, Pageable pageable);
}
