package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.DishRequestDto;
import com.pragma.powerup.application.dto.response.DishPageResponseDto;
import com.pragma.powerup.application.handler.IDishHandler;
import com.pragma.powerup.application.mapper.IDishRequestMapper;
import com.pragma.powerup.domain.api.IDishServicePort;
import com.pragma.powerup.domain.model.DishModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DishHandler implements IDishHandler {
    private final IDishServicePort dishServicePort;
    private final IDishRequestMapper dishRequestMapper;
    @Override
    public void saveDish(DishRequestDto dishRequestDto, String token) {
        DishModel dishModel = dishRequestMapper.toDish(dishRequestDto);
        dishServicePort.saveDish(dishModel, token);
    }


    @Override
    public void updateDish(Long id, Map<String, Object> fields, String token) {
        dishServicePort.updateDish(id, fields, token);
    }

    @Override
    public void updateActiveField(Long id, Map<String, Object> fields, String token) {
        dishServicePort.updateActiveField(id, fields, token);
    }

    @Override
    public List<DishPageResponseDto> showMenu(Long restaurantId, Long categoryId, Integer number, String token) {
        List<DishModel> dishModelList = dishServicePort.showMenu(restaurantId,categoryId,number, token);

        return dishModelList
                .stream()
                .map(dishRequestMapper::toPageDto).collect(Collectors.toList());
    }


}
