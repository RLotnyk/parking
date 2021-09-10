package ru.lotnyk.parking.utils;

import org.springframework.stereotype.Component;
import ru.lotnyk.parking.dto.CarDto;
import ru.lotnyk.parking.entities.CarEntity;

@Component
public class CarDtoFactory {

    public CarDto makeCarDto(CarEntity entity) {
        return CarDto.builder()
                .id(entity.getId())
                .fullName(entity.getOwnerName())
                .mark(entity.getMarkAuto())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
