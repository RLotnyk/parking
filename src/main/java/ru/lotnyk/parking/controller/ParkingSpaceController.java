package ru.lotnyk.parking.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lotnyk.parking.dto.ParkingSpaceDto;
import ru.lotnyk.parking.entities.CarEntity;
import ru.lotnyk.parking.entities.Place;
import ru.lotnyk.parking.entities.ParkingSpaceEntity;
import ru.lotnyk.parking.entities.Payment;
import ru.lotnyk.parking.exceptions.BadRequestException;
import ru.lotnyk.parking.exceptions.NotFoundException;
import ru.lotnyk.parking.repository.CarRepository;
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

    CarRepository carRepository;

    ParkingSpaceDtoFactory parkingSpaceDtoFactory;


    public static final String GET_ALL_PARKING_SPACE = "/api/parking/";
    public static final String CREATE_PARKING_SPACE = "/api/parking/space/";
    public static final String DELETE_PARKING_SPACE = "/api/parking/space/{name}/";
    public static final String GET_PARKING_SPACE = "/api/parking/space/{name}/";
    public static final String EDIT_PARKING_SPACE = "/api/parking/space/";


    @GetMapping(GET_ALL_PARKING_SPACE)
    public List<ParkingSpaceDto> getAllSpace() {
        List<ParkingSpaceEntity> parkingSpaceEntityList = parkingSpaceRepository.findAll();
        return parkingSpaceEntityList.stream()
                .map(parkingSpaceDtoFactory::makeParkingSpaceDto)
                .collect(Collectors.toList());
    }

    @GetMapping(GET_PARKING_SPACE)
    public ParkingSpaceDto getSpace(@PathVariable(name = "name") String spaceName) {
        ParkingSpaceEntity parkingSpaceEntity = this.getParkingSpaceEntityOrThrowException(spaceName);
        return parkingSpaceDtoFactory.makeParkingSpaceDto(parkingSpaceEntity);
    }

    @PostMapping(CREATE_PARKING_SPACE)
    public ParkingSpaceDto createOrUpdateSpace(
            @RequestParam(value = "space_name") String spaceName,
            @RequestParam(value = "car_id") Long carId,
            @RequestParam(value = "payment", required = false) Payment payment,
            @RequestParam(value = "place", required = false) Place placeStatus
    ) {
        if (spaceName.trim().isEmpty()) {
            throw new BadRequestException("Space Name Can't Be Empty.");
        }
        Optional<CarEntity> optionalCarEntity = carRepository.findById(carId);
        CarEntity carEntity = optionalCarEntity.orElseThrow(
                ()-> new NotFoundException(String.format("Car ID: %s Not Found!", carId)));

        ParkingSpaceEntity parkingSpace = ParkingSpaceEntity.builder()
                .name(spaceName)
                .car(carEntity)
                .payment(payment)
                .placeStatus(placeStatus)
                .build();


        ParkingSpaceEntity parkingSpaceEntity = parkingSpaceRepository.saveAndFlush(parkingSpace);

        return parkingSpaceDtoFactory.makeParkingSpaceDto(parkingSpaceEntity);
    }

    @PutMapping(EDIT_PARKING_SPACE)
    public ParkingSpaceDto editSpace(
            @RequestParam(value = "space_id") Long spaceId,
            @RequestParam(value = "car_id", required = false) Long carId,
            @RequestParam(value = "payment", required = false) Payment payment,
            @RequestParam(value = "place", required = false) Place place) {

        ParkingSpaceEntity spaceEntity = Optional.of(
                parkingSpaceRepository.getById(spaceId)).orElseThrow(() -> new NotFoundException(
                String.format("Space is ID: %s Not Found", carId)));

        CarEntity carEntity = Optional.of(
                carRepository.getById(carId)).orElseThrow(() -> new NotFoundException(
                String.format("Car is ID: %s Not Found", carId)));

        parkingSpaceRepository.findAll().forEach(
                space-> {
                    if (space.getCar().equals(carEntity)) {
                        throw new BadRequestException(
                                String.format("This Сar: %s Is Already Registered\n" +
                                        "Owner:%s\nBrand:%s", carEntity.getOwnerName(), carEntity.getMarkAuto()));
                    }
                }
        );

        spaceEntity.setCar(carEntity);
        spaceEntity.setPlaceStatus(place);
        spaceEntity.setPayment(payment);

        ParkingSpaceEntity space = parkingSpaceRepository.saveAndFlush(spaceEntity);

        return parkingSpaceDtoFactory.makeParkingSpaceDto(space);
    }

    @DeleteMapping(DELETE_PARKING_SPACE)
    public ResponseEntity<Boolean> deleteSpace(
            @PathVariable(name = "name") String name) {
        ParkingSpaceEntity parkingSpaceEntity = this.getParkingSpaceEntityOrThrowException(name);
        parkingSpaceRepository.delete(parkingSpaceEntity);
        return ResponseEntity.of(Optional.of(true));
    }


    private ParkingSpaceEntity getParkingSpaceEntityOrThrowException(String name) {
        return parkingSpaceRepository.findByName(name)
                .orElseThrow(()-> new NotFoundException(String.format("Parking Space %s Not Found.", name)));
    }
}
