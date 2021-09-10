package ru.lotnyk.parking.utils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.lotnyk.parking.dto.ParkingSpaceDto;
import ru.lotnyk.parking.entities.ParkingSpaceEntity;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class ParkingSpaceDtoFactory {

    CarDtoFactory carDtoFactory;

    public ParkingSpaceDto makeParkingSpaceDto(ParkingSpaceEntity entity) {
        return ParkingSpaceDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .car(carDtoFactory.makeCarDto(entity.getCar()))
                .build();
    }
}
