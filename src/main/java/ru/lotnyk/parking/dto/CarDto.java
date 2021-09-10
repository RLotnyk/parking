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

    @NonNull
    Long id;

    @NonNull
    @JsonProperty("full_name")
    String fullName;

    @NonNull
    String mark;

    @JsonProperty("created_at")
    Instant createdAt;


}
