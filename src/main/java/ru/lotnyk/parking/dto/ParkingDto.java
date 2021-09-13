package ru.lotnyk.parking.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.lotnyk.parking.entity.PlaceStatus;
import ru.lotnyk.parking.entity.PaymentStatus;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParkingDto {

    @NonNull
    Long id;

    @NonNull
    String name;

    @JsonProperty("created_at")
    Instant createdAt;

    @JsonProperty("payment_status")
    PaymentStatus paymentStatus;

    @JsonProperty("place_status")
    PlaceStatus placeStatus;

    CarDto car;

}
