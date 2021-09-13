package ru.lotnyk.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lotnyk.parking.entity.PlaceParkingEntity;

public interface ParkingRepository extends JpaRepository<PlaceParkingEntity, Long> {

}
