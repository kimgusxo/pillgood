package com.kit.pillgood.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TakePillCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAKE_PILL_CHECK_INDEX")
    private Long takePillCheckIndex;

    @ManyToOne
    @JoinColumn(name="TAKE_PILL_INDEX")
    private TakePill takePill;

    @Column(name = "TAKE_DATE")
    private LocalDate takeDate;

    @Column(name = "TAKE_PILL_TIME")
    private Integer takePillTime;

    @Column(name = "TAKE_CHECK")
    private Boolean takeCheck;
}
