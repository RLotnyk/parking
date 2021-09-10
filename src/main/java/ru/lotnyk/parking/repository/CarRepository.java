package ru.lotnyk.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lotnyk.parking.entities.CarEntity;

public interface CarRepository extends JpaRepository<CarEntity, Long> {
}
