package ru.lotnyk.parking.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lotnyk.parking.dto.ParkingDto;
import ru.lotnyk.parking.entity.CarEntity;
import ru.lotnyk.parking.entity.PlaceStatus;
import ru.lotnyk.parking.entity.PlaceParkingEntity;
import ru.lotnyk.parking.entity.PaymentStatus;
import ru.lotnyk.parking.exception.BadRequestException;
import ru.lotnyk.parking.repository.ParkingRepository;
import ru.lotnyk.parking.utils.ParkingDtoFactory;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RestController
public class ParkingController {

    ParkingRepository parkingRepository;

    ControllerHelper controllerHelper;

    ParkingDtoFactory parkingDtoFactory;


    public static final String GET_ALL_PARKING_PLACES = "/api/parking/place";
    public static final String CREATE_PARKING_PLACE = "/api/parking/place";
    public static final String DELETE_PARKING_SPACE = "/api/parking/place/{place_id}";
    public static final String UPDATE_PARKING_PLACE = "/api/parking/place";

    @GetMapping(GET_ALL_PARKING_PLACES)
    public List<ParkingDto> getAllParkingPlaces() {
        List<PlaceParkingEntity> parkingEntityList = parkingRepository.findAll();
        return parkingEntityList.stream()
                .map(parkingDtoFactory::makeParkingSpaceDto)
                .collect(Collectors.toList());
    }


    @PostMapping(CREATE_PARKING_PLACE)
    public ParkingDto createParkingPlace(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "car_number") String carNumber,
            @RequestParam(value = "place_status", required = false) PlaceStatus placeStatus,
            @RequestParam(value = "payment_status", required = false) PaymentStatus paymentStatus
    ) {
        if (name.trim().isEmpty()) {
            throw new BadRequestException("Name can't be empty.");
        }

        CarEntity car = controllerHelper.getCarEntityByNumberOrThrowException(carNumber);

        PlaceParkingEntity place = PlaceParkingEntity.builder()
                .name(name)
                .car(car)
                .place(placeStatus)
                .payment(paymentStatus)
                .build();

        parkingRepository.saveAndFlush(place);
        return parkingDtoFactory.makeParkingSpaceDto(place);
    }

    @PutMapping(UPDATE_PARKING_PLACE)
    public ParkingDto updateParkingPlace(
            @RequestParam(value = "place_id") Long placeId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "car_number") String carNumber,
            @RequestParam(value = "payment_status", required = false) PaymentStatus paymentStatus,
            @RequestParam(value = "place_status", required = false) PlaceStatus placeStatus
    ) {

        PlaceParkingEntity parking = controllerHelper.getParkingPlaceEntityByIdOrThrowException(placeId);

        CarEntity car = controllerHelper.getCarEntityByNumberOrThrowException(carNumber);

        PlaceParkingEntity updatePlace = PlaceParkingEntity.builder()
                        .id(parking.getId())
                        .name(name)
                        .car(car)
                        .payment(paymentStatus)
                        .place(placeStatus)
                        .build();

        PlaceParkingEntity update = parkingRepository.saveAndFlush(updatePlace);
        return parkingDtoFactory.makeParkingSpaceDto(update);
    }

    @DeleteMapping(DELETE_PARKING_SPACE)
    public ResponseEntity<Boolean> deleteParkingPlace(
            @PathVariable(name = "place_id") Long placeId) {
        PlaceParkingEntity parkingEntity = controllerHelper.getParkingPlaceEntityByIdOrThrowException(placeId);
       parkingRepository.delete(parkingEntity);
       return ResponseEntity.ok(true);
    }
}
