package ru.lotnyk.parking.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.lotnyk.parking.entity.CarEntity;
import ru.lotnyk.parking.entity.PlaceParkingEntity;
import ru.lotnyk.parking.exception.BadRequestException;
import ru.lotnyk.parking.exception.NotFoundException;
import ru.lotnyk.parking.repository.CarRepository;
import ru.lotnyk.parking.repository.ParkingRepository;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
@Transactional
public class ControllerHelper {

    CarRepository carRepository;

    ParkingRepository parkingRepository;

    public CarEntity getCarEntityByIdOrThrowException(Long carId) {
        return carRepository
                .findById(carId)
                .orElseThrow(() ->
                        new BadRequestException(
                                String.format("This Car Id: %s Is Not Found.", carId))
                        );
    }

    public CarEntity getCarEntityByNumberOrThrowException(String carNumber) {
        return carRepository
                .findByNumber(carNumber)
                .orElseThrow(() ->
                        new BadRequestException(
                                String.format("This Car Number: %s Is Not Found.", carNumber))
                );
    }


    public PlaceParkingEntity getParkingPlaceEntityByIdOrThrowException(Long placeID) {
        return parkingRepository
                .findById(placeID)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Parking Place This Id: %s Is Not Found.", placeID)
                ));
    }
}
