package com.kit.pillgood.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class TakePillCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAKE_PILL_CHECK_INDEX")
    private Long takePillCheckIndex;

    @ManyToOne
    @JoinColumn(name="TAKE_PILL_INDEX")
    private TakePill takePill;

    @Column(name = "TAKE_DATE")
    @NonNull
    private LocalDate takeDate;

    @Column(name = "TAKE_PILL_TIME")
    @NonNull
    private Integer takePillTime;

    @Column(name = "TAKE_CHECK")
    @NonNull
    private Boolean takeCheck;
}
