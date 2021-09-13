package ru.lotnyk.parking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarDto {

    Long id;

    @JsonProperty("car_owner")
    String owner;

    @NonNull
    @JsonProperty("car_number")
    String number;

}
