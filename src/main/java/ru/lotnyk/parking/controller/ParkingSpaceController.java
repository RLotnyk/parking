package ru.lotnyk.parking.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lotnyk.parking.dto.ParkingSpaceDto;
import ru.lotnyk.parking.entities.CarEntity;
import ru.lotnyk.parking.entities.ParkingSpaceEntity;
import ru.lotnyk.parking.entities.Payment;
import ru.lotnyk.parking.exceptions.BadRequestException;
import ru.lotnyk.parking.exceptions.NotFoundExceptions;
import ru.lotnyk.parking.repository.ParkingSpaceRepository;
import ru.lotnyk.parking.utils.ParkingSpaceDtoFactory;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RestController
public class ParkingSpaceController {


    ParkingSpaceRepository parkingSpaceRepository;

    ParkingSpaceDtoFactory parkingSpaceDtoFactory;


    public static final String GET_ALL_PARKING_SPACE = "/api/parking/parking_space";
    public static final String CREATE_PARKING_SPACE = "/api/parking/create_parking_space/";
    public static final String DELETE_PARKING_SPACE = "/api/parking/parking_space/{parking_space_name}/";
    public static final String GET_PARKING_SPACE = "/api/parking/{parking_space_name}";

    @GetMapping(GET_ALL_PARKING_SPACE)
    public List<ParkingSpaceDto> getAllParkingSpace() {
        List<ParkingSpaceEntity> parkingSpaceEntityList = parkingSpaceRepository.findAll();
        return parkingSpaceEntityList.stream()
                .map(parkingSpaceDtoFactory::makeParkingSpaceDto)
                .collect(Collectors.toList());
    }

    @GetMapping(GET_PARKING_SPACE)
    public ParkingSpaceDto getParkingSpace(@PathVariable(name = "parking_space_name") String parking_space_name) {
        ParkingSpaceEntity parkingSpaceEntity = this.getParkingSpaceEntityOrThrowException(parking_space_name);
        return parkingSpaceDtoFactory.makeParkingSpaceDto(parkingSpaceEntity);
    }

    @PostMapping(CREATE_PARKING_SPACE)
    public ParkingSpaceDto createParkingSpace(
            @RequestParam(value = "parking_space_name") String parkingSpaceName,
            @RequestParam(value = "full_name") String fullName,
            @RequestParam(value = "mark") String mark) {

        if (parkingSpaceName.trim().isEmpty()) {
            throw new BadRequestException("Parking Space Name Can't Be Empty.");
        }


        CarEntity carEntity = CarEntity.builder()
                        .ownerName(fullName)
                        .markAuto(mark)
                        .build();

        ParkingSpaceEntity parkingSpaceEntity = ParkingSpaceEntity.builder()
                .name(parkingSpaceName)
                .payment(Payment.SUCCESS)
                .car(carEntity)
                .build();

        final ParkingSpaceEntity parkingSpace = parkingSpaceRepository.saveAndFlush(parkingSpaceEntity);
        return parkingSpaceDtoFactory.makeParkingSpaceDto(parkingSpace);

    }

    @DeleteMapping(DELETE_PARKING_SPACE)
    public ResponseEntity<Boolean> deleteParkingSpace(
            @PathVariable(name = "parking_space_name") String parking_space_name) {
        ParkingSpaceEntity parkingSpaceEntity = this.getParkingSpaceEntityOrThrowException(parking_space_name);
        parkingSpaceRepository.delete(parkingSpaceEntity);
        return ResponseEntity.of(Optional.of(true));
    }

    private ParkingSpaceEntity getParkingSpaceEntityOrThrowException(String name) {
        return parkingSpaceRepository.findByName(name)
                .orElseThrow(()-> new NotFoundExceptions(String.format("Parking Space %s Not Found.", name)));
    }
}
