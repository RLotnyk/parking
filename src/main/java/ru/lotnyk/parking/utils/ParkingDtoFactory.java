package ru.lotnyk.parking.utils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.lotnyk.parking.dto.ParkingDto;
import ru.lotnyk.parking.entity.PlaceParkingEntity;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class ParkingDtoFactory {

    CarDtoFactory carDtoFactory;

    public ParkingDto makeParkingSpaceDto(PlaceParkingEntity entity) {
        return ParkingDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .placeStatus(entity.getPlace())
                .paymentStatus(entity.getPayment())
                .createdAt(entity.getCreatedAt())
                .car(carDtoFactory.makeCarDto(entity.getCar()))
                .build();
    }
}
