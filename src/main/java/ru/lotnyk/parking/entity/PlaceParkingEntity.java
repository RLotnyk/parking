package ru.lotnyk.parking.entity;

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
@Table(name = "parking")
public class PlaceParkingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String name;

    @Enumerated(EnumType.STRING)
    PaymentStatus payment;

    @Enumerated(EnumType.STRING)
    PlaceStatus place;

    @Builder.Default
    Instant createdAt = Instant.now();

    @OneToOne
    CarEntity car;

}
