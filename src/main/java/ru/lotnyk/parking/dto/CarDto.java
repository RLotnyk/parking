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
public class CarDto {

    Long id;

    @JsonProperty("full_name")
    String fullName;

    @JsonProperty("car_mark")
    String mark;

    @JsonProperty("created_at")
    Instant createdAt;


}
