package ru.lotnyk.parking.utils;

import org.springframework.stereotype.Component;
import ru.lotnyk.parking.dto.CarDto;
import ru.lotnyk.parking.entity.CarEntity;

@Component
public class CarDtoFactory {

    public CarDto makeCarDto(CarEntity entity) {
        return CarDto.builder()
                .id(entity.getId())
                .owner(entity.getOwner())
                .number(entity.getNumber())
                .build();
    }
}
