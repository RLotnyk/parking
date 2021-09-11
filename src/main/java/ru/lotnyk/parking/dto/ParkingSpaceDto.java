package ru.lotnyk.parking.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.lotnyk.parking.entities.Place;
import ru.lotnyk.parking.entities.Payment;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParkingSpaceDto {

    @NonNull
    Long id;

    @NonNull
    String name;

    @JsonProperty("created_at")
    Instant createdAt;

    @JsonProperty("payment_status")
    Payment paymentStatus;

    @JsonProperty("place_status")
    Place placeStatus;

    CarDto car;

}
