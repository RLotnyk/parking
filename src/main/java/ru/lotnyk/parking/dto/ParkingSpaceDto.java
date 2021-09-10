package ru.lotnyk.parking.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

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

    @NonNull
    CarDto car;

}
