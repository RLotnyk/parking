package ru.lotnyk.parking.entities;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.Instant;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "parking_space")
public class ParkingSpaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String name;

    @Enumerated(EnumType.STRING)
    Payment payment;

    @Enumerated(EnumType.STRING)
    Place placeStatus;

    @Builder.Default
    Instant createdAt = Instant.now();

    @OneToOne(cascade = CascadeType.ALL)
    CarEntity car;

}
