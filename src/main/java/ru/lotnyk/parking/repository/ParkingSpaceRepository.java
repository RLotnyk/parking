package ru.lotnyk.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lotnyk.parking.entities.ParkingSpaceEntity;

import java.util.Optional;

public interface ParkingSpaceRepository extends JpaRepository<ParkingSpaceEntity, Long> {

    Optional<ParkingSpaceEntity> findByName(String name);
}
