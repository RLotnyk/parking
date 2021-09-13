package ru.lotnyk.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lotnyk.parking.entity.CarEntity;

import java.util.Optional;

public interface CarRepository extends JpaRepository<CarEntity, Long> {

    Optional<CarEntity> findByNumber(String number);
}
