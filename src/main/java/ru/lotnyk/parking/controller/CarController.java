package ru.lotnyk.parking.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lotnyk.parking.dto.CarDto;
import ru.lotnyk.parking.entity.CarEntity;
import ru.lotnyk.parking.exception.BadRequestException;
import ru.lotnyk.parking.repository.CarRepository;
import ru.lotnyk.parking.utils.CarDtoFactory;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RestController
public class CarController {

    CarDtoFactory carDtoFactory;

    CarRepository carRepository;

    ControllerHelper controllerHelper;

    public static final String CREATE_CAR = "/api/parking/car";
    public static final String GET_ALL_CARS = "/api/parking/car";
    public static final String DELETE_CAR = "/api/parking/car/{car_id}";



    @PostMapping(value = CREATE_CAR)
    public CarDto createCar(
            @RequestParam(value = "owner", required = false) String owner,
            @RequestParam(value="number") String number
    ) {
        if (number.trim().isEmpty()) {
            throw new BadRequestException("Car Number Can't be empty.");
        }
        CarEntity car = carRepository.saveAndFlush(CarEntity.builder()
                .owner(owner)
                .number(number)
                .build()
        );

        return carDtoFactory.makeCarDto(car);
    }

    @GetMapping(GET_ALL_CARS)
    public List<CarDto> getAllCars() {
        List<CarEntity> carEntityList = carRepository.findAll();
        return carEntityList.stream()
                .map(carDtoFactory::makeCarDto).collect(Collectors.toList());
    }

    @DeleteMapping(DELETE_CAR)
    public ResponseEntity<Boolean> deleteCar(@PathVariable("car_id") Long carId) {
        CarEntity carEntity = controllerHelper.getCarEntityByIdOrThrowException(carId);
        carRepository.delete(carEntity);
        return ResponseEntity.ok(true);
    }
}
