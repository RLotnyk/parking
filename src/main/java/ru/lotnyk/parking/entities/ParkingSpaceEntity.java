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
    @Builder.Default
    Payment payment = Payment.SUCCESS;

    @Builder.Default
    Instant createdAt = Instant.now();

    @OneToOne(cascade = CascadeType.ALL)
    CarEntity car;

}
